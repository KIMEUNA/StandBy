package Cafe_c.Frame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import Cafe_c.controller.Controller;
import service.CoffeeVO;
import service.MySbVO;
import service.OrderVO;

public class OrderPanel extends JPanel implements ActionListener {

	MenuPanel mn;
	CoffeeVO cvo;	
	MySbVO sbvo;
	Controller ct = new Controller();
	
	int cnt = 0, sum = 0, col = 0, ea = 1, VOtype;
	
	boolean chk = true, chk_point;
	
	SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");	
	
	Font [] f = {
			new Font("Hans Memory", Font.BOLD, 25),
			new Font("Hans Memory", Font.CENTER_BASELINE, 17)
			};
	
	HashMap<String, int[]> option = new HashMap<>();	// <����, {����, �ִ� ���� Ƚ��, ���� �߰��� Ƚ��}>
	HashMap<JButton, Integer> column = new HashMap<>();	// ���õ� �ɼ� �����ϱ� ���� ��
	
	JComboBox<String> cb_option = new JComboBox<>();
	
	ButtonGroup bg_op = new ButtonGroup();
	String [] opArr = {"���̽�","��"};
	JRadioButton [] rd_op = new JRadioButton[opArr.length];
	
	JButton btn_op;
	JButton [] btn_ea = { new JButton("-"), new JButton("+") };
	
	JButton btn_order = new JButton("�ֹ� ����");
	JButton btn_add = new JButton("������ ���Ĺ��� �߰�");
	
	JLabel lb_name, lb_price, lb_img, lb_regdate, lb;		
	JLabel lb_ea = new JLabel("   "+ea);
	JLabel lb_sum = new JLabel("");
	JLabel lb_sumText = new JLabel("�հ�: ");
	
	JTextField tf = new JTextField();
	
	ArrayList<Integer> nextCol = new ArrayList<>();
	
	Iterator<JButton> it;

	public OrderPanel(MenuPanel mn, CoffeeVO cvo) {
		// TODO Auto-generated constructor stub
		VOtype = 0;
		
		this.mn = mn;
		this.cvo = cvo;		
		sum = cvo.getPrice() - mn.price;

		setBounds(50, 150, 900, 460);
		setLayout(null);
		setBackground(Color.white);	
		
		lb_img = new JLabel(new ImageIcon("img\\menu\\view\\250" + cvo.getImg().substring(cvo.getImg().lastIndexOf("\\") + 1)));
		lb_name = new JLabel(cvo.getC_name());
		lb_price = new JLabel("����: " + Integer.toString(cvo.getPrice() - mn.price) + "��");
		lb_sum.setText(Integer.toString(sum) + "��");
		
		lb_img.setBounds(50, 50, 250, 250);
		lb_name.setBounds(90, 325, 200, 37);
		lb_name.setFont(f[0]);		
		lb_price.setBounds(90, 360, 200, 30);
		lb_price.setFont(f[0]);
		lb_ea.setBounds(375, 170, 25, 25);
		lb_ea.setBackground(Color.LIGHT_GRAY);
		lb_ea.setOpaque(true);
		lb_sumText.setBounds(350, 200, 55, 30);
		lb_sumText.setFont(f[1]);
		lb_sum.setBounds(420, 200, 50, 20);
		
		for (int i = 0; i < btn_ea.length; i++) {
			btn_ea[i].setBounds(350+i*50, 170, 25, 25);
			btn_ea[i].setBorder(null);
			btn_ea[i].addActionListener(this);
		}
		btn_order.setBounds(350, 230, 200, 30);
		btn_add.setBounds(350, 265, 200, 30);
		
		btn_order.addActionListener(this);
		btn_add.addActionListener(this);
		
		if(cvo.getGroup().equals("����������") || cvo.getGroup().equals("����Ǫġ��")) {
	         setOption();   
	         add(btn_add);
		}
		
		add(lb_img);
		add(lb_name);
		add(lb_price);	
		add(lb_ea);
		add(lb_sumText);
		add(lb_sum);	
		add(btn_ea[0]);
		add(btn_ea[1]);
		add(btn_order);	
		
		setVisible(false);
		
		System.out.println(cvo.toString());
	}
	
