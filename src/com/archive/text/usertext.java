package com.archive.text;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Session;

import com.archive.dao.DAOTReserve;
import com.archive.dao.DAOTReserveDetail;
import com.archive.dao.DAOTStaff;
import com.archive.jpa.TReserve;
import com.archive.jpa.TReserveDetail;
import com.archive.jpa.TStaff;
import com.archive.utility.HibernateUtility;
import com.archive.utility.baseUtil;

public class usertext {

	/**
	 * @param args
	 */
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HibernateUtility hb=new HibernateUtility();
		Session s1=hb.getSession();
		Session s2=hb.getSession();
		s1.close();
		s2.close();
		/*DAOTReserveDetail dao = new DAOTReserveDetail();
		TReserveDetail li = new TReserveDetail();
		try {
			li.setArchive_type("file");
			li.setArchive_id("5");
			dao.save(li);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		
	}

}
