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

//预约借阅详细信息
@Entity
@Table(catalog="digital",name="tb_reserveDetail")
public class TReserveDetail implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private TReserve tTReserve_id;	//预约单号
	private String archive_type; //档案类型
	private String archive_id; //预约档案编号
	private String audiu_result; //审核结果
	private TUsers tUsers_id;      //用户外键
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tTReserve_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TReserve gettTReserve_id() {
		return tTReserve_id;
	}
	public void settTReserve_id(TReserve tTReserve_id) {
		this.tTReserve_id = tTReserve_id;
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
	public String getAudiu_result() {
		return audiu_result;
	}
	public void setAudiu_result(String audiu_result) {
		this.audiu_result = audiu_result;
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
	
}
