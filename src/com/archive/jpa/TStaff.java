package com.archive.jpa;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Entity;

//工作人员
@Entity
@Table(catalog="digital",name="tb_staff") 
public class TStaff implements Serializable{
	/**
	 * 工作人员
	 * TStaff
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;	
	private String suserName;	   //user name
	private String staffNo;
	private String staffPwd;       
	private String realName;       //real name
	private String staffGrant;
		
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSuserName() {
		return suserName;
	}
	public void setSuserName(String suserName) {
		this.suserName = suserName;
	}
	public String getStaffNo() {
		return staffNo;
	}
	public void setStaffNo(String staffNo) {
		this.staffNo = staffNo;
	}
	public String getStaffPwd() {
		return staffPwd;
	}
	public void setStaffPwd(String staffPwd) {
		this.staffPwd = staffPwd;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getStaffGrant() {
		return staffGrant;
	}
	public void setStaffGrant(String staffGrant) {
		this.staffGrant = staffGrant;
	}
	
	
	
	
	
	


}