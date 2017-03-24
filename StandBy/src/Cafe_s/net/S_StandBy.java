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
			System.out.println("�����غ� �Ϸ�");
			
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
					
					System.out.println(client.getInetAddress()+" ���� ~~");
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
						
						if (str.equals("ȸ������"))
							cc.m_insert(vo);
						if(str.equals("ȸ��Ż��"))
							cc.m_delete(vo);
						if(str.equals("��������")){
							cc.m_update(vo);
							CafeSender("", cc.m_logChk(vo));
						}
						if (str.equals("�α���")) {
							vo = cc.m_logChk(vo);
							if (vo != null){
								key = "�α���";
								map.put(vo.getId(), new ChatPanel(me, cs.getEvo()));
								map.get(vo.getId()).setVisible(false);
								cs.chat.removeAll();
								Iterator<String> it = map.keySet().iterator();
								while(it.hasNext()){
									String buf = it.next();
									cs.chat.addItem(buf);
									System.out.println("buf: "+buf);
								}
								cs.lb_chat.setText("�����ο�: "+map.size()+"��");
							}
							CafeSender(key, vo);
						}
						if (str.equals("���̵�ã��"))
							CafeSender("ã�Ѵ�", cc.idfind(vo));
						
						if(str.equals("��й�ȣ"))
							CafeSender("ã�Ѵ�", cc.pwfind(vo));
						
						if (str.equals("�ߺ�Ȯ��")) {
							if(cc.idChk(vo.getId())){
								key = "�ߺ�Ȯ��";
							}
							CafeSender(key, vo);
						}
						if (str.equals("��������")){
							ArrayList<OrderVO> list = cc.order_select(vo);
							CafeSender("��������", list);
						}
						if (str.equals("����Ʈ"))
							cc.m_pointUpdate(vo);
						if(str.equals("���"))
							cc.grade_Update(vo);
						if (str.equals("������ ���Ĺ���")) {
							CafeSender("������ ���Ĺ���", cc.MySb_select(vo));
						}
					}
					
					if (obj instanceof OrderVO) {
						OrderVO vo = (OrderVO) obj;
						if(str.equals("����"))
							cc.order_update(vo, 2);
						if(str.equals("���")){
							cc.order_delete(vo);
							cs.getOrder().dataInit();
						}
						if(str.equals("�ֹ�")){
							if(vo.getCate().equals("��ǰ")){
								StockVO svo = new StockVO();
								svo.setItem(vo.getName());
								svo.setIn(0);
								svo.setOut(vo.getEa());
								svo.setStat("���");
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
						
						if(str.equals("�߰�")){
							System.out.println("sbvo: "+sbvo.getTitle());
							cc.sb_insert(sbvo);
						}
						if(str.equals("�ɼ� ���� �ֹ�") || str.equals("������ ���Ĺ��� �ֹ�"))
							cc.orderSb_select(sbvo);
						if(str.equals("����"))
							cc.share_update(sbvo);
						if(str.equals("��õ�� ����"))
							cc.like_update(sbvo);
						if(str.equals("������ ���Ĺ��� ����"))
							cc.sb_delete(sbvo);
					}
					if (str.equals("�ʿͳ��� ���Ĺ���"))
						CafeSender("�ʿͳ��� ���Ĺ���", cc.OurSb_select());
					
					if (obj instanceof ArrayList) 		// ��ü �޴� �ҷ�����
						CafeSender("�޴�", cc.c_select());
					
					if (str.equals("���Ͼ���")) {
						System.out.println("111111111111�������������� . �������Ҷ�");
						CoffeeVO vo = (CoffeeVO) obj;
						
						System.out.println(vo.getImg());
						
						CafeSender("��������", vo);
						
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
						System.out.println(vo.getC_name()+" ���� ����");
					}

				} catch (Exception e) {
					// TODO Auto-generated catch block
					// e.printStackTrace();
				}
			}
		}
	}
}