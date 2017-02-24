package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//借还信息
@Entity
@Table(catalog="digital",name="tb_checkout")
public class TCheckout implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id; //记录号
	private TReserveDetail tReserveDetail_id; //借阅信息ID
	private TStaff staff_id; //操作人id
	private Date borrow_time;//借阅时间
	private Date return_time; //还回时间
	private String return_status; //还回状态：完好，有损，遗失
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) 
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tReserveDetail_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TReserveDetail gettReserveDetail_id() {
		return tReserveDetail_id;
	}
	public void settReserveDetail_id(TReserveDetail tReserveDetail_id) {
		this.tReserveDetail_id = tReserveDetail_id;
	}
	@ManyToOne(fetch=FetchType.EAGER)    
	@JoinColumn(name = "staff_id",referencedColumnName = "id")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TStaff getStaff_id() {
		return staff_id;
	}
	public void setStaff_id(TStaff staff_id) {
		this.staff_id = staff_id;
	}
	public Date getBorrow_time() {
		return borrow_time;
	}
	public void setBorrow_time(Date borrow_time) {
		this.borrow_time = borrow_time;
	}
	public Date getReturn_time() {
		return return_time;
	}
	public void setReturn_time(Date return_time) {
		this.return_time = return_time;
	}
	public String getReturn_status() {
		return return_status;
	}
	public void setReturn_status(String return_status) {
		this.return_status = return_status;
	}
	
	
}