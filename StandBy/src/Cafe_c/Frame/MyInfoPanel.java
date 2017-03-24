package Cafe_c.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Cafe_c.controller.Controller;
import service.MemberVO;
import service.OrderVO;

public class MyInfoPanel extends JPanel implements ActionListener {

	MainPanel mp;
	MemberVO me;
	Controller ct = new Controller();

	int type;
	
	boolean passChk;
	String pass;
	
	JPanel[] pn = { new JPanel(), new JPanel() };
	JPanel[] pn_view = { new JPanel(), new JPanel() };
	JScrollPane[] sp_view = { new JScrollPane(pn_view[0]), new JScrollPane(pn_view[1]) };

	ArrayList<JPanel> pnArr= new ArrayList<>();

	String[][] orderArr = { 
			{ "주문번호", "수량", "가격", "주문일", "주문상태" }, 
			{ "주문번호", "수량", "가격", "주문일" } };
	
	JLabel[] lbOrder;

	String[] viewStr = { "현재 주문 내역", "총 주문 내역" };
	JLabel[] lbArr;
	JTextField[] tfArr;
	JButton[] btnArr;
	
	JPanel pn_order;

	ArrayList<OrderVO> list;
	JPasswordField tf_pass;
	
	JButton submit = new JButton("확인");
	JButton submit_back = new JButton("취소");
	
	String stat = null;
	
	public MyInfoPanel(MainPanel mp) {
		// TODO Auto-generated constructor stub
		setBounds(39, 100, 960, 610);
		setLayout(null);

		this.mp = mp;
		me = mp.cc.getMvo();

		dataInit();

		setVisible(false);
	}

	void dataInit() {

		pnArr.add(new JPanel()); // 내 정보 및 결제 내역 (내 정보 메인)
		pnArr.add(new JPanel()); // 정보 수정
		pnArr.add(new JPanel()); // 탈퇴	
		
		for (int i = 0; i < pnArr.size(); i++) {
			pnArr.get(i).setBounds(20, 20, 920, 500);
			pnArr.get(i).setLayout(null);
			pnArr.get(i).setVisible(false);

			add(pnArr.get(i));
		}
	}

	void order() {
		pnArr.get(1).setVisible(false);
		pnArr.get(0).setVisible(true);
		
		for (int i = 0; i < pn_view.length; i++) {
			pn[i].removeAll();
			pn[i].revalidate();
			pn[i].repaint();
			pn_view[i].removeAll();
			pn_view[i].revalidate();
			pn_view[i].repaint();
		}
		
		ct.sender(me, "결제내역"); // 전체메뉴 불러오기
		list = ct.getOrderArr();
		me = ct.getMvo();
		
		String[] arr = {"아이디", "이름", "등급", "S포인트", "가입일"};
		lbArr = new JLabel[arr.length];
		
		String[] buf = me.myInfo().split(";");
		for (int i = 0; i < lbArr.length; i++) {
			lbArr[i] = new JLabel(arr[i] +": "+buf[i]);
			lbArr[i].setBounds(20, 50+i*33, 150, 30);
			pnArr.get(0).add(lbArr[i]);
		}
		
		arr = new String [] {"S.Point 충전", "내 정보 수정", "회원 탈퇴"};
		btnArr = new JButton[arr.length];
		
		for (int i = 0; i < btnArr.length; i++) {
			btnArr[i] = new JButton(arr[i]);
			btnArr[i].setBounds(15, (50+lbArr.length*33)+50+i*45, 150, 30);
			btnArr[i].addActionListener(this);
			pnArr.get(0).add(btnArr[i]);
		}
		
		lbArr = new JLabel[viewStr.length];
		for (int i = 0; i < pn.length; i++) {
			
			lbArr[i] = new JLabel(viewStr[i]);
			lbArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			lbArr[i].setBounds(180 + i * 370, 70, 150, 30);
			pn[i].setBounds(180 + i * 370, 100, 350, 30);
			pn[i].setLayout(new GridLayout(0, orderArr[i].length));
			
			lbOrder = new JLabel[orderArr[i].length];
			for (int j = 0; j < lbOrder.length; j++) {
				lbOrder[j] = new JLabel(orderArr[i][j]);
				lbOrder[j].setHorizontalAlignment(SwingConstants.CENTER);
				pn[i].add(lbOrder[j]);
			}

			sp_view[i].setBounds(180 + i * 370, 130, 350, 318);
			pn_view[i].setLayout(new GridLayout(0, orderArr[i].length));

			pnArr.get(0).add(lbArr[i]);
			pnArr.get(0).add(pn[i]);
			pnArr.get(0).add(sp_view[i]);
		}
		
		for (int i = 0; i < list.size(); i++) {

			buf = list.get(i).myOrder().split(";");
			for (int j = 0; j < orderArr[list.get(i).getOrder() / 2].length; j++) {
				JPanel pn_buf = new JPanel(new BorderLayout());
				if (j <= 3) {
					JLabel lb = new JLabel(buf[j]);
					lb.setHorizontalAlignment(SwingConstants.CENTER);
					pn_buf.add(lb);
				} else {
					stat = new String[] { "취소", "수령" }[list.get(i).getOrder()];
						JButton btn = new JButton(stat);
						btn.setBorder(null);
						btn.setBackground(Color.white);
						btn.addActionListener(new BtnAction(list.get(i)));
						pn_buf.add(btn);
				}
				pn_view[list.get(i).getOrder() / 2].add(pn_buf);
			}
		}

		for (JPanel pp : pn_view) {
			for (int i = pp.getComponentCount(); i < 50; i++) {
				JLabel lb = new JLabel();
				pp.add(lb);
			}
		}
	}
	