	public OrderPanel(MySbVO sbvo) {
		VOtype = 1;
		this.sbvo = sbvo;
		sum = sbvo.getPrice();		
	}
	
	// �հ� ���� 
	public void resetSum() {
		remove(lb_sum);
		revalidate();
		repaint();	
		
		lb_sum = new JLabel(Integer.toString(sum) + "��");
		lb_sum.setBounds(410, 200, 50, 20);
		
		add(lb_sum);	
	}
	
	// ���� ����
	public void resetEa(String mark) {
		if (ea < 1) {
			ea = 1;
			return;
		}		
		if (ea > 3) {
			ea = 3;
			return;
		}	
		
		if (mark.equals("-")) 
			sum = sum/(ea+1) * ea;		
		else if (mark.equals("+"))
			sum = sum/(ea-1) * ea;
						
		resetSum();
		remove(lb_ea);
		revalidate();
		repaint();	
		
		lb_ea = new JLabel("   "+ea);
		lb_ea.setBounds(375, 170, 25, 25);
		lb_ea.setBackground(Color.LIGHT_GRAY);
		lb_ea.setOpaque(true);
			
		add(lb_ea);
	}	
	
	// �ɼ� ����
	public void selectOp(String op){
		if (option.get(op)[1] < ++option.get(op)[2]) {
			System.out.println("�� �̻� �߰��� �� �����ϴ�.");
			option.get(op)[2]--;
		} else {				
			btn_op = new JButton(op);
			btn_op.addActionListener(new RemoveOption());
			
			if (chk) {
				btn_op.setBounds(600, col = 85 + 25 * cnt, 200, 20);
				cnt++;
			}
			else {
				btn_op.setBounds(600, nextCol.get(0), 200, 20);
				nextCol.remove(0);
				
				if(nextCol.isEmpty())
					chk = true;
			}
			
			column.put(btn_op, col);
			sum += option.get(op)[0];
			resetSum();
			add(btn_op);			
		}
	}
	
	// �ɼ� ����
	public void setOption() {
		JLabel lb_op = new JLabel("�ɼ�");
	
		lb_op.setFont(f[1]);	
		
		for (int i = 0; i < opArr.length; i++) {
			rd_op[i] = new JRadioButton(opArr[i]);
			rd_op[i].setBounds(350+i*100, 130, 100, 30);
			bg_op.add(rd_op[i]);
			add(rd_op[i]);			
		}
		rd_op[0].setSelected(true);		
		
		option.put("��: 500��", new int[] {500, 3, 0});
		option.put("�ڹ�Ĩ: 800��", new int[] {800, 1, 0});
		option.put("ī��� �÷�: 100��", new int[] {100, 1, 0});
		option.put("���� �÷�: 100��", new int[] {100, 1, 0});
		option.put("����ũ��: 200��", new int[] {200, 1, 0});		
		
		for (String op : option.keySet()) 
			cb_option.addItem(op);
		
		cb_option.addActionListener(this);	
		
		lb_op.setBounds(350, 50, 50, 30);
		cb_option.setBounds(350, 85, 200, 30);				
				
		add(lb_op);		
		add(cb_option);		
	}
	
