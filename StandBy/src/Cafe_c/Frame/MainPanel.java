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

// �α��� �� ����ȭ��
public class MainPanel extends JPanel implements ActionListener {

	CafeClient cc;
	
	MenuPanel mn;
	MyInfoPanel mi;
	
	JButton btn = new JButton("�α׾ƿ�");
	JButton chat_btn = new JButton("�����ڿ� ä���ϱ�");
	JLabel lb;
	
	String[][] category1 = { 
			{ "" },
			{ "���� �޴�", "�� �޴�" } };

	String[][] category2 = { 
			{ "��ü �޴�", "����", "Ǫ��", "��ǰ" }, 
			{ "�ʿͳ��� ���Ĺ���", "������ ���Ĺ���" } };

	HashMap<String, JComboBox<String>> cateMap1 = new HashMap<>(); // �� ����, �޴�, �¼� ��Ȳ
	HashMap<String, JComboBox<String>> cateMap2 = new HashMap<>(); // ���� �޴�, �� �޴�

	// �� ����, �޴�, �¼� ��Ȳ
	JComboBox<String> cb_category = new JComboBox<>();

	// �� ����-category1[0], �޴�-category1[1], �¼���Ȳ-category1[2],
	JComboBox<String>[] cb_category1 = new JComboBox[category1.length];
	// category1[1][0] (���� �޴�) -category2[0], category1[1][0] (��
	// �޴�)-category2[1]
	JComboBox<String>[] cb_category2 = new JComboBox[category2.length];

	public MainPanel(CafeClient c) {
		setBounds(0, 0, 1024, 100);
		setLayout(null);

		this.cc = c;		
		
		lb = new JLabel(cc.getMvo().getName() + " �� ȯ���մϴ�.");
		
		btn.setBounds(800, 15, 100, 30);
		btn.addActionListener(this);
		
		lb.setBounds(650, 15, 200, 30);
		
		chat_btn.setBounds(800, 55, 150, 30); //ä�ù�ư
		chat_btn.addActionListener(this);
		
		cb_category.addItem("�� ����");
		cb_category.addItem("�޴�");
		cb_category.setBounds(100, 45, 200, 30);
		cb_category.addActionListener(this);
		

		// �� ����, �޴�, �¼� ��Ȳ
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

		// ���� �޴�, �� �޴�
		for (int i = 0; i < category2.length; i++) {
			cb_category2[i] = new JComboBox<>();
			String str = category1[1][i]; // ���� �޴�, �� �޴�
			
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
	
    // �г� �ʱ�ȭ
    public void initPanel(ArrayList<JPanel> pnArr) {
    	for (JPanel pn : pnArr) {
    		pn.removeAll();
    		pn.revalidate();
    		pn.repaint();
    		
    		pn.setVisible(false);
		}
    }

	// �޺��ڽ� �Ⱥ��̰� �ʱ�ȭ
	public void init(JComboBox<String>[] cb) {
		for (JComboBox<String> com : cb)
			com.setVisible(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		String str = null;
		
		if (e.getSource().equals(cb_category)) {	// �� ����, �޴� �޺��ڽ� Ŭ�� ��
			
			if(cb_category.getSelectedIndex() == 0) {	// �޺��ڽ����� '�� ����' ���� ��
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
		
		if (e.getSource().equals(cb_category1[1])) {	// ����޴�, ���޴� �޺��ڽ� Ŭ�� ��
			str = cb_category1[1].getSelectedItem().toString();
			init(mn.cb_category);
			init(cb_category2);
			initPanel(mn.pnArr);

			cateMap2.get(str).setVisible(true);
			mn.setVisible(true);
		}
		
		if (e.getSource().equals(chat_btn)) // ä�� ��ư Ŭ�� �� 
			cc.cc.getTc().getChat(cc.getMvo());
		
		if (e.getSource().equals(btn)) { // �α׾ƿ� ��ư Ŭ�� ��
			new Controller().clear(cc);
		}
	}
}