package service;

import java.io.Serializable;
import java.util.Date;

public class MySbVO implements Serializable{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private int seq;
	private String title;
	private String name;
	private String user_id;
	private int ice;
	private int hot;
	private int shot;
	private int javachip;
	private int	caramelsyrup;
	private int chocosyrup;
	private int whippingcream;
	private int price;
	private Date regdate;
	private int like_cnt;
	private int sharing;
	private String img;

	public int getSeq() {
		return seq;
	}
	public void setSeq(int seq) {
		this.seq = seq;
	}	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}	
	public int getIce() {
		return ice;
	}
	public void setIce(int ice) {
		this.ice = ice;
	}
	public int getHot() {
		return hot;
	}
	public void setHot(int hot) {
		this.hot = hot;
	}
	public int getShot() {
		return shot;
	}
	public void setShot(int shot) {
		this.shot = shot;
	}
	public int getJavachip() {
		return javachip;
	}
	public void setJavachip(int javachip) {
		this.javachip = javachip;
	}
	public int getCaramelsyrup() {
		return caramelsyrup;
	}
	public void setCaramelsyrup(int caramelsyrup) {
		this.caramelsyrup = caramelsyrup;
	}
	public int getChocosyrup() {
		return chocosyrup;
	}
	public void setChocosyrup(int chocosyrup) {
		this.chocosyrup = chocosyrup;
	}
	public int getWhippingcream() {
		return whippingcream;
	}
	public void setWhippingcream(int whippingcream) {
		this.whippingcream = whippingcream;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	public int getLike_cnt() {
		return like_cnt;
	}
	public void setLike_cnt(int like_cnt) {
		this.like_cnt = like_cnt;
	}
	public int getSharing() {
		return sharing;
	}
	public void setSharing(int sharing) {
		this.sharing = sharing;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
