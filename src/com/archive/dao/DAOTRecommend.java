package com.archive.dao;

import java.util.ArrayList;
import java.util.List;

import com.archive.jpa.TRecommend;
import com.archive.utility.baseUtil;

public class DAOTRecommend {
	
	//保存所有文件档案
	@SuppressWarnings({ "rawtypes" })
	public void save(TRecommend tr){
		baseUtil session = new baseUtil();
		session.save(tr);
	}
	
	@SuppressWarnings({ "rawtypes" })
	public void delete(){
		baseUtil session = new baseUtil();
		session.deleteall("delete from TRecommend where 1=1");
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TRecommend> getall(TRecommend msg){
		baseUtil session = new baseUtil();
		List<TRecommend> li = new ArrayList();
		String query="from TRecommend where 1=1 ";
		List arr = new ArrayList();
		
		if(msg != null){
			if(msg.getArchive_type()!=null){
				arr.add(msg.getArchive_type());
			    query=query+"and archive_type = ? "; 
			}
			if(msg.getArchive_id()!=null){
				arr.add(msg.getArchive_id());
			    query=query+"and archive_id = ? "; 
			}
			if(msg.gettUser_id()!=null){
				arr.add(msg.gettUser_id());
			    query=query+"and tUser_id = ? "; 
			}
		}
		query=query+" order by similarity asc";
		li = session.findList(query, arr);
		return li;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<TRecommend> getnull(){
		baseUtil session = new baseUtil();
		List<TRecommend> li = new ArrayList();
		String query="from TRecommend where 1=1 ";
	
		query=query+" and tUser_id IS NULL";
		li = session.findList(query, null);
		return li;
	}
}
