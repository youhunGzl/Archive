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


//用户浏览历史表
@Entity
@Table(catalog="digital",name="tb_Record") 
public class TRecord implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private TUsers tUsers_id;	//用户Id
	private String search_word;	//检索词
	private Date operator_time;	//操作时间
	private String operator_type; //操作类型
	private String operator_content; //操作内容/路径
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
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tUsers_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TUsers gettUsers_id() {
		return tUsers_id;
	}
	public void settUsers_id(TUsers tUsers_id) {
		this.tUsers_id = tUsers_id;
	}
	public String getSearch_word() {
		return search_word;
	}
	public void setSearch_word(String search_word) {
		this.search_word = search_word;
	}
	public Date getOperator_time() {
		return operator_time;
	}
	public void setOperator_time(Date operator_time) {
		this.operator_time = operator_time;
	}
	public String getOperator_type() {
		return operator_type;
	}
	public void setOperator_type(String operator_type) {
		this.operator_type = operator_type;
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
