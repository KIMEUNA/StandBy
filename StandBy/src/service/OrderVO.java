package service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ���� ���� Ŭ����
 */
public class OrderVO  implements Serializable{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private int seq;		// �Ǹ� ��ȣ
	private String user_id;	// ������ �� id
	private String name;	// �Ǹ� ��ǰ �̸�
	private int ea;			// �Ǹ� ����
	private int price;		// ��ǰ ����
	private int Order;		// �ֹ� �Ϸ� (������ ���� �Ϸ� ����)
	private Date regDate;	// �Ǹ� ��¥
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
		String str = seq + ";" + user_id + ";" + name + ";" + ea + "��;" + new DecimalFormat("#,##0").format(price) + "��;"+new SimpleDateFormat("yy/MM/dd").format(regDate);
		return str;
	}
	public String order(){
		return cate+";"+group+";"+name+";"+ea;
	}
	
	public String myOrder(){
		return seq + ";" +ea + "��;" + new DecimalFormat("#,##0").format(price) + "��;"+new SimpleDateFormat("yy/MM/dd").format(regDate);
		
	}
	
	
	
	
}
