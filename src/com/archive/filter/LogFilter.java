package com.archive.filter;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTMaterial;
import com.archive.dao.DAOTMultimedia;
import com.archive.dao.DAOTRecord;
import com.archive.dao.DAOTUsers;
import com.archive.jpa.TFiles;
import com.archive.jpa.TMaterial;
import com.archive.jpa.TMultimedia;
import com.archive.jpa.TRecord;
import com.archive.jpa.TUsers;
import com.archive.utility.GetIpAddr;
import com.archive.utility.TransformDate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * @WebFilter将一个实现了javax.servlet.Filte接口的类定义为过滤器组件
 * 属性filterName声明过滤器的名称,可选
 * 属性urlPatterns指定要过滤 的URL模式,也可使用属性value来声明.(指定要过滤的URL模式是必选属性)
 */
@WebFilter(filterName="LogFilter",urlPatterns={"/SearchServlet","/LoginServlet","/RegisterServlet","/ReserveServlet","/UserServlet"})
public class LogFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

	@SuppressWarnings({ "unused", "static-access", "deprecation", "rawtypes", "unchecked" })
	@Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        // 获得在下面代码中要用的request,response,session对象
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        HttpServletResponse servletResponse = (HttpServletResponse) response;
        HttpSession session = servletRequest.getSession();
		String action = request.getParameter("action");

        TRecord t = new TRecord();
        DAOTRecord dao = new DAOTRecord();
        
        // 获得用户请求的URI
        String path = servletRequest.getRequestURI();
        //System.out.println("==用户操作记录拦截=记录操作路径==========="+path);
        t.setOperator_content(path);
        
        // 从session里取用戶名信息
        String empId = (String) session.getAttribute("username");
        //System.out.println("==用户操作记录拦截=记录用户==========="+empId);
        DAOTUsers udao = new DAOTUsers();
		TUsers u = udao.getUserByUsername(empId);
		t.settUsers_id(u);
        
        GetIpAddr ipclass = new GetIpAddr();
		String ip = ipclass.getLocalIp(servletRequest);
		//System.out.println("==用户操作记录拦截=记录ip==========="+ip);
		t.setIp_address(ip);
		
		TransformDate date = new TransformDate();
		//System.out.println("==用户操作记录拦截=记录操作时间==========="+date.getCurrentTime());
		t.setOperator_time(date.getCurrentTime());

		if(empId != null){
			String type = "";
			if(action.equals("find")){
		        String a = request.getParameter("params");
				String data = URLDecoder.decode(request.getParameter("term"),"utf-8");
				Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
				
				t.setSearch_word(data);
				type = "检索"+a;
			}
			
			
			if(action.equals("getdata")){
		        String id = request.getParameter("params");
				String cond = request.getParameter("cond");
				Map<String,Object> map1 = new HashMap<String,Object>();
				Map<String,Object> map2 = new HashMap<String,Object>();
				map1.put("id", id);
				map2.put("cond", cond);
				List<Map<String,Object>> l = new ArrayList();
				l.add(map1);
				l.add(map2);
				t.setSearch_word(String.valueOf(l));
				type = "检索";
				
			}
			
			if(action.equals("reservehistory")){
				type = "查看系统信息";
				
			}
			
			
			t.setOperator_type(type);
			//System.out.println("==用户操作记录拦截=记录操作表==========="+t.getOperator_type());
			//System.out.println("==用户操作记录拦截=记录操作数据==========="+t.getSearch_word());
			
			if(t.getOperator_type() != null && t.getOperator_type() != ""){
				dao.savelog(t);
			}
			
		}
		
		
        chain.doFilter(request, response);
        
    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

}