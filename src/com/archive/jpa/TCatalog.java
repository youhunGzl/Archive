package com.archive.jpa;

import java.io.Serializable;
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


//档案目录表
@Entity
@Table(catalog="digital", name="tb_Catalog")
public class TCatalog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String catalog_no;	//目录编号
	private String name;	//目录名称
	private TCatalog parent_id; //父目录Id
	private String status; //状态
	private String description; //描述
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getCatalog_no() {
		return catalog_no;
	}
	public void setCatalog_no(String catalog_no) {
		this.catalog_no = catalog_no;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@ManyToOne(fetch=FetchType.EAGER)    
	@JoinColumn(name = "parent_id")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TCatalog getParent_id() {
		return parent_id;
	}
	public void setParent_id(TCatalog parent_id) {
		this.parent_id = parent_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
