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


//借还信息
@Entity
@Table(name="tb_dictionary",catalog="digital")
public class TDictionary implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer id;
	private String name; //字典名称
	private String code; //字典代码
	private TDictionaryType typecode; //字典类型代码
	private String status; //状态
	private String remark; //备注
	
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
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="typecode",referencedColumnName = "typecode")
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TDictionaryType getTypecode() {
		return typecode;
	}
	public void setTypecode(TDictionaryType typecode) {
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
