package Cafe_s.Frame;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import Cafe_s.Frame.CalenFrame;
import Cafe_s.controller.Controller;
import service.OrderVO;

/**
 * �Ǹ���Ȳ Ŭ����.
 */
public class SalesPanel extends JPanel implements ActionListener{

	MainPanel main;
	
	JPanel pn = new JPanel();
	JPanel pn_view = new JPanel();
	JScrollPane sp_view = new JScrollPane(pn_view);
	
	String[] schArr = {"�Ⱓ��", "�Ⱓ�� �Ǹ� ��ŷ"};
	
	JRadioButton[] rbtn;
	ButtonGroup btng = new ButtonGroup();
	
	String[][] strArr = {
			{ "�ǸŹ�ȣ", "�Ǹ� ��ǰ", "���� ��", "����", "����", "�Ǹ���" },
			{"���� �з�", "���� �з�", "�Ǹ� ��ǰ", "�� �Ǹ� ����"}
			};
	JLabel[] lbArr;
	
	String[] arr = {"����", "��"};
	JButton[] btn = new JButton[arr.length];
	JLabel[] lb = new JLabel[btn.length];
	JLabel lb_total =  new JLabel();
	
	JButton btn_sch = new JButton("�˻�");
	
	Controller cc = new Controller();
	ArrayList<OrderVO> list;
	
	Date[] date = new Date[btn.length];
	
	int flag;
	
	public SalesPanel(MainPanel main) {
		// TODO Auto-generated constructor stub
		
		this.main = main;
		
		setBounds(250, 100, 774, 628);
//		setBackground(Color.gray);
		setLayout(null);
		
		pn.setBounds(10, 100, 754, 60);
		
		sp_view.setBounds(10, 160, 754, 418);
		
		dataInit();
		
		if(!main.evo.getGrade().equals("������")){
			btn_sch.setVisible(false);
			for (int i = 0; i < btn.length; i++) {
				btn[i].setVisible(false);
				lb[i].setVisible(false);
				rbtn[i].setVisible(false);
			}
		}
		
		add(pn);
		add(btn_sch);
		add(sp_view);
		
	}
	
	void dataInit(){
		
		btn_sch.setBounds(450, 20, 70, 30);
		btn_sch.setBorder(null);
//		btn_sch.setBackground(Color.orange);
		btn_sch.addActionListener(this);
		
		for (int i = 0; i < btn.length; i++) {
			btn[i] = new JButton(new ImageIcon("img\\�޷�.png"));
			btn[i].setBounds(133+i*150, 15, 20, 20);
			btn[i].setBorder(null);
			btn[i].addActionListener(this);
			
			lb[i] = new JLabel();
			lb[i].setBounds(10+i*150, 15, 120, 30);
//			lb[i].setBackground(Color.white);
			lb[i].setOpaque(true);
			
			add(btn[i]);
			add(lb[i]);
		}
		
		rbtn = new JRadioButton[schArr.length];
		for (int i = 0; i < schArr.length; i++) {
			rbtn[i] = new JRadioButton(schArr[i]);
			rbtn[i].setBounds(50+i*105, 55, 100+i*30, 30);
			btng.add(rbtn[i]);
			add(rbtn[i]);
		}
		rbtn[0].setSelected(true);
		
		salesSch(new Date(), new Date());
	}
	
	void salesSch(Date start, Date end){
		
		pn.removeAll();
		pn.revalidate();
		pn.repaint();
		pn_view.removeAll();
		pn_view.revalidate();
		pn_view.repaint();
		
		int sch = 0;
		for (sch = 0; sch < rbtn.length; sch++) 
			if(rbtn[sch].isSelected())
				break;
		
		pn.setLayout(new GridLayout(0, strArr[sch].length));
		pn_view.setLayout(new GridLayout(0, strArr[sch].length));
		
		list = cc.s_select(start, end, sch);
		
		lbArr = new JLabel[strArr[sch].length];
		
		for (int i = 0; i < lbArr.length; i++) {
			lbArr[i] = new JLabel(strArr[sch][i]);
			lbArr[i].setHorizontalAlignment(SwingConstants.CENTER);
			pn.add(lbArr[i]);
		}
		
		lb_total.setText("�˻� ����: "+list.size());
		pn.add(lb_total);
		String[] buf = null;
		for (int i = 0; i < list.size(); i++) {	
			if(sch == 0)
				buf = list.get(i).toString().split(";");
			else if(sch == 1)
				buf = list.get(i).order().split(";");
			for (int j = 0; j < buf.length; j++) {
				lbArr[j] = new JLabel(buf[j]);
				lbArr[j].setHorizontalAlignment(SwingConstants.CENTER);
				pn_view.add(lbArr[j]);
			}
		}
		for (int i = pn_view.getComponentCount(); i < 50; i++)
			pn_view.add(new JLabel(""));
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		if (e.getSource().equals(btn_sch)) {
			for (int i = 0; i < date.length; i++) 
				if (date[i] == null) {
					JOptionPane.showMessageDialog(new JOptionPane(), "�˻� �Ⱓ�� �����ϼ���.", "��¥ ����",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
			salesSch(date[0], date[1]);
		}
		
		for (int i = 0; i < btn.length; i++) {
			if(e.getSource().equals(btn[i])){
				if(date[0] == null && i == 1){
					JOptionPane.showMessageDialog(new JOptionPane(), "�������� �������ּ���.", "��¥ ����", JOptionPane.ERROR_MESSAGE);
					return;
				}
					flag = i;
					new CalenFrame(this);
			}
		}
	}
	
}
