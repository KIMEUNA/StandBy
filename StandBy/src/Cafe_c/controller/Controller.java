package Cafe_c.controller;

import java.util.ArrayList;

import javax.swing.JFrame;

import Cafe_c.Frame.CafeClient;
import Cafe_c.net.C_StandBy;
import service.CoffeeVO;
import service.MemberVO;
import service.MySbVO;
import service.OrderVO;

public class Controller {

	C_StandBy tc;

	public Controller() {
		// TODO Auto-generated constructor stub

		tc = C_StandBy.getInstance();
	}
	public void sender(Object obj, String keyword) {
		tc.Sender(obj, keyword);
	}
	public C_StandBy getTc(){
		return tc;
	}
	public String getKey() {
		return tc.getKey();
	}
	public MemberVO getMvo() {
		return tc.getMvo();
	}
	public ArrayList<CoffeeVO> getCvoArr() {
		return tc.getCvoArr();
	}
	public ArrayList<OrderVO> getOrderArr() {
		return tc.getOrderArr();
	}
	public ArrayList<MySbVO> getMySbArr(){
		return tc.getMySbArr();
	}
	// ¾ÆÀÌµð ºñ¹ø Ã£À» ¶§
	public String getStr() {
		return tc.getStr();
	}
	public boolean idChk(String id) {
		if (id.matches("^[a-zA-Z]{1}[a-zA-Z0-9]{4,18}[0-9]{1}$"))
			return true;
		
		return false;
	}
	
	public boolean submitChk(String[] tf) {
		boolean bb = false;

		String[] ppId = {
				"", // ¾ÆÀÌµð"^[a-z]+[a-z0-9]+[0-9]{6,20}$"
				".*[a-zA-Z0-9°¡-ÆR].*", "^[°¡-ÆR]{2,5}$", // ÀÌ¸§
				"^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$", // ÀüÈ­¹øÈ£
		};

		for (int i = 1; i < ppId.length; i++) {
			if (!tf[i].matches(ppId[i])) {
				bb = true;
				return bb;
			}
		}
		return bb;
	}
	 public boolean submitPWChk(String pass, String passChk) {
	      boolean chk = true;
	      // String ppPw = "[a-zA-Z]{1,}[0-9]{1,}[@._-]{1,}{6,20}";
	      String ppPw = "^(?=.*[a-zA-Z]+)(?=.*[!@#$%^*+=-]|.*[0-9]+).{6,13}$";
	      
	      if (pass.matches(ppPw)) {
	         if (pass.equals(passChk))
	            chk = false;
	      } 
	      return chk;
	   }
	
	public void clear(CafeClient frame){
		frame.dispose();
		frame.setMvo(null);
		tc.setKey(null);
		new CafeClient();
	}

}
