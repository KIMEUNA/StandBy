package service;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MemberVO implements Serializable{

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
	
	private String name;	// 이름
	private int grade;		// 회원 등급
	private String id;		// 아이디
	private String pass;	// 비밀번호
	private String gender;
	private String phone;	// 핸드폰
	private Date brithDate;		// 생년월일
	private Date joinDate;		// 가입일
	private int block;			// 블랙리스트 
	private String passhint;	// 비밀번호 힌트
	private String hintanswer;	// 힌트 답
	private int point;			// 현금처럼 사용할 수 있는 포인트.

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Date getBrithDate() {
		return brithDate;
	}
	public void setBrithDate(Date brithDate) {
		this.brithDate = brithDate;
	}
	public Date getJoinDate() {
		return joinDate;
	}
	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}
	public int getBlock() {
		return block;
	}
	public void setBlock(int block) {
		this.block = block;
	}
	public String getPasshint() {
		return passhint;
	}
	public void setPasshint(String passhint) {
		this.passhint = passhint;
	}
	public String getHintanswer() {
		return hintanswer;
	}
	public void setHintanswer(String hintanswer) {
		this.hintanswer = hintanswer;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	
	@Override
	public String toString() {
		String str =  id + "," + name + "," + new String[]{"새싹", "실버", "골드"}[grade < 10 ? 0 : grade < 30 ? 1:2]
				  + "," + point + "," + sdf.format(joinDate);
	
		return str;
	}
	
	public String info() {
		String str =  id + "," + name + "," + gender + "," + new String[]{"새싹", "실버", "골드"}[grade < 10 ? 0 : grade < 30 ? 1:2] + "," + phone
				 + "," + sdf.format(brithDate) + "," + sdf.format(joinDate);
	
		return str;
	}
	
	public String update() {
		return id + "," + name + "," + pass + "," + gender + "," + phone + "," + sdf.format(brithDate) + "," + passhint
				+ "," + hintanswer;

	}
	
	public String myInfo(){
		return id +";" +name +";" +new String[]{"새싹", "실버", "골드"}[grade < 10 ? 0 : grade < 30 ? 1:2] +";" +new DecimalFormat("#,##0").format(point) +";" +new SimpleDateFormat("yy/MM/dd").format(joinDate);
	}
	
	
}
