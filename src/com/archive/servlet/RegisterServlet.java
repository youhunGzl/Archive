package com.archive.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.archive.dao.DAOTStaff;
import com.archive.dao.DAOTUsers;
import com.archive.jpa.TStaff;
import com.archive.jpa.TUsers;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Md5Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;


@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(RegisterServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		System.out.println("=========请求RegisterServlet========执行=="+action);
		if(action.equals("getAll")){
			getAll(request, response);
		}else if(action.equals("chickname")){
			chickname(request, response);
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

	
public void getAll(HttpServletRequest request, HttpServletResponse response){
		String cond = request.getParameter("params");
		DAOTUsers daotusers = new DAOTUsers();
		if(cond==null){
			Exception e = new Exception("请求参数有误！");
	    	HibernateUtility.exceptionOutput(e,request,response);
		}
		
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		TUsers user = gson.fromJson(cond, TUsers.class);
		JsonObject jobj = new JsonObject();
		
		
		try{
			user.setUser_pwd(Md5Test.toMD5((user.getUser_pwd())));	
			user.setAddress(URLDecoder.decode(user.getUser_name(), "utf-8"));
			user.setReal_name(URLDecoder.decode(user.getReal_name(), "utf-8"));
			user.setProfession(URLDecoder.decode(user.getProfession(), "utf-8"));
			user.setCert_type(URLDecoder.decode(user.getCert_type(), "utf-8"));
			user.setSex(URLDecoder.decode(user.getSex(), "utf-8"));
			user.setAddress(URLDecoder.decode(user.getAddress(), "utf-8"));
			user.setSelflabel(URLDecoder.decode(user.getSelflabel(), "utf-8"));
			daotusers.saveUsers(user);
			
			jobj.addProperty("success", true);	
			jobj.addProperty("message", "注册成功");
			
			String responseResult = gson.toJson(jobj);
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
			
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}

	public void chickname(HttpServletRequest request, HttpServletResponse response){
		String cond = request.getParameter("params");
		DAOTUsers daotusers = new DAOTUsers();
		if(cond==null){
			Exception e = new Exception("请求参数有误！");
	    	HibernateUtility.exceptionOutput(e,request,response);
		}
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonObject jobj = new JsonObject();
		
		try{
			TUsers user = daotusers.getUserByUsername(cond);
			if(user == null){
				jobj.addProperty("success", true);	
				jobj.addProperty("message", "用户名可使用");
			}else{
				jobj.addProperty("success", false);	
				jobj.addProperty("message", "用户名已存在");
			}
			
			String responseResult = gson.toJson(jobj);
			response.setContentType("text/html;charset=utf-8");	
			PrintWriter pw = null;
			pw = response.getWriter();
			pw.print(responseResult);
			pw.close();
			
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}	
	}
}
