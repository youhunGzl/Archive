package com.archive.dao;

import java.util.ArrayList;
import java.util.List;


import com.archive.jpa.TCatalog;
import com.archive.utility.baseUtil;

public class DAOTCatalog {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TCatalog> find(TCatalog msg){
		List arr = new ArrayList();
		List<TCatalog> list = new ArrayList();
		baseUtil session = new baseUtil();
		
		String query="from TCatalog where 1=1 ";
		if(msg != null){
			if(msg.getCatalog_no()!=null){
				arr.add(msg.getCatalog_no());
			    query=query+"and catalog_no = ? "; 
			}else if(msg.getParent_id() != null){
				arr.add(msg.getParent_id());
			    query=query+"and parent_id = ? "; 
			}else if(msg.getStatus() != null){
				arr.add(msg.getStatus());
			    query=query+"and status = ? "; 
			}
		}
		
		list = session.findList(query, arr);
		
		return list;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int count(TCatalog msg,String is){
		List arr = new ArrayList();
		baseUtil session = new baseUtil();
		arr.add(msg);
		
		String query1="select count(*) from TFiles where tCatalog_id = ?";
		String query2="select count(*) from TMaterial where tCatalog_id = ?";
		String query3="select count(*) from TMultimedia where tCatalog_id = ?";
		int i1 = session.findCount(query1, arr);
		int i2 = session.findCount(query2, arr);
		int i3 = session.findCount(query3, arr);
		int i = 0;
		if(is == "all"){
			i= i1+i2+i3;
		}else if(is == "file"){
			i=i1;
		}else if(is == "material"){
			i=i2;
		}else if(is == "multimedia"){
			i=i3;
		}
		
		
		
		return i;
	}
	
	
}
