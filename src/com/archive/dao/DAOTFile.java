package com.archive.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.archive.jpa.TFiles;
import com.archive.utility.Pager;
import com.archive.utility.baseUtil;

/*
 * 文件级类CUID操作
 */

public class DAOTFile {
	
	
	//获得所有文件档案
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TFiles> getAll(){
		List<TFiles> li = null;	
		baseUtil session = new baseUtil();
		li = session.findList("from TFiles", null);
		return li;
	}
	//通过条件查找文件档案
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TFiles> find(TFiles data, Date firstcreate_time, Date endcreate_time, Pager pager){
		List<TFiles> li = null;
		baseUtil session = new baseUtil();
		String query="from TFiles where 1=1";
		List arr = new ArrayList();
		if(data != null){
			if(data.getNotions()!=null){
				if(data.getNotions()!="" && data.getNotions().length()>0){
				    query=query+"and notions like '%"+data.getNotions()+"%'"; 
				}
			}
			if(data.getResource_no()!=null){
				if(data.getResource_no()!="" && data.getResource_no().length()>0){
					arr.add(data.getResource_no());
				    query=query+"and resource_no= ?"; 
				}
			}
			if(data.gettCatalog_id()!=null){
				arr.add(data.gettCatalog_id());
				query=query+"and tCatalog_id= ?"; 
			}
			if(data.getAuthor()!=null){
				if(data.getAuthor()!=""&&data.getAuthor().length()>0){ 
				    query=query+"and author like '%"+data.getAuthor()+"%' ";
				}
			} 
			if(data.getSubject_abstract()!=null){
				if(data.getSubject_abstract()!="" && data.getSubject_abstract().length()>0){
				    query=query+"and subject_abstract like '%"+data.getSubject_abstract()+"%' "; 
				}
			}
			if(data.getUnit_code()!=null){
				if(data.getUnit_code()!="" && data.getUnit_code().length()>0){
					arr.add(data.getUnit_code());
				    query=query+"and unit_code= ?"; 
				}
			}
			if(data.getName()!=null){
				if(data.getName()!="" && data.getName().length()>0){
				    query=query+"and name like '%"+data.getName()+"%' "; 
				}
			}
			if(data.getFile_no()!=null){
				if(data.getFile_no()!="" && data.getFile_no().length()>0){
					arr.add(data.getFile_no());
				    query=query+"and file_no= ?"; 
				}
			}
			if(data.getPiece_no()!=null){
				if(data.getPiece_no()!="" && data.getPiece_no().length()>0){
					arr.add(data.getPiece_no());
				    query=query+"and piece_no= ?"; 
				}
			}
		}
		
		if(firstcreate_time != null){
			if(firstcreate_time instanceof Date){
				if(endcreate_time != null){
					if(endcreate_time instanceof Date){
						arr.add(firstcreate_time);
						arr.add(endcreate_time);
					    query=query+"and create_time between ? and ?"; 
					}
				}else{
					arr.add(firstcreate_time);
				    query=query+"and create_time >= ?"; 
				}
			}
		}
		if(endcreate_time != null){
			if(endcreate_time instanceof Date){
				if(firstcreate_time != null){
					if(firstcreate_time instanceof Date){
					}else{
						arr.add(endcreate_time);
					    query=query+"and create_time <= ?"; 
					}
				}
			}
		}
		query=query+" order by create_time desc";
		if(pager != null){
			li = session.findPage(query, arr, (pager.getCurrentPage()-1)*pager.getPageSize(), pager.getPageSize());
		}else{
			li = session.findList(query, arr);
		}
		
		return li;
	}
	//通过id查找档案
	@SuppressWarnings("rawtypes")
	public TFiles getbyid(long id){
		TFiles li = new TFiles();
		baseUtil session = new baseUtil();
		li = (TFiles) session.findById(li, id);
		return li;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int gettotal(TFiles data, Date firstcreate_time, Date endcreate_time) {
		int li = 0;
		baseUtil session = new baseUtil();
		String query="select count(*) from TFiles where 1=1";
		List arr = new ArrayList();
		if(data.getNotions()!=null){
			if(data.getNotions()!="" && data.getNotions().length()>0){
			    query=query+"and notions like '%"+data.getNotions()+"%'"; 
			}
		}
		if(data.getId()>0){
			arr.add(data.getId());
			query=query+"and id= ?"; 
		}
		if(data.getResource_no()!=null){
			if(data.getResource_no()!="" && data.getResource_no().length()>0){
				arr.add(data.getResource_no());
			    query=query+"and resource_no= ?"; 
			}
		}
		if(data.getAuthor()!=null){
			if(data.getAuthor()!=""&&data.getAuthor().length()>0){ 
				query=query+"and author like '%"+data.getAuthor()+"%' ";
			}
		} 
		if(data.gettCatalog_id()!=null){
			arr.add(data.gettCatalog_id());
			query=query+"and tCatalog_id= ?"; 
		}
		if(data.getSubject_abstract()!=null){
			if(data.getSubject_abstract()!="" && data.getSubject_abstract().length()>0){
			    query=query+"and subject_abstract like '%"+data.getSubject_abstract()+"%' "; 
			}
		}
		if(data.getUnit_code()!=null){
			if(data.getUnit_code()!="" && data.getUnit_code().length()>0){
				arr.add(data.getUnit_code());
			    query=query+"and unit_code= ?"; 
			}
		}
		if(data.getName()!=null){
			if(data.getName()!="" && data.getName().length()>0){
				query=query+"and name like '%"+data.getName()+"%' "; 
			}
		}
		if(data.getFile_no()!=null){
			if(data.getFile_no()!="" && data.getFile_no().length()>0){
				arr.add(data.getFile_no());
			    query=query+"and file_no= ?"; 
			}
		}
		if(data.getPiece_no()!=null){
			if(data.getPiece_no()!="" && data.getPiece_no().length()>0){
				arr.add(data.getPiece_no());
			    query=query+"and piece_no= ?"; 
			}
		}
		
		if(firstcreate_time != null){
			if(firstcreate_time instanceof Date){
				if(endcreate_time != null){
					if(endcreate_time instanceof Date){
						arr.add(firstcreate_time);
						arr.add(endcreate_time);
					    query=query+"and create_time between ? and ?"; 
					}
				}else{
					arr.add(firstcreate_time);
				    query=query+"and create_time >= ?"; 
				}
			}
		}
		if(endcreate_time != null){
			if(endcreate_time instanceof Date){
				if(firstcreate_time != null){
					if(firstcreate_time instanceof Date){
					}else{
						arr.add(endcreate_time);
					    query=query+"and create_time <= ?"; 
					}
				}
			}
		}
		li = session.findCount(query, arr);
		return li;
	}
}