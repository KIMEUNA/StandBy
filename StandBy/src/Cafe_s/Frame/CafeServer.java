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
	JLabel lb_pass = new JLabel("��й�ȣ");
	
	JTextField tf_id = new JTextField();
	JPasswordField tf_pass = new JPasswordField();
	
	String[] btnArr = { "�α���", "��й�ȣã��" };
	JButton[] btn;

	EmployeeVO evo;
	CafeServer cs;
	
	Controller cc = new Controller();
	
	S_StandBy server;
	OrderPanel pn_order;

	public ArrayList<ChatPanel> chat_list = new ArrayList<>();
	public JComboBox<String> chat = new JComboBox<>();
	public JLabel lb_chat = new JLabel("���� �ο�: 0��");
	
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
					if (evo != null) { // loginChk �޼ҵ带 ���� DB�� �ִ� ������ �����̶� ��Ī�� �Ѵ�.
						lb_chat.setVisible(true);
						pn_login.setVisible(false);
						chat.setVisible(true);
						pn_order = new OrderPanel();
//						pn_chat = new ChatPanel(server, evo);
						cc.pn_main(this);
						return; // �α��� ���������� �޼ҵ带 ���� ������.
					}
					else
						JOptionPane.showMessageDialog(this, 
								"���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
				}
				else if(i == 1){	// ��й�ȣ ã��
					pn_login.setVisible(false);
					add(new PwFindPanel());
				}
			}
		}
	}
	
	class PwFindPanel extends JPanel implements ActionListener {
		JLabel lb_pw = new JLabel("[ ��й�ȣ ã�� ]");
		JLabel lb_name = new JLabel("�� �� :");
		JLabel lb_id = new JLabel("���̵� :");
		JLabel lb_find = new JLabel("��й�ȣ :");
		JTextField tf_name = new JTextField();	// �̸� �Է�
		JTextField tf_id = new JTextField();	// ���̵� �Է�
		JTextField tf_find = new JTextField(); // db���� �޾ƿ� ��й�ȣ


		String[] chk = { "Ȯ��", "���" };
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

			// Ȯ��, ��� ��ư
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
					"�̸� �Ǵ� ���̵� Ȯ�����ּ���.",
					"�Է��� �ּ���",
					"���̵�� 6�� �̻�"};
			
			if(e.getSource().equals(btn[1])){		// ��� ��ư Ŭ�� ��
				setVisible(false);
				pn_login.setVisible(true);
			}
			
			if(e.getSource().equals(btn[0])){		// Ȯ�� ��ư Ŭ�� ��
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
					JOptionPane.showMessageDialog(new JOptionPane(), "��й�ȣ ��ȸ �Ϸ�", "��й�ȣ ��ȸ",
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