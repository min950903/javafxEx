package application.dto;

import java.io.Serializable;
import java.sql.Date;

public class Todo implements Serializable{
	private static final long serialVersionUID = 1L;

	private int no;
	private String title;
	private String desc;
	private Date date;
	private String state;
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Todo(int no, String title, String desc, Date date, String state) {
		this.no = no;
		this.title = title;
		this.desc = desc;
		this.date = date;
		this.state = state;
	}
	
	public Todo(String title, String desc, Date date, String state, String userId) {
		this.title = title;
		this.desc = desc;
		this.date = date;
		this.state = state;
		this.userId = userId;
	}
	
	public Todo(String title, String state) {
		this.title = title;
		this.state = state;
	}
	
	public Todo(String title, String state, Date date) {
		this.title = title;
		this.state = state;
		this.date = date;
	}

	public int getNo() {
		return no;
	}

	public void setNo(int no) {
		this.no = no;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String toString() {
		return this.title + " / " + this.desc + " / " + this.state; 
	}
	
}
