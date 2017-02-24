package com.archive.text;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.archive.dao.DAOTReserve;
import com.archive.dao.DAOTStaff;
import com.archive.dao.DAOTUsers;
import com.archive.jpa.TReserve;
import com.archive.jpa.TStaff;
import com.archive.utility.baseUtil;

public class TRservetext {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub

		DAOTReserve dao = new DAOTReserve();
		DAOTUsers daou = new DAOTUsers();
		TReserve li = new TReserve();
		TReserve result = new TReserve();
		
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString = formatter.format(currentTime);//获取现在时间
		Date currentTime_2 =  formatter.parse(dateString);
		  
		
		li.setAudiu_time(currentTime_2);
		li.setBorrow_objects("1");
		li.setReserve_no("1");
		li.setReserve_time(currentTime_2);
		li.settUsers_id(daou.getUserByUsername("q12"));
		
		
		try {
			result = dao.save(li);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		System.out.println("=========="+result.getId()+"====");
		System.out.println("=========="+formatter.format(result.getAudiu_time())+"====");
		System.out.println("=========="+result.gettUsers_id().getAddress()+"====");
		
	}

}
