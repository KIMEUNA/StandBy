package Cafe_c.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Cafe_c.controller.Controller;
import service.MemberVO;

public class CafeClient extends JFrame implements ActionListener {

	static String title = "StandBy Client";

	CafeClient cf;
	Membership mm;

	Controller cc;
	private MemberVO mvo; // �α��ε� ȸ��
	
	public MemberVO getMvo() {
		return mvo;
	}

	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}


	JPanel pn_main = new JPanel();
	
	boolean chk;

	JLabel lb_angel = new JLabel(new ImageIcon(("img/angel.png")));
	JLabel lb_id = new JLabel("ID");
	JLabel lb_pass = new JLabel("��й�ȣ");

	JTextField tf_id = new JTextField();
	JPasswordField tf_pass = new JPasswordField();

	IdFindPanel id; // idã��
	PwFindPanel pw; // ���ã��
	
	String[] btnArr = { "�α���", "ȸ������", "���̵�ã��", "��й�ȣã��" };
	JButton[] btn = new JButton[btnArr.length];
	
	public CafeClient() {
		// TODO Auto-generated constructor stub
		super(title);
		setBounds(0, 0, 1024, 728);
		setLayout(null);
		setBackground(Color.orange);

		cf = this;
		cc = new Controller();
		
		dataInit();

		pn_main.setVisible(true);
		mm = new Membership();
		mm.setVisible(false);
		
		id = new IdFindPanel();
		id.setVisible(false);

		pw = new PwFindPanel();
		pw.setVisible(false);

		add(pn_main);
		add(mm);
		add(id);
		add(pw);

		setVisible(true);
		addWindowListener(new WinClose());

	}

	void dataInit() {

		pn_main.setBounds(0, 0, 1024, 728);
		pn_main.setLayout(null);

		lb_angel.setBounds(250, 250, 270, 60);
		lb_id.setBounds(270, 350, 30, 40);
		lb_pass.setBounds(230, 400, 70, 40);

		tf_id.setBounds(300, 350, 200, 40);
		tf_pass.setBounds(300, 400, 200, 40);

		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(btnArr[i]);
			if (i < 2)
				btn[i].setBounds(280 + i * 110, 460, 100, 40);
			else {
				btn[i].setBounds(750+(i-2)*110, 80, 100, 40);
				btn[i].setBackground(Color.white);
				btn[i].setBorder(null);
			}

			btn[i].addActionListener(this);

			pn_main.add(btn[i]);
		}

		tf_id.addActionListener(this);
		tf_pass.addActionListener(this);

		pn_main.add(lb_angel);
		pn_main.add(lb_id);
		pn_main.add(lb_pass);
		pn_main.add(tf_id);
		pn_main.add(tf_pass);

	}

	class Membership extends JPanel implements ActionListener {
		

		JTextField[] tf;
		JLabel[] lb;
		JButton[] btn;
		JLabel lb_idChk = new JLabel();

		JPasswordField tf_joinpass = new JPasswordField();
		JPasswordField tf_passChk = new JPasswordField();
		
		Vector<String> hintArr = new Vector<>();
		JComboBox<String> cb_Hint = new JComboBox<>(hintArr);
		
		ButtonGroup btng_gender = new ButtonGroup();
		String[] genArr = { "����", "����" };
		JRadioButton[] Rbtn_gender = new JRadioButton[genArr.length];

		String[] btn_Arr = { "ID �ߺ�Ȯ��", "���� �Ϸ�", "���", ""};
		String[] lb_Arr = { "���̵�", "��й�ȣ", "��й�ȣ Ȯ��", "��й�ȣ ��Ʈ", "��Ʈ ��", "�̸�", "��ȭ��ȣ", "�������", "����", "" };
		String[] tf_Arr = { "ID �Է�", "��Ʈ ��", "�̸� �Է�", "'-'����" }; // ""
		String[] err_Arr = { "��Ʈ�� �Է����ּ���", "�̸��� �Է��ϼ��� ", // �н� ����
				"�ڵ��� ��ȣ�� �Է����ּ���", "����, �������� 6~20 Ư������ 1", "��й�ȣ�� ��ġ���� �ʽ��ϴ�  " };
		String[] hint_Q = { "���� ���� 1ȣ��?", "���� ���� 2ȣ��?", "���� ���� 3ȣ��? ", "���� ���� 4ȣ��?", "���� ���� 5ȣ��?" };

		Date date;
		int index;
		int num1 = 1, num2 = 4;

		public Membership() {
			setBounds(0, 0, 1000, 700);
			setLayout(null);
			
			lb = new JLabel[lb_Arr.length];
			tf = new JTextField[tf_Arr.length];
			btn = new JButton[btn_Arr.length];

			for (int i = 0; i < lb.length; i++) {
				String str = lb_Arr[i];
				lb[i] = new JLabel(str);
				lb[i].setBounds(30, 50 + i * 40, 90, 30);
				if (i != 9)
					lb[i].setHorizontalAlignment(SwingConstants.RIGHT);
				
				if (i >= 7)
					lb[i].setBounds(30, 300 + 40, 90, 30);
				if (i == 8)
					lb[i].setBounds(30, 380, 90, 30);
				if (i == 9) {
					lb[i].setBounds(130, 340, 100, 30);
					lb[i].setBackground(Color.lightGray);
					lb[i].setOpaque(true);
				}
				add(lb[i]);
			}
			lb_idChk.setBounds(100, 20, 230, 30);

			for (int i = 0; i < tf_Arr.length; i++) {
				tf[i] = new JTextField(tf_Arr[i]);
				add(tf[i]);
			}

			for (int i = 0; i < btn_Arr.length; i++) {
				if(i != 3)
					btn[i] = new JButton(btn_Arr[i]);
				else if(i == 3)
					btn[i] = new JButton(new ImageIcon("img\\�޷�.png"));
				btn[i].addActionListener(this);
				add(btn[i]);
			}

			tf[0].setBounds(130, 50, 150, 30); // ���̵�
			tf[1].setBounds(130, 210, 150, 30); // ��Ʈ
			tf[2].setBounds(130, 250, 150, 30); // �̸�
			tf[3].setBounds(130, 290, 150, 30); // �ڵ���

			btn[0].setBounds(290, 50, 100, 30);
			btn[1].setBounds(100, 450, 100, 30);
			btn[2].setBounds(210, 450, 100, 30);
			btn[3].setBounds(235, 340, 20, 20);

			tf_joinpass.setBounds(130, 90, 150, 30);
			tf_passChk.setBounds(130, 130, 150, 30);

			for (int i = 0; i < hint_Q.length; i++) {
				hintArr.add(hint_Q[i]);
			}
			cb_Hint.setSelectedIndex(0);
			cb_Hint.setBounds(130, 170, 150, 30);

			for (int i = 0; i < Rbtn_gender.length; i++) {
				Rbtn_gender[i] = new JRadioButton(genArr[i]);
				Rbtn_gender[i].setBounds(130 + i * 70, 380, 60, 30);
				add(Rbtn_gender[i]);
				btng_gender.add(Rbtn_gender[i]);
			}
			Rbtn_gender[0].setSelected(true);

			add(lb_idChk);
			add(tf_joinpass);
			add(tf_passChk);
			add(cb_Hint);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if (e.getSource().equals(btn[0])) {
				chk = false;
				MemberVO vo = new MemberVO();
				vo.setId(tf[0].getText());
				cc.sender(vo, "�ߺ�Ȯ��");
				lb_idChk.setForeground(Color.red);
				if (cc.getKey() != null) {
					lb_idChk.setText("�̹� ��� ���� ID�Դϴ�.");
					tf[0].setBackground(Color.gray);
					
				} else {
					if (cc.idChk(tf[0].getText())) {
						lb_idChk.setForeground(Color.black);
						lb_idChk.setText("��� ������ ID�Դϴ�.");
						chk = true;
					} else
						lb_idChk.setText("����, ���� ����  6~13��");
				}
				return;
			}
			
			if (e.getSource().equals(btn[1])) { // ���� ��ư
				if (chk) {
					String[] buf = new String[tf.length];
					String pass = "", passChk = "";
					for (int i = 0; i < tf.length; i++) {
						buf[i] = new String();
						buf[i] = tf[i].getText();
					}
					for (int j = 0; j < tf_joinpass.getPassword().length; j++)
						pass += tf_joinpass.getPassword()[j];
					for (int j = 0; j < tf_passChk.getPassword().length; j++)
						passChk += tf_passChk.getPassword()[j];
					
					if (cc.submitChk(buf))  // ȸ�� ���� ��� ���Խ� �˻�
						return;
					
					
					if (cc.submitPWChk(pass, passChk)) { // ȸ�� ���� ��� ���Խ� �˻�
						JOptionPane.showMessageDialog (
								this,
								"��й�ȣ�� Ȯ���� �ּ���.",
								"��й�ȣ ���Է� ��û", 
								JOptionPane.OK_OPTION
								);
						return;
					}

					MemberVO vo = new MemberVO();

					vo.setId(tf[0].getText());
					vo.setName(tf[2].getText());
					vo.setPass(pass);
					for (int i = 0; i < Rbtn_gender.length; i++) {
						if (btng_gender.isSelected(Rbtn_gender[i].getModel()))
							vo.setGender(Rbtn_gender[i].getActionCommand());
					}
					vo.setPhone(tf[3].getText());
					if (date == null) {
						return;
					}
					vo.setBrithDate(date);

					for (int i = 0; i < hint_Q.length; i++) {
						if (cb_Hint.getSelectedIndex() == i)
							vo.setPasshint(hint_Q[i]);
					}
					vo.setHintanswer(tf[1].getText());

					cc.sender(vo, "ȸ������");

					// ������ �Ϸ�Ǹ� ȸ�����̺� ȸ������ �߰�.
					JOptionPane.showMessageDialog(new JOptionPane(), tf[0].getText() + "�� ������ �����մϴ�.", "ȸ������",
							JOptionPane.INFORMATION_MESSAGE);
				} else{
					errMessage("ID �ߺ�Ȯ�� ��ư�� �����ּ���.");
					return;
				}
			}
					
			if (e.getSource().equals(btn[3])) {
				new CalenFrame(this);
				return;
			}
			// dispose(); // ���� �Ϸ� or ���
			pn_main.setVisible(true);
			setVisible(false);
		}
		
		void errMessage(String err){
			JOptionPane.showMessageDialog(new JOptionPane(), err,
					"ȸ������ ����", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	class IdFindPanel extends JPanel implements ActionListener {
		JLabel lb_id = new JLabel("[ ���̵� ã�� ]");
		JLabel lb_name = new JLabel("�� �� :");
		JLabel lb_find = new JLabel("���̵� :");
		JLabel lb_ph = new JLabel("�ڵ�����ȣ :");
		
		JTextField tf_ph = new JTextField(); // �ڵ�����ȣ �Է�
		JTextField tf_name = new JTextField(); // �̸� �Է�
		JTextField tf_id = new JTextField(); // db���� �޾ƿ� id

		String[] chk = { "Ȯ��", "���" };
		JButton[] btn = new JButton[chk.length];
		
		boolean chk2;

		public IdFindPanel() {
			// TODO Auto-generated constructor stub
			setBounds(100, 100, 300, 300);
			setBackground(Color.orange);
			setLayout(null);
			
			lb_id.setBounds(110, 40, 120, 30); // Ÿ��Ʋ
			lb_find.setBounds(80, 220, 100, 30);	// ���̵�:
			lb_name.setBounds(85, 90, 100, 30);	// �̸� :
			lb_ph.setBounds(50, 130, 100, 30);		// �ڵ�����ȣ :
			tf_name.setBounds(150, 90, 100, 30);	 // �̸��Է�
			tf_ph.setBounds(150, 130, 100, 30); // �ڵ��� �Է�
			tf_id.setBounds(150, 220, 100, 30); // db
			
			tf_id.setBackground(Color.gray);
			tf_id.setEnabled(false);
			
			add(lb_id);			
			add(lb_find);			
			add(lb_name);		
			add(lb_ph);			
			add(tf_name);
			add(tf_ph);			
			add(tf_id);

			btn = new JButton[chk.length];

			// Ȯ�� , ��� 	��ư
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

			String[] error = { 
					"������ �����ϴ�.", 
					"�Է��� �ּ���" };
			
			if (e.getSource().equals(btn[1])) {		// ��� ��ư Ŭ�� ��
				dispose();
				new CafeClient();
			}

			if (e.getSource().equals(btn[0])) {		// Ȯ�� ��ư Ŭ�� ��
				MemberVO vo = new MemberVO();
				
				vo.setName(tf_name.getText());
				vo.setPhone(tf_ph.getText());
				cc.sender(vo, "���̵�ã��");
				vo = cc.getMvo();
				
				if(vo.getId() != null){				
					tf_id.setText(vo.getId());
					JOptionPane.showMessageDialog(new JOptionPane(), "���̵� ��ȸ �Ϸ�", "���̵� ��ȸ",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					tf_id.setText("");
					JOptionPane.showMessageDialog(new JOptionPane(), error[0], "��ȸ����", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	}

	class PwFindPanel extends JPanel implements ActionListener {
		JLabel lb_pw = new JLabel("[ ��й�ȣ ã�� ]");
		JLabel lb_name = new JLabel("�� �� :");
		JLabel lb_id = new JLabel("���̵� :");
		JLabel lb_hint = new JLabel("��й�ȣ ��Ʈ :");
		JLabel lb_answer = new JLabel("��Ʈ ��:");
		JLabel lb_find = new JLabel("��й�ȣ :");
		JTextField tf_name = new JTextField();	// �̸� �Է�
		JTextField tf_id = new JTextField();	// ���̵� �Է�
		JTextField tf_answer = new JTextField();	// ��Ʈ �� �Է�
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
				dispose();
				new CafeClient();
			}
			
			if(e.getSource().equals(btn[0])){		// Ȯ�� ��ư Ŭ�� ��
				if (tf_id.getText().length() < 6) {
					int i = tf_id.getText().length() == 0 ? 1 : 2;
					JOptionPane.showMessageDialog(this, error[i]);
					return;
				}				
				
				MemberVO vo = new MemberVO();
				vo.setId(tf_id.getText());
				vo.setName(tf_name.getText());
				
				cc.sender(vo, "��й�ȣ");
				vo = cc.getMvo();
				
				if(vo.getPass() != null){
					tf_find.setText(vo.getPass().substring(0, vo.getPass().length() - 3)+"***");
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


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(btn[0])) {
			MemberVO vo = new MemberVO();

			String buf = "";
			for (int i = 0; i < tf_pass.getPassword().length; i++)
				buf += tf_pass.getPassword()[i];
			
			vo.setId(tf_id.getText());
			vo.setPass(buf);
			cc.sender(vo, "�α���");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (cc.getKey() != null) { // ���⼭ ȸ�� ���̺��� ������
				System.out.println("�α��� ����");
				mvo = cc.getMvo();
				pn_main.setVisible(false);
				new MainPanel(this);
				
			} else {
				JOptionPane.showMessageDialog(this, "���̵� �Ǵ� ��й�ȣ�� Ȯ�����ּ���.", "�α��� ����", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		if (e.getSource().equals(btn[1])) {		// ȸ������ ��ư Ŭ�� ��
			System.out.println("ȸ������");
			pn_main.setVisible(false);
			new Membership();
			mm.setVisible(true);
		}
		if (e.getSource().equals(btn[2])) {		// ID ã�� ��ư Ŭ�� ��
			System.out.println("���̵� ��ȸ");
			pn_main.setVisible(false);
			new IdFindPanel();
			id.setVisible(true);
		}
		if (e.getSource().equals(btn[3])) {		// ��й�ȣ ã�� ��ư Ŭ�� ��
			System.out.println("��й�ȣ ��ȸ");
			pn_main.setVisible(false);
			new PwFindPanel();
			pw.setVisible(true);
		}
	}
}