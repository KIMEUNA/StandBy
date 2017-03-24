package Cafe_s.Frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Cafe_s.controller.Controller;
import Cafe_s.model.service.StockVO;
import service.CoffeeVO;

/**
 * 기간 검색
 */
public class StockPanel extends JPanel implements ActionListener {

	MainPanel main;

	JPanel pn = new JPanel();
	JPanel pn_view = new JPanel();
	JScrollPane sp_view = new JScrollPane(pn_view);

	String[] stockBtnArr = { "입고", "출고" };
	JButton[] stockBtn = new JButton[stockBtnArr.length];

	String[][] stockArr = {
			{ "상품번호", "이름", "입고량", "출고량", "잔고량" },
			{ "상품번호", "이름", "수량", "날짜", "비고" }
			};
	
	JLabel[] lbArr;

	String[] arr = { "시작", "끝" };
	JButton[] btn = new JButton[arr.length];
	JLabel[] lb = new JLabel[btn.length];

	String[] schArr = { "전체", "입고내역", "출고내역" };
	JRadioButton[] rbtn;
	ButtonGroup btng = new ButtonGroup();
	JButton btn_sch = new JButton("검색");

	Controller cc = new Controller();
	ArrayList<StockVO> list;
	ArrayList<StockVO> buf;
	
	
	Date[] date = new Date[btn.length];

	int flag;

	public StockPanel(MainPanel main) {
		// TODO Auto-generated constructor stub

		this.main = main;

		setBounds(250, 100, 774, 628);
		setLayout(null);

		pn.setBounds(30, 100, 714, 60);
		pn.setLayout(new GridLayout(0, 5));

		sp_view.setBounds(30, 160, 714, 418);
		pn_view.setLayout(new GridLayout(0, 1));

		add(pn);
		add(sp_view);

		dataInit();

		stockSch(new Date(1970 - 01 - 01), new Date(116, 11, 30));
		
		add(btn_sch);
	}

	void dataInit() {
		btn_sch.setBounds(650, 20, 70, 30);
		btn_sch.setBorder(null);
		btn_sch.addActionListener(this);

		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(new ImageIcon("img\\달력.png"));
			btn[i].setBounds(423 + i * 150, 15, 20, 20);
			btn[i].setBorder(null);
			btn[i].addActionListener(this);

			lb[i] = new JLabel();
			lb[i].setBounds(300 + i * 150, 15, 120, 30);
//			lb[i].setBackground(Color.white);
			lb[i].setOpaque(true);

			add(btn[i]);
			add(lb[i]);
		}

