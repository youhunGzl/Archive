package com.archive.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.archive.dao.DAOTCatalog;
import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTUsers;
import com.archive.dao.DAOTVisited;
import com.archive.jpa.TCatalog;
import com.archive.jpa.TFiles;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TUsers;
import com.archive.jpa.TVisited;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Pager;
import com.archive.utility.TransformDate;
import com.archive.utility.tCatalogUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.net.URLDecoder;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet("/SearchServlet")
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(SearchServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//设置request编码格式，可以避免post请求的中文乱码，但get不行
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println("=========请求SearchServlet========执行=="+action);
		if(action.equals("find")){
			find(request, response);
		}else if(action.equals("getdata")){
			getdata(request, response);
		}else if(action.equals("getcatalog")){
			getcatalog(request, response);
		}else{
			logger.log(Level.INFO, "action type parameter error");
			Exception e = new Exception("不支持操作  " + action);
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
	@SuppressWarnings({"rawtypes", "unchecked" })
	public void find(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String cond = URLDecoder.decode(request.getParameter("params"),"utf-8");
		String data = URLDecoder.decode(request.getParameter("term"), "utf-8");
		String catalog = URLDecoder.decode(request.getParameter("catalog"), "utf-8");
		String firstcreate_time = request.getParameter("firstcreate_time");
		String endcreate_time = request.getParameter("endcreate_time");
		String currentPage = request.getParameter("currentPage");
		TransformDate tran = new TransformDate();
		
		Pager pager = new Pager();
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List<?> li = null;
		if(catalog != null){
			System.out.println("==查询条件=="+data+"===目录==="+catalog+"===时间==="+firstcreate_time+"===数据表==="+cond);	
		}
		try{
			if(cond.equals("file")){
				TFiles list = gson.fromJson(data, TFiles.class);
				
				TCatalog tcatalog = new TCatalog();
				if(catalog != null && catalog != ""){
					tcatalog.setId(Long.parseLong(catalog));
					list.settCatalog_id(tcatalog);
				}
				
				DAOTFile dao = new DAOTFile();
				Integer totalResult = dao.gettotal(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time));
				pager.setTotalResults(totalResult);
				if(currentPage!=null){
					pager.setCurrentPage(Integer.parseInt(currentPage));
				}
				
				li = (List<TFiles>)dao.find(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time),pager);
			}else if(cond.equals("multimedia")){
				TMultimedia list = gson.fromJson(data, TMultimedia.class);
				
				TCatalog tcatalog = new TCatalog();
				if(catalog != null && catalog != ""){
					tcatalog.setId(Long.parseLong(catalog));
					list.settCatalog_id(tcatalog);
				}
				
				DAOTMultimedia dao = new DAOTMultimedia();
				Integer totalResult = dao.gettotal(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time));
				pager.setTotalResults(totalResult);
				if(currentPage!=null){
					pager.setCurrentPage(Integer.parseInt(currentPage));
				}
				li = (List<TMultimedia>)dao.find(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time),pager);
			}else if(cond.equals("material")){
				TMaterial list = gson.fromJson(data, TMaterial.class);
				
				TCatalog tcatalog = new TCatalog();
				if(catalog != null && catalog != ""){
					tcatalog.setId(Long.parseLong(catalog));
					list.settCatalog_id(tcatalog);
				}
				
				DAOTMaterial dao = new DAOTMaterial();
				Integer totalResult = dao.gettotal(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time));
				pager.setTotalResults(totalResult);
				if(currentPage!=null){
					pager.setCurrentPage(Integer.parseInt(currentPage));
				}
				li = (List<TMaterial>)dao.find(list,tran.trancurrentTime(firstcreate_time),tran.trancurrentTime(endcreate_time),pager);
			}
			if(cond == null || cond == ""){
				/*
				List<Object> k = new ArrayList();
				TFiles flist = gson.fromJson(data, TFiles.class);
				DAOTFile daof = new DAOTFile();
				li = (List<TFiles>)daof.find(flist);
				System.out.println("==结果1=="+li+"===k===");
				if(li != null){
					k.addAll(li);
					li = null;
				}
				
				TMultimedia ulist = gson.fromJson(data, TMultimedia.class);
				DAOTMultimedia daou = new DAOTMultimedia();
				li = (List<TMultimedia>)daou.find(ulist);
				System.out.println(ulist.getAuthor()+"==结果2=="+li+"===k===");
				if(li!=null){
					k.addAll(li);
					li = null;
				}
				
				TMaterial alist = gson.fromJson(data, TMaterial.class);
				DAOTMaterial daoa = new DAOTMaterial();
				li = (List<TMaterial>)daoa.find(alist);
				System.out.println(alist.getAuthor()+"==结果3=="+li+"===k===");
				if(li!=null){
					k.addAll(li);
					li = null;
				}
				System.out.println("==结果=="+k+"======");
				if(k!=null&&k.size()>0){
					jobj.addProperty("success", true);
					JsonArray ja = (JsonArray) gson.toJsonTree(k);
					jobj.add("result", ja);
				}else{
					jobj.addProperty("success", false);
					jobj.addProperty("message", "数据不存在");
				}*/
			}else{
				if(li!=null&&li.size()>0){
					jobj.addProperty("success", true);
					jobj.addProperty("cond", cond);
					JsonArray ja = (JsonArray) gson.toJsonTree(li);
					List a = new ArrayList();
					a.add(pager);
					JsonArray page = (JsonArray) gson.toJsonTree(a);
					jobj.add("result", ja);
					jobj.add("page", page);
				}else{
					jobj.addProperty("success", false);
					jobj.addProperty("message", "数据不存在");
				}
			}
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);	
			pw.print(responseResult);
			pw.close();
			
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void getdata(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("params");
		String cond = request.getParameter("cond");
		
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		List a = new ArrayList();
		
		try{
			if(cond.equals("file")){
				DAOTFile dao = new DAOTFile();
				TFiles data = dao.getbyid(Long.parseLong(id));
				a.add(data);
			}else if(cond.equals("multimedia")){
				DAOTMultimedia dao = new DAOTMultimedia();
				TMultimedia data = dao.getbyid(Long.parseLong(id));
				a.add(data);
			}else if(cond.equals("material")){
				DAOTMaterial dao = new DAOTMaterial();
				TMaterial data = dao.getbyid(Long.parseLong(id));
				a.add(data);
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据表不存在");
			}
			
			if(a!=null&&a.size()>0){
				//保存浏览历史
				savevisit(request, response);
				jobj.addProperty("success", true);
				jobj.addProperty("cond", cond);
				JsonArray ja = (JsonArray) gson.toJsonTree(a);
				jobj.add("result", ja);
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据不存在");
			}
		
		response.setContentType("text/html;charset=utf-8");
		String responseResult = null;
		PrintWriter pw = response.getWriter();
		responseResult = gson.toJson(jobj);	
		pw.print(responseResult);
		pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
		
	}
	
	public void savevisit(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws IOException, ServletException {
        HttpSession session = servletRequest.getSession();
        String id = servletRequest.getParameter("params");
		String cond = servletRequest.getParameter("cond");

        TVisited t = new TVisited();
        DAOTVisited dao = new DAOTVisited();
        
        // 从session里取用戶名信息
        String empId = (String) session.getAttribute("username");
        DAOTUsers udao = new DAOTUsers();
		TUsers u = udao.getUserByUsername(empId);
		t.settUser_id(u);
		
		TransformDate date = new TransformDate();
		t.setTime(date.getCurrentTime());
		
		t.setArchive_id(id);
		t.setArchive_type(cond);

		
		dao.save(t);
		
		return;
        
    }
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getcatalog(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        List<TCatalog> t = new ArrayList();
        DAOTCatalog dao = new DAOTCatalog();
        List<tCatalogUtil> counts = new ArrayList();
        
        JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        
        t = dao.find(null);
        try{
	        for(TCatalog t1:t){
	        	if(t1.getParent_id() != null){
	        		tCatalogUtil count = new tCatalogUtil();
	        		count.setCatalog_no(t1.getCatalog_no());
	        		count.setCount(dao.count(t1,"all"));
	        		count.setFile(dao.count(t1,"file"));
	        		count.setMaterial(dao.count(t1,"material"));
	        		count.setMultimedia(dao.count(t1,"multimedia"));
	        		counts.add(count);
	        	}
	        }
			jobj.addProperty("success", true);
			JsonArray ja = (JsonArray) gson.toJsonTree(t);
			jobj.add("result", ja);
			JsonArray ja1 = (JsonArray) gson.toJsonTree(counts);
			jobj.add("count", ja1);
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);	
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
		
		return;
        
    }
}
