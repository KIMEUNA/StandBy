package Cafe_c.Frame;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import Cafe_c.Frame.CafeClient;
import Cafe_c.Frame.CafeClient.Membership;
import Cafe_s.Frame.CafeServer;

public class CalenFrame extends JFrame implements ItemListener, ActionListener{
	
	static String title = "��¥ �˻�";
	
    JPanel p1, p2;
    JComboBox<Integer> month;
    JComboBox<Integer> year;
    int days[]={31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
    String weekdays[] = {"�� ", "�� ", "ȭ ", "�� ", "�� ", "�� ", "�� "};
    int months[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    
    Object obj;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    Date today = new Date();
    
    public CalenFrame(Object obj){
        super(title);
        setBounds(200, 200, 230, 235);
        
        this.obj = obj;
        
        p1 = new JPanel();
        month = new JComboBox<Integer>();
        
        for(int i=0;i< months.length;i++)
            month.addItem(months[i]);
        
        month.addItemListener(this);
        year = new JComboBox<Integer>();
        
        for(int i=1970;i<=today.getYear()+1900;i++)
            year.addItem(i);
        
        year.addItemListener(this);
       
        p1.add(year);
        p1.add(month);
        add(p1);
        
        Date date = new Date();
        drawCalendar(months[date.getMonth()], (1900+date.getYear()));
        year.setSelectedItem((1900+date.getYear()));
        month.setSelectedItem(months[date.getMonth()]);
        Container c=getContentPane();
        c.setLayout(new FlowLayout());
        
        setVisible(true);
        
        setResizable(false);
        addWindowListener(new WinClose());
    }
    
    @Override
    public void itemStateChanged(ItemEvent e){
    	
        if(e.getStateChange() == ItemEvent.SELECTED) {
            drawCalendar((Integer)month.getSelectedItem(), (Integer)year.getSelectedItem());
        }
    }
    
    public void drawCalendar(int inputMonth, int inputYear){
    	if(p2 != null)
    		p2.setVisible(false);
    	p2 = new JPanel();
        p2.setLayout(new GridLayout(0,7,10,5));
        
        for(int i=0;i< weekdays.length;i++){
            JLabel label = new JLabel(weekdays[i]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            p2.add(label);
        }
        
        Date date = new Date(inputYear-1900, inputMonth-1, 1);
        int noOfDaysInMonth = days[date.getMonth()];
        if(date.getYear()%4==0 && date.getMonth()==1){
            noOfDaysInMonth = 29;
        }
        JButton btn = null;
        for(int i=1, day=1;day<=noOfDaysInMonth;i++){
        	System.out.println(date);
            for(int j=0;j<7;j++) {
                if(day==1) {
                    if(j==date.getDay())
                    	btn = new JButton(String.valueOf(day));
                    else {
                        JLabel label = new JLabel("");
                        p2.add(label);
                    }
                }
                else{
                	btn = new JButton(String.valueOf(day));
                }
                if(btn != null){
                	btn.setBorder(null);
                	btn.addActionListener(this);
                	btn.setHorizontalAlignment(SwingConstants.CENTER);
                    p2.add(btn);
                    day++;
                }
                
                if(day>noOfDaysInMonth)
                    break;
            }
        }
        add(p2);
    }

	@Override
    public void actionPerformed(ActionEvent e) {
    	// TODO Auto-generated method stub
		
		String str = year.getSelectedItem()+"-"+
    			month.getSelectedItem()+"-"+
    			e.getActionCommand();
		try {
			if (obj instanceof Membership) {
				if (today.before(sdf.parse(str))) {
					JOptionPane.showMessageDialog(this, "���� ��¥�� �Է� ����");
					return;
				}
				((Membership) obj).lb[9].setText(str);
				((Membership) obj).date = sdf.parse(str);
			}
		
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
    	dispose();
    }
}