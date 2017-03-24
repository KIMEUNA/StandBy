package Cafe_s.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Cafe_s.controller.Controller;
import service.CoffeeVO;

/**
 * 메뉴관리
 */
public class MenuPanel extends JPanel implements ActionListener {

	JPanel pn_view = new JPanel(new GridLayout(0, 5, 10, 10));
	JScrollPane sp_view = new JScrollPane(pn_view);

	JButton btn = new JButton("등록");

	Controller cc = new Controller();
	ArrayList<CoffeeVO> list;
	CoffeeVO cvo;

	MainPanel main;

	JButton[] btn_img;
	
	int flag;

	public MenuPanel(MainPanel main) {
		// TODO Auto-generated constructor stub
		setBounds(250, 100, 774, 628);
//		setBackground(Color.lightGray);
		setLayout(null);

		System.out.println("생성자당");

		sp_view.setBounds(10, 100, 754, 478);

		this.main = main;

		btn.setBounds(100, 30, 130, 30);
//		btn.setBackground(Color.orange);
		btn.setBorder(null);
		btn.addActionListener(this);

		view();

		add(btn);
		add(sp_view);
	}

	void view() {
		pn_view.removeAll();
		pn_view.revalidate();
		pn_view.repaint();
//		pn_view.setBackground(Color.white);

		list = cc.c_select();

		System.out.println("list: " + list.size());
		
		btn_img = new JButton[list.size()];
		
		for (int i = 0; i < list.size(); i++) {
			JPanel pn = new JPanel(new BorderLayout());
//			pn.setBackground(Color.white);

			btn_img[i] = new JButton(new ImageIcon(list.get(i).getImg()));
			JLabel lb_menu = new JLabel(list.get(i).getC_name());
			String buf = new DecimalFormat("#,##0").format(list.get(i).getPrice());
			JLabel lb_price = new JLabel(buf + "원");

			btn_img[i].setBorder(null);
//			btn_img[i].setBackground(Color.white);
			btn_img[i].addActionListener(this);
			
			lb_menu.setHorizontalAlignment(SwingConstants.CENTER);
			lb_price.setHorizontalAlignment(SwingConstants.CENTER);

			pn.add(btn_img[i], "North");
			pn.add(lb_menu);
			pn.add(lb_price, "South");
			pn_view.add(pn);
		}
		
		for (int i = pn_view.getComponentCount(); i < 20; i++) {
			pn_view.add(new JLabel());
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(btn) && main.gradeChk()) {
			flag = 0;
			new MenuInsert();
		}
		for (int i = 0; i < btn_img.length; i++) {
			if(e.getSource().equals(btn_img[i])){
				new BtnAction(list.get(i));
			}
		}
	}
	
	class BtnAction extends JDialog implements ActionListener{
		
		CoffeeVO vo;
		
		String[] arr = {"수정", "삭제"};
		JButton[] btn = new JButton[arr.length];
		
		public BtnAction(CoffeeVO vo) {
			// TODO Auto-generated constructor stub
			super(new JFrame(), "선택", true);
			this.vo = vo;
			setBounds(300, 200, 250, 300);
			setLayout(null);
			
			dataInit();
			
			setVisible(true);
		}
		
		void dataInit(){
			
			JLabel lb_img = new JLabel(new ImageIcon(vo.getImg()));
			JLabel lb_menu = new JLabel(vo.getC_name());
			String buf = new DecimalFormat("#,##0").format(vo.getPrice());
			JLabel lb_price = new JLabel(buf + "원");
			
			lb_img.setBounds(90, 50, 70, 70);
			lb_menu.setBounds(90, 130, 70, 30);
			lb_price.setBounds(90, 170, 70, 30);
			
			lb_img.setHorizontalAlignment(SwingConstants.CENTER);
			lb_menu.setHorizontalAlignment(SwingConstants.CENTER);
			lb_price.setHorizontalAlignment(SwingConstants.CENTER);
			
			for (int i = 0; i < arr.length; i++) {
				btn[i] = new JButton(arr[i]);
				btn[i].setBounds(50+i*75, 220, 70, 30);
				btn[i].addActionListener(this);
				add(btn[i]);
			}
			
			add(lb_img);
			add(lb_menu);
			add(lb_price);
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
			setVisible(false);
			
			if (e.getSource().equals(btn[0])) { // 수정
				new MenuInsert(vo);
			} else if (e.getSource().equals(btn[1])) { // 삭제
				cc.c_delete(vo);
				JOptionPane.showMessageDialog(this, "삭제되었습니다.");
				view();
			}
			
			
			
		}
	}

