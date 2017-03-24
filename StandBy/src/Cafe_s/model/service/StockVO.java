package Cafe_s.model.service;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StockVO {
	
	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
	
	private int item_id;
	private String item;		// 품목
	private int in;
	private int out;
	private int total;			// 총 수량
	private String stat;
	private Date regDate;		// 입고날

	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getItem() {
		return item;
	}
	public void setItem(String item) {
		this.item = item;
	}
	public int getIn() {
		return in;
	}
	public void setIn(int in) {
		this.in = in;
	}
	public int getOut() {
		return out;
	}
	public void setOut(int out) {
		this.out = out;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getStat() {
		return stat;
	}
	public void setStat(String stat) {
		this.stat = stat;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	
	
	
	@Override
	public String toString() {
		return item_id + "," + item + "," + in + "," + out + "," + total;
	}
	
	public String inStock() {
		return item_id + "," + item + "," + in + "," + sdf.format(regDate) + "," + stat;
	}
	public String outStock() {
		return item_id + "," + item + "," + out + "," + sdf.format(regDate) + "," + stat;
	}
	
	
	
	
	
}
