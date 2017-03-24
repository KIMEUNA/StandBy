package Cafe_s.model.service;

import java.text.DecimalFormat;
import java.util.Date;

public class EmployeeVO {
	
	private String id;
	private String e_name;	// 사원 이름
	private String e_pass;	// 계정 비밀번호
	private String grade;	// 사원 직급
	private int pay;
	private Date date;		// 입사 날짜

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getE_name() {
		return e_name;
	}
	public void setE_name(String e_name) {
		this.e_name = e_name;
	}
	public String getE_pass() {
		return e_pass;
	}
	public void setE_pass(String e_pass) {
		this.e_pass = e_pass;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public int getPay() {
		return pay;
	}
	public void setPay(int pay) {
		this.pay = pay;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return id + "," + e_name  + "," + grade + "," + date + ", ";
	}
	public String update() {
		return id + "," + e_name  + "," + e_pass + "," + pay + ", ";
	}
	
	public String info(){
		return id + ";" + e_name  + ";" + grade + ";" + new DecimalFormat("#,##0").format(pay)+ "만원;" + date + "; ";
	}

	
}
