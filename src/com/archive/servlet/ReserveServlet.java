package com.archive.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.Cookies;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTRecord;
import com.archive.dao.DAOTReserve;
import com.archive.dao.DAOTReserveDetail;
import com.archive.dao.DAOTUsers;
import com.archive.jpa.TFiles;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TRecord;
import com.archive.jpa.TReserve;
import com.archive.jpa.TReserveDetail;
import com.archive.jpa.TUsers;
import com.archive.utility.GetIpAddr;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Pager;
import com.archive.utility.TransformDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@SuppressWarnings("unused")
@WebServlet("/ReserveServlet")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(ReserveServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReserveServlet() {
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
		System.out.println("=========请求ReserveServlet========执行=="+action);
		if(action.equals("getnum")){
			getnum(request, response);
		}else if(action.equals("addData")){
			addData(request, response);
		}else if(action.equals("addcard")){
			addcard(request, response);
		}else if(action.equals("getrd")){
			getrd(request, response);
		}else if(action.equals("listrd")){
			listrd(request, response);
		}else if(action.equals("savereserv")){
			savereserv(request, response);
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

	public void getUser(HttpServletRequest request, HttpServletResponse response){
		TUsers user = new TUsers();
		DAOTUsers daoTUsers = new DAOTUsers();
		
		try{
			String username = (String) request.getSession().getAttribute("username");
			user = daoTUsers.getUserByUsername(username);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jobj = new JsonObject();
			if(user!=null){
				jobj.addProperty("success", true);
				String ja = gson.toJson(user);
				jobj.addProperty("result", ja);
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
	
	public void addData(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		String id = request.getParameter("params");
		String cond = request.getParameter("cond");
		String username = (String) request.getSession().getAttribute("username");
		
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		TReserveDetail data = new TReserveDetail();
		
		try{
			if(username != null){
				DAOTUsers udao = new DAOTUsers();
				TUsers u = udao.getUserByUsername(username);
				
				DAOTReserveDetail dao = new DAOTReserveDetail();
				data.setArchive_type(cond);
				data.setArchive_id(id);
				data.settUsers_id(u);
				boolean a = dao.save(data);
				
				if(a){
					jobj.addProperty("success", true);
					jobj.addProperty("message", "添加成功");
				}else{
					jobj.addProperty("success", true);
					jobj.addProperty("message", "已存在");
				}
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("message", "请先登录");
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
	
	public void getnum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		String username = (String) request.getSession().getAttribute("username");
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		try{
			if(username != null){
				TReserveDetail data = new TReserveDetail();
				DAOTUsers udao = new DAOTUsers();
				TUsers u = udao.getUserByUsername(username);
				
				DAOTReserveDetail dao = new DAOTReserveDetail();
				data.settUsers_id(u);
				List<TReserveDetail> list = dao.getbyUId(u.getId());
				System.out.println("=============获得getnum===="+list.size());
				if(list != null){
					jobj.addProperty("success", true);
					jobj.addProperty("message", list.size());
				}else{
					jobj.addProperty("success", true);
					jobj.addProperty("message", "0");
				}
			}else{
				jobj.addProperty("success", true);
				jobj.addProperty("message", "请登录");
			}
			
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = gson.toJson(jobj);
			PrintWriter pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getrd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		String username = (String) request.getSession().getAttribute("username");
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		
		TReserveDetail data = new TReserveDetail();
		DAOTUsers udao = new DAOTUsers();
		TUsers u = udao.getUserByUsername(username);
		try{
			DAOTReserveDetail dao = new DAOTReserveDetail();
			data.settUsers_id(u);
			List<TReserveDetail> list = dao.getbyUId(u.getId());
			System.out.println("=============获得getrd===="+list.size());
			List<Object> li = new ArrayList();
			for(int i = 0 ;i < list.size();i++){
				if(list.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
					
				}else if(list.get(i).getArchive_type().equals("multimedia")){
					DAOTMultimedia d = new DAOTMultimedia();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
					
				}else if(list.get(i).getArchive_type().equals("material")){
					DAOTMaterial d = new DAOTMaterial();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
				}
				
			}
			if(list != null){
				JsonObject ja1 = new JsonObject();
				for(int i = 0; i<list.size();i++){
					ja1.addProperty(Integer.toString(i), Long.toString(list.get(i).getId()));
				}
				jobj.add("rdresult", ja1);
				JsonArray ja2 = (JsonArray) gson.toJsonTree(li);
				jobj.add("result", ja2);
				jobj.addProperty("success", true);
			}else{
				jobj.addProperty("success", true);
				jobj.addProperty("message", "0");
			}
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = gson.toJson(jobj);
			PrintWriter pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
	
	public void addcard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		String ids = request.getParameter("ids");
		
		//String[] id = ids.split(",");
		
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		try{
			if(ids.length() > 0){
			request.getSession().setAttribute("ids", ids);
			jobj.addProperty("success", true);
			jobj.addProperty("message", "");
			
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("message", "未选择");
			}
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = gson.toJson(jobj);
			PrintWriter pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void listrd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		
		TReserveDetail data = new TReserveDetail();
		try{
			DAOTReserveDetail dao = new DAOTReserveDetail();
			List<TReserveDetail> list = new ArrayList();
			
			String ids = (String) request.getSession().getAttribute("ids");
			String[] id = ids.split(",");
			
			for(int i = 0 ; i<id.length;i++){
				TReserveDetail t = dao.getbyRId(Long.parseLong(id[i]));
				list.add(t);
			}
			
			List<Object> li = new ArrayList();
			for(int i = 0 ;i < list.size();i++){
				if(list.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
					
				}else if(list.get(i).getArchive_type().equals("multimedia")){
					DAOTMultimedia d = new DAOTMultimedia();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
					
				}else if(list.get(i).getArchive_type().equals("material")){
					DAOTMaterial d = new DAOTMaterial();
					li.add(d.getbyid(Long.parseLong(list.get(i).getArchive_id())));
				}
				
			}
			if(list != null){
				JsonObject ja1 = new JsonObject();
				for(int i = 0; i<list.size();i++){
					ja1.addProperty(Integer.toString(i), Long.toString(list.get(i).getId()));
				}
				jobj.add("rdresult", ja1);
				JsonArray ja2 = (JsonArray) gson.toJsonTree(li);
				jobj.add("result", ja2);
				jobj.addProperty("success", true);
			}else{
				jobj.addProperty("success", true);
				jobj.addProperty("message", "0");
			}
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = gson.toJson(jobj);
			PrintWriter pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void savereserv(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException  {
		request.setCharacterEncoding("utf-8");
		
		String reserve_time = request.getParameter("reserve_time");
		String borrow_objects = URLDecoder.decode(request.getParameter("borrow_objects"), "utf-8");
		String ids = request.getParameter("ids");
		
		String[] id = ids.split(",");
		
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		
		String username = (String) request.getSession().getAttribute("username");
		
		DAOTUsers udao = new DAOTUsers();
		TUsers u = udao.getUserByUsername(username);
		TReserve tr = new TReserve();
		
		
		DAOTReserveDetail dao = new DAOTReserveDetail();
		DAOTReserve daor = new DAOTReserve();
		try{
			tr.setBorrow_objects(borrow_objects);
			TransformDate date = new TransformDate();
			tr.setReserve_time(date.trancurrentTime(reserve_time));
			tr.setAudiu_status("0");//0 ==未审核
			tr.settUsers_id(u);
			
			tr = daor.save(tr);
			tr.setReserve_no(getReserveid(tr.getId()));
			daor.update(tr);
			
			List<TReserveDetail> list = new ArrayList();
			
			for(int i = 0 ; i<id.length;i++){
				TReserveDetail t = dao.getbyRId(Long.parseLong(id[i]));
				t.settTReserve_id(tr);
				list.add(t);
			}
			dao.update(list);
			
			if(ids.length() > 0){
			jobj.addProperty("success", true);
			jobj.addProperty("message", "");
			
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("message", "未选择");
			}
			
			response.setContentType("text/html;charset=utf-8");
			String responseResult = gson.toJson(jobj);
			PrintWriter pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
		
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}
	
	public String getReserveid(long l){
		
		String no = "000";
		if(l>1000){
			int i = (int) (l%1000);
			no = i+"";
		}else if(l<1000){
			no = String.format("%03d", l);
		}
		TransformDate time = new TransformDate("yyyyMMddHHmmss");
		String reserve = time.getDateString()+no;
		return reserve;
		
	}
	

}
