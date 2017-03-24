package service;

import java.io.Serializable;

public class CoffeeVO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private int c_seq;		// 커피 번호
	private String cate;	// 1차 분류
	private String group;	// 2차 분류
	private String c_name;	// 커피 이름
	private int price;		// 커피 가격
	private String img;
	private String sort;
	
	public int getC_seq() {
		return c_seq;
	}
	public void setC_seq(int c_seq) {
		this.c_seq = c_seq;
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
	public String getC_name() {
		return c_name;
	}
	public void setC_name(String c_name) {
		this.c_name = c_name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	@Override
	public String toString() {
		String str = c_seq + "     " + cate + "     " + group + "     " + c_name + "     " + price;
		
		return str;
	}
	
}
