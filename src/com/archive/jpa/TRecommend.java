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

@Entity
@Table(catalog="digital",name="tb_trecommend")
public class TRecommend implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private TUsers tUser_id;	//用户编号
	private Date update_time;	//更新时间
	private String similarity;	//兴趣相似度
	private String archive_type; //档案类型
	private String archive_id; //预约档案编号
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "tUser_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TUsers gettUser_id() {
		return tUser_id;
	}
	public void settUser_id(TUsers tUser_id) {
		this.tUser_id = tUser_id;
	}
	public Date getUpdate_time() {
		return update_time;
	}
	public void setUpdate_time(Date update_time) {
		this.update_time = update_time;
	}
	public String getSimilarity() {
		return similarity;
	}
	public void setSimilarity(String similarity) {
		this.similarity = similarity;
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
	
	
	
}
