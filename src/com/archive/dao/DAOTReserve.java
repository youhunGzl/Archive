package com.archive.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import com.archive.jpa.TReserve;
import com.archive.jpa.TUsers;
import com.archive.utility.baseUtil;

//预约借阅单
public class DAOTReserve {
	
	
	//通过用户名查找预约订阅单
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TReserve> getbyuser(String uid) throws Exception{
		baseUtil session = new baseUtil();
		List<TReserve> li = null;
		List arr = new ArrayList();
		TUsers u = new TUsers();
		u.setId(Long.parseLong(uid));
		arr.add(u);
		
		li = session.findPage("from TReserve where tUsers_id = ?", arr,0,0);
		return li;
	}
	
	//通过用户名查找预约订阅单
		@SuppressWarnings({ "rawtypes", "unchecked" })
		public List<TReserve> getbysuccess(String uid,Date date) throws Exception{
			baseUtil session = new baseUtil();
			List<TReserve> li = new ArrayList();
			List<TReserve> li1 = new ArrayList();
			List<TReserve> li2 = new ArrayList();
			List arr = new ArrayList();
			TUsers u = new TUsers();
			u.setId(Long.parseLong(uid));
			String pa  = "from TReserve where tUsers_id = ? and audiu_status = ? ";
			if(date != null){
				pa = pa+"and audiu_time >= ?"; 
			}
			arr.add(u);
			arr.add("1"); //1==同意
			arr.add(date);
			li1 = session.findPage(pa, arr,0,0);
			for(int i = 0 ;i<li1.size();i++){
				li.add(li1.get(i));
			}
			
			arr.set(1, "2");//2==拒绝
			li2 = session.findPage(pa, arr,0,0);
			for(int i = 0 ;i<li2.size();i++){
				li.add(li2.get(i));
			}
			return li;
		}
	
	//保存预约订阅单
	@SuppressWarnings("rawtypes")
	public TReserve save(TReserve msg) throws Exception{
		baseUtil session = new baseUtil();
		if(msg!=null){
			session.save(msg);
		}
		return msg;
		
	}
	
	@SuppressWarnings("rawtypes")
	public void update(TReserve msg) throws Exception{
		baseUtil session = new baseUtil();
		session.update(msg);
	}
}
