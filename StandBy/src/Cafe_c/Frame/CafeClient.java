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
	private MemberVO mvo; // 로그인된 회원
	
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
	JLabel lb_pass = new JLabel("비밀번호");

	JTextField tf_id = new JTextField();
	JPasswordField tf_pass = new JPasswordField();

	IdFindPanel id; // id찾기
	PwFindPanel pw; // 비번찾기
	
	String[] btnArr = { "로그인", "회원가입", "아이디찾기", "비밀번호찾기" };
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
		String[] genArr = { "남자", "여자" };
		JRadioButton[] Rbtn_gender = new JRadioButton[genArr.length];

		String[] btn_Arr = { "ID 중복확인", "가입 완료", "취소", ""};
		String[] lb_Arr = { "아이디", "비밀번호", "비밀번호 확인", "비밀번호 힌트", "힌트 답", "이름", "전화번호", "생년월일", "성별", "" };
		String[] tf_Arr = { "ID 입력", "힌트 답", "이름 입력", "'-'제외" }; // ""
		String[] err_Arr = { "힌트를 입력해주세요", "이름을 입력하세요 ", // 패스 워드
				"핸드폰 번호를 입력해주세요", "영문, 숫자조합 6~20 특수문자 1", "비밀번호가 일치하지 않습니다  " };
		String[] hint_Q = { "나의 보물 1호는?", "나의 보물 2호는?", "나의 보물 3호는? ", "나의 보물 4호는?", "나의 보물 5호는?" };

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
					btn[i] = new JButton(new ImageIcon("img\\달력.png"));
				btn[i].addActionListener(this);
				add(btn[i]);
			}

			tf[0].setBounds(130, 50, 150, 30); // 아이디
			tf[1].setBounds(130, 210, 150, 30); // 힌트
			tf[2].setBounds(130, 250, 150, 30); // 이름
			tf[3].setBounds(130, 290, 150, 30); // 핸드폰

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
				cc.sender(vo, "중복확인");
				lb_idChk.setForeground(Color.red);
				if (cc.getKey() != null) {
					lb_idChk.setText("이미 사용 중인 ID입니다.");
					tf[0].setBackground(Color.gray);
					
				} else {
					if (cc.idChk(tf[0].getText())) {
						lb_idChk.setForeground(Color.black);
						lb_idChk.setText("사용 가능한 ID입니다.");
						chk = true;
					} else
						lb_idChk.setText("영문, 숫자 조합  6~13자");
				}
				return;
			}
			
			if (e.getSource().equals(btn[1])) { // 가입 버튼
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
					
					if (cc.submitChk(buf))  // 회원 가입 양식 정규식 검사
						return;
					
					
					if (cc.submitPWChk(pass, passChk)) { // 회원 가입 양식 정규식 검사
						JOptionPane.showMessageDialog (
								this,
								"비밀번호를 확인해 주세요.",
								"비밀번호 재입력 요청", 
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

					cc.sender(vo, "회원가입");

					// 가입이 완료되면 회원테이블에 회원정보 추가.
					JOptionPane.showMessageDialog(new JOptionPane(), tf[0].getText() + "님 가입을 축하합니다.", "회원가입",
							JOptionPane.INFORMATION_MESSAGE);
				} else{
					errMessage("ID 중복확인 버튼을 눌러주세요.");
					return;
				}
			}
					
			if (e.getSource().equals(btn[3])) {
				new CalenFrame(this);
				return;
			}
			// dispose(); // 가입 완료 or 취소
			pn_main.setVisible(true);
			setVisible(false);
		}
		
		void errMessage(String err){
			JOptionPane.showMessageDialog(new JOptionPane(), err,
					"회원가입 오류", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	class IdFindPanel extends JPanel implements ActionListener {
		JLabel lb_id = new JLabel("[ 아이디 찾기 ]");
		JLabel lb_name = new JLabel("이 름 :");
		JLabel lb_find = new JLabel("아이디 :");
		JLabel lb_ph = new JLabel("핸드폰번호 :");
		
		JTextField tf_ph = new JTextField(); // 핸드폰번호 입력
		JTextField tf_name = new JTextField(); // 이름 입력
		JTextField tf_id = new JTextField(); // db에서 받아온 id

		String[] chk = { "확인", "취소" };
		JButton[] btn = new JButton[chk.length];
		
		boolean chk2;

		public IdFindPanel() {
			// TODO Auto-generated constructor stub
			setBounds(100, 100, 300, 300);
			setBackground(Color.orange);
			setLayout(null);
			
			lb_id.setBounds(110, 40, 120, 30); // 타이틀
			lb_find.setBounds(80, 220, 100, 30);	// 아이디:
			lb_name.setBounds(85, 90, 100, 30);	// 이름 :
			lb_ph.setBounds(50, 130, 100, 30);		// 핸드폰번호 :
			tf_name.setBounds(150, 90, 100, 30);	 // 이름입력
			tf_ph.setBounds(150, 130, 100, 30); // 핸드폰 입력
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

			// 확인 , 취소 	버튼
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
					"정보가 없습니다.", 
					"입력해 주세요" };
			
			if (e.getSource().equals(btn[1])) {		// 취소 버튼 클릭 시
				dispose();
				new CafeClient();
			}

			if (e.getSource().equals(btn[0])) {		// 확인 버튼 클릭 시
				MemberVO vo = new MemberVO();
				
				vo.setName(tf_name.getText());
				vo.setPhone(tf_ph.getText());
				cc.sender(vo, "아이디찾기");
				vo = cc.getMvo();
				
				if(vo.getId() != null){				
					tf_id.setText(vo.getId());
					JOptionPane.showMessageDialog(new JOptionPane(), "아이디 조회 완료", "아이디 조회",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					tf_id.setText("");
					JOptionPane.showMessageDialog(new JOptionPane(), error[0], "조회오류", 
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			}
		}
	}

	class PwFindPanel extends JPanel implements ActionListener {
		JLabel lb_pw = new JLabel("[ 비밀번호 찾기 ]");
		JLabel lb_name = new JLabel("이 름 :");
		JLabel lb_id = new JLabel("아이디 :");
		JLabel lb_hint = new JLabel("비밀번호 힌트 :");
		JLabel lb_answer = new JLabel("힌트 답:");
		JLabel lb_find = new JLabel("비밀번호 :");
		JTextField tf_name = new JTextField();	// 이름 입력
		JTextField tf_id = new JTextField();	// 아이디 입력
		JTextField tf_answer = new JTextField();	// 힌트 답 입력
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
				dispose();
				new CafeClient();
			}
			
			if(e.getSource().equals(btn[0])){		// 확인 버튼 클릭 시
				if (tf_id.getText().length() < 6) {
					int i = tf_id.getText().length() == 0 ? 1 : 2;
					JOptionPane.showMessageDialog(this, error[i]);
					return;
				}				
				
				MemberVO vo = new MemberVO();
				vo.setId(tf_id.getText());
				vo.setName(tf_name.getText());
				
				cc.sender(vo, "비밀번호");
				vo = cc.getMvo();
				
				if(vo.getPass() != null){
					tf_find.setText(vo.getPass().substring(0, vo.getPass().length() - 3)+"***");
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
			cc.sender(vo, "로그인");

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (cc.getKey() != null) { // 여기서 회원 테이블을 돌린다
				System.out.println("로그인 성공");
				mvo = cc.getMvo();
				pn_main.setVisible(false);
				new MainPanel(this);
				
			} else {
				JOptionPane.showMessageDialog(this, "아이디 또는 비밀번호를 확인해주세요.", "로그인 오류", JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		if (e.getSource().equals(btn[1])) {		// 회원가입 버튼 클릭 시
			System.out.println("회원가입");
			pn_main.setVisible(false);
			new Membership();
			mm.setVisible(true);
		}
		if (e.getSource().equals(btn[2])) {		// ID 찾기 버튼 클릭 시
			System.out.println("아이디 조회");
			pn_main.setVisible(false);
			new IdFindPanel();
			id.setVisible(true);
		}
		if (e.getSource().equals(btn[3])) {		// 비밀번호 찾기 버튼 클릭 시
			System.out.println("비밀번호 조회");
			pn_main.setVisible(false);
			new PwFindPanel();
			pw.setVisible(true);
		}
	}
}