package com.archive.utility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTInterest;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTReserveDetail;
import com.archive.dao.DAOTVisited;
import com.archive.jpa.TFiles;
import com.archive.jpa.TInterest;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TReserveDetail;
import com.archive.jpa.TUsers;
import com.archive.jpa.TVisited;

public class SimilarityBase {

	/**
	 * 传值arg[0]="Online"或者"Outline"；在线或者离线状态
	 * arg[1]=用户id；离线为空
	 * 计算相识度方法
	 * @return 
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	public static List main(String arg[]) {
		// TODO Auto-generated method stub
		System.out.println("计算相识度方法");
		List result = new ArrayList();
		
		if(arg[0] == "Outline"){
			
			Map<String , Object> base = OutlineSimilarityModel();
			System.out.println("离线"+base.toString());
			if(base == null){
				return null;
			}else{
				result = compute(base);
			}
			
		}else if(arg[0] == "Online"){
			Map<String , Object> base = OnlineSimilarityModel(arg[1]);
			System.out.println("在线");
			
			if(base == null){
				base = OutlineSimilarityModel();
			}
			if(base == null){
				return null;
			}else{
				result = compute(base);
				updateinter(arg[1]);
			}
			
		}
		return result;

		
	}
	
	/**
	 * 计算所有档案的相识度并排序
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List compute(Map<String , Object> base){
		DAOTFile fdao = new DAOTFile();
		List<TFiles> fdata = new ArrayList();
		DAOTMaterial adao = new DAOTMaterial();
		List<TMaterial> adata = new ArrayList();
		DAOTMultimedia udao = new DAOTMultimedia();
		List<TMultimedia> udata = new ArrayList();
		List<Map<String,Object>> end = new ArrayList();
		Map<String , Object> cond = new HashMap();
		Map<String , Object> result = new HashMap();
		
		fdata = fdao.getAll();
		adata = adao.getAll();
		udata = udao.getAll();
		if(fdata != null){
			for(int i = 0 ;i<fdata.size();i++){
				Map<String , Object> msg = new HashMap();
				String theme = ((TFiles) fdata.get(i)).getTag_theme();
				String item = ((TFiles) fdata.get(i)).getTag_item();
				String[] themes = theme.split(",");
				String[] items = item.split(",");
				for(int j = 0 ; j<themes.length;j++){
					msg.put(themes[j], items[j]);
				}
				String a = distance(msg,base);//计算获得相识度
				
				System.out.println(a);
				if(a != "0" ){
					result.put(a, fdata.get(i).getId());
					cond.put(a, "file");
				}
			}
		}
		if(adata != null){
			for(int i = 0 ;i<adata.size();i++){
				Map<String , Object> msg = new HashMap();
				String theme = ((TMaterial) adata.get(i)).getTag_theme();
				String item = ((TMaterial) adata.get(i)).getTag_item();
				String[] themes = theme.split(",");
				String[] items = item.split(",");
				for(int j = 0 ; j<themes.length;j++){
					msg.put(themes[j], items[j]);
				}
				String a = distance(msg,base);//计算获得相识度
				
				System.out.println(a);
				if(a != "0" ){
					result.put(a, adata.get(i).getId());
					cond.put(a, "material");
				}
				
			}
		}
		if(udata != null){
			for(int i = 0 ;i<udata.size();i++){
				Map<String , Object> msg = new HashMap();
				String theme = ((TMultimedia) udata.get(i)).getTag_theme();
				String item = ((TMultimedia) udata.get(i)).getTag_item();
				String[] themes = theme.split(",");
				String[] items = item.split(",");
				for(int j = 0 ; j<themes.length;j++){
					msg.put(themes[j], items[j]);
				}
				String a = distance(msg,base);//计算获得相识度
				
				System.out.println(a);
				if(a != "0" ){
					result.put(a, udata.get(i).getId());
					cond.put(a, "multimedia");
				}
				
			}
		}
		
		Set set = result.keySet();
		Iterator it = set.iterator();
		List<String> keys = new ArrayList();
		while(it.hasNext()){
			String key = (String)it.next();
			keys.add(key);
		}
		//将结果排序写入List
		for(int i = 0 ;i<keys.size() ;i++){
			for(int j = i+1 ;j<keys.size() ;j++){
				
				if( Double.parseDouble(keys.get(i)) >  Double.parseDouble(keys.get(j))){
					System.out.println("=======大小比较===="+Double.parseDouble(keys.get(i))+">"+Double.parseDouble(keys.get(j)));
					String c = keys.get(i);
					keys.set(i, keys.get(j));
					keys.set(j, c);
				}
			}
		}
		for(int i=0; i<keys.size(); i++){
			Map<String, Object> da = new HashMap();
			da.put("msg", result.get(keys.get(i)).toString());
			da.put("cond", cond.get(keys.get(i)).toString());
			da.put("sim", keys.get(i));
			end.add(da);
		}
		return end;
		
	}
	
	/**
	 * 转换类型
	 * @return
	 */
	public static double tran(Object msg){
		double a ;
		a= Double.parseDouble(msg.toString());
		return a;
		
	}

