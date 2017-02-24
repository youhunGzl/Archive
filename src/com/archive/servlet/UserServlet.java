package com.archive.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTRecord;
import com.archive.dao.DAOTReserve;
import com.archive.dao.DAOTReserveDetail;
import com.archive.dao.DAOTUsers;
import com.archive.dao.DAOTVisited;
import com.archive.jpa.TFiles;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TRecord;
import com.archive.jpa.TReserve;
import com.archive.jpa.TReserveDetail;
import com.archive.jpa.TUsers;
import com.archive.jpa.TVisited;
import com.archive.utility.HibernateUtility;
import com.archive.utility.Md5Test;
import com.archive.utility.Pager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

@WebServlet("/UserServlet")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserServlet.class
			.getName());

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// 设置request编码格式，可以避免post请求的中文乱码，但get不行
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("action");
		System.out.println("=========请求UserServlet========执行=="+action);
		if (action.equals("getUser")) {
			getUser(request, response);
		} else if (action.equals("updatepwd")) {
			updatepwd(request, response);
		} else if (action.equals("updatecert")) {
			updatecert(request, response);
		} else if (action.equals("updateuser")) {
			updateuser(request, response);
		} else if (action.equals("visithistory")) {
			visithistory(request, response);
		} else if (action.equals("reservehistory")) {
			reservehistory(request, response);
		} else if (action.equals("systemnews")) {
			systemnews(request, response);
		} else {
			logger.log(Level.INFO, "action type parameter error");
			Exception e = new Exception("不支持操作  " + action);
			HibernateUtility.exceptionOutput(e, request, response);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doGet(request, response);
	}

	protected void getUser(HttpServletRequest request,
			HttpServletResponse response) {
		TUsers user = new TUsers();
		DAOTUsers daoTUsers = new DAOTUsers();

		try {
			String username = (String) request.getSession().getAttribute(
					"username");
			user = daoTUsers.getUserByUsername(username);
			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jobj = new JsonObject();
			if (user != null) {
				jobj.addProperty("success", true);
				String ja = gson.toJson(user);
				jobj.addProperty("result", ja);
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据不存在");
			}
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();

		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}

	protected void updatepwd(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ypwd = Md5Test.toMD5(request.getParameter("ypwd"));
		String npwd = Md5Test.toMD5(request.getParameter("npwd"));
		TUsers user = new TUsers();
		DAOTUsers daoTUsers = new DAOTUsers();

		try {
			String username = (String) request.getSession().getAttribute(
					"username");
			user = daoTUsers.getUserByUsername(username);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jobj = new JsonObject();
			if (user.getUser_pwd().equals(ypwd)) {
				user.setUser_pwd(npwd);
				daoTUsers.updateTUsers(user);
				jobj.addProperty("success", true);
				jobj.addProperty("result", "成功");
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("result", "原始密码错误");
			}
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();

		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}

	protected void updatecert(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String ycert_type = request.getParameter("ycert_type");
		String ycert_no = request.getParameter("ycert_no");
		String ncert_type = request.getParameter("ncert_type");
		String ncert_no = request.getParameter("ncert_no");
		TUsers user = new TUsers();
		DAOTUsers daoTUsers = new DAOTUsers();

		try {
			String username = (String) request.getSession().getAttribute(
					"username");
			user = daoTUsers.getUserByUsername(username);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jobj = new JsonObject();
			if (user.getCert_type().equals(
					URLDecoder.decode(ycert_type, "utf-8"))) {
				if (user.getCert_no().equals(ycert_no)) {
					user.setCert_no(ncert_no);
					user.setCert_type(URLDecoder.decode(ncert_type, "utf-8"));
					daoTUsers.updateTUsers(user);

					jobj.addProperty("success", true);
					jobj.addProperty("result", "成功");
				} else {
					jobj.addProperty("success", false);
					jobj.addProperty("result", "原始证件号错误");
				}
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("result", "原始证件类型错误");
			}
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();

		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}

	protected void updateuser(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String email = request.getParameter("email");
		String sex = request.getParameter("sex");
		String profession = request.getParameter("profession");
		String phone = request.getParameter("phone");
		String address = request.getParameter("address");
		String zipcode = request.getParameter("zipcode");
		String selflabel = request.getParameter("selflabel");
		String tis = request.getParameter("tis");

		TUsers user = new TUsers();
		DAOTUsers daoTUsers = new DAOTUsers();

		try {
			String username = (String) request.getSession().getAttribute(
					"username");
			user = daoTUsers.getUserByUsername(username);

			Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			JsonObject jobj = new JsonObject();
			user.setEmail(email);
			user.setSex(sex);
			user.setPhone(phone);
			user.setProfession(URLDecoder.decode(profession, "utf-8"));
			user.setAddress(URLDecoder.decode(address, "utf-8"));
			user.setZipcode(zipcode);
			user.setSelflabel(URLDecoder.decode(selflabel, "utf-8"));

			daoTUsers.updateTUsers(user);

			jobj.addProperty("success", true);
			jobj.addProperty("result", "成功");
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();

		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	protected void visithistory(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession().getAttribute("username");
		String currentPage = request.getParameter("currentPage");

		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		DAOTUsers udao = new DAOTUsers();
		DAOTVisited dao = new DAOTVisited();
		TVisited data = new TVisited();
		Pager pager = new Pager();

		
		try {
			TUsers u = udao.getUserByUsername(username);
			data.settUser_id(u);

			List<TVisited> totalResult = dao.find(data,null);
			//取出不重复数据
			List<TVisited> li = new ArrayList();
			List<TVisited> tli = new ArrayList();
			int sum = 0;
			for(int i = 0;i<totalResult.size();i++){
				for(int j = i+1;j<totalResult.size();j++){
					if(totalResult.get(i).getArchive_type().equals(totalResult.get(j).getArchive_type())){
						if(totalResult.get(i).getArchive_id().equals(totalResult.get(j).getArchive_id())){
							sum++;
						}
					}
				}
				if(sum == 0){
					li.add(totalResult.get(i));
				}else{
					sum = 0;
				}
			}
			//按时间排序
			for(int i = (li.size()-1);i >= 0 ;i-- ){
				tli.add(li.get(i));
			}
			pager.setTotalResults(tli.size());
			if (currentPage != null) {
				pager.setCurrentPage(Integer.parseInt(currentPage));
			}
			
			//分页
			List<TVisited> r = new ArrayList();
			for(int i = pager.getStartResults();i<(pager.getStartResults()+pager.getPageSize());i++){
				if(i < pager.getTotalResults()){
					r.add(tli.get(i));
				}
				
			}
			
			
			if(r != null){
				
				List<Object> result = new ArrayList();
				for(int i = 0 ;i < r.size();i++){
					if(r.get(i).getArchive_type().equals("file")){
						DAOTFile d = new DAOTFile();
						result.add(d.getbyid(Long.parseLong(r.get(i).getArchive_id())));
						
					}else if(r.get(i).getArchive_type().equals("multimedia")){
						DAOTMultimedia d = new DAOTMultimedia();
						result.add(d.getbyid(Long.parseLong(r.get(i).getArchive_id())));
						
					}else if(r.get(i).getArchive_type().equals("material")){
						DAOTMaterial d = new DAOTMaterial();
						result.add(d.getbyid(Long.parseLong(r.get(i).getArchive_id())));
					}
					
				}

				
				jobj.addProperty("success", true);
				JsonArray ja = (JsonArray) gson.toJsonTree(r);
				JsonArray ja2 = (JsonArray) gson.toJsonTree(result);
				List p = new ArrayList();
				p.add(pager);
				JsonArray page = (JsonArray) gson.toJsonTree(p);
				jobj.add("result", ja);
				jobj.add("resultdata", ja2);
				jobj.add("page", page);
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据不存在");
			}

			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();

		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	protected void systemnews(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession()
				.getAttribute("username");
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		DAOTUsers udao = new DAOTUsers();
		DAOTReserve rdao = new DAOTReserve();
		
		List<TReserve> rdata = new ArrayList();
		
		try {
			TUsers u = udao.getUserByUsername(username);
			rdata = rdao.getbysuccess(Long.toString(u.getId()),null);
		
		
			if(rdata != null){
				
				jobj.addProperty("success", true);
				JsonArray ja = (JsonArray) gson.toJsonTree(rdata);
				
				jobj.add("result", ja);
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据不存在");
			}
	
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();
	
		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	protected void reservehistory(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = (String) request.getSession()
				.getAttribute("username");
		JsonObject jobj = new JsonObject();
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();

		DAOTUsers udao = new DAOTUsers();
		DAOTReserve rdao = new DAOTReserve();
		DAOTReserveDetail rddao = new DAOTReserveDetail();
		
		List<TReserve> rdata = new ArrayList();
		List<TReserveDetail> rddata = new ArrayList();
		
		try {
			TUsers u = udao.getUserByUsername(username);
			rdata = rdao.getbyuser(Long.toString(u.getId()));
			rddata = rddao.getbyUIdok(u.getId());
			
			List<Object> resultdata = new ArrayList();
			for(int i = 0 ;i < rddata.size();i++){
				if(rddata.get(i).getArchive_type().equals("file")){
					DAOTFile d = new DAOTFile();
					resultdata.add(d.getbyid(Long.parseLong(rddata.get(i).getArchive_id())));
					
				}else if(rddata.get(i).getArchive_type().equals("multimedia")){
					DAOTMultimedia d = new DAOTMultimedia();
					resultdata.add(d.getbyid(Long.parseLong(rddata.get(i).getArchive_id())));
					
				}else if(rddata.get(i).getArchive_type().equals("material")){
					DAOTMaterial d = new DAOTMaterial();
					resultdata.add(d.getbyid(Long.parseLong(rddata.get(i).getArchive_id())));
				}
				
			}
		
			if(resultdata != null){
				
				jobj.addProperty("success", true);
				JsonArray ja = (JsonArray) gson.toJsonTree(rdata);
				JsonArray ja2 = (JsonArray) gson.toJsonTree(resultdata);
				JsonArray ja3 = (JsonArray) gson.toJsonTree(rddata);
				
				jobj.add("rddata", ja3);
				jobj.add("result", ja);
				jobj.add("resultdata", ja2);
			} else {
				jobj.addProperty("success", false);
				jobj.addProperty("message", "数据不存在");
			}
	
			response.setContentType("text/html;charset=utf-8");
			String responseResult = null;
			PrintWriter pw = response.getWriter();
			responseResult = gson.toJson(jobj);
			pw.print(responseResult);
			pw.close();
	
		} catch (Exception e) {
			HibernateUtility.exceptionOutput(e, request, response);
			e.printStackTrace();
		}
	}
}
