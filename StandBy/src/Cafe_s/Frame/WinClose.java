package Cafe_s.Frame;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WinClose extends WindowAdapter {

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		e.getWindow().dispose();
	}

}
