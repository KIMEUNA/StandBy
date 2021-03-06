package Cafe_s.Frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Cafe_s.controller.Controller;
import Cafe_s.model.service.EmployeeVO;

public class EmployeePanel extends JPanel implements ActionListener {

	MainPanel main;
	EmployeePanel ep;
	
	Controller cc = new Controller();

	JPanel pn = new JPanel();
	JPanel pn_view = new JPanel();
	JScrollPane sp_view = new JScrollPane(pn_view);

	String[] arr = { "선택", "아이디", "이름", "직급", "입사일", " " };
	JLabel[] lbArr = new JLabel[arr.length];
	JCheckBox cb;
	ButtonGroup btng = new ButtonGroup();

	JButton btn_insert = new JButton("사원 등록");

	ArrayList<EmployeeVO> list;

	public EmployeePanel(MainPanel main) {
		// TODO Auto-generated constructor stub
		this.main = main;
		ep = this;
		
		setBounds(250, 100, 774, 628);
		setLayout(null);

		btn_insert.setBounds(80, 30, 100, 30);
		btn_insert.addActionListener(this);

		sp_view.setBounds(30, 130, 734, 438);
		pn_view.setLayout(new GridLayout(0, arr.length));
		pn.setBounds(30, 100, 754, 30);
		pn.setLayout(new GridLayout(0, arr.length));

		for (int i = 0; i < lbArr.length; i++) {
			lbArr[i] = new JLabel(arr[i]);
			lbArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			pn.add(lbArr[i]);
		}

		dataInit();

		add(btn_insert);
		add(pn);
		add(sp_view);
	}
	
