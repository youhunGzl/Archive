package com.archive.jpa;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


//借还信息
@Entity
@Table(catalog="digital",name="tb_dictionarytype")
public class TDictionaryType implements Serializable {

	/**
	 * 字典类型
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;  //类型ID
	private String name; //类型名称
	private String typecode; //类型代码
	private String status;  //状态
	private String remark;  //备注
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTypecode() {
		return typecode;
	}
	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	

}