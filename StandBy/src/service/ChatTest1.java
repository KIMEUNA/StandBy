package service;
 
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
 
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
 
public class ChatTest1 extends JFrame implements ActionListener {
 
    private static final long serialVersionUID = 1L;
    private JTextArea jta = new JTextArea(40, 25);
    private JTextField jtf = new JTextField(25);
    private ChatTest server = new ChatTest();
 
    public ChatTest1() throws IOException {
 
        add(jta, BorderLayout.CENTER);
        add(jtf, BorderLayout.SOUTH);
        jtf.addActionListener(this);
 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
        setBounds(200, 100, 400, 600);
        setTitle("서버부분");
 
        server.setGui(this);
        server.setting();
    }
 
    public static void main(String[] args) throws IOException {
        new ChatTest1();
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = "서버 : "+ jtf.getText() + "\n";
        System.out.print(msg);
        server.sendMessage(msg);
        jtf.setText("");
    }
 
    public void appendMsg(String msg) {
        jta.append(msg);
    }
 
}
