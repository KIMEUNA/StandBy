package service;

import java.io.Serializable;
import java.util.Date;

public class ChatVO implements Serializable {

	private static final long serialVersionUID = 1L;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	private String id;
	private String content;
	private Date regdate;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getRegdate() {
		return regdate;
	}
	public void setRegdate(Date regdate) {
		this.regdate = regdate;
	}
	@Override
	public String toString() {
		return "ChatVO [id=" + id + ", content=" + content + ", regdate=" + regdate + "]";
	}
	
	
	
	
	
	
}
