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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

//资料档案信息
@Entity
@Table(catalog="digital",name="tb_Material")
public class TMaterial implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String resource_no; //档案资料编号
	private TCatalog  tCatalog_id; //目录Id
	private String unit_code;	//档案馆代码
	private String material_type;	//资料类别
	private String content_type;	//内容类别
	private String name;	//题名
	private String author;	//作者
	private TEdocument tEdocument_id;	//电子文档号
	private String isbn;	//ISBN
	private String publisher;	//出版社
	private Date create_time;	//形成时间
	private String subject_abstract;	//提要
	private String notions;	//附注
	private String status;	//状态
	private String isFree;  //是否免费
	private String secret_level; //密级
	private String serial_no; //顺序号
	private String period; //保管期限
	private Date file_time; //归档时间
	private String tag_theme; //资源标签主题
	private String tag_item; //资源标签项
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getResource_no() {
		return resource_no;
	}
	public void setResource_no(String resource_no) {
		this.resource_no = resource_no;
	}
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tCatalog_id", referencedColumnName = "id")  
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TCatalog gettCatalog_id() {
		return tCatalog_id;
	}
	public void settCatalog_id(TCatalog tCatalog_id) {
		this.tCatalog_id = tCatalog_id;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
	}
	public String getMaterial_type() {
		return material_type;
	}
	public void setMaterial_type(String material_type) {
		this.material_type = material_type;
	}
	public String getContent_type() {
		return content_type;
	}
	public void setContent_type(String content_type) {
		this.content_type = content_type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "tEdocument_id", referencedColumnName = "id") 
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	public TEdocument gettEdocument_id() {
		return tEdocument_id;
	}
	public void settEdocument_id(TEdocument tEdocument_id) {
		this.tEdocument_id = tEdocument_id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getSubject_abstract() {
		return subject_abstract;
	}
	public void setSubject_abstract(String subject_abstract) {
		this.subject_abstract = subject_abstract;
	}
	public String getNotions() {
		return notions;
	}
	public void setNotions(String notions) {
		this.notions = notions;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIsFree() {
		return isFree;
	}
	public void setIsFree(String isFree) {
		this.isFree = isFree;
	}
	public String getSecret_level() {
		return secret_level;
	}
	public void setSecret_level(String secret_level) {
		this.secret_level = secret_level;
	}
	public String getSerial_no() {
		return serial_no;
	}
	public void setSerial_no(String serial_no) {
		this.serial_no = serial_no;
	}
	public String getPeriod() {
		return period;
	}
	public void setPeriod(String period) {
		this.period = period;
	}
	public Date getFile_time() {
		return file_time;
	}
	public void setFile_time(Date file_time) {
		this.file_time = file_time;
	}
	public String getTag_theme() {
		return tag_theme;
	}
	public void setTag_theme(String tag_theme) {
		this.tag_theme = tag_theme;
	}
	public String getTag_item() {
		return tag_item;
	}
	public void setTag_item(String tag_item) {
		this.tag_item = tag_item;
	}
	
}
