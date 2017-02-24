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

//文件级档案信息
@Entity
@Table(catalog="digital",name="tb_files")
public class TFiles implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long id;	//记录号
	private String resource_no;	//档案资料编号
	private TCatalog  tCatalog_id; //目录Id
	private String piece_no;	//件号
	private String unit_code;	//档案馆代码
	private TEdocument tEdocument_id;	//电子文档号
	private String name;	//题名
	private String file_no;	//文件编号
	private String author;	//责任者
	private String attach_name;	//附件名
	private String doc_version;	//稿本
	private String doc_type;	//文种
	private String pages;	//页数
	private Date create_time;	//形成时间
	private String carrier_type;	//载体类型
	private String secret_level;	//密级
	private String subject_theme;	//档案主题词
	private String subject_abstract;	//档案提要
	private String notions;	//附注
	private String status;	//状态
	private String isFree;  //是否免费
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
	public String getPiece_no() {
		return piece_no;
	}
	public void setPiece_no(String piece_no) {
		this.piece_no = piece_no;
	}
	public String getUnit_code() {
		return unit_code;
	}
	public void setUnit_code(String unit_code) {
		this.unit_code = unit_code;
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
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFile_no() {
		return file_no;
	}
	public void setFile_no(String file_no) {
		this.file_no = file_no;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAttach_name() {
		return attach_name;
	}
	public void setAttach_name(String attach_name) {
		this.attach_name = attach_name;
	}
	public String getDoc_version() {
		return doc_version;
	}
	public void setDoc_version(String doc_version) {
		this.doc_version = doc_version;
	}
	public String getDoc_type() {
		return doc_type;
	}
	public void setDoc_type(String doc_type) {
		this.doc_type = doc_type;
	}
	public String getPages() {
		return pages;
	}
	public void setPages(String pages) {
		this.pages = pages;
	}
	public Date getCreate_time() {
		return create_time;
	}
	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}
	public String getCarrier_type() {
		return carrier_type;
	}
	public void setCarrier_type(String carrier_type) {
		this.carrier_type = carrier_type;
	}
	public String getSecret_level() {
		return secret_level;
	}
	public void setSecret_level(String secret_level) {
		this.secret_level = secret_level;
	}
	public String getSubject_theme() {
		return subject_theme;
	}
	public void setSubject_theme(String subject_theme) {
		this.subject_theme = subject_theme;
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
	@Override
	public String toString() {
		return "TFiles [id=" + id + ", resource_no=" + resource_no
				+ ", tCatalog_id=" + tCatalog_id + ", piece_no=" + piece_no
				+ ", unit_code=" + unit_code + ", tEdocument_id="
				+ tEdocument_id + ", name=" + name + ", file_no=" + file_no
				+ ", author=" + author + ", attach_name=" + attach_name
				+ ", doc_version=" + doc_version + ", doc_type=" + doc_type
				+ ", pages=" + pages + ", create_time=" + create_time
				+ ", carrier_type=" + carrier_type + ", secret_level="
				+ secret_level + ", subject_theme=" + subject_theme
				+ ", subject_abstract=" + subject_abstract + ", notions="
				+ notions + ", status=" + status + ", isFree=" + isFree
				+ ", period=" + period + ", file_time=" + file_time
				+ ", tag_theme=" + tag_theme + ", tag_item=" + tag_item + "]";
	}
	
	
}
