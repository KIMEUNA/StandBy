package Cafe_s.net;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import Cafe_s.Frame.CafeServer;
import Cafe_s.Frame.ChatPanel;
import Cafe_s.controller.Controller;
import Cafe_s.model.service.StockVO;
import service.ChatVO;
import service.CoffeeVO;
import service.MemberVO;
import service.MySbVO;
import service.OrderVO;

public class S_StandBy {

	private static S_StandBy me;

	ServerSocket server;
	
	HashMap<String, ChatPanel> map = new HashMap<>();
	
	public CafeReceiver rr;
	
	CafeServer cs;

	public static S_StandBy getInstance(CafeServer cs){
		if(me == null)
			me = new S_StandBy(cs);
		return me;
	}

	private S_StandBy(CafeServer cs) {
		// TODO Auto-generated constructor stub
		
		this.cs = cs;
		
		try {
			server = new ServerSocket(7777);
			System.out.println("서버준비 완료");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new ClientAccept().start();
	}

	class ClientAccept extends Thread {

		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				while (true) {
					Socket client = server.accept();
					
					rr = new CafeReceiver(client);
					rr.start();
					
					System.out.println(client.getInetAddress()+" 접속 ~~");
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public class CafeReceiver extends Thread {
		
		ObjectOutputStream oos;
		ObjectInputStream ois;
		OutputStream os;
	    FileInputStream fis;

		Controller cc = new Controller();
		String key;
		
		Socket client;
		
		public CafeReceiver(Socket client) {
			// TODO Auto-generated constructor stub
			try {
				this.client = client;
				os = client.getOutputStream();
				oos = new ObjectOutputStream(client.getOutputStream());
				ois = new ObjectInputStream(client.getInputStream());
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public void CafeSender(String key, Object obj) {
			// TODO Auto-generated constructor stub
			try {
				
				System.out.println("key: "+key);
				
				if(obj instanceof MemberVO)
					System.out.println(((MemberVO)obj).getId());
				
				oos.writeUTF(key);
				oos.writeObject(obj);
				oos.flush();
				oos.reset();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (ois != null) {
				try {
					key = null;
					
					String str = ois.readUTF();
					Object obj = ois.readObject();
					
					System.out.println("str: " + str);
					
					if (obj instanceof MemberVO) {
						MemberVO vo = (MemberVO) obj;
						
						if (str.equals("회원가입"))
							cc.m_insert(vo);
						if(str.equals("회원탈퇴"))
							cc.m_delete(vo);
						if(str.equals("정보수정")){
							cc.m_update(vo);
							CafeSender("", cc.m_logChk(vo));
						}
						if (str.equals("로그인")) {
							vo = cc.m_logChk(vo);
							if (vo != null){
								key = "로그인";
								map.put(vo.getId(), new ChatPanel(me, cs.getEvo()));
								map.get(vo.getId()).setVisible(false);
								cs.chat.removeAll();
								Iterator<String> it = map.keySet().iterator();
								while(it.hasNext()){
									String buf = it.next();
									cs.chat.addItem(buf);
									System.out.println("buf: "+buf);
								}
								cs.lb_chat.setText("접속인원: "+map.size()+"명");
							}
							CafeSender(key, vo);
						}
						if (str.equals("아이디찾기"))
							CafeSender("찾앗당", cc.idfind(vo));
						
						if(str.equals("비밀번호"))
							CafeSender("찾앗당", cc.pwfind(vo));
						
						if (str.equals("중복확인")) {
							if(cc.idChk(vo.getId())){
								key = "중복확인";
							}
							CafeSender(key, vo);
						}
						if (str.equals("결제내역")){
							ArrayList<OrderVO> list = cc.order_select(vo);
							CafeSender("결제내역", list);
						}
						if (str.equals("포인트"))
							cc.m_pointUpdate(vo);
						if(str.equals("등업"))
							cc.grade_Update(vo);
						if (str.equals("나만의 스탠바이")) {
							CafeSender("나만의 스탠바이", cc.MySb_select(vo));
						}
					}
					
					if (obj instanceof OrderVO) {
						OrderVO vo = (OrderVO) obj;
						if(str.equals("수령"))
							cc.order_update(vo, 2);
						if(str.equals("취소")){
							cc.order_delete(vo);
							cs.getOrder().dataInit();
						}
						if(str.equals("주문")){
							if(vo.getCate().equals("상품")){
								StockVO svo = new StockVO();
								svo.setItem(vo.getName());
								svo.setIn(0);
								svo.setOut(vo.getEa());
								svo.setStat("출고");
								svo.setRegDate(new Date());
								cc.stock_insert(svo);
							}
							cc.o_insert(vo);
							cs.getOrder().dataInit();
						}
					}
					
					if (obj instanceof ChatVO) {
						ChatVO vo = (ChatVO) obj;
						// cs.getChat().SingleCafeReceiver((ChatVO)obj);
						map.get(vo.getId()).setVisible(true);
						map.get(vo.getId()).ChatReceiver(vo);
					}
					
					if (obj instanceof MySbVO) {
						MySbVO sbvo = (MySbVO) obj;
						
						if(str.equals("추가")){
							System.out.println("sbvo: "+sbvo.getTitle());
							cc.sb_insert(sbvo);
						}
						if(str.equals("옵션 선택 주문") || str.equals("나만의 스탠바이 주문"))
							cc.orderSb_select(sbvo);
						if(str.equals("공유"))
							cc.share_update(sbvo);
						if(str.equals("추천수 업뎃"))
							cc.like_update(sbvo);
						if(str.equals("나만의 스탠바이 삭제"))
							cc.sb_delete(sbvo);
					}
					if (str.equals("너와나의 스탠바이"))
						CafeSender("너와나의 스탠바이", cc.OurSb_select());
					
					if (obj instanceof ArrayList) 		// 전체 메뉴 불러오기
						CafeSender("메뉴", cc.c_select());
					
					if (str.equals("파일없음")) {
						System.out.println("111111111111에러나오지마라 . 좋은말할때");
						CoffeeVO vo = (CoffeeVO) obj;
						
						System.out.println(vo.getImg());
						
						CafeSender("파일전송", vo);
						
						fis = new FileInputStream(vo.getImg());

						byte[] buf = new byte[1024];
						
						while (fis.available() > 0) {
							System.out.print(".");
							int length = fis.read(buf);
							os.write(buf, 0, length);
							System.out.println("len: "+length);
						}
						fis.close();
						
						fis = new FileInputStream(
								"img\\menu\\view\\250" + vo.getImg().substring(vo.getImg().lastIndexOf("\\") + 1));
						System.out
								.println("img\\menu\\view\\250" + vo.getImg().substring(vo.getImg().lastIndexOf("\\") + 1));
						buf = new byte[1024];

						while (fis.available() > 0) {
							System.out.print(".");
							int length = fis.read(buf);
							os.write(buf, 0, length);
							System.out.println("len: "+length);
						}
						fis.close();
						System.out.println(vo.getC_name()+" 전송 성공");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
	}
}