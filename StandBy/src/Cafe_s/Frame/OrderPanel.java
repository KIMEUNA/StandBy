package Cafe_s.Frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Cafe_s.controller.Controller;
import service.OrderVO;

/**
 * 주문 현황 클래스
 */
public class OrderPanel extends JPanel{
	
	Controller cc = new Controller();
	ArrayList<OrderVO> list;
	
	JPanel pn_view = new JPanel(new GridLayout(0, 1));
	JScrollPane sp_view = new JScrollPane(pn_view);
	
	public OrderPanel() {
		// TODO Auto-generated constructor stub
		
		setBounds(0, 158, 250, 573);
		setBackground(Color.darkGray);
		setLayout(null);
		
		sp_view.setBounds(10, 10, 230, 530);
		
		dataInit();
		
		add(sp_view);
		
		setVisible(true);
	}
	
	public void dataInit(){
		
		pn_view.removeAll();
//		pn_view.repaint();
//		pn_view.revalidate();
		
		list = cc.newOrder();
		
		String[] arr = {"주문번호: ", "주문 ID: ", "상품이름: ", "사이즈: ", "가격: ", "주문일자: "};
		
		System.out.println("order: "+list.size());
		
		for (int i = 0; i < list.size(); i++) {
			JButton btn = new JButton();
			JPanel pn = new JPanel(new GridLayout(0, 1));
			String[] buf = list.get(i).toString().split(";");
			pn.add(new JLabel());
			
			for (int j = 0; j < arr.length; j++) {
//				System.out.println(arr[j]+buf[j]);
				JLabel lb = new JLabel(arr[j]+buf[j]);
				lb.setHorizontalAlignment(SwingConstants.LEFT);
				pn.add(lb);
			}
			
			pn.add(new JLabel());
			btn.setLayout(new BorderLayout());
//			btn.setBackground(Color.white);
			btn.add(pn);
			btn.addActionListener(new BtnAction(i));
			pn_view.add(btn);
		}
		
		pn_view.add(new JLabel());
		
//		pn_view.setVisible(true);
		
		sp_view.getVerticalScrollBar().setValue(
				sp_view.getVerticalScrollBar().getMaximum());
		sp_view.getVerticalScrollBar().setValue(
				sp_view.getVerticalScrollBar().getMinimum());
	}
	
	class BtnAction implements ActionListener{
		
		int i;
		
		public BtnAction(int i) {
			// TODO Auto-generated constructor stub
			this.i = i;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			int res = JOptionPane.showConfirmDialog(new JOptionPane(), list.get(i).getUser_id()+" 고객님께 음료가 전달됩니다.", "완료",
					JOptionPane.OK_CANCEL_OPTION);
			if(res == JOptionPane.YES_OPTION){
				cc.order_update(list.get(i), 1);
				dataInit();
			}
		}
	}
}
