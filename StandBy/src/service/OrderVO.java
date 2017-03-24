package service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 매출 정보 클래스
 */
public class OrderVO  implements Serializable{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private int seq;		// 판매 번호
	private String user_id;	// 구매한 고객 id
	private String name;	// 판매 물품 이름
	private int ea;			// 판매 수량
	private int price;		// 물품 가격
	private int Order;		// 주문 완료 (고객에게 전달 완료 여부)
	private Date regDate;	// 판매 날짜
	private String cate;
	private String group;
	
	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getEa() {
		return ea;
	}
	public void setEa(int ea) {
		this.ea = ea;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getOrder() {
		return Order;
	}
	public void setOrder(int order) {
		Order = order;
	}
	public Date getRegDate() {
		return regDate;
	}
	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}
	public String getCate() {
		return cate;
	}
	public void setCate(String cate) {
		this.cate = cate;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	@Override
	public String toString() {
		String str = seq + ";" + user_id + ";" + name + ";" + ea + "잔;" + new DecimalFormat("#,##0").format(price) + "원;"+new SimpleDateFormat("yy/MM/dd").format(regDate);
		return str;
	}
	public String order(){
		return cate+";"+group+";"+name+";"+ea;
	}
	
	public String myOrder(){
		return seq + ";" +ea + "잔;" + new DecimalFormat("#,##0").format(price) + "원;"+new SimpleDateFormat("yy/MM/dd").format(regDate);
		
	}
	
	
	
	
}
