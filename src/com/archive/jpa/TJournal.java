package com.archive.jpa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//日志表
@Entity
@Table(catalog="digital",name="tb_Journal") 
public class TJournal implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String tStaff_id;	//操作者Id
	private Date operator_time;	//操作时间
	private String operator_content;	//操作内容/路径
	private String ip_address; //IP地址
	private String remark; //备注
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String gettStaff_id() {
		return tStaff_id;
	}
	public void settStaff_id(String tStaff_id) {
		this.tStaff_id = tStaff_id;
	}
	public Date getOperator_time() {
		return operator_time;
	}
	public void setOperator_time(Date operator_time) {
		this.operator_time = operator_time;
	}
	public String getOperator_content() {
		return operator_content;
	}
	public void setOperator_content(String operator_content) {
		this.operator_content = operator_content;
	}
	public String getIp_address() {
		return ip_address;
	}
	public void setIp_address(String ip_address) {
		this.ip_address = ip_address;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	
}
