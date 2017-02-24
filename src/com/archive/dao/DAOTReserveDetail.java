package com.archive.dao;

import java.util.ArrayList;
import java.util.List;


import com.archive.jpa.TReserveDetail;
import com.archive.utility.baseUtil;

//预约借阅详细信息
public class DAOTReserveDetail {
	

	//查找所有订阅单详情
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TReserveDetail> getall(){
		baseUtil session = new baseUtil();
		List<TReserveDetail> li = null;
		li = session.findList("from TReserveDetail ", null);
		return li;
	}
		
	
	//通过订阅单id查找订阅单详情
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<TReserveDetail> getbyReserve(String rid){
		baseUtil session = new baseUtil();
		List<TReserveDetail> li = null;
		List arr = new ArrayList();
		arr.add(rid);
		
		li = session.findList("from TReserveDetail where tTReserve_id = ?", arr);
		return li;
	}
	
	//通过id查找订阅单详情
	@SuppressWarnings("rawtypes")
	public TReserveDetail getbyRId(long l){
		baseUtil session = new baseUtil();
		TReserveDetail li = new TReserveDetail();
		li = (TReserveDetail) session.findByCond("from TReserveDetail where id = "+l);
		return li;
	}
	
	//通过用户id查找订阅单详情未加入购物单
		@SuppressWarnings({ "rawtypes", "unchecked"  })
		public List<TReserveDetail> getbyUId(long l){
			List<TReserveDetail> li = new ArrayList();
			List arr = new ArrayList();
			baseUtil session = new baseUtil();
			arr.add(l);
			li = session.findList("from TReserveDetail where tusers_id = ?", arr);
			List<TReserveDetail> list = new ArrayList();
			
			for(TReserveDetail check:li){
				if(check.gettTReserve_id()!=null){
				}else{
					list.add(check);
				}
				
			}
			return list;
		}
		
		//通过用户id查找订阅单详情已加入购物单
		@SuppressWarnings({ "unchecked", "rawtypes" })
		public List<TReserveDetail> getbyUIdok(long l){
			baseUtil session = new baseUtil();
			List<TReserveDetail> li = new ArrayList();
			List arr = new ArrayList();
			arr.add(l);
			li = session.findList("from TReserveDetail where tusers_id = ?", arr);
			List<TReserveDetail> list = new ArrayList();
			
			for(TReserveDetail check:li){
				if(check.gettTReserve_id()!=null){
					list.add(check);
				}
				
			}
			return list;
		}
	
	//保存订阅单详情
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public  boolean save(TReserveDetail data){
		baseUtil session = new baseUtil();
		if(data != null){
			List<TReserveDetail> li = null;
			List arr = new ArrayList();
			arr.add(data.getArchive_id());
			arr.add(data.getArchive_type());
			arr.add(data.gettUsers_id());
			li = session.findList("from TReserveDetail where archive_id = ? and archive_type = ? and tUsers_id = ?", arr);
			
			
			if(li.size() == 0){
				session.save(data);
				return true;
			}else{
				return false;
			}
			
		}
		return false;
		
	}
	
	//修改订阅单详情
	@SuppressWarnings("rawtypes")
	public void update(List<TReserveDetail> msg){
		baseUtil session = new baseUtil();
		
		for(TReserveDetail result:msg){
			if(result != null){
				session.update(result);
			}
			
		}
		
	}
}
