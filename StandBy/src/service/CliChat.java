package service;
 
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
 
public class CliChat {
 
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private CliChat1 gui;
    private String msg;
    private String nickName = "�켺";
 
    public final void setGui(CliChat1 gui) {
        this.gui = gui;
    }
 
    public void connet() {
        try {
            socket = new Socket("127.0.0.1", 7777);
            System.out.println("���� �����.");
             
            out = new DataOutputStream(socket.getOutputStream());
            in = new DataInputStream(socket.getInputStream());
             
            //�������ڸ��� �г��� �����ϸ�. ������ �̰� �г������� �ν��� �ϰ� �ʿ� ����ְ�����?
            out.writeUTF(nickName); 
            System.out.println("Ŭ���̾�Ʈ : �޽��� ���ۿϷ�");
            while(in!=null){
                msg=in.readUTF();
                gui.appendMsg(msg);             
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public static void main(String[] args) {
        CliChat clientBackground = new CliChat();
        clientBackground.connet();
    }
 
    public void sendMessage(String msg2) {
        try {
            out.writeUTF(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
 
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }
 
}
