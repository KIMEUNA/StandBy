package Cafe_c.Frame;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class ReserveDialog extends JDialog implements ActionListener {

	OrderPanel od;
	Date curr = new Date();
	
	String regDate, regMin = "0";

	JLabel lb;
	JButton btn_ok = new JButton("확인");
	JButton btn_back = 	new JButton("취소");	
	JComboBox<Integer> cb_hour = new JComboBox<>();
	JComboBox<Integer> cb_min_curr = new JComboBox<>();
	JComboBox<Integer> cb_min_after = new JComboBox<>();
	
	public ReserveDialog(OrderPanel od) {
		// TODO Auto-generated constructor stub
		super(new Frame(), "예약시간 설정", true);
		this.od = od;
			
		int hour, min;
		
		cb_hour = new JComboBox<>();
		cb_min_curr = new JComboBox<>();
		cb_min_after = new JComboBox<>();
		
		if(curr.getMinutes() >= 55) {
			hour = curr.getHours() + 1;
			min = (curr.getMinutes() + 5) - 60;
		}
		else {
			hour =  curr.getHours();
			min = curr.getMinutes() + 5;
		}
		
		for (int i = hour; i < 24; i++)
			cb_hour.addItem(i);

		for (int j = min; j < 60; j++)
			cb_min_curr.addItem(j);

		for (int j = 0; j < 60; j++)
			cb_min_after.addItem(j);					

		setBounds(300, 200, 530, 250);
		setLayout(null);
		
		lb =  new JLabel("예약시간을 설정해주세요. (현시각 기준 5분 이후부터 가능)");
		JLabel lb_hour = new JLabel("시");
		JLabel lb_min = new JLabel("분");
		
		cb_hour.addActionListener(this);
		cb_min_curr.addActionListener(this);	
		cb_min_after.addActionListener(this);
		btn_ok.addActionListener(this);
		btn_back.addActionListener(this);	
			
		lb.setBounds(80, 30, 400, 30);
		lb_hour.setBounds(210, 70, 30, 30);
		lb_min.setBounds(310, 70, 30, 30);
		cb_hour.setBounds(150, 70, 50, 30);
		cb_min_curr.setBounds(250, 70, 50, 30);
		cb_min_after.setBounds(250, 70, 50, 30);
		btn_ok.setBounds(150, 120, 80, 30);
		btn_back.setBounds(240, 120, 80, 30);
	
		cb_min_curr.setVisible(false);
		cb_min_after.setVisible(false);
		
		add(lb);	
		add(lb_hour);
		add(lb_min);
		add(cb_hour);
		add(cb_min_curr);
		add(cb_min_after);
		add(btn_ok);	
		add(btn_back);

		setVisible(true);	
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		regDate = ""+cb_hour.getSelectedItem()+":"+regMin;
//				new DecimalFormat("00").format(
//				Integer.parseInt(
//						cb_hour.getSelectedItem().toString()))+":" + 
//						new DecimalFormat("00").format(
//								Integer.parseInt(regMin));
		System.out.println("reg: "+regDate);
		if(e.getSource().equals(cb_hour))
		{			
			cb_min_curr.setSelectedIndex(0);
			cb_min_after.setSelectedIndex(0);
			
			if(cb_hour.getSelectedIndex() == 0) {
				cb_min_after.setVisible(false);
				cb_min_curr.setVisible(true);					
			}
			else {
				cb_min_curr.setVisible(false);	
				cb_min_after.setVisible(true);
			}				
		}
		if(e.getSource().equals(cb_min_curr)){
			regMin = ""+cb_min_curr.getSelectedItem();	
		}
		if(e.getSource().equals(cb_min_after)){
			regMin = ""+cb_min_after.getSelectedItem();	
		}
		if(e.getSource().equals(btn_ok))	// 주문 예약 시간 설정 다이얼로그 확인 버튼
		{
			if(od.order(this)) {
				setVisible(false);
				JOptionPane.showMessageDialog(
						od.mn,
						"주문 예약이 완료되었습니다.",
						"주문 예약 완료", 
						JOptionPane.OK_OPTION
						
						);
			}			
		}	
		if(e.getSource().equals(btn_back))	// 다이얼로그 취소 버튼
		{
//			jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			setVisible(false);			
		}	
		
	}
}