	void message() {
		JOptionPane.showMessageDialog(this, "등록 되었습니다.", "등록", JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * 메뉴 추가 클래스.
	 */
	class MenuInsert extends JFrame implements ActionListener {
		
		String[] cateArr = { "음료", "푸드", "상품" };
		String[][] cate_Arr = { 
				{ "에스프레소", "프라푸치노", "스무디", "병음료" }, 
				{ "베이커리", "케익", "샌드위치&샐러드" },
				{ "에스프레소 로스트", "블론드 로스트", "텀블러", "머그컵" } };

		String[] btnArr = { "확인", "취소" };

		Vector<String> vc_cate = new Vector<>();
		JComboBox<String> cb_cate = new JComboBox<>(vc_cate);

		Vector<String>[] vcArr = new Vector[cateArr.length];
		JComboBox<String>[] cbArr = new JComboBox[cateArr.length];

		String[] lbStr = {"메뉴 이름:", "메뉴 가격:"};
		JLabel[] lbArr = new JLabel[lbStr.length+1];
		
		JTextField[] tf = new JTextField[2];

		JButton[] btn = new JButton[btnArr.length];
		JButton btn_img = new JButton(new ImageIcon("img\\img.png"));
		JCheckBox cb;
		
		boolean insertChk = true;

		public MenuInsert() {
			// TODO Auto-generated constructor stub
			setBounds(600, 150, 400, 400);
			setLayout(null);

			cvo = new CoffeeVO();

			for (int i = 0; i < cateArr.length; i++) {
				vc_cate.add(cateArr[i]);
			}
			cb_cate.setBounds(30, 50, 100, 30);
			cb_cate.setSelectedIndex(0);

			for (int i = 0; i < cate_Arr.length; i++) {
				vcArr[i] = new Vector<>();
				cbArr[i] = new JComboBox<>(vcArr[i]);
				for (int j = 0; j < cate_Arr[i].length; j++) {
					vcArr[i].add(cate_Arr[i][j]);
				}
				cbArr[i].setBounds(140, 50, 100, 30);
				cbArr[i].setSelectedIndex(0);
				cbArr[i].addActionListener(this);
				cbArr[i].setVisible(false);
				add(cbArr[i]);
			}
			
			for (int i = 0; i < lbStr.length; i++) {
				lbArr[i] = new JLabel(lbStr[i]);
				lbArr[i].setBounds(30, 120+i*33, 80, 30);
				add(lbArr[i]);
			}
			lbArr[2] = new JLabel("이미지 버튼 클릭->");
			lbArr[2].setBounds(110, 186, 150, 30);
			btn_img.setBounds(265, 186, 20,	20);
			
			for (int i = 0; i < tf.length; i++) {
				tf[i] = new JTextField();
				tf[i].setBounds(110, 120+i*33, 200, 30);
				add(tf[i]);
			}

			for (int i = 0; i < btnArr.length; i++) {
				btn[i] = new JButton(btnArr[i]);
				btn[i].setBounds(80 + i * 110, 230, 100, 30);
				btn[i].addActionListener(this);
				add(btn[i]);
			}
			
			btn_img.addActionListener(this);
			cb_cate.addActionListener(this);
			cb_cate.setVisible(true);
			cbArr[0].setVisible(true);

			add(lbArr[2]);
			add(btn_img);
			add(cb_cate);

			setVisible(true);
			addWindowListener(new WinClose());
		}

		MenuInsert(CoffeeVO vo) {
			this();
			cvo = vo;
			insertChk = false;

			for (int i = 0; i < cateArr.length; i++) {
				if (cateArr[i].equals(vo.getCate())) {
					cb_cate.setSelectedIndex(i);
					for (int j = 0; j < cate_Arr[i].length; j++) {
						if (cate_Arr[i][j].equals(vo.getGroup())) {
							cbArr[i].setSelectedIndex(j);
						}
					}
				}
			}
			tf[0].setText(vo.getC_name());
			tf[1].setText(vo.getPrice() + "");
			lbArr[2].setText(vo.getImg());

		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			for (int i = 0; i < cate_Arr.length; i++) { // 첫번째 카테고리에 따라 콤보박스를 나타낸다.
				cbArr[i].setVisible(false);
				if (cb_cate.getSelectedItem().equals(cateArr[i]))
					cbArr[i].setVisible(true);
			}
			
			if (e.getSource().equals(btn_img)) {
				FileDialog fd = new FileDialog(this, "img", FileDialog.LOAD);
				fd.setVisible(true);
				System.out.println(fd.getFile());
				lbArr[2].setText(fd.getFile());
			}

			for (int i = 0; i < btn.length; i++) {
				if (e.getSource().equals(btn[i])) {
					if (i == 0) {
						boolean chk = false;
						boolean gapChk = !(tf[0].getText().length() > 0) || !(tf[1].getText().length() > 0);
						boolean priceChk = !tf[1].getText().matches("[0-9]*");
						for (CoffeeVO cc : list) {
							if (tf[0].getText().equals(cc.getC_name()))
								chk = true;
								if(cc.getC_name().equals(cvo.getC_name()))
									chk = false;
								
						}
						if (chk)
							JOptionPane.showMessageDialog(this, "같은 이름의 메뉴가 있습니다.", "메뉴 등록 오류",
									JOptionPane.ERROR_MESSAGE);

						if (gapChk)
							JOptionPane.showMessageDialog(this, "공백", "메뉴 추가 오류", JOptionPane.ERROR_MESSAGE);
						if (priceChk)
							JOptionPane.showMessageDialog(this, "가격은 숫자만 입력하세요.", "메뉴 등록 오류",
									JOptionPane.ERROR_MESSAGE);
						if (chk || gapChk || priceChk)
							return;
						
						
						cvo.setC_name(tf[0].getText());
						cvo.setCate(cb_cate.getSelectedItem().toString());
						for (int j = 0; j < cbArr.length; j++) { // 분류에 따라 값 세팅.
							if (cb_cate.getSelectedIndex() == j)
								cvo.setGroup(cbArr[j].getSelectedItem().toString());
						}
						cvo.setPrice(Integer.parseInt(tf[1].getText()));
						String path = "";
						if(insertChk)
							path = "img\\menu\\";
						cvo.setImg(path+lbArr[2].getText());
						for (int j = 0; j < cate_Arr.length; j++) {
							for (int k = 0; k < cate_Arr[j].length; k++) {
								if (cvo.getGroup().equals(cate_Arr[j][k])) {
									cvo.setSort(j + "-" + k);
									break;
								}
							}
						}
						if (insertChk)
							cc.c_insert(cvo);
						else
							cc.c_update(cvo);

						view();
						message();
					}
					dispose();
				}
			}
		}
	}
}
