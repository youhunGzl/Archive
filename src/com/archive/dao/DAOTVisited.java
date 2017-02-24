package com.archive.dao;

import java.util.ArrayList;
import java.util.List;

import com.archive.jpa.TVisited;
import com.archive.utility.Pager;
import com.archive.utility.baseUtil;

public class DAOTVisited {
	
	//查找浏览记录
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TVisited> find(TVisited msg,Pager pager){
		baseUtil session = new baseUtil();
		List<TVisited> list =new ArrayList();
		List arr = new ArrayList();
		
		String query="from TVisited where 1=1 ";
		if(msg != null){
			if(msg.getArchive_type()!=null){
				arr.add(msg.getArchive_type());
			    query=query+"and archive_type = ? "; 
			}
			if(msg.getArchive_id()!=null){
				arr.add(msg.getArchive_id());
			    query=query+"and archive_id= ? "; 
			}
			if(msg.gettUser_id()!=null){
				arr.add(msg.gettUser_id());
			    query=query+"and tUser_id = ? "; 
			}
			query=query+"order by time asc";
		}
		
		list = session.findList(query, arr);
		return list;
	}
	
	//保持浏览记录
	@SuppressWarnings("rawtypes")
	public void save(TVisited msg){
		baseUtil session = new baseUtil();
		session.save(msg);
	}
	
	//计算浏览记录
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public int count(TVisited msg){
		baseUtil session = new baseUtil();
		int list = 0 ;
		List arr = new ArrayList();
		
		String query="select count(*) from TVisited where 1=1 ";
		if(msg != null){
			if(msg.getArchive_type()!=null){
				arr.add(msg.getArchive_type());
			    query=query+"and archive_type = ? "; 
			}
			if(msg.getArchive_id()!=null){
				arr.add(msg.getArchive_id());
			    query=query+"and archive_id= ? "; 
			}
			if(msg.gettUser_id()!=null){
				arr.add(msg.gettUser_id());
			    query=query+"and tUser_id = ? "; 
			}
		}
		
		list = session.findCount(query, arr);
		return list;
	}
}
