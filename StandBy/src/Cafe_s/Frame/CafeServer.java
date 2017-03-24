package Cafe_s.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Cafe_s.controller.Controller;
import Cafe_s.model.service.EmployeeVO;
import Cafe_s.net.S_StandBy;

public class CafeServer extends JFrame implements ActionListener {

	static String title = "StandBy Server";
	
	JPanel pn_login = new JPanel();
	
	JLabel lb_angel = new JLabel(new ImageIcon(("img/angel.png")));
	JLabel lb_id = new JLabel("ID");
	JLabel lb_pass = new JLabel("비밀번호");
	
	JTextField tf_id = new JTextField();
	JPasswordField tf_pass = new JPasswordField();
	
	String[] btnArr = { "로그인", "비밀번호찾기" };
	JButton[] btn;

	EmployeeVO evo;
	CafeServer cs;
	
	Controller cc = new Controller();
	
	S_StandBy server;
	OrderPanel pn_order;

	public ArrayList<ChatPanel> chat_list = new ArrayList<>();
	public JComboBox<String> chat = new JComboBox<>();
	public JLabel lb_chat = new JLabel("접속 인원: 0명");
	
	public CafeServer() {
		// TODO Auto-generated constructor stub
		super(title);
		setBounds(0, 0, 1024, 728);
		setLayout(null);

		cs = this;
		
		dataInit();

		pn_login.setVisible(true);

		add(pn_login);
		
		setVisible(true);
		setResizable(false);
		addWindowListener(new WinClose());
		
		server = S_StandBy.getInstance(this);
		
	}
	public EmployeeVO getEvo() {
		return evo;
	}
	
	//	public ChatPanel getChat(){
//		return pn_chat;
//	}
	public OrderPanel getOrder(){
		System.out.println("getorder");
		return pn_order;
	}
	
	void dataInit() {
		
		pn_login.setBounds(0, 0, 1024, 728);
//		pn_login.setBackground(Color.white);
		pn_login.setLayout(null);
		
		lb_angel.setBounds(400, 200, 270, 60);
		lb_id.setBounds(400, 300, 30, 40);
		lb_pass.setBounds(370, 350, 70, 40);

		tf_id.setBounds(450, 300, 200, 40);
		tf_pass.setBounds(450, 350, 200, 40);

		lb_chat.setBounds(0, 100, 230, 20);
		chat.setBounds(0, 123, 230, 30);
		
		lb_chat.setVisible(false);
		chat.setVisible(false);
		
		btn = new JButton[btnArr.length];
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnArr[i]);
			if (i == 0)
				btn[i].setBounds(500, 430, 100, 40);
			else {
				btn[i].setBounds(850, 50, 100, 20);
//				btn[i].setBackground(Color.white);
			}
			btn[i].setBorder(null);
			btn[i].addActionListener(this);
			pn_login.add(btn[i]);
		}

		tf_pass.addActionListener(this);

		pn_login.add(lb_angel);
		pn_login.add(lb_id);
		pn_login.add(lb_pass);
		pn_login.add(tf_id);
		pn_login.add(tf_pass);
		add(chat);
		add(lb_chat);

	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		for (int i = 0; i < btn.length; i++) {
			if(e.getSource().equals(btn[i])){
				if(i == 0){
					evo = cc.loginChk(tf_id.getText(), tf_pass.getPassword());
					if (evo != null) { // loginChk 메소드를 돌려 DB에 있는 관리자 계정이랑 매칭을 한다.
						lb_chat.setVisible(true);
						pn_login.setVisible(false);
						chat.setVisible(true);
						pn_order = new OrderPanel();
//						pn_chat = new ChatPanel(server, evo);
						cc.pn_main(this);
						return; // 로그인 성공했을때 메소드를 빠져 나간다.
					}
					else
						JOptionPane.showMessageDialog(this, 
								"아이디 또는 비밀번호를 확인해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				}
				else if(i == 1){	// 비밀번호 찾기
					pn_login.setVisible(false);
					add(new PwFindPanel());
				}
			}
		}
	}
	
	class PwFindPanel extends JPanel implements ActionListener {
		JLabel lb_pw = new JLabel("[ 비밀번호 찾기 ]");
		JLabel lb_name = new JLabel("이 름 :");
		JLabel lb_id = new JLabel("아이디 :");
		JLabel lb_find = new JLabel("비밀번호 :");
		JTextField tf_name = new JTextField();	// 이름 입력
		JTextField tf_id = new JTextField();	// 아이디 입력
		JTextField tf_find = new JTextField(); // db에서 받아온 비밀번호


		String[] chk = { "확인", "취소" };
		JButton[] btn = new JButton[chk.length];
		
		public PwFindPanel() {
			// TODO Auto-generated constructor stub

			setBounds(100, 100, 300, 300);
			setBackground(Color.pink);
			setLayout(null);
			
			lb_pw.setBounds(110, 40, 120, 30);
			lb_name.setBounds(85, 90, 100, 30);
			lb_id.setBounds(80, 130, 100, 30);
			lb_find.setBounds(70, 220, 100, 30);
			tf_name.setBounds(150, 90, 100, 30);
			tf_id.setBounds(150, 130, 100, 30);
			tf_find.setBounds(150, 220, 100, 30);
			
			tf_find.setBackground(Color.gray);
			tf_find.setEnabled(false);
			
			add(lb_pw);
			add(lb_name);
			add(lb_id);
			add(lb_find);
			add(tf_name);
			add(tf_id);		
			add(tf_find);		

			btn = new JButton[chk.length];

			// 확인, 취소 버튼
			for (int i = 0; i < btn.length; i++) {
				btn[i] = new JButton(chk[i]);
				btn[i].setBounds(40 + i * 110, 170, 100, 30);
				btn[i].addActionListener(this);
				add(btn[i]);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			String [] error = {
					"이름 또는 아이디를 확인해주세요.",
					"입력해 주세요",
					"아이디는 6자 이상"};
			
			if(e.getSource().equals(btn[1])){		// 취소 버튼 클릭 시
				setVisible(false);
				pn_login.setVisible(true);
			}
			
			if(e.getSource().equals(btn[0])){		// 확인 버튼 클릭 시
				if (tf_id.getText().length() < 6) {
					int i = tf_id.getText().length() == 0 ? 1 : 2;
					JOptionPane.showMessageDialog(this, error[i]);
					return;
				}
				
				EmployeeVO vo = new EmployeeVO();
				vo.setId(tf_id.getText());
				vo.setE_name(tf_name.getText());
				
				if(cc.pwfind(vo).getE_pass() != null){
					tf_find.setText(vo.getE_pass().substring(0, vo.getE_pass().length() - 3)+"***");
					JOptionPane.showMessageDialog(new JOptionPane(), "비밀번호 조회 완료", "비밀번호 조회",
							JOptionPane.INFORMATION_MESSAGE);
				} else{
					tf_find.setText("");
					JOptionPane.showMessageDialog(this, error[0]);
					return;
				}
			}					
		}
	}
}