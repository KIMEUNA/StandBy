package Cafe_c.Frame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Cafe_c.net.C_StandBy;
import service.ChatVO;
import service.MemberVO;

public class ChatPanel extends JFrame implements ActionListener {

	JTextArea ta = new JTextArea();
	JTextField tf = new JTextField();
	JScrollPane sp = new JScrollPane(ta);

	C_StandBy client;
	MemberVO vo;
	ChatVO chat;

	SimpleDateFormat sdf = new SimpleDateFormat("a h:mm");

	String name;

	public ChatPanel(C_StandBy client, MemberVO vo) {
		// TODO Auto-generated constructor stub
		super("고객의 소리");
		setBounds(300, 200, 300, 400);

		this.client = client;
		this.vo = vo;

		dataInit();

		tf.setText("님이 대화를 신청 하였습니다"); 
		Sender();
		
		add(sp, "Center");
		add(tf, "South");

		setVisible(true);
		addWindowListener(new WinClose_c());
	}

	void dataInit() {
		
		ta.setEditable(false);
		ta.setLineWrap(true);
		ta.setBackground(Color.LIGHT_GRAY);

		tf.addActionListener(this);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Sender();
	}

	void Sender() {

		chat = new ChatVO();
		Date time = new Date();

		chat.setId(vo.getId());
		chat.setContent(tf.getText());
		chat.setRegdate(new Date());
		
		ta.append("["+chat.getId() + "]: " + tf.getText() + "     " + sdf.format(time) + "\n");
		
		int pos = ta.getText().length();
		ta.setCaretPosition(pos);
//		ta.requestFocus();
		
		client.Sender(chat, "채팅");

		tf.setText("");
	}

	public void Receiver(ChatVO chat) {
		
		ta.append("["+chat.getId() + "]: " + 
				chat.getContent() + "     " + 
				sdf.format(chat.getRegdate()) + "\n");
		
		int pos = ta.getText().length();
		ta.setCaretPosition(pos);
//		ta.requestFocus();

	}
	class WinClose_c extends WindowAdapter {

		@Override
		public void windowClosing(WindowEvent e) {
			// TODO Auto-generated method stub
			e.getWindow().dispose();
			tf.setText("님이 퇴장 하셨습니다.");
			Sender();
					
		}
	}

}
