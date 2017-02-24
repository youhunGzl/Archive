package com.archive.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.archive.jpa.TMultimedia;
import com.archive.utility.Pager;
import com.archive.utility.baseUtil;

public class DAOTMultimedia {
	
	
	//查找所有档案
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<TMultimedia> getAll(){
			baseUtil session = new baseUtil();
			List<TMultimedia> li = null;	
			li = session.findList("from TMultimedia", null);
			return li;
		}
	
	//通过查找条件查询媒体档案
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TMultimedia> find(TMultimedia data, Date firstcreate_time, Date endcreate_time, Pager pager){
		baseUtil session = new baseUtil();
		List<TMultimedia> li = null;	
		String query="from TMultimedia where 1=1";
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
			if(data.getAuthor()!=null){
				if(data.getAuthor()!=""&&data.getAuthor().length()>0){ 
					arr.add(data.getAuthor());
				    query=query+"and author like '%"+data.getAuthor()+"%' ";
				}
			} 
			if(data.getMedia_type()!=null){
				if(data.getMedia_type()!="" && data.getMedia_type().length()>0){
					arr.add(data.getMedia_type());
				    query=query+"and media_type= ?"; 
				}
			}
			if(data.gettCatalog_id()!=null){
				arr.add(data.gettCatalog_id());
				query=query+"and tCatalog_id= ?"; 
			}
			if(data.getUnit_code()!=null){
				if(data.getUnit_code()!="" && data.getUnit_code().length()>0){
					arr.add(data.getUnit_code());
				    query=query+"and unit_code= ?"; 
				}
			}
			if(data.getIllustrate()!=null){
				if(data.getIllustrate()!="" && data.getIllustrate().length()>0){
				    query=query+"and illustrate like '%"+data.getIllustrate()+"%' "; 
				}
			}
			if(data.getName()!=null){
				if(data.getName()!="" && data.getName().length()>0){
					arr.add(data.getName());
				    query=query+"and name like '%"+data.getName()+"%' "; 
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
		query=query+"order by create_time desc";
		if(pager != null){
			li = session.findPage(query, arr, (pager.getCurrentPage()-1)*pager.getPageSize(), pager.getPageSize());
		}else{
			li = session.findList(query, arr);
		}
		return li;
	}
	//通过id查询媒体档案
	@SuppressWarnings("rawtypes")
	public TMultimedia getbyid(long id){
		baseUtil session = new baseUtil();
		TMultimedia li = new TMultimedia();
		li = (TMultimedia) session.findById(li, id);
		return li;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int gettotal(TMultimedia data, Date firstcreate_time, Date endcreate_time){
		baseUtil session = new baseUtil();
		int li = 0;	
		String query="select count(*) from TMultimedia where 1=1";
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
			if(data.getAuthor()!=null){
				if(data.getAuthor()!=""&&data.getAuthor().length()>0){ 
					arr.add(data.getAuthor());
				    query=query+"and author like '%"+data.getAuthor()+"%' ";
				}
			} 
			if(data.getMedia_type()!=null){
				if(data.getMedia_type()!="" && data.getMedia_type().length()>0){
					arr.add(data.getMedia_type());
				    query=query+"and media_type= ?"; 
				}
			}
			if(data.gettCatalog_id()!=null){
				arr.add(data.gettCatalog_id());
				query=query+"and tCatalog_id= ?"; 
			}
			if(data.getUnit_code()!=null){
				if(data.getUnit_code()!="" && data.getUnit_code().length()>0){
					arr.add(data.getUnit_code());
				    query=query+"and unit_code= ?"; 
				}
			}
			if(data.getIllustrate()!=null){
				if(data.getIllustrate()!="" && data.getIllustrate().length()>0){
				    query=query+"and illustrate like '%"+data.getIllustrate()+"%' "; 
				}
			}
			if(data.getName()!=null){
				if(data.getName()!="" && data.getName().length()>0){
					arr.add(data.getName());
				    query=query+"and name like '%"+data.getName()+"%' "; 
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
		li = session.findCount(query, arr);
		return li;
	}
}
