package com.archive.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTRecommend;
import com.archive.dao.DAOTUsers;
import com.archive.dao.DAOTVisited;
import com.archive.jpa.TCatalog;
import com.archive.jpa.TFiles;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TRecommend;
import com.archive.jpa.TUsers;
import com.archive.jpa.TVisited;
import com.archive.utility.GetIpAddr;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Pager;
import com.archive.utility.SimilarityBase;
import com.archive.utility.TransformDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/IndexServlet")
public class IndexServlet extends HttpServlet {
	private static String updatetime = "";	
	
	private static final long serialVersionUID = 1L;	
	private static Logger logger = Logger.getLogger(IndexServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IndexServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		//获取前端传过来的参数－请求
		String action = request.getParameter("action");
		System.out.println("=========请求IndexServlet========执行=="+action);
		if("getrecommend".equals(action)){
			getrecommend(request,response);
		}else if("getnews".equals(action)){
			getnews(request,response);
		}else if("historyday".equals(action)){
			historyday(request,response);
		}else if("getmax".equals(action)){
			getmax(request,response);
		}else{
			logger.log(Level.INFO, "action type parameter error");
			Exception e = new Exception("不支持操作  "  + action);
			HibernateUtility.exceptionOutput(e, request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}
	
	
	@SuppressWarnings({ "static-access", "rawtypes", "unchecked" })
	public void getrecommend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//读取前端传来的参数－对象
		request.setCharacterEncoding("utf-8");
		TransformDate time = new TransformDate();
		String username =  (String) request.getAttribute("username");
		List<Map<String, Object>> li = new ArrayList();
		TRecommend tr = new TRecommend();
		DAOTRecommend dao = new DAOTRecommend();
		DAOTUsers udao = new DAOTUsers();
		String responseResult = null;
		List<TRecommend> data = new ArrayList();
		JsonObject jobj = new JsonObject();	
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonArray ja = new JsonArray();

		
		try{
			if(!updatetime.equals(time.getDateString())){//第一次访问,把数据放入到数据表
				System.out.println("============第一次============="+updatetime);
				dao.delete();
				List<TUsers> us = udao.getall();
				for(TUsers u:us){//在线
					String[] a = {"Online" ,Long.toString(u.getId()) };
					SimilarityBase s = new SimilarityBase();
					li = s.main(a);
					
					for(int i = 0;i<20;i++){
						
						if(li.size() > i){
							Map<String,Object> d = li.get(i);
							tr.setArchive_id((String) d.get("msg"));
							tr.setArchive_type((String) d.get("cond"));
							tr.settUser_id(u);
							tr.setUpdate_time(time.getCurrentTime());
							tr.setSimilarity((String) d.get("sim"));
							dao.save(tr);
							
						}
						
					}
					
				}
				//离线
				String[] b = {"Outline" };
				SimilarityBase s = new SimilarityBase();
				li = s.main(b);
				for(int i = 0;i<20;i++){
					System.out.println("===============a"+li.toString());
					
					if(li.size() > i){
						Map<String,Object> d = li.get(i);
						tr = new TRecommend();
						tr.setArchive_id((String) d.get("msg"));
						tr.setArchive_type((String) d.get("cond"));
						tr.setUpdate_time(time.getCurrentTime());
						tr.setSimilarity((String) d.get("sim"));
						
						dao.save(tr);
						
					}
					
				}
				
				updatetime = time.getDateString();
			}
			if(username != null && username != ""){//已经登录
				TRecommend msg = new TRecommend();
				msg.settUser_id(udao.getUserByUsername(username));
				data = dao.getall(tr);
				ja = (JsonArray) gson.toJsonTree(data);
			}else{
				data = dao.getnull();
				List<Map<String ,Object>> ds = new ArrayList();
				for(TRecommend d:data){
					Map<String ,Object> m = new HashMap();
					m.put("archive_id", d.getArchive_id());
					m.put("archive_type", d.getArchive_type());
					m.put("similarity", d.getSimilarity());
					ds.add(m);
					
				}
				ja = (JsonArray) gson.toJsonTree(ds);
			}
			
			List msg = new ArrayList();
			for(TRecommend da:data){
				if(da.getArchive_type().equals("file")){
					DAOTFile daof = new DAOTFile();
					msg.add(daof.getbyid(Long.parseLong(da.getArchive_id())));
				}else if(da.getArchive_type().equals("multimedia")){
					DAOTMultimedia daou = new DAOTMultimedia();
					msg.add(daou.getbyid(Long.parseLong(da.getArchive_id())));
				}else if(da.getArchive_type().equals("material")){
					DAOTMaterial daoa = new DAOTMaterial();
					msg.add(daoa.getbyid(Long.parseLong(da.getArchive_id())));
				}
			}
			
			
			jobj.addProperty("success", true);
			jobj.add("result", ja);
			JsonArray ja2 = (JsonArray) gson.toJsonTree(msg);
			jobj.add("msg", ja2);
			responseResult = gson.toJson(jobj);
			
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();	
		} catch (IOException e) {
			HibernateUtility.exceptionOutput(e,request,response);			
			e.printStackTrace();
		}
		
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getnews(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		
		Pager pager = new Pager();
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List li = new ArrayList();
		List li2 = new ArrayList();
		String responseResult = null;
		
		try {
			List<TFiles> lif = new ArrayList();
			DAOTFile daof = new DAOTFile();
			lif = daof.find(null,null,null,pager);
			
			for(int i = 0;i<lif.size();i++){
				li2.add("file");
				li.add(lif.get(i));
			}

			List<TMultimedia> liu = new ArrayList();
			DAOTMultimedia daou = new DAOTMultimedia();
			liu = (List<TMultimedia>)daou.find(null,null,null,pager);
			for(int i = 0;i<liu.size();i++){
				li2.add("multimedia");
				li.add(liu.get(i));
			}
		
			List<TMaterial> lia = new ArrayList();
			DAOTMaterial daoa = new DAOTMaterial();
			lia = (List<TMaterial>)daoa.find(null,null,null,pager);
			for(int i = 0;i<lia.size();i++){
				li2.add("material");
				li.add(lia.get(i));
			}
			
			JsonArray ja = (JsonArray) gson.toJsonTree(li);
			jobj.add("msg", ja);
			JsonArray ja2 = (JsonArray) gson.toJsonTree(li2);
			jobj.add("cond", ja2);
			jobj.addProperty("success", true);
			responseResult = gson.toJson(jobj);
		
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		} catch (IOException e) {
			HibernateUtility.exceptionOutput(e,request,response);			
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void historyday(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		JsonObject jobj = new JsonObject();
		String responseResult = null;
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		// 定义即将访问的链接
		String url = "http://www.ipip5.com/today/api.php?type=json";
		// 定义一个字符串用来存储网页内容 
		String result = ""; 
		// 定义一个缓冲字符输入流
		BufferedReader in = null;
		
		try {
			// 将string转成url对象
			URL realUrl = new URL(url);   
			// 初始化一个链接到那个url的连接
			URLConnection connection = realUrl.openConnection();
			
			// 开始实际的连接 
			connection.connect();
			// 初始化 BufferedReader输入流来读取URL的响应  
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			// 用来临时存储抓取到的每一行的数据
			String line; 
			
			while ((line = in.readLine()) != null) { 
				//遍历抓取到的每一行并将其存储到result里面   
				result += line;
			}
			in.close();
			JSONObject a = null;
			
			try {
				a = new JSONObject(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			jobj.addProperty("msg", a.toString());
			jobj.addProperty("success", true);
			responseResult = gson.toJson(jobj);
		
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();	
		} catch (IOException e) {
			HibernateUtility.exceptionOutput(e,request,response);			
			e.printStackTrace();
		}
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getmax(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		request.setCharacterEncoding("utf-8");
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<Map<String,Object>> li = new ArrayList();
		
		String responseResult = null;
		
		try {
			DAOTVisited daov = new DAOTVisited();
			TVisited v = new TVisited();
			
			List<TFiles> lif = new ArrayList();
			DAOTFile daof = new DAOTFile();
			lif = daof.find(null,null,null,null);
			
			for(int i = 0;i<lif.size();i++){
				Map<String,Object> msg = new HashMap();
				msg.put("cond", "file");
				msg.put("msg", lif.get(i));
				v.setArchive_id(Long.toString(lif.get(i).getId()));
				v.setArchive_type("file");
				msg.put("count", daov.count(v));
				
				li.add(msg);
			}

			List<TMultimedia> liu = new ArrayList();
			DAOTMultimedia daou = new DAOTMultimedia();
			liu = (List<TMultimedia>)daou.find(null,null,null,null);
			for(int i = 0;i<liu.size();i++){
				Map<String,Object> msg = new HashMap();
				msg.put("cond", "multimedia");
				msg.put("msg", liu.get(i));
				
				v.setArchive_id(Long.toString(liu.get(i).getId()));
				v.setArchive_type("multimedia");
				msg.put("count", daov.count(v));
				li.add(msg);
			}
		
			List<TMaterial> lia = new ArrayList();
			DAOTMaterial daoa = new DAOTMaterial();
			lia = (List<TMaterial>)daoa.find(null,null,null,null);
			for(int i = 0;i<lia.size();i++){
				Map<String,Object> msg = new HashMap();
				msg.put("cond", "material");
				msg.put("msg", lia.get(i));
				
				v.setArchive_id(Long.toString(lia.get(i).getId()));
				v.setArchive_type("material");
				msg.put("count", daov.count(v));
				li.add(msg);
			}
			JsonArray ja = (JsonArray) gson.toJsonTree(li);
			System.out.println(ja);
			jobj.add("result", ja);
			jobj.addProperty("success", true);
			responseResult = gson.toJson(jobj);
		
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();	
		} catch (IOException e) {
			HibernateUtility.exceptionOutput(e,request,response);			
			e.printStackTrace();
		}
	}
	
	
	
}


