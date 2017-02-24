package com.archive.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TransformDate {

	/**
     * Date格式
     */
    private Date currentTime = new Date();
    /**
     * String格式
     */
    private String dateString = "";
    /**
     * 默认时间格式
     */
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 实例化转换Date bean
     */
    public TransformDate() { }
 
    /**
     * @param i 时间格式
     */
    public TransformDate(String i) {
        this.formatter = new SimpleDateFormat(i);
    }
    

    /**
     * @return currentTime 
     * 返回当前时间
     * Date格式
     */
    public Date getCurrentTime() {
        return currentTime;
    }
    
    /**
     * @return dateString 
     * 返回当前时间
     * String格式
     */
    public String getDateString() {
    	dateString = formatter.format(currentTime);//获取现在时间
        return dateString;
    }
    
    /**
     * @return dateString 
     * 转换时间格式
     * Date->String格式
     */
    public String tranDateString(Date time) throws Exception{
    	if(time != null){
    		// 如果日期不合法，则抛异常  
        	try {  
        		dateString = formatter.format(time);
        	} catch (Exception ex){  
        	    ex.printStackTrace();  
        	    System.out.println("日期不合法");  
        	} 
        	return dateString;
    	}else{
    		return null;
    	}
    	
    	
    		
    }
    
    /**
     * @return currentTime 
     * 转换时间格式
     * String->Date格式
     * @throws ParseException 
     */
    public Date trancurrentTime(String time) throws Exception {
    	if(time != null&&time != ""){
    		// 如果日期不合法，则抛异常  
    	try {  
        	currentTime = formatter.parse(time);
    	} catch (Exception ex){  
    	    ex.printStackTrace();  
    	    System.out.println("日期不合法");  
    	} 
    	return currentTime;
    	}else{
    		return null;
    	}
    }
}


