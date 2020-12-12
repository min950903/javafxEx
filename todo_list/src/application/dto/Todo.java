package application.dto;

import java.io.Serializable;
import java.util.Date;

public class Todo implements Serializable{
	private static final long serialVersionUID = 1L;

	private int no;
	private String title;
	private String desc;
	private Date date;
	private char state;
	
	

	public Todo(int no, String title, String desc, Date date, char state) {
		this.no = no;
		this.title = title;
		this.desc = desc;
		this.date = date;
		this.state = state;
	}
	
	public Todo(String title, String desc, Date date, char state) {
		this.title = title;
		this.desc = desc;
		this.date = date;
		this.state = state;
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

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}
}
