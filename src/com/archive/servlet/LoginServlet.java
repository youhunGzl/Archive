package com.archive.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.catalina.Session;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;

import com.archive.dao.DAOTRecord;
import com.archive.dao.DAOTReserve;
import com.archive.dao.DAOTUsers;
import com.archive.jpa.TRecord;
import com.archive.jpa.TReserve;
import com.archive.jpa.TUsers;
import com.archive.utility.GetIpAddr;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Md5Test;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;	
	private static Logger logger = Logger.getLogger(LoginServlet.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		System.out.println("=========请求LoginServlet========执行=="+action);
		if("login".equals(action)){
			login(request,response);
		}else if("checkuser".equals(action)){
			checkuser(request,response);
		}else if("loginout".equals(action)){
			Loginout(request,response);
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
	
	
	@SuppressWarnings("static-access")
	public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//读取前端传来的参数－对象
		request.setCharacterEncoding("utf-8");
		String data = request.getParameter("params");
		
	    if(data==null){
	    	Exception e = new Exception("请求参数有误！");
	    	HibernateUtility.exceptionOutput(e,request,response);
	    }
		//创建GSON对象
	    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
	    //将GSON对象映射成JAVA对象
		TUsers emp = gson.fromJson(data, TUsers.class);
		
		
		//访问数据库，验证用户的合法性
		String username = null;
		DAOTUsers daouser = new DAOTUsers();
		
		try{
			username = daouser.getUsers(URLDecoder.decode(emp.getUser_name(), "utf-8"),Md5Test.toMD5(emp.getUser_pwd()));
		}catch(Exception e){
			HibernateUtility.exceptionOutput(e,request,response);
			e.printStackTrace();
		}
		
		String responseResult = null;
		//创建jsonobject，封装处理结果状态和结果字符串
		JsonObject jobj = new JsonObject();		
		if(username!="no user"&&username!="pwd error"){
			System.out.print("================登录成功===用户名==============="+username);
			request.getSession().setAttribute("username", username);
			jobj.addProperty("success", true);
			jobj.addProperty("result", username);
		}else if(username=="no user"){
			jobj.addProperty("success", false);
			jobj.addProperty("result", username);
		}else if(username=="pwd error"){
			jobj.addProperty("success", false);
			jobj.addProperty("result", username);
		}else{
			jobj.addProperty("success", false);			
			jobj.addProperty("message", "noexist");
		}
		//将json对象转字符串，用于传回前端	
		responseResult = gson.toJson(jobj);
		//写回前端
		try {
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
	
	public void checkuser(HttpServletRequest request, HttpServletResponse response) {
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		JsonObject jobj = new JsonObject();	
		String responseResult = null;
		DAOTRecord daotre = new DAOTRecord();
		DAOTReserve daot = new DAOTReserve();
		
		List<TReserve> t = new ArrayList();
		List<TRecord> tre = new ArrayList();
		
		
		try {
			
			if(request.getSession().getAttribute("username") != null && request.getSession().getAttribute("username") != ""){
				DAOTUsers daou = new DAOTUsers();
				TUsers u = daou.getUserByUsername((String) request.getSession().getAttribute("username"));
				TRecord data = new TRecord();
				data.settUsers_id(u);
				data.setOperator_type("查看系统信息");
				
				tre = daotre.find(data, null);
				try {
					if(tre.size() != 0){
						t = daot.getbysuccess(Long.toString(u.getId()), tre.get(0).getOperator_time());
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				jobj.addProperty("success", true);
				jobj.addProperty("result", (String) request.getSession().getAttribute("username"));
				jobj.addProperty("message", t.size());
			}else{
				jobj.addProperty("success", false);
				jobj.addProperty("result", "未登录");
			}
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
	
	public void Loginout(HttpServletRequest request, HttpServletResponse response) {
		HttpSession session=request.getSession();
		String responseResult = null;
		session.removeAttribute("username");
		//session.invalidate();//清除
		System.out.print("=========退出登录==========");
		try {
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


