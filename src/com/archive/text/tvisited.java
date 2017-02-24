package com.archive.text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.archive.dao.DAOTFile;
import com.archive.dao.DAOTUsers;
import com.archive.dao.DAOTVisited;
import com.archive.jpa.TFiles;
import com.archive.jpa.TUsers;
import com.archive.jpa.TVisited;
import com.archive.servlet.ReserveServlet;
import com.archive.utility.SimilarityBase;

public class tvisited {

	/**
	 * @param args
	 * @throws ParseException 
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) throws ParseException {
		// TODO Auto-generated method stub
		String[] a = {"Outline"};
		SimilarityBase s = new SimilarityBase();
		s.main(a);
		
	}
}
