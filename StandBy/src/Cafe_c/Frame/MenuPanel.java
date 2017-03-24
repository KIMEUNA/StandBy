package Cafe_c.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Cafe_c.Frame.MainPanel;
import Cafe_c.controller.Controller;
import service.CoffeeVO;
import service.MySbVO;

public class MenuPanel extends JPanel implements ActionListener {

	MainPanel mp;
	OrderPanel od;
	Controller ct = new Controller();

	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");

	ArrayList<JPanel> pnArr = new ArrayList<>();
	ArrayList<CoffeeVO> cvoArr = new ArrayList<>();
	ArrayList<MySbVO> sbvoArr = null;
	JButton btn;
	JButton btn_like;
	
	JLabel lb_name, lb_price, lb_img, lb_title, lb_id, lb_regdate, lb_like;

	JPanel pn_menu = new JPanel();
	JPanel pn_mysb = new JPanel();

	String[][] category = { 
			{ "" }, 
			{ "전체", "에스프레소", "프라푸치노", "스무디", "병음료" }, 
			{ "전체", "베이커리", "케익", "샌드위치&샐러드" },
			{ "전체", "로스트", "텀블러", "머그컵" } };

	HashMap<String, JComboBox<String>> cateMap = new HashMap<>();

	JComboBox<String>[] cb_category = new JComboBox[category.length];

	ImageIcon icon;
	
	int price;

	public MenuPanel(MainPanel mp) {

		this.mp = mp;

		pnArr.add(pn_menu);
		pnArr.add(pn_mysb);

		setBounds(50, 100, 900, 50);
		setLayout(null);
		setBackground(Color.orange);

		for (int i = 0; i < category.length; i++) {
			cb_category[i] = new JComboBox<>();
			String str = mp.category2[0][i];
			for (String cate : category[i])
				cb_category[i].addItem(cate);

			cateMap.put(str, cb_category[i]);
			cateMap.get(str).setVisible(true);
			cateMap.get(str).setBounds(50, 15, 200, 30);
			cateMap.get(str).setSelectedIndex(0);
			cateMap.get(str).addActionListener(this);
			add(cateMap.get(str));

		}
		
		pn_mysb.setBounds(50, 150, 900, 460);
		pn_mysb.setLayout(null);
		pn_mysb.setBackground(Color.pink);

		mp.cb_category1[1].addActionListener(this);
		mp.cb_category2[0].addActionListener(this);
		mp.cb_category2[1].addActionListener(this);

		setVisible(false);
	}

