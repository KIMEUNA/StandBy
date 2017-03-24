package Cafe_c.net;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import Cafe_c.Frame.ChatPanel;
import service.ChatVO;
import service.CoffeeVO;
import service.MemberVO;
import service.MySbVO;
import service.OrderVO;

public class C_StandBy {

	private static C_StandBy me;
	
	public static C_StandBy getInstance(){
		if(me == null)
			me = new C_StandBy();
		return me;
	}
	ObjectOutputStream oos;
	Receiver receiver;

	MemberVO mvo = null;
	CoffeeVO cvo = null;
	ChatPanel chat = null;
	
	Object obj;
	
	private ArrayList<CoffeeVO> cvoArr = new ArrayList<>();
	private ArrayList<OrderVO> orderArr = new ArrayList<>();
	private ArrayList<MySbVO> mysbArr = new ArrayList<>();

	String str = null;		// id,pw
	
	private String key = null;
	
	public ChatPanel getChat(MemberVO vo) {
		 chat = new ChatPanel(me, vo);
		 return chat;
	}
	
	void delay(){
		try {
			Thread.sleep(100);	// 딜레이를 줘서 값이 바뀔때까지 기다린다.
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}

	public String getStr(){
		delay();
		return str;
	}
	
	public MemberVO getMvo() {
		delay();
		return mvo;
	}
	public ArrayList<CoffeeVO> getCvoArr() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return cvoArr;
	}	
	public ArrayList<OrderVO> getOrderArr(){
		delay();
		return orderArr;
	}
	public ArrayList<MySbVO> getMySbArr() {
		delay();
		return mysbArr;
	}
	

	private C_StandBy() {
		// TODO Auto-generated constructor stub
		
		try {
			Socket socket = new Socket("localhost", 7777);
			System.out.println("서버 연결 성공");
			
			oos = new ObjectOutputStream(socket.getOutputStream());
			
			receiver = new Receiver(socket);
			receiver.start();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void Sender(Object obj, String str) {

		try {
			
			oos.writeUTF(str);
			oos.writeObject(obj);
			oos.flush();
			oos.reset();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	class Receiver extends Thread {

		ObjectInputStream ois;
		InputStream is;
		
		Socket socket;
		
		public Receiver(Socket socket) {
			// TODO Auto-generated constructor stub
			
			this.socket = socket;
			
			try {
				is = socket.getInputStream();
				ois = new ObjectInputStream(socket.getInputStream());
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (ois != null) {
					
					key = ois.readUTF();
					obj = ois.readObject();
					
					System.out.println("key: "+key);
					
					if (obj instanceof MemberVO)
						mvo = (MemberVO) obj;
					
					
					if (key.equals("파일전송")) {
						cvo = (CoffeeVO) obj;
						System.out.println("cvo: "+cvo.getImg());
						FileOutputStream fos = new FileOutputStream(cvo.getImg());
						
						byte[] buf = new byte[10000];
						int length;
						
						System.out.print("다운중 ");
						
						while ((length = is.read(buf)) != 0) {
							System.out.print(".");
//							int length = is.read(buf);
							fos.write(buf, 0, length);
							System.out.println("length: "+length);
						}
						
						System.out.println("파일 저장 성공");
						fos.close();
						
						fos = new FileOutputStream(
								"img\\menu\\view\\250" + cvo.getImg().substring(cvo.getImg().lastIndexOf("\\") + 1));
						System.out.println(
								"img\\menu\\view\\250" + cvo.getImg().substring(cvo.getImg().lastIndexOf("\\") + 1));
						buf = new byte[10000];
						System.out.print("다운중 ");
						while ((length = is.read(buf)) != 0) {
//							System.out.print(".");
//							int length = is.read(buf);
							fos.write(buf, 0, length);
							System.out.println("length: "+length);
						}
						fos.close();
						System.out.println("파일 저장 성공");
						
					}
					if(obj instanceof ChatVO)
						chat.Receiver((ChatVO)obj);
					
					if (obj instanceof String)
						str = (String) obj;
					
					if (obj instanceof ArrayList){
						ArrayList buf = (ArrayList) obj;
						if (buf.size() != 0) {
							if (key.equals("메뉴"))
								cvoArr = buf;
							else if (buf.get(0) instanceof OrderVO)
								orderArr = buf;
							else if (buf.get(0) instanceof MySbVO) {
								if (key.equals("나만의 스탠바이"))
									mysbArr = buf;
								else if (key.equals("너와나의 스탠바이"))
									mysbArr = buf;
							}
						}
					}
				}
				System.out.println("client close!!!!!!!!!!!!!!!!!!!!!!!!!@@@@@@@@@");
				ois.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}
}