		rbtn = new JRadioButton[schArr.length];
		for (int i = 0; i < schArr.length; i++) {
			rbtn[i] = new JRadioButton(schArr[i]);
			rbtn[i].setBounds(300 + i * 120, 55, 110, 30);
			btng.add(rbtn[i]);
			add(rbtn[i]);
		}
		rbtn[0].setSelected(true);
		for (int i = 0; i < stockBtn.length; i++) {
			stockBtn[i] = new JButton(stockBtnArr[i]);
			stockBtn[i].setBounds(20 + i * 100, 55, 100, 30);
			stockBtn[i].addActionListener(this);
			add(stockBtn[i]);
		}
	}

	void stockSch(Date start, Date end) {

		pn.removeAll();
		pn.revalidate();
		pn.repaint();
		pn_view.removeAll();
		pn_view.revalidate();
		pn_view.repaint();

		System.out.println(start);
		System.out.println(end);

		int flag = 0;

		for (flag = 0; flag < rbtn.length; flag++)
			if (rbtn[flag].isSelected())
				break;
		list = cc.stock_select(start, end, flag);
		
		if(flag == 0)
			buf = list;
		
		int index = flag == 0 ? 0:1;
		
		lbArr = new JLabel[stockArr[index].length];
		for (int i = 0; i < stockArr[index].length; i++) {
			lbArr[i] = new JLabel(stockArr[index][i]);
			lbArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			pn.add(lbArr[i]);
		}
		
		String[] buf = null;

		for (int i = 0; i < list.size(); i++) {
			if (flag == 0)
				buf = list.get(i).toString().split(",");
			else if(flag == 1)
				buf = list.get(i).inStock().split(",");
			else if(flag == 2)
				buf = list.get(i).outStock().split(",");
			JPanel pn_line = new JPanel(new GridLayout(0, buf.length));
			
			for (int j = 0; j < buf.length; j++) {
				System.out.println(buf[j]);
				JLabel lb = new JLabel(buf[j]);
				lb.setHorizontalAlignment(SwingConstants.CENTER);
				pn_line.add(lb);
			}
			pn_view.add(pn_line);
		}
		for (int j = pn_view.getComponentCount(); j < 20; j++) {
			pn_view.add(new JLabel());
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getSource().equals(btn_sch)) {
			for (int i = 0; i < date.length; i++)
				if (date[i] == null) {
					JOptionPane.showMessageDialog(new JOptionPane(), "검색 기간을 선택하세요.", "날짜 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			stockSch(date[0], date[1]);
		}

		for (int i = 0; i < btn.length; i++) {
			if (e.getSource().equals(btn[i])) {
				if (date[0] == null && i == 1) {
					JOptionPane.showMessageDialog(new JOptionPane(), "시작일을 선택해주세요.", "날짜 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				flag = i;
				new CalenFrame(this);
			}
		}

		for (int i = 0; i < stockBtn.length; i++) {
			if (e.getSource().equals(stockBtn[i]))
				new Stock(stockBtn[i].getActionCommand());
		}
	}

	class Stock extends JFrame implements ActionListener {

		StockVO vo;

		String[] arr = { "상품: ", "수량: ", "날짜: " };
		JLabel[] lb = new JLabel[arr.length + 1];

		ArrayList<CoffeeVO> list;

		Vector<String> vec = new Vector<>();
		JComboBox<String> cb = new JComboBox<>(vec);
		Vector<String> vec_ea = new Vector<>();
		JComboBox<Integer> cb_ea = new JComboBox<>();

		String[] btnArr = { "확인", "취소" };
		JButton[] btn = new JButton[btnArr.length + 1];

		String stat;

		public Stock(String stat) {
			// TODO Auto-generated constructor stub
			super("상품 입출고");
			setBounds(450, 150, 400, 400);
			setLayout(null);

			this.stat = stat;

			vo = new StockVO();

			cb.setBounds(138, 40, 130, 30);
			cb_ea.setBounds(138, 73, 50, 30);

			for (int i = 0; i < 10; i++) {
				cb_ea.addItem(i + 1);
			}
			dataInit();

			for (int i = 0; i < btn.length; i++) {
				if (i != btn.length - 1) {
					btn[i] = new JButton(btnArr[i]);
					btn[i].setBounds(130 + i * 80, 200, 70, 30);
				} else {
					btn[i] = new JButton(new ImageIcon("img\\달력.png"));
					btn[i].setBounds(240, 106, 20, 20);
				}
				btn[i].setBorder(null);
				btn[i].addActionListener(this);
				add(btn[i]);
			}

			add(cb);
			add(cb_ea);

			setVisible(true);
			addWindowListener(new WinClose());

		}

		void dataInit() {

			list = cc.c_select();

			for (int i = 0; i < arr.length; i++) {
				lb[i] = new JLabel(arr[i] + ": ");
				lb[i].setBounds(30, 40 + i * 33, 100, 30);
				lb[i].setHorizontalAlignment(SwingConstants.RIGHT);
				add(lb[i]);
			}

			for (int i = arr.length; i < lb.length; i++) {
				lb[i] = new JLabel();
				lb[i].setBounds(138, 106 + (i - arr.length) * 33, 100, 30);
//				lb[i].setBackground(Color.lightGray);
				lb[i].setOpaque(true);
				add(lb[i]);
			}

			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).getCate().equals("상품"))
					continue;
				vec.add(list.get(i).getC_name());
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			for (int i = 0; i < btn.length; i++) {
				if (e.getSource().equals(btn[i])) {
					if (i == 0) {
						for (int j = 0; j < list.size(); j++) {
							if (list.get(j).getC_name().equals(cb.getSelectedItem())) {
								try {
									System.out.println(cb.getSelectedItem());
									vo.setItem_id(list.get(j).getC_seq());
									vo.setItem(list.get(j).getC_name());
									if(stat.equals("입고")){
										vo.setIn(cb_ea.getSelectedIndex() + 1);
										vo.setOut(0);
										for (int k = 0; k < buf.size(); k++) 
											if(buf.get(k).getItem_id() == vo.getItem_id())
												buf.get(k).setTotal(vo.getIn());
									}
									else{
										vo.setIn(0);
										vo.setOut(cb_ea.getSelectedIndex() + 1);
										for (int k = 0; k < buf.size(); k++) {
											if(buf.get(k).getItem_id() == vo.getItem_id()){
												System.out.println(buf.get(k).getTotal());
												if(buf.get(k).getTotal() - vo.getOut() < 0){
													JOptionPane.showMessageDialog(this, "남은 재고가 없습니다.");
													return;
												}
											}
										}
									}
									vo.setStat(stat);
									vo.setRegDate(new SimpleDateFormat("yyyy-MM-dd").parse(lb[3].getText()));
								} catch (ParseException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
								cc.stock_insert(vo);
								System.out.println(vo.toString());
							}
						}
					}
					if (i == 0 && i == 1) {
						dispose();
					} else if (i == 2) {
						new CalenFrame(this);
					}
				}
			}

		}

	}
}
