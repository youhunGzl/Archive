package com.archive.utility;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.archive.jpa.TUsers;

public class baseUtil<T> {
	private  Session session= null;
	

	
	/**
	 * 保存
	 * @param obj
	 */
	 
	public void save(Object obj){
		session = HibernateUtility.getSession();
		try{
			session.save(obj);
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			e.printStackTrace();
			HibernateUtility.rollbackTransaction(session);
		}finally{
			session.close();
		}
		
	}
	
	/**
	 * 修改
	 * @param obj
	 */
	public void update(Object obj){
		session = HibernateUtility.getSession();
		try{
			session.update(obj);
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	/**
	 * 根据传来的hql，和paras 修改
	 * @param hql（"update TUsers set userName=? where id =1"）
	 * @param paras 问号里的内容，按顺序排列
	 * @return 成功返回1
	 */
	public Integer updateByHql(String hql,final List<Object> paras){
		session = HibernateUtility.getSession();
		Integer i = null;
		try{
			Query query=session.createQuery(hql);
			if(paras!=null&&paras.size()>0)
			{
				int parasCount=paras.size();
				for (int k=0;k<parasCount;k++)
				{
					query.setParameter(k, paras.get(k));
				}
			}
			i=query.executeUpdate();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return i;
	}
	/**
	 * 根据一组id修改多行数据的状态，可以用作物理删除
	 * @param obj 格式：" 表名（实体类名）"
	 * @param str 格式："需要修改为的内容（如：state=1）"
	 * @param ids
	 * @return 成功返回1
	 */
	public Integer updateByIds(Object obj,String str,Object[] ids){
		session = HibernateUtility.getSession();
		Integer i = null;
		try{
			String hql="update "+obj.getClass().getName()+" set "+str+" where id in(:ids)";
			Query query=session.createQuery(hql);
			query.setParameterList("ids", ids);
			i=query.executeUpdate();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return i;
	}
	/**
	 * 删除 
	 * @param obj在object中id不能为空
	 */
	public void delete(Object obj){
		session = HibernateUtility.getSession();
		try{
			session.delete(obj);
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	/**
	 * 根据一组id删除多行数据
	 * @param obj 格式：" 表名（实体类名）"
	 * @param ids
	 * @return 成功返回1
	 */
	public Integer deleteByIds(Object obj,Object[] ids){
		session = HibernateUtility.getSession();
		Integer i = null;
		try{
			String hql="delete "+obj.getClass().getName()+" where id in(:ids)";
			Query query=session.createQuery(hql);
			query.setParameterList("ids", ids);
			i=query.executeUpdate();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return i;
	}
	/**
	 * 删除多行数据
	 * @param 
	 * @param delete T where 1 = 1
	 * @return 
	 */
	public void deleteall(String hql){
		session = HibernateUtility.getSession();
		try{
			Query query = session.createQuery(hql);
			query.executeUpdate();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
	}
	/**
	 * 根据id查询
	 * @param obj（实体类）
	 * @param key（id）
	 * @return 返回一个类Object（((实体类) obj)）
	 */
	@SuppressWarnings("unchecked")
	public  T findById(Object obj,Object key)
	{
		session = HibernateUtility.getSession();
		T instance=null;
		try{
			
			if (key instanceof String)
			{
				instance = (T) session.get(obj.getClass().getName(), (String)key);
			}else if(key instanceof Integer)
			{
				instance = (T) session.get(obj.getClass().getName(), (Integer)key);
			}else if(key instanceof Long)
			{
				instance = (T) session.get(obj.getClass().getName(), (Long)key);
			}
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return instance;
	}
	/**
	 * 查询多行数据
	 * @param HQL
	 * @param para（？代表的内容，para为空时查询全部数据）
	 * @return 数组list
	 */
	public List<?> findList(final String HQL,final List<Object> paras)
	{
		
		List<?> li = null;
		
		try{
			session = HibernateUtility.getSession();
			System.out.println(HQL);
			Query query=session.createQuery(HQL);
			System.out.println(query);
			if(paras!=null&&paras.size()>0)
			{
				int parasCount=paras.size();
				for (int i=0;i<parasCount;i++)
				{
					query.setParameter(i, paras.get(i));
				}
			}
			li = query.list();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			e.printStackTrace();
			HibernateUtility.rollbackTransaction(session);
			
		}finally{
			session.close();
		}
		return li;
	}
	/**
	 * 分页查询
	 * @param HQL
	 * @param paras（list）
	 * @param first 开始条数（初始为0）
	 * @param size  查询条数（需要查多小条）
	 * @return list
	 */
	@SuppressWarnings({ "unchecked" })
	public List<T> findPage(final String HQL,final List<Object> paras,final int first,final int size)
	{
		session = HibernateUtility.getSession();
		List<T> li = null;
		try{
			Query query = null;
			
			query=session.createQuery(HQL);
			if(paras!=null&&paras.size()>0){
				int parasCount=paras.size();
				int k = 0;
				for (int i=0;i<parasCount;i++){
					if(paras.get(i)!=null&&paras.get(i)!=""){
						System.out.println("========第"+k+"位=========插入"+paras.get(i)+"====");
						query.setParameter(k, paras.get(i));
						k++;
					}
				}
			}
			query.setFirstResult(first);
			query.setMaxResults(size);
			li = query.list();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			e.printStackTrace();
			HibernateUtility.rollbackTransaction(session);
			
		}finally{
			session.close();
		}
		return li;
	}
	/**
	 * 查询记录数
	 * @param HQL("select count(*)")
	 * @param paras
	 * @return
	 */
	public int findCount(final String HQL,final List<Object> paras)
	{
		session = HibernateUtility.getSession();
		int i = 0;
		try{
			Query query=session.createQuery(HQL);
			if(paras!=null&&paras.size()>0)
			{
				int parasCount=paras.size();
				for (int k=0;k<parasCount;k++)
				{
					query.setParameter(k, paras.get(k));
				}
			}
			Long count=(Long)query.uniqueResult();
			i = count.intValue();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			e.printStackTrace();
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return i;
	}
	
	public List<?> validate(final String HQL)
	{
		session = HibernateUtility.getSession();
		List<?> li = null;
		try{
			Query query = null;
			query=session.createQuery(HQL);
			li =  query.list();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		return li;
	}
	@SuppressWarnings("rawtypes")
	public Object findByCond(final String HQL)
	{
		session = HibernateUtility.getSession();
		List<?> list = null;
		try{
			
			Query query=session.createQuery(HQL);
			list = query.list();
			HibernateUtility.commitTransaction(session);
		}catch(Exception e){
			HibernateUtility.rollbackTransaction(session);
			e.printStackTrace();
		}finally{
			session.close();
		}
		if(list.size() == 1){
			return list.get(0);
		}else{
			return null;
		}
	}
	
}
