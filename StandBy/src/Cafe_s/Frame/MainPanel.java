package Cafe_s.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Cafe_s.controller.Controller;
import Cafe_s.model.service.EmployeeVO;

//로그인 후 메인화면
public class MainPanel extends JPanel implements ActionListener {

	CafeServer cs;
	EmployeeVO evo;
	Controller cc = new Controller();
	
	JButton btn_logout = new JButton("로그아웃");
	JButton btn_me = new JButton("내 정보");
	JLabel lb;

	String[] cateArr = { "관리" };
	String[][] cateStrArr = { { "판매현황", "메뉴관리", "회원관리", "직원관리", "재고관리" }};

	Vector<String> categoryArr = new Vector<>();
	JComboBox<String> cb_category = new JComboBox<>(categoryArr);

	Vector<String> menuArr;
	JComboBox<String>[] cb_menu = new JComboBox[cateStrArr.length];

//	SeatPanel pn_seat = null;
	MenuPanel pn_menu = null;
	MemberPanel pn_mb = null;
	EmployeePanel pn_em = null;
	SalesPanel pn_sale = null;
	StockPanel pn_stock = null;

	public MainPanel(CafeServer cs) {
		setBounds(0, 0, 1024, 100);
		setLayout(null);

		this.cs = cs;
		evo = cs.evo;
		
		lb = new JLabel(cs.evo.getE_name() + "님 접속");
		
		btn_logout.setBounds(850, 15, 100, 30);
		btn_me.setBounds(850, 50, 100, 30);
		btn_logout.addActionListener(this);
		btn_me.addActionListener(this);
		add(btn_logout);
		add(btn_me);
		
		lb.setBounds(700, 15, 200, 30);
		add(lb);

		for (int i = 0; i < cateArr.length; i++) {
			categoryArr.add(cateArr[i]);
		}
		cb_category.setBounds(100, 45, 200, 30);
		cb_category.setSelectedIndex(0);
		cb_category.addActionListener(this);

		for (int i = 0; i < cateStrArr.length; i++) {
			menuArr = new Vector<>();
			cb_menu[i] = new JComboBox<>(menuArr);
			for (int j = 0; j < cateStrArr[i].length; j++) {
				menuArr.add(cateStrArr[i][j]);
			}
			cb_menu[i].setBounds(350, 45, 200, 30);
			cb_menu[i].setSelectedIndex(0);
			cb_menu[i].addActionListener(this);
			add(cb_menu[i]);

		}
		
//		pn_seat = new SeatPanel(this);
		pn_menu = new MenuPanel(this);
		pn_mb = new MemberPanel(this);
		pn_em = new EmployeePanel(this);
		pn_sale = new SalesPanel(this);
		pn_stock = new StockPanel(this);
		
		pn_menu.setVisible(false);
//		pn_seat.setVisible(false);
		pn_mb.setVisible(false);
		pn_em.setVisible(false);
		pn_sale.setVisible(false);
		pn_stock.setVisible(false);

		add(cb_category);
		
		cs.add(cs.pn_order);
		cs.add(pn_menu);
		cs.add(pn_mb);
		cs.add(pn_em);
		cs.add(pn_sale);
//		cs.add(pn_seat);
		cs.add(pn_stock);

		cs.add(this);
	}
	
	boolean gradeChk(){
		boolean chk = true;
		if (!evo.getGrade().equals("관리자")) {
			JOptionPane.showMessageDialog(this, "권한이 없습니다.");
			chk = false;
		}
		return chk;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		pn_menu.setVisible(false);
		pn_mb.setVisible(false);
		pn_em.setVisible(false);
		pn_sale.setVisible(false);
//		pn_seat.setVisible(false);
		pn_stock.setVisible(false);

		if (e.getSource().equals(btn_logout)) { // 로그아웃 버튼 클릭 시
			cc.logout(cs);
		}
		else if(e.getSource().equals(btn_me)) {	// 내 정보 버튼 클릭 시
			EmployeePanel ep = new EmployeePanel(this);
			ep.setVisible(false);
			ep.new EMInfo(evo, 0);		// 본인 정보 수정할 때 (비밀번호, 연봉 수정가능)
		}
		else
			for (int i = 0; i < cateArr.length; i++) {
				cb_menu[i].setVisible(false);
				if (cb_category.getSelectedItem().toString().equals(cateArr[0])) {
					cb_menu[i].setVisible(true);

					if (cb_menu[i].getSelectedItem().toString().equals(cateStrArr[0][0])) {
						pn_sale.setVisible(true);
					}
					if (cb_menu[i].getSelectedItem().toString().equals(cateStrArr[0][1])) {
						pn_menu.view();
						pn_menu.setVisible(true);
					}
					if (cb_menu[i].getSelectedItem().toString().equals(cateStrArr[0][2])) {
						pn_mb.setVisible(true);
					}
					if (cb_menu[i].getSelectedItem().toString().equals(cateStrArr[0][3]) && gradeChk()) {
						pn_em.setVisible(true);
					}
					if (cb_menu[i].getSelectedItem().toString().equals(cateStrArr[0][4]) && gradeChk()) {
						pn_stock.setVisible(true);
					}
				}
//				if (cb_category.getSelectedItem().toString().equals(cateArr[1])) {
//					pn_seat.setVisible(true);
//				}
			}
	}
}