	void chargePointDialog() {
		
		String point = JOptionPane.showInputDialog("충전하실 금액을 입력해 주세요.");

		if (!point.matches(".*[0-9].*")) {
			JOptionPane.showMessageDialog(this, "숫자만 입력 가능 합니다.");
			chargePointDialog();
			return;
		}
		int chargePoint = Integer.parseInt(point);
		if(chargePoint < 5000){
			JOptionPane.showMessageDialog(this, "최소 5000원 이상 충전 가능.");
			chargePointDialog();
			return;
		}
		
		me.setPoint(me.getPoint() + chargePoint);
		ct.sender(me, "포인트");

		JOptionPane.showMessageDialog(
				this, 
				chargePoint + " S.Point가 충전되었습니다.", 
				"포인트 충전 완료", 
				JOptionPane.OK_OPTION);
	}
	
	boolean passChkDialog() {

		String pass = "";
		JPasswordField pf_pass = new JPasswordField(15);
		JLabel lb = new JLabel("비밀번호를 입력해 주세요.");
		JPanel pn = new JPanel(new GridLayout(2, 1));

		pn.add(lb);
		pn.add(pf_pass);

		JOptionPane.showOptionDialog(null, pn, "비번입력", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null,
				null, null);

		if (!passChk(pf_pass.getPassword())) {
			JOptionPane.showMessageDialog(this, "비밀번호를 확인해주세요.");
			return false;
		}

		return true;

	}

	void infoUpdate() {
		pnArr.get(0).setVisible(false);
		pnArr.get(1).setVisible(true);
		
		String[] arr = {"아이디: ", "이름: ", "비밀번호: ", "성별: ", "핸드폰: ", "생일: ", "비번힌트: ", "힌트 답: "};
		
		lbArr = new JLabel[arr.length];
		tfArr = new JTextField[lbArr.length];
		
		String[] buf = me.update().split(",");
		for (int i = 0; i < lbArr.length; i++) {
			lbArr[i] = new JLabel(arr[i]);
			lbArr[i].setBounds(200, 70+i*33, 100, 30);
			
			tfArr[i] = new JTextField(buf[i]);
			tfArr[i].setBounds(305, 70+i*33, 150, 30);
			
			pnArr.get(1).add(lbArr[i]);
			if(i != 2)
				pnArr.get(1).add(tfArr[i]);
		}
		
		tfArr[0].setEditable(false);
		tfArr[1].setEditable(false);
		tfArr[3].setEditable(false);
		tfArr[5].setEditable(false);
		tfArr[6].setEditable(false);

		System.out.println("수정한당ㅇㅇㅇ");
		
		tf_pass = new JPasswordField();
		tf_pass.setBounds(305, 136, 150, 30);
		pnArr.get(1).add(tf_pass);

		submit.setBounds(300, 350, 70, 30);
		submit_back.setBounds(380, 350, 70, 30);
		submit.addActionListener(this);
		submit_back.addActionListener(this);
		
		pnArr.get(1).add(submit);	
		pnArr.get(1).add(submit_back);	
	}

	void delete() {
		ct.sender(me, "회원탈퇴");
		JOptionPane.showMessageDialog(
				this,
				"회원탈퇴 되었습니다.",
				"회원탈퇴 완료", 
				JOptionPane.OK_OPTION
				);
		ct.clear(mp.cc);
	}

	boolean passChk(char[] cc) {
		String buf = "";
		for (int i = 0; i < cc.length; i++) {
			buf += cc[i];
		}
		return me.getPass().equals(buf);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(submit)) {
			
			pass = "";
			for (int j = 0; j < tf_pass.getPassword().length; j++) 
				pass += tf_pass.getPassword()[j];
			
			if (pass.matches("^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{6,13}$")) {
				me.setPass(pass);
				me.setPhone(tfArr[4].getText());
				me.setHintanswer(tfArr[7].getText());

				ct.sender(me, "정보수정");
				me = ct.getMvo();
				JOptionPane.showMessageDialog(this, me.getId() + "님의 정보가 수정되었습니다.");

				pnArr.get(1).setVisible(false);
				pnArr.get(0).setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog (
						this,
						"비밀번호를 확인해 주세요.",
						"비밀번호 재입력 요청", 
						JOptionPane.OK_OPTION
						);
			}
		}
		if (e.getSource().equals(submit_back)) {
			pnArr.get(1).setVisible(false);
			pnArr.get(0).setVisible(true);
		}

		if (e.getSource().equals(btnArr[0])) {
			chargePointDialog();	
		}
		if (e.getSource().equals(btnArr[1])) {
			if(passChkDialog())
				infoUpdate();
		}
		if (e.getSource().equals(btnArr[2])) {
			if (passChkDialog()) {
				delete();
			}
		}
	}

	class BtnAction implements ActionListener {

		OrderVO vo;

		public BtnAction(OrderVO vo) {
			// TODO Auto-generated constructor stub
			this.vo = vo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			if(e.getActionCommand().equals("취소")){
				ct.getMvo().setGrade(ct.getMvo().getGrade()+vo.getEa());
				ct.sender(ct.getMvo(), "등업");
			}
			
			ct.sender(vo, e.getActionCommand());
			JOptionPane.showMessageDialog(new JOptionPane(), e.getActionCommand()+"완료");
			order();
		}

	}

}
