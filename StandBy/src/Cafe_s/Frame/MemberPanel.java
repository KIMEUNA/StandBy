package Cafe_s.Frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Cafe_s.controller.Controller;
import service.MemberVO;

public class MemberPanel extends JPanel implements ActionListener {

	MainPanel main;

	JPanel pn = new JPanel(new GridLayout(0, 5));
	JPanel pn_view = new JPanel(new GridLayout(0, 5));
	JScrollPane sp_view = new JScrollPane(pn_view);

	String[] arr = { "아이디", "이름", "회원등급", "포인트", "가입일" };
	String[] flagArr = { "일반회원", "블랙리스트", "전체회원" };

	Vector<String> vec = new Vector<>();
	JComboBox<String> cb = new JComboBox<>(vec);

	JButton[] btnArr = new JButton[arr.length];
	JLabel[] lbArr = new JLabel[arr.length];
	JLabel lb;

	Controller cc = new Controller();
	ArrayList<MemberVO> list;

	int flag = 2;

	public MemberPanel(MainPanel main) {
		// TODO Auto-generated constructor stub
		this.main = main;

		setBounds(250, 100, 774, 628);
//		setBackground(Color.gray);
		setLayout(null);
		
//		pn_view.setBackground(Color.white);
		sp_view.setBounds(10, 130, 754, 478);
		pn.setBounds(10, 100, 754, 30);

		for (int i = 0; i < flagArr.length; i++)
			vec.add(flagArr[i]);

		cb.setBounds(30, 30, 100, 30);
		cb.setSelectedIndex(2);
		cb.addActionListener(this);

		for (int i = 0; i < lbArr.length; i++) {
			lbArr[i] = new JLabel(arr[i]);
			lbArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			pn.add(lbArr[i]);
		}
		
		list = cc.m_select();

		dataInit();

		add(pn);
		add(cb);
		add(sp_view);

	}

	void dataInit() {

		pn_view.removeAll();
		pn_view.revalidate();
		pn_view.repaint();

		int height = 0;
		for (int i = 0; i < list.size(); i++) {
			if (flag != 2)
				if (list.get(i).getBlock() != flag) {
					height++;
					continue;
				}
			System.out.println(list.get(i).toString());
			String[] buf = list.get(i).toString().split(",");
			for (int j = 0; j < buf.length; j++) {
				btnArr[j] = new JButton(buf[j]);
				btnArr[j].setBorder(null);
				btnArr[j].setBackground(Color.white);
				btnArr[j].setHorizontalAlignment(SwingConstants.CENTER);
				btnArr[j].addActionListener(new BtnAction(list.get(i)));
				pn_view.add(btnArr[j]);
			}
		}
		
		for (int i = pn_view.getComponentCount(); i < 50; i++) {
			pn_view.add(new JLabel(""));
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getActionCommand().equals(cb.getActionCommand())) {
			for (int i = 0; i < flagArr.length; i++) {
				if (cb.getSelectedIndex() == i) {
					flag = i;
				}
			}
			dataInit();
		}
	}

	class BtnAction implements ActionListener {

		MemberVO vo;

		public BtnAction(MemberVO vo) {
			// TODO Auto-generated constructor stub
			this.vo = vo;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MemberInfo(vo);
		}
	}

	class MemberInfo extends JFrame implements ActionListener {

		String[] arr = { "회원 ID: ", "회원 성명: ", "성별: ", "회원 등급: ", "연락처: ", "생일: ", "회원 가입일: " };
		JLabel[] lb = new JLabel[arr.length];

		JButton btn;
		MemberVO vo;

		public MemberInfo(MemberVO vo) {
			// TODO Auto-generated constructor stub
			this.vo = vo;

			setBounds(300, 100, 200, 370);
			setLayout(null);

			dataInit();

			setVisible(true);
			addWindowListener(new WinClose());
		}

		void dataInit() {

			for (int i = 0; i < arr.length; i++) {
				String[] buf = vo.info().split(",");
				System.out.println(arr[i] + buf[i]);
				lb[i] = new JLabel(arr[i] + buf[i]);
				lb[i].setBounds(20, 20 + i * 38, 250, 30);
				add(lb[i]);
			}

			if (vo.getBlock() == 0) {
				btn = new JButton("나빠요");
				btn.setBounds(50, 280, 100, 30);
				btn.addActionListener(this);
				btn.setBorder(null);
				add(btn);
			}

		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			int res = JOptionPane.showConfirmDialog(new JOptionPane(), 
					vo.getId() + " 고객님이 블랙리스트가 됩니다.", "블랙~~",
					JOptionPane.OK_CANCEL_OPTION);
			
			if (res == JOptionPane.OK_OPTION){
				cc.block_update(vo);
				
			}
			
		}

	}

}
