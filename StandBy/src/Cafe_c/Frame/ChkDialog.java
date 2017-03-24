package Cafe_c.Frame;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ChkDialog extends JDialog {

	private static ChkDialog me;
	JLabel lb = new JLabel("충전하실 금액을 선택해 주세요.");
	JButton btn_ok = new JButton("확인");
	JButton btn_can = new JButton("취소");
	JPasswordField tf;
	JButton btn_charge = new JButton("충전");	
	JLabel lb_won = new JLabel("원");
	
	JPanel pn_charge = new JPanel();
	JPanel pn_passChk = new JPanel();
	
	JComboBox<Integer> cb_point = new JComboBox<>();
	
	public static ChkDialog getInstance(JFrame ff, String str, boolean bb){
		if(me == null){
			System.out.println("싱글톤이당ㅇㅇ");
			me = new ChkDialog(ff, str, bb);
		}
		return me;
	}
	
	private ChkDialog(JFrame ff, String str, boolean bb) {
		// TODO Auto-generated constructor stub
		super(ff, str, bb);
		setBounds(300, 200, 400, 300);
		setLayout(null);
		charge();
		passChk();
		pn_charge.setVisible(false);
		pn_passChk.setVisible(false);
	}
	
	void charge(){
		pn_charge.setVisible(false);
		pn_passChk.setVisible(false);
		
		for (int i = 1; i <= 100000/5000; i++) {
			cb_point.addItem(5000*i);
		}
		
		pn_charge.setBounds(0, 0, 400, 300);
		lb.setBounds(100, 70, 300, 30);
		lb_won.setBounds(270, 110, 30, 30);
		cb_point.setBounds(100, 110, 160, 30);
		btn_charge.setBounds(100, 150, 80, 30);
		btn_can.setBounds(200, 150, 80, 30);
		
		pn_charge.add(lb);
		pn_charge.add(lb_won);
		pn_charge.add(cb_point);
		pn_charge.add(btn_charge);
		pn_charge.add(btn_can);
		
		add(pn_charge);
		
		pn_charge.setVisible(true);
	}
	void passChk(){
		pn_charge.setVisible(false);
		pn_passChk.setVisible(false);
		
		lb = new JLabel("비밀번호 확인");
		tf = new JPasswordField();

		pn_passChk.setBounds(0, 0, 400, 300);
		lb.setBounds(80, 70, 300, 30);
		tf.setBounds(40, 100, 300, 30);
		btn_ok.setBounds(100, 150, 80, 30);
		btn_can.setBounds(200, 150, 80, 30);

		pn_passChk.add(tf);
		pn_passChk.add(lb);
		pn_passChk.add(btn_ok);
		pn_passChk.add(btn_can);
		
		add(pn_passChk);
		
		pn_passChk.setVisible(true);
	}
	
	
}
