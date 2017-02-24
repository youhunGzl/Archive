package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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

//预约借阅单
@Entity
@Table(catalog="digital",name="tb_reserve")
public class TReserve implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String reserve_no;	//预约单编号
	private Date reserve_time;	//预约时间
	private String borrow_objects;	//借阅目的
	private TUsers tUsers_id;	//用户编号
	private String audiu_status;	//审核状态
	private TStaff tStaff_id;	//审核人
	private Date audiu_time;	//审核时间
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getReserve_no() {
		return reserve_no;
	}
	public void setReserve_no(String reserve_no) {
		this.reserve_no = reserve_no;
	}
	public Date getReserve_time() {
		return reserve_time;
	}
	public void setReserve_time(Date reserve_time) {
		this.reserve_time = reserve_time;
	}
	public String getBorrow_objects() {
		return borrow_objects;
	}
	public void setBorrow_objects(String borrow_objects) {
		this.borrow_objects = borrow_objects;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tUsers_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TUsers gettUsers_id() {
		return tUsers_id;
	}
	public void settUsers_id(TUsers tUsers_id) {
		this.tUsers_id = tUsers_id;
	}
	public String getAudiu_status() {
		return audiu_status;
	}
	public void setAudiu_status(String audiu_status) {
		this.audiu_status = audiu_status;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tStaff_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TStaff gettStaff_id() {
		return tStaff_id;
	}
	public void settStaff_id(TStaff tStaff_id) {
		this.tStaff_id = tStaff_id;
	}
	public Date getAudiu_time() {
		return audiu_time;
	}
	public void setAudiu_time(Date audiu_time) {
		this.audiu_time = audiu_time;
	}

	
}
