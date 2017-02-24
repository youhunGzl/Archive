package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//订单详细
@Entity
@Table(catalog="digital",name="tb_orderDetail")
public class TOrdersDetail implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private TOrders tTOrders_id;	//订单记录号
	private TFiles tTFiles_id;	//预约文件级档案编号
	private TMaterial tTMaterial_id; //预约资料档案编号
	private TRecordsFile tTRecordsFile_id; //预约案卷文件档案编号
	private TMultimedia tTMultimedia_id; //预约多媒体文件档案编号
	private Integer copies;	//份数
	private String postStatus;	//邮寄状态
	private String downStatus;	//下载状态
	private TUsers tTUsers_id;	//下载人
	private TStaff tTStaff_id;	//邮寄人
	private Date times;	//操作时间
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TOrders gettTOrders_id() {
		return tTOrders_id;
	}
	public void settTOrders_id(TOrders tTOrders_id) {
		this.tTOrders_id = tTOrders_id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TFiles gettTFiles_id() {
		return tTFiles_id;
	}
	public void settTFiles_id(TFiles tTFiles_id) {
		this.tTFiles_id = tTFiles_id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TMaterial gettTMaterial_id() {
		return tTMaterial_id;
	}
	public void settTMaterial_id(TMaterial tTMaterial_id) {
		this.tTMaterial_id = tTMaterial_id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TRecordsFile gettTRecordsFile_id() {
		return tTRecordsFile_id;
	}
	public void settTRecordsFile_id(TRecordsFile tTRecordsFile_id) {
		this.tTRecordsFile_id = tTRecordsFile_id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TMultimedia gettTMultimedia_id() {
		return tTMultimedia_id;
	}
	public void settTMultimedia_id(TMultimedia tTMultimedia_id) {
		this.tTMultimedia_id = tTMultimedia_id;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public String getPostStatus() {
		return postStatus;
	}
	public void setPostStatus(String postStatus) {
		this.postStatus = postStatus;
	}
	public String getDownStatus() {
		return downStatus;
	}
	public void setDownStatus(String downStatus) {
		this.downStatus = downStatus;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TUsers gettTUsers_id() {
		return tTUsers_id;
	}
	public void settTUsers_id(TUsers tTUsers_id) {
		this.tTUsers_id = tTUsers_id;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TStaff gettTStaff_id() {
		return tTStaff_id;
	}
	public void settTStaff_id(TStaff tTStaff_id) {
		this.tTStaff_id = tTStaff_id;
	}
	public Date getTimes() {
		return times;
	}
	public void setTimes(Date times) {
		this.times = times;
	}
	
}