	// �ֹ� �Ϸ�
	public boolean order(ReserveDialog rs) {
		int point;
			
		chk_point = false;
		
		point = ct.getMvo().getPoint() - sum;
		
		System.out.println("����Ʈ ���:"+ct.getMvo().getPoint()+"-"+sum+"="+ point);
		
		if(sum > ct.getMvo().getPoint()) {
			JOptionPane.showMessageDialog(
					mn,
					"�����Ͻ� ����Ʈ�� �����մϴ�.",
					"����Ʈ ���� �޽���", 
					JOptionPane.OK_OPTION
					);
			return chk_point;
		}
		else {
			ct.getMvo().setPoint(point);
			ct.sender(ct.getMvo(), "����Ʈ");
			
			chk_point = true;
		}
		
		OrderVO ovo =  new OrderVO();
				
		if(VOtype == 0) {	// ���� �޴� �ֹ��� ��		
			if(cvo.getGroup().equals("����������") || cvo.getGroup().equals("����Ǫġ��"))
				ct.sender(setRecipe(""), "�ɼ� ���� �ֹ�");
			ovo.setName(cvo.getC_name());	
			ovo.setCate(cvo.getCate());
			ovo.setGroup(cvo.getGroup());
		}
		else if(VOtype == 1) {	// �� �޴� �ֹ��� ��
			ovo.setName(sbvo.getName());
			ct.sender(sbvo, "������ ���Ĺ��� �ֹ�");
		}			
			
		ovo.setUser_id(ct.getMvo().getId());
		ovo.setEa(ea);
		ovo.setPrice(sum);
		ovo.setOrder(0);
		try {
			ovo.setRegDate(sdf.parse(rs.regDate));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ct.getMvo().setGrade(ct.getMvo().getGrade()+ea);
		
		ct.sender(ovo, "�ֹ�");
		ct.sender(ct.getMvo(), "���");
		
		return chk_point;
	}
	
	// ������ ���Ĺ��� �̸� ����
	public void setSbTitle() {
		
		String sb_name = JOptionPane.showInputDialog("������ ���Ĺ��� �۸��");
		
		System.out.println("sb: "+sb_name);
		
		ct.sender(setRecipe(sb_name), "�߰�");
		
		JOptionPane.showMessageDialog(
				mn,
				"������ ���Ĺ��̿� [ " + sb_name + " ]\n��(��) �߰� �Ǿ����ϴ�.",
				"�߰� �Ϸ�", 
				JOptionPane.OK_OPTION
				);
	}
	
	// ������ ����
	public MySbVO setRecipe(String title) {
		MySbVO svo = new MySbVO();

		if(!title.equals(""))
			svo.setTitle(title);
		
		svo.setName(cvo.getC_name());
		svo.setUser_id(ct.getMvo().getId());
		
		if (rd_op[0].isSelected()) {
			svo.setIce(1);
			svo.setHot(0);
		} else if (rd_op[1].isSelected()) {
			svo.setIce(0);
			svo.setHot(1);
		}
		
		svo.setShot(option.get("��: 500��")[2]);
		svo.setJavachip(option.get("�ڹ�Ĩ: 800��")[2]);
		svo.setCaramelsyrup(option.get("ī��� �÷�: 100��")[2]);
		svo.setChocosyrup(option.get("���� �÷�: 100��")[2]);
		svo.setWhippingcream(option.get("����ũ��: 200��")[2]);
		svo.setPrice(sum);
		svo.setLike_cnt(0);
		svo.setSharing(0);
		svo.setImg(cvo.getImg());
		
		return svo;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		mn.pn_menu.setVisible(false);
		mn.mp.cc.add(this);
		setVisible(true);
		
		if(e.getSource().equals(btn_ea[0])){	// ���� ���� ��ư '-'
			ea--;
			resetEa("-");
		}
		if(e.getSource().equals(btn_ea[1])){	// ���� ���� ��ư '+'
			ea++;
			resetEa("+");
		}
		if(e.getSource().equals(cb_option)){	// �ɼ� �޺��ڽ�
		
			String op = cb_option.getSelectedItem().toString();			
			selectOp(op);
		}		
		if(e.getSource().equals(btn_order)){	// �ֹ� ���� ��ư
		
			new ReserveDialog(this);		
		}
		if(e.getSource().equals(btn_add)){	// ������ ���Ĺ��� �߰� ��ư
			setSbTitle();
		}	
	}	
	
	class RemoveOption implements ActionListener {	// ������ �ɼ� ���� �� �۵�

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub	
			it = column.keySet().iterator();
			
			while(it.hasNext()) {
				JButton buf = it.next();

				if (e.getSource().equals(buf)) {
					chk = false;
					option.get(buf.getText())[2]--;
					sum -= option.get(buf.getText())[0];
					nextCol.add(column.get(buf));
					
					remove(buf);
					resetSum();
					it.remove();
				}
			}			
		}		
	}
}