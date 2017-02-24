package com.archive.dao;

import java.util.ArrayList;
import java.util.List;


import com.archive.jpa.TRecord;
import com.archive.utility.Pager;
import com.archive.utility.baseUtil;

public class DAOTRecord {
	
	
	@SuppressWarnings("rawtypes")
	public void savelog(TRecord t){
		baseUtil session = new baseUtil();
		session.save(t);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TRecord> find(TRecord data, Pager pager){
		baseUtil session = new baseUtil();
		List<TRecord> li = new ArrayList();
		String query="from TRecord where 1=1 ";
		List arr = new ArrayList();
		if(data.gettUsers_id()!=null){
			arr.add(data.gettUsers_id());
			query=query+"and tUsers_id  = ?"; 
		}
		if(data.getSearch_word()!=null){
			if(data.getSearch_word()!=""&&data.getSearch_word().length()>0){ 
				arr.add(data.getSearch_word());
			    query=query+"and search_word= ?";
			}
		} 
		if(data.getOperator_type()!=null){
			if(data.getOperator_type()!="" && data.getOperator_type().length()>0){
				arr.add(data.getOperator_type());
			    query=query+"and operator_type= ?"; 
			}
		}
		query=query+"order by operator_time desc "; 
		if(pager != null){
			li = session.findPage(query, arr, (pager.getCurrentPage()-1)*pager.getPageSize(), pager.getPageSize());
		}else{
			li = session.findList(query, arr);
		}
		
		return li;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int gettotal(TRecord data) {
		baseUtil session = new baseUtil();
		int li = 0;
		String query="select count(*) from TRecord where 1=1 ";
		List arr = new ArrayList();
		if(data.gettUsers_id()!=null){
			arr.add(data.gettUsers_id());
			query=query+"and tUsers_id = ?"; 
		}
		if(data.getSearch_word()!=null){
			if(data.getSearch_word()!=""&&data.getSearch_word().length()>0){ 
				arr.add(data.getSearch_word());
			    query=query+"and search_word= ?";
			}
		} 
		if(data.getOperator_type()!=null){
			if(data.getOperator_type()!="" && data.getOperator_type().length()>0){
				arr.add(data.getOperator_type());
			    query=query+"and operator_type= ?"; 
			}
		}
		
		li = session.findCount(query, arr);
		return li;
	}
}
