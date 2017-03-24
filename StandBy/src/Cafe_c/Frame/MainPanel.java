package Cafe_c.Frame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Cafe_c.controller.Controller;

// 로그인 후 메인화면
public class MainPanel extends JPanel implements ActionListener {

	CafeClient cc;
	
	MenuPanel mn;
	MyInfoPanel mi;
	
	JButton btn = new JButton("로그아웃");
	JButton chat_btn = new JButton("관리자와 채팅하기");
	JLabel lb;
	
	String[][] category1 = { 
			{ "" },
			{ "매장 메뉴", "고객 메뉴" } };

	String[][] category2 = { 
			{ "전체 메뉴", "음료", "푸드", "상품" }, 
			{ "너와나의 스탠바이", "나만의 스탠바이" } };

	HashMap<String, JComboBox<String>> cateMap1 = new HashMap<>(); // 내 정보, 메뉴, 좌석 현황
	HashMap<String, JComboBox<String>> cateMap2 = new HashMap<>(); // 매장 메뉴, 고객 메뉴

	// 내 정보, 메뉴, 좌석 현황
	JComboBox<String> cb_category = new JComboBox<>();

	// 내 정보-category1[0], 메뉴-category1[1], 좌석현황-category1[2],
	JComboBox<String>[] cb_category1 = new JComboBox[category1.length];
	// category1[1][0] (매장 메뉴) -category2[0], category1[1][0] (고객
	// 메뉴)-category2[1]
	JComboBox<String>[] cb_category2 = new JComboBox[category2.length];

	public MainPanel(CafeClient c) {
		setBounds(0, 0, 1024, 100);
		setLayout(null);

		this.cc = c;		
		
		lb = new JLabel(cc.getMvo().getName() + " 님 환영합니다.");
		
		btn.setBounds(800, 15, 100, 30);
		btn.addActionListener(this);
		
		lb.setBounds(650, 15, 200, 30);
		
		chat_btn.setBounds(800, 55, 150, 30); //채팅버튼
		chat_btn.addActionListener(this);
		
		cb_category.addItem("내 정보");
		cb_category.addItem("메뉴");
		cb_category.setBounds(100, 45, 200, 30);
		cb_category.addActionListener(this);
		

		// 내 정보, 메뉴, 좌석 현황
		for (int i = 0; i < category1.length; i++) {
			cb_category1[i] = new JComboBox<>();
			String str = cb_category.getItemAt(i);
		
			for (String cate : category1[i]) 
				cb_category1[i].addItem(cate);	
			
			cateMap1.put(str, cb_category1[i]);
			cateMap1.get(str).setBounds(330, 45, 200, 30);
			cateMap1.get(str).setVisible(false);
			cateMap1.get(str).setSelectedIndex(0);
			cateMap1.get(str).addActionListener(this);
			add(cateMap1.get(str));
		}

		// 매장 메뉴, 고객 메뉴
		for (int i = 0; i < category2.length; i++) {
			cb_category2[i] = new JComboBox<>();
			String str = category1[1][i]; // 매장 메뉴, 고객 메뉴
			
			for (String cate : category2[i]) 
				cb_category2[i].addItem(cate);
	
			cateMap2.put(str, cb_category2[i]);
			cateMap2.get(str).setBounds(570, 45, 200, 30);
			cateMap2.get(str).setVisible(false);
			cateMap2.get(str).setSelectedIndex(0);
			cateMap2.get(str).addActionListener(this);
			add(cateMap2.get(str));
		}
		
		mn = new MenuPanel(this);
		
		mi = new MyInfoPanel(this);

		add(btn);
		add(lb);
		add(chat_btn);
		add(cb_category);
		
		cc.add(this);
		cc.add(mn);
		cc.add(mi);
		

	}
	
    // 패널 초기화
    public void initPanel(ArrayList<JPanel> pnArr) {
    	for (JPanel pn : pnArr) {
    		pn.removeAll();
    		pn.revalidate();
    		pn.repaint();
    		
    		pn.setVisible(false);
		}
    }

	// 콤보박스 안보이게 초기화
	public void init(JComboBox<String>[] cb) {
		for (JComboBox<String> com : cb)
			com.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		String str = null;
		
		if (e.getSource().equals(cb_category)) {	// 내 정보, 메뉴 콤보박스 클릭 시
			
			if(cb_category.getSelectedIndex() == 0) {	// 콤보박스에서 '내 정보' 선택 시
				mn.setVisible(false);
				init(mn.cb_category);
				init(cb_category1);
				init(cb_category2);
				initPanel(mi.pnArr);
				initPanel(mn.pnArr);			
				mi.setVisible(true);
				mi.order();
			}	
			
			if(cb_category.getSelectedIndex() == 1) {
			str = cb_category.getSelectedItem().toString();
			mi.setVisible(false);
			init(mn.cb_category);
			init(cb_category1);
			init(cb_category2);
			initPanel(mi.pnArr);
			initPanel(mn.pnArr);			

			JComboBox<String> buf = cateMap1.get(str);
			if (!buf.getSelectedItem().equals(""))
				buf.setVisible(true);
			}			
		}		
		
		if (e.getSource().equals(cb_category1[1])) {	// 매장메뉴, 고객메뉴 콤보박스 클릭 시
			str = cb_category1[1].getSelectedItem().toString();
			init(mn.cb_category);
			init(cb_category2);
			initPanel(mn.pnArr);

			cateMap2.get(str).setVisible(true);
			mn.setVisible(true);
		}
		
		if (e.getSource().equals(chat_btn)) // 채팅 버튼 클릭 시 
			cc.cc.getTc().getChat(cc.getMvo());
		
		if (e.getSource().equals(btn)) { // 로그아웃 버튼 클릭 시
			new Controller().clear(cc);
		}
	}
}