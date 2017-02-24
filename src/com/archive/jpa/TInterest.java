package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//资源受访情况
@Entity
@Table(catalog="digital",name="tb_interest")
public class TInterest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String tUsers_id;	//用户编号
	private String interest_theme;	//兴趣列表
	private String interest_item;	//兴趣列表
	private Date update_time;	//获取时间
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String gettUsers_id() {
		return tUsers_id;
	}
	public void settUsers_id(String tUsers_id) {
		this.tUsers_id = tUsers_id;
	}
	
	public String getInterest_theme() {
		return interest_theme;
	}
	public void setInterest_theme(String interest_theme) {
		this.interest_theme = interest_theme;
	}
	public String getInterest_item() {
		return interest_item;
	}
	public void setInterest_item(String interest_item) {
		this.interest_item = interest_item;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	
	
}
