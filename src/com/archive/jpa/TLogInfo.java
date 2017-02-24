package com.archive.jpa;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//日志信息
@Entity
@Table(catalog="digital",name="tb_Sloginfo")
public class TLogInfo {
	private Integer id;	//记录号
	private TStaff staff_id; //操作者id
	private Date operator_time;	//操作时间
	private String operator_content; //操作内容/路径
	private String ip;  //ip地址
	private String remark; //备注
	
	//查询数据，不插入数据库
	private String starttime;
	private String endtime;
	
	public TLogInfo(Integer id,TStaff staff_id,Date operator_time,String operator_content,String ip,String remark){
		this.id=id;
		this.staff_id=staff_id;
		this.operator_time=operator_time;
		this.operator_content=operator_content;
		this.ip=ip;
		this.remark=remark;
	}
	public TLogInfo(){
		
	}
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@JoinColumn(name = "staff_id", referencedColumnName = "id")
	public TStaff getstaff_id() {
		return staff_id;
	}
	public void setstaff_id(TStaff staff_id) {
		this.staff_id = staff_id;
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	@Transient
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	@Transient
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	
}
