package com.archive.dao;

import java.util.ArrayList;
import java.util.List;


import com.archive.jpa.TInterest;
import com.archive.utility.Pager;
import com.archive.utility.baseUtil;

public class DAOTInterest {
	
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public TInterest find(TInterest msg,Pager pager){
		List<TInterest> list =new ArrayList();
		List arr = new ArrayList();
		baseUtil session = new baseUtil();
		
		String query="from TInterest where 1=1";
		if(msg != null){
			if(msg.gettUsers_id()!=null){
				arr.add(msg.gettUsers_id());
			    query=query+"and tUsers_id = ?"; 
			}
		}
		
		list = session.findList(query, arr);
		if(list.size() != 0){
			return list.get(0);
		}else{
			return null;
		}
		
		
	}
	
	//保持浏览记录
	@SuppressWarnings("rawtypes")
	public void save(TInterest msg){
		baseUtil session = new baseUtil();
		session.save(msg);
	}
	
	@SuppressWarnings("rawtypes")
	public void update(TInterest msg){
		baseUtil session = new baseUtil();
		session.update(msg);
	}
	
}
