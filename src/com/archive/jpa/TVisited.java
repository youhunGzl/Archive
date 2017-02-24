package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//资源受访情况
@Entity
@Table(name="tb_visited",catalog="digital") 
public class TVisited implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String archive_type; //档案类型
	private String archive_id; //预约档案编号
	private TUsers tUser_id;	//用户编号
	private Date time;	//获取时间
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public String getArchive_type() {
		return archive_type;
	}
	public void setArchive_type(String archive_type) {
		this.archive_type = archive_type;
	}
	public String getArchive_id() {
		return archive_id;
	}
	public void setArchive_id(String archive_id) {
		this.archive_id = archive_id;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tUser_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TUsers gettUser_id() {
		return tUser_id;
	}
	public void settUser_id(TUsers tUser_id) {
		this.tUser_id = tUser_id;
	}	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	
}