	/**
	 * 计算距离方法
	 * 越大相识度越不同
	 * 距离算法
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String distance(Map<String , Object> data,Map<String , Object> base){
		
		double a = 0;
		Set set = data.keySet();
		Iterator it = set.iterator();
		while(it.hasNext()){
			String key = (String)it.next();
			System.out.println("==1=="+tran(data.get(key)));
			if(base.get(key) != null){
				a = a + Math.pow((tran(data.get(key))-(tran(base.get(key)))),2);
			}else{
				a = a + Math.pow(tran(data.get(key)),2);
			}
			
		}
		a = Math.sqrt(a);
		java.text.DecimalFormat df=new java.text.DecimalFormat("#.####");
		return df.format(a);
		
		
	}
	

	/**
	 * 计算用户离线兴趣模型方法
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static Map<String , Object> OutlineSimilarityModel(){
		//获取所有用户浏览记录，借阅情况来计算
		System.out.println("=========1=="); 
		DAOTVisited vdao = new DAOTVisited();
		DAOTReserveDetail rdao = new DAOTReserveDetail();
		List<TVisited> tlist = new ArrayList();
		List<TReserveDetail> rlist = new ArrayList();
		
		List<Map<String , Object>> msg = new ArrayList();
		Map<String , Object> average = new HashMap();//记录每个主题项求平均次数
		Map<String , Object> result = new HashMap();//最终结果
		
		tlist = vdao.find(null, null);
		rlist = rdao.getall();
		if(tlist.size() != 0){
			for(int i = 0 ;i < tlist.size();i++){
				if(tlist.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					TFiles li = new TFiles();
					li = d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(tlist.get(i).getArchive_type().equals("multimedia")){
					TMultimedia li = new TMultimedia();
					DAOTMultimedia d = new DAOTMultimedia();
					li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(tlist.get(i).getArchive_type().equals("material")){
					TMaterial li = new TMaterial();
					DAOTMaterial d = new DAOTMaterial();
					li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
				}
				
			}
		}
		if(rlist.size() != 0){
			for(int i = 0 ;i < rlist.size();i++){
				if(rlist.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					TFiles li = new TFiles();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(rlist.get(i).getArchive_type().equals("multimedia")){
					TMultimedia li = new TMultimedia();
					DAOTMultimedia d = new DAOTMultimedia();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(rlist.get(i).getArchive_type().equals("material")){
					TMaterial li = new TMaterial();
					DAOTMaterial d = new DAOTMaterial();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
				}
				
			}
		}
		
		System.out.println("=========2=="+msg.size()); 
		//计算每个主题项需要求平均数的个数
		if(msg.size() != 0){
			for(int i = 0;i<msg.size();i++){
				Map<String , Object> data = new HashMap();
				data = msg.get(i);
				Set set = data.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()){
					String key = (String)it.next();
					if(average.get(key) != null){
						double a = Double.parseDouble(average.get(key).toString()) + 1;
						average.put(key, a);
					}else{
						average.put(key, "1");
					}
				}
			}
			System.out.println("=========3=="+average.keySet()); 
			//求离线状态下的最终兴趣度
			for(int i = 0;i<msg.size();i++){
				Map<String , Object> data = new HashMap();
				data = msg.get(i);
				Set set = data.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()){
					String key = (String)it.next();
					if(result.get(key) != null){
						double a = Double.parseDouble(result.get(key).toString()) + Double.parseDouble(data.get(key).toString());
						result.put(key, a);
					}else{
						result.put(key, Double.parseDouble(data.get(key).toString()));
					}
				}
			}
			Set set = result.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				double a = Double.parseDouble(result.get(key).toString())/Double.parseDouble(average.get(key).toString());
				java.text.DecimalFormat df=new java.text.DecimalFormat("#.####");
				System.out.println("=========结果=="+key+a); 
				if(!Double.isNaN(a)){
					System.out.println("=========1111=="+a); 
					 result.put(key, df.format(a));
					
				}
				
			}
			
			System.out.println("=========4=="); 
		}
		
		
		return result;
		
	}
	

	/**
	 * 计算用户在线兴趣模型方法
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map<String , Object> OnlineSimilarityModel(String uid){
		System.out.println("=========1=="); 
		DAOTInterest idao = new DAOTInterest();
		TInterest ilist = new TInterest();
		TInterest inter = new TInterest();
		inter.settUsers_id(uid);
		ilist = idao.find(inter, null);
		
		Map<String , Object> result = new HashMap();//最终结果
		if(ilist != null){//兴趣列表里有值，取表里的值
			String theme = ilist.getInterest_theme();
			String item = ilist.getInterest_item();
			String[] themes = theme.split(",");
			String[] items = item.split(",");
			for(int i = 0 ;i < themes.length; i++){
				System.out.println("=======结果=="+themes[i]+ items[i]); 
				result.put(themes[i], items[i]);
			}
			
		}else{//兴趣列表里没有值，做查询插入
			DAOTVisited vdao = new DAOTVisited();
			DAOTReserveDetail rdao = new DAOTReserveDetail();
			List<TVisited> tlist = new ArrayList();
			List<TReserveDetail> rlist = new ArrayList();
			
			List<Map<String , Object>> msg = new ArrayList();//该用户的浏览记录
			Map<String , Object> average = new HashMap();//记录每个主题项求平均次数
			
			TVisited tv = new TVisited();
			TUsers u = new TUsers();
			u.setId(Long.parseLong(uid));
			tv.settUser_id(u);
			tlist = vdao.find(tv, null);
			rlist = rdao.getbyUId(Long.parseLong(uid));
			if(tlist.size() != 0){
				for(int i = 0 ;i < tlist.size();i++){
					if(tlist.get(i).getArchive_type().equals("file")){
						DAOTFile d = new DAOTFile();
						TFiles li = new TFiles();
						li = d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
						
					}else if(tlist.get(i).getArchive_type().equals("multimedia")){
						TMultimedia li = new TMultimedia();
						DAOTMultimedia d = new DAOTMultimedia();
						li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
						
					}else if(tlist.get(i).getArchive_type().equals("material")){
						TMaterial li = new TMaterial();
						DAOTMaterial d = new DAOTMaterial();
						li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
					}
					
				}
			}
			if(rlist.size() != 0){
				for(int i = 0 ;i < rlist.size();i++){
					if(rlist.get(i).getArchive_type().equals("file")){
						DAOTFile d = new DAOTFile();
						TFiles li = new TFiles();
						li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
						
					}else if(rlist.get(i).getArchive_type().equals("multimedia")){
						TMultimedia li = new TMultimedia();
						DAOTMultimedia d = new DAOTMultimedia();
						li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
						
					}else if(rlist.get(i).getArchive_type().equals("material")){
						TMaterial li = new TMaterial();
						DAOTMaterial d = new DAOTMaterial();
						li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
						
						Map<String , Object> data = new HashMap();
						String theme = li.getTag_theme();
						String item = li.getTag_item();
						String[] themes = theme.split(",");
						String[] items = item.split(",");
						for(int j = 0 ; j<themes.length;j++){
							data.put(themes[j], items[j]);
						}
						msg.add(data);
					}
					
				}
			}
			
			System.out.println("=========2=="+msg.size()); 
			//计算每个主题项需要求平均数的个数
			for(int i = 0;i<msg.size();i++){
				Map<String , Object> data = new HashMap();
				data = msg.get(i);
				Set set = data.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()){
					String key = (String)it.next();
					if(average.get(key) != null){
						double a = Double.parseDouble(average.get(key).toString()) + 1;
						average.put(key, a);
					}else{
						average.put(key, "1");
					}
				}
				System.out.println("=======结果3=="+msg.get(i)+average.get("旅游"));
			}
			System.out.println("=========3=="+average.keySet()); 
			//求在线状态下的最终兴趣度
			for(int i = 0;i<msg.size();i++){
				Map<String , Object> data = new HashMap();
				data = msg.get(i);
				Set set = data.keySet();
				Iterator it = set.iterator();
				while(it.hasNext()){
					String key = (String)it.next();
					if(result.get(key) != null){
						System.out.println("======================"+i+"=========="+data.get(key).toString()+"=========="+key);
						double a = Double.parseDouble(result.get(key).toString()) + Double.parseDouble(data.get(key).toString());
						result.put(key, a);
					}else{
						result.put(key, Double.parseDouble(data.get(key).toString()));
					}
				}
				System.out.println("=======结果3=="+result.get("旅游"));
			}
			Set set = result.keySet();
			Iterator it = set.iterator();
			String themedata = "";
			String itemdata = "";
			int i = 0;
			while(it.hasNext()){
				String key = (String)it.next();
				double a = Double.parseDouble(result.get(key).toString())/Double.parseDouble(average.get(key).toString());
				java.text.DecimalFormat df=new java.text.DecimalFormat("#.####");
				if(i==0){
					themedata = themedata + key;
					itemdata = itemdata + df.format(a);
					i++;
				}else{
					themedata = themedata +","+key;
					itemdata = itemdata +","+df.format(a);
				}
				
				System.out.println("=========结果=="+key+df.format(a)); 
				result.put(key, df.format(a));
			}
			TInterest interest = new TInterest();
			interest.settUsers_id(uid);
			TransformDate date = new TransformDate();
			interest.setUpdate_time(date.getCurrentTime());
			
			System.out.println("=========结果=="+themedata+itemdata); 
			interest.setInterest_item(itemdata);
			interest.setInterest_theme(themedata);
			if(themedata != ""){
				idao.save(interest);
			}
			
			
			System.out.println("=========4=="+itemdata+themedata); 
		}
		
		return result;
	}
	
	/**
	 * 更新用户兴趣模型
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static void updateinter(String uid){
		Map<String , Object> result = new HashMap();//最终结果
		DAOTInterest idao = new DAOTInterest();
		
		DAOTVisited vdao = new DAOTVisited();
		DAOTReserveDetail rdao = new DAOTReserveDetail();
		List<TVisited> tlist = new ArrayList();
		List<TReserveDetail> rlist = new ArrayList();
		
		List<Map<String , Object>> msg = new ArrayList();//该用户的浏览记录
		Map<String , Object> average = new HashMap();//记录每个主题项求平均次数
		
		TVisited tv = new TVisited();
		TUsers u = new TUsers();
		u.setId(Long.parseLong(uid));
		tv.settUser_id(u);
		tlist = vdao.find(tv, null);
		rlist = rdao.getbyUId(Long.parseLong(uid));
		if(tlist != null){
			for(int i = 0 ;i < tlist.size();i++){
				if(tlist.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					TFiles li = new TFiles();
					li = d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(tlist.get(i).getArchive_type().equals("multimedia")){
					TMultimedia li = new TMultimedia();
					DAOTMultimedia d = new DAOTMultimedia();
					li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(tlist.get(i).getArchive_type().equals("material")){
					TMaterial li = new TMaterial();
					DAOTMaterial d = new DAOTMaterial();
					li=d.getbyid(Long.parseLong(tlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
				}
				
			}
		}
		if(rlist != null){
			for(int i = 0 ;i < rlist.size();i++){
				if(rlist.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					TFiles li = new TFiles();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(rlist.get(i).getArchive_type().equals("multimedia")){
					TMultimedia li = new TMultimedia();
					DAOTMultimedia d = new DAOTMultimedia();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
					
				}else if(rlist.get(i).getArchive_type().equals("material")){
					TMaterial li = new TMaterial();
					DAOTMaterial d = new DAOTMaterial();
					li=d.getbyid(Long.parseLong(rlist.get(i).getArchive_id()));
					
					Map<String , Object> data = new HashMap();
					String theme = li.getTag_theme();
					String item = li.getTag_item();
					String[] themes = theme.split(",");
					String[] items = item.split(",");
					for(int j = 0 ; j<themes.length;j++){
						data.put(themes[j], items[j]);
					}
					msg.add(data);
				}
				
			}
		}
		
		System.out.println("=========2=="+msg.size()); 
		//计算每个主题项需要求平均数的个数
		for(int i = 0;i<msg.size();i++){
			Map<String , Object> data = new HashMap();
			data = msg.get(i);
			Set set = data.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				if(average.get(key) != null){
					double a = Double.parseDouble(average.get(key).toString()) + 1;
					average.put(key, a);
				}else{
					average.put(key, "1");
				}
			}
		}
		System.out.println("=========3=="+average.keySet()); 
		//求在线状态下的最终兴趣度
		for(int i = 0;i<msg.size();i++){
			Map<String , Object> data = new HashMap();
			data = msg.get(i);
			Set set = data.keySet();
			Iterator it = set.iterator();
			while(it.hasNext()){
				String key = (String)it.next();
				if(result.get(key) != null){
					System.out.println("======================"+i+"=========="+data.get(key).toString()+"=========="+key);
					double a = Double.parseDouble(result.get(key).toString()) + Double.parseDouble(data.get(key).toString());
					result.put(key, a);
				}else{
					result.put(key,  Double.parseDouble(data.get(key).toString()));
				}
			}
		}
		Set set = result.keySet();
		Iterator it = set.iterator();
		String themedata = "";
		String itemdata = "";
		int i = 0;
		while(it.hasNext()){
			String key = (String)it.next();
			double a = Double.parseDouble(result.get(key).toString())/Double.parseDouble(average.get(key).toString());
			java.text.DecimalFormat df=new java.text.DecimalFormat("#.####");
			if(i==0){
				themedata = themedata + key;
				itemdata = itemdata + df.format(a);
				i++;
			}else{
				themedata = themedata +","+key;
				itemdata = itemdata +","+df.format(a);
			}
			
			System.out.println("=========结果=="+key+df.format(a)); 
			result.put(key, df.format(a));
		}
		TInterest interest = new TInterest();
		TInterest inter = new TInterest();
		inter.settUsers_id(uid);
		interest = idao.find(inter, null);
		
		if(interest != null){
			interest.settUsers_id(uid);
			TransformDate date = new TransformDate();
			interest.setUpdate_time(date.getCurrentTime());
			
			System.out.println("=========结果=="+themedata+itemdata); 
			interest.setInterest_item(itemdata);
			interest.setInterest_theme(themedata);
			
			idao.update(interest);
		}
		
		
	}

}