	// 카테고리별로 메뉴 불러오기
	public void loadMenu(String cate1, String cate2) {
		System.out.println("loadMenu");
		ct.sender(cvoArr, "메뉴"); // 전체메뉴 불러오기
		cvoArr = ct.getCvoArr();
		
		int x = 0, y = 0;

		pn_menu.setBounds(50, 150, 900, 460);
		pn_menu.setLayout(null);
		pn_menu.setBackground(Color.pink);

		for (CoffeeVO vo : cvoArr) {

			price = (int)(vo.getPrice() * 
					(ct.getMvo().getGrade() < 10 ? 0 : ct.getMvo().getGrade() < 30 ? 0.05 : 0.1));
			
			if (!cate1.equals("전체 메뉴")) {
				if (!vo.getCate().equals(cate1))
					continue;
				if (!cate2.equals("전체")) {
					if (!vo.getGroup().equals(cate2))
						continue;
				}
			}

			File ff = new File(vo.getImg());

			if (!ff.exists()) {
				ct.sender(vo, "파일없음");
				System.out.println();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			icon = new ImageIcon(vo.getImg());
			btn = new JButton(icon);
			od = new OrderPanel(this, vo);
			pnArr.add(od);
			btn.addActionListener(od);
			
			lb_name = new JLabel(vo.getC_name());
			lb_price = new JLabel(new DecimalFormat("#,##0").format(vo.getPrice() - price)+"원");
			lb_price.setHorizontalAlignment(SwingConstants.RIGHT);

			btn.setBounds(50 + 120 * x, 15 + 130 * y, 70, 70);
			lb_name.setBounds(50 + 120 * x, 90 + 130 * y, 90, 15);
			lb_price.setBounds(50 + 120 * x, 110 + 130 * y, 80, 15);

			pn_menu.add(btn);
			pn_menu.add(lb_name);
			pn_menu.add(lb_price);

			x++;

			if (x == 7) {
				y++;
				x = 0;
			}
		}

		pn_menu.setVisible(true);
		mp.cc.add(pn_menu);
	}

	public void loadMySb(int type) {
		int y = 0;
		pn_mysb.removeAll();
		pn_mysb.revalidate();
		pn_mysb.repaint();
		
		if(type == 0) 
			ct.sender(ct.getMvo(), "나만의 스탠바이"); // 나만의 스탠바이 불러오기			
		else if(type == 1) 
			ct.sender(null, "너와나의 스탠바이"); // 너와나의 스탠바이 불러오기
		
		sbvoArr = ct.getMySbArr();
		
		for (MySbVO vo : sbvoArr) {
			ArrayList<JLabel> opArr = new ArrayList<>();
			
			if(type == 0) {
				JCheckBox box = new JCheckBox("공유");
				JButton remove = new JButton("X");
				
				box.setBounds(630, 20 + 80 * y, 100, 15);
				remove.setBounds(750, 20 + 80 * y, 25, 25);
				remove.setBorder(null);
				
				box.addActionListener(new MySb(vo, 0));
				remove.addActionListener(new MySb(vo, 4));
				
				if(vo.getSharing() == 0) 
					box.setSelected(false);
				else if(vo.getSharing() == 1) 
					box.setSelected(true);
				
				pn_mysb.add(box);
				pn_mysb.add(remove);
			}			

			if(type == 1) {
				btn_like = new JButton("추천★");
				btn_like.addActionListener(new MySb(vo, 2));
				btn_like.setBounds(630, 20 + 80 * y, 100, 15);
				pn_mysb.add(btn_like);
			}
			
			icon = new ImageIcon(vo.getImg());
			btn = new JButton(icon);
			btn.addActionListener(new MySb(vo, 1));

			lb_title = new JLabel("[ " + vo.getTitle() + " ]");
			lb_id = new JLabel("ID: " + vo.getUser_id());
			lb_regdate = new JLabel("등록일: " + sdf.format(vo.getRegdate()));
			lb_name = new JLabel("출신: " + vo.getName());
			lb_price = new JLabel("가격: " + Integer.toString(vo.getPrice()));
			lb_like = new JLabel("추천수: " + vo.getLike_cnt());

			if (vo.getIce() == 1)
				opArr.add(new JLabel("*아이스 "));
			if (vo.getHot() == 1)
				opArr.add(new JLabel("*핫 "));
			if (vo.getShot() > 0)
				opArr.add(new JLabel("*샷: " + vo.getShot()));
			if (vo.getJavachip() == 1)
				opArr.add(new JLabel(" *자바칩 "));
			if (vo.getCaramelsyrup() == 1)
				opArr.add(new JLabel("*카라멜 시럽 "));
			if (vo.getChocosyrup() == 1)
				opArr.add(new JLabel("*초코 시럽 "));
			if (vo.getWhippingcream() == 1)
				opArr.add(new JLabel("*휘핑크림 "));

			btn.setBounds(50, 15 + 80 * y, 70, 70);
			lb_title.setBounds(150, 20 + 80 * y, 150, 15);
			lb_id.setBounds(310, 20 + 80 * y, 150, 15);
			lb_regdate.setBounds(470, 20 + 80 * y, 150, 15);
			lb_name.setBounds(150, 40 + 80 * y, 150, 15);
			lb_price.setBounds(310, 40 + 80 * y, 150, 15);
			lb_like.setBounds(470, 40 + 80 * y, 150, 15);

			for (int i = 0; i < opArr.size(); i++) {
				opArr.get(i).setBounds(150 + 80 * i, 60 + 80 * y, 80, 15);
				pn_mysb.add(opArr.get(i));
			}

			pn_mysb.add(btn);
			pn_mysb.add(lb_title);
			pn_mysb.add(lb_id);
			pn_mysb.add(lb_regdate);
			pn_mysb.add(lb_name);
			pn_mysb.add(lb_price);
			pn_mysb.add(lb_like);

			y++;
		}

		pn_mysb.setVisible(true);
		mp.cc.add(pn_mysb);
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
		for (JComboBox com : cb)
			com.setVisible(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		String cate1 = null;
		String cate2 = "전체";
		JComboBox<String> buf = null;

		init(cb_category);

		if (mp.cb_category1[1].getSelectedItem().equals("매장 메뉴")) {
			cate1 = mp.cb_category2[0].getSelectedItem().toString();
			buf = cateMap.get(cate1);
			if (!buf.getSelectedItem().equals(""))
				buf.setVisible(true);

			if (e.getSource().equals(buf))
				cate2 = buf.getSelectedItem().toString();

			initPanel(pnArr);
			loadMenu(cate1, cate2);
		}
		if (mp.cb_category1[1].getSelectedItem().equals("고객 메뉴")) {
			if (mp.cb_category2[1].getSelectedItem().equals("나만의 스탠바이")) {
				initPanel(pnArr);
				loadMySb(0);
			}
			if (mp.cb_category2[1].getSelectedItem().equals("너와나의 스탠바이")) {
				initPanel(pnArr);
				loadMySb(1);
			}
		}	
	}
	
	class MySb implements ActionListener {

		MySbVO sbvo;
		
		int type;
		
		public MySb(MySbVO sbvo, int type) {
			// TODO Auto-generated constructor stub
			this.sbvo = sbvo;
			this.type = type;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			if(type == 0 && ((JCheckBox) e.getSource()).isSelected() == false) {
				sbvo.setSharing(0);
				ct.sender(sbvo, "공유");
			}
			if(type == 0 && ((JCheckBox) e.getSource()).isSelected() == true) {
				sbvo.setSharing(1);
				ct.sender(sbvo, "공유");
			}
			if(type == 1)
				new ReserveDialog(new OrderPanel(sbvo));
			if(type == 2) {
				sbvo.setLike_cnt(sbvo.getLike_cnt() + 1);
				ct.sender(sbvo, "추천수 업뎃");
				initPanel(pnArr);
				loadMySb(1);  	// 새로고침을 위함				
			}
			if(type == 4) {
				ct.sender(sbvo, "나만의 스탠바이 삭제");
				initPanel(pnArr);
				loadMySb(0);  	// 새로고침을 위함		
			}
		}
	}

}
