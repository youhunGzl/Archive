package com.archive.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


import com.archive.jpa.TUsers;
import com.archive.utility.baseUtil;

public class DAOTUsers {
	

	//保存用户
	@SuppressWarnings("rawtypes")
	public TUsers saveUsers(TUsers u) throws Exception{
		baseUtil session = new baseUtil();
		if (u!=null)
		{
			session.save(u);
		}
				
		return u;
	}
	
	//登录
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public String getUsers(String username, String pwd) throws Exception{
		baseUtil session = new baseUtil();
		List<TUsers> li = null;
		TUsers user = null;
		li = session.validate("from TUsers where user_name ='"+username+"'");
		Iterator it = li.iterator();
		 //将链表的元素迭代出并打印
		while(it.hasNext())
			user = (TUsers) it.next();
		if(user != null){
			if(pwd.equals(user.getUser_pwd())){
				return user.getUser_name();
			}else{
				return "pwd error";
			}
		}else{
			return "no user";
		}
	}

	//通过用户名查找用户
	@SuppressWarnings("rawtypes")
	public  TUsers getUserByUsername(String username) {
		baseUtil session = new baseUtil();
		// TODO Auto-generated method stub
		TUsers user = new TUsers();
		user = (TUsers) session.findByCond("from TUsers where user_name ='"+username+"'");
		return user;
	}
	
	//查找所有用户
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public  List<TUsers> getall() {
			baseUtil session = new baseUtil();
			// TODO Auto-generated method stub
			List<TUsers> user = new ArrayList();
			user = session.findList("from TUsers ",null);
			return user;
		}
	
	//更新用户
	@SuppressWarnings("rawtypes")
	public  void updateTUsers(TUsers u) {
		baseUtil session = new baseUtil();
		// TODO Auto-generated method stub
		if(u != null){
			session.update(u);
		}
	}
}