	void dataInit() {

		pn_view.removeAll();
		pn_view.revalidate();
		pn_view.repaint();

		list = cc.e_select();

		for (int i = 0; i < list.size(); i++) {
			cb = new JCheckBox();
			cb.setHorizontalAlignment(SwingConstants.CENTER);
			cb.addActionListener(new BtnAction(list.get(i)));
			btng.add(cb);
			pn_view.add(cb);
			String[] buf = list.get(i).toString().split(",");
			for (int j = 0; j < buf.length; j++) {
				lbArr[j] = new JLabel(buf[j]);
				lbArr[j].setHorizontalAlignment(SwingConstants.CENTER);
				pn_view.add(lbArr[j]);
			}
		}

		for (int i = 0; i < 100; i++) {
			JLabel lb = new JLabel("");
			pn_view.add(lb);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		new NewEmployee();
	}

	class BtnAction implements ActionListener {

		EmployeeVO vo;

		public BtnAction(EmployeeVO vo) {
			// TODO Auto-generated constructor stub
			this.vo = vo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new EMInfo(vo, 1);		// 관리자가 직원 수정할 때 (연봉만 수정가능)
		}
	}

	class EMInfo extends JFrame implements ActionListener {

		EmployeeVO vo;
		int flag;

		String[] arr = { "직원 ID: ", "직원 성명: ", "직급: ", "연봉: ", "입사일: " };
		JLabel[] lb = new JLabel[arr.length];

		String[] btnStr = { "수정", "퇴사" };
		JButton[] btn = new JButton[btnStr.length];

		public EMInfo(EmployeeVO vo, int flag) {
			// TODO Auto-generated constructor stub
			this.vo = vo;
			this.flag = flag;

			setBounds(300, 100, 200, 350);
			setLayout(null);

			dataInit();

			setVisible(true);
			addWindowListener(new WinClose());
		}

		void dataInit() {
			for (int i = 0; i < arr.length; i++) {
				String[] buf = vo.info().split(";");
				lb[i] = new JLabel(arr[i] + buf[i]);
				lb[i].setBounds(20, 20 + i * 38, 250, 30);
				add(lb[i]);
			}
			
			for (int i = 0; i < btnStr.length; i++) {
				if (vo.getGrade().equals("관리자") && i == 1)
					break;
				btn[i] = new JButton(btnStr[i]);
				btn[i].setBounds(20 + i * 70, 210, 50, 30);
				btn[i].setBorder(null);
				btn[i].addActionListener(this);
				add(btn[i]);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if (e.getSource().equals(btn[0])) {
				new NewEmployee(vo, flag);
			} else if (e.getSource().equals(btn[1])) { // 퇴사
				int res = JOptionPane.showConfirmDialog(this, "직원 정보를 삭제하시겠습니까?", "퇴사 처리", JOptionPane.OK_CANCEL_OPTION);
				
				if(res == JOptionPane.OK_OPTION){
					cc.e_delete(vo);
					JOptionPane.showMessageDialog(this, "퇴사 처리 되었습니다.");					
				}
			}
	
			ep.dataInit();
			dispose();
		}
	}

	class NewEmployee extends JFrame implements ActionListener {

		EmployeeVO vo;

		String[] str = { "아이디", "이름", "비밀번호", "연봉" };

		JLabel[] lb = new JLabel[str.length];
		JTextField[] tf = new JTextField[lb.length];
		JPasswordField tf_pass;

		String[] submit = { "확인", "취소" };
		JButton[] btn = new JButton[submit.length];

		boolean updateChk = true;

		public NewEmployee() {
			// TODO Auto-generated constructor stub
			setBounds(600, 150, 400, 400);
			setLayout(null);

			dateInit();

			for (int i = 0; i < btn.length; i++) {
				btn[i] = new JButton(submit[i]);
				btn[i].setBounds(70 + i * 110, 230, 100, 30);
				btn[i].addActionListener(this);
				add(btn[i]);
			}

			setVisible(true);
			addWindowListener(new WinClose());
		}

		public NewEmployee(EmployeeVO vo, int flag) {
			// TODO Auto-generated constructor stub
			this();

			this.vo = vo;

			String[] buf = vo.update().split(",");
			for (int i = 0; i < tf.length; i++) {
				if(i==2) {
					tf_pass.setText(buf[i]);
					continue;
				}
				tf[i].setText(buf[i]);
			}
			tf[0].setEditable(false);
			tf[1].setEditable(false);
			
			if(flag==0) {
				if(vo.getGrade().equals("직원")) 
					tf[3].setEnabled(false);
			}
			else if(flag==1)
				tf_pass.setEnabled(false);
			
			updateChk = false;
		}

		void dateInit() {
			vo = new EmployeeVO();
			
			JLabel lb_pay = new JLabel("만원");
			
			for (int i = 0; i < str.length; i++) {
				lb[i] = new JLabel(str[i]);
				lb[i].setBounds(20, 50 + i * 40, 70, 30);
				lb[i].setHorizontalAlignment(SwingConstants.RIGHT);
				if(i==2) {
					tf_pass = new JPasswordField();
					tf_pass.setBounds(95, 50 + i * 40, 100, 30);
					
					add(lb[i]);
					add(tf_pass);
					
					continue;
				}
				tf[i] = new JTextField();
				tf[i].setBounds(95, 50 + i * 40, 100, 30);

				add(lb[i]);
				add(tf[i]);
			}
			
			lb_pay.setBounds(200, 170, 50, 30);
			add(lb_pay);
		}

		// 계정 생성 시 유효성 검사
		boolean chk() {
			
			String[] ppId = { 
					"^[a-zA-Z]{1}[a-zA-Z0-9]{4,18}[0-9]{1}$", // ID 유효성 검사
					"[가-힣]+{2,5}$",		// 이름 유효성 검사
					"", 
					"[0-9]+$" // 연봉 유효성 검사
					};

			for (int i = 0; i < ppId.length; i++) {
				if(i==2)
					continue;
				
				if(i==0) {
					if(cc.e_idChk(tf[i].getText())) {
						JOptionPane.showMessageDialog(this, "이미 등록된 아이디 입니다.", 
								"아이디 중복", JOptionPane.ERROR_MESSAGE);
						return true;
					}
					if (tf[i].getText().equals("admin")) {
						JOptionPane.showMessageDialog(this, "해당 ID는 관리자만 사용가능합니다.", 
								str[i]+ " Err", JOptionPane.ERROR_MESSAGE);
						return true;
					}
				}					
				if (!tf[i].getText().matches(ppId[i])) {
					JOptionPane.showMessageDialog(this, str[i] + "을(를) 확인해주세요.",
							str[i] + " 오류", JOptionPane.ERROR_MESSAGE);
					return true;
				}
			}
			
			if(!chkPass(tf_pass)) {
				return true;
			}		
			
			return false;
		}
		
		// 정보 수정 시 유효성 검사
		boolean chkUpdate() {
			if (vo.getId().equals("admin")) {
				if (!tf[3].getText().matches("[0-9]+$")) {// 연봉 유효성 검사
					JOptionPane.showMessageDialog(
							this,
							"연봉을 확인해주세요.", 
							"연봉 오류",
							JOptionPane.ERROR_MESSAGE);
					return true;
				}			
				if (!chkPass(tf_pass)) 
					return true;		
			} 
			else
				if (!chkPass(tf_pass)) 
					return true;		
	
			return false;
		}

		// 비밀번호 유효성 검사
		public boolean chkPass(JPasswordField tf_pass) {				
			String pass = "";
			
			for (int i = 0; i < tf_pass.getPassword().length; i++) {
				pass += tf_pass.getPassword()[i];			
			}
			System.out.println(pass);
			
			if (pass.matches("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{6,13}$"))
				return true;
			
			JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요.", 
					"비밀번호 오류", JOptionPane.ERROR_MESSAGE);
			
			return false;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if (e.getSource().equals(btn[0])) {
				if (updateChk) {
					if(chk())
						return;
					
					vo.setId(tf[0].getText());
					vo.setE_name(tf[1].getText());
					
					String pass = "";
					
					for (int i = 0; i < tf_pass.getPassword().length; i++)
						pass += tf_pass.getPassword()[i];			
										
					vo.setE_pass(pass);
					vo.setPay(Integer.parseInt(tf[3].getText()));
		
					cc.e_insert(vo);
				} else {					
					if(chkUpdate())
						return;
					
					vo.setPay(Integer.parseInt(tf[3].getText()));
					
					String pass = "";

					for (int i = 0; i < tf_pass.getPassword().length; i++)
						pass += tf_pass.getPassword()[i];
								
					vo.setE_pass(pass);
					
					cc.e_update(vo);
				}
			}
					
			ep.dataInit();
			dispose();
		}
	}
}
