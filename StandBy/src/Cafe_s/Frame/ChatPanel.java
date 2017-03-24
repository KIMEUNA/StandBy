package Cafe_s.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import Cafe_s.model.service.EmployeeVO;
import Cafe_s.net.S_StandBy;
import service.ChatVO;

public class ChatPanel extends JFrame implements ActionListener {

	S_StandBy server;
	EmployeeVO evo;

	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField();
	JScrollPane sp_view = new JScrollPane(ta);

	SimpleDateFormat sdf = new SimpleDateFormat("a h:mm");

	public ChatPanel(S_StandBy server, EmployeeVO evo) {
		// TODO Auto-generated constructor stub

		this.server = server;
		this.evo = evo;

		setBounds(0, 414, 250, 314);
		setLayout(null);
		setBackground(Color.white);
		
		dataInit();
		
		add(sp_view);
		add(tf);

		setVisible(true);
	}

	void dataInit() {
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setBackground(Color.lightGray);
		
		sp_view.setBounds(10, 10, 230, 240);

		tf.setBounds(10, 255, 230, 20);
		tf.setFocusable(true);
		tf.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Sender();
	}

	void Sender() {

		ChatVO chat = new ChatVO();
		Date time = new Date();
		
		chat.setId(evo.getId());
		chat.setContent(tf.getText());
		chat.setRegdate(time);
		
		ta.append(evo.getId() + ":  " + tf.getText() + "     " + sdf.format(time) + "\n");
		
		int pos = ta.getText().length();
		ta.setCaretPosition(pos);
//		ta.requestFocus();
		
		server.rr.CafeSender("Ã¤ÆÃ", chat);

		tf.setText("");
		tf.setFocusable(true);

	}
	
	public void ChatReceiver(ChatVO chat){
		
		JLabel lb = new JLabel("["+chat.getId() + "]: "+
				chat.getContent() + "     "+ 
				sdf.format(chat.getRegdate()) + "\n");
		
		lb.setHorizontalAlignment(SwingConstants.RIGHT);
		
		ta.append(lb.getText());
		
		int pos = ta.getText().length();
		ta.setCaretPosition(pos);
//		ta.requestFocus();
		
	}
}
