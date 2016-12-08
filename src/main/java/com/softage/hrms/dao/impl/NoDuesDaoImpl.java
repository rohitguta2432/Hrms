package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.mail.Session;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.NoDuesDao;

@Repository
public class NoDuesDaoImpl implements NoDuesDao {

	@Autowired
	private SessionFactory sessionfactory;
	
	
	@Override
	@Transactional
	public List<String> getrmacceptedempcode() {
		
		org.hibernate.Session session=sessionfactory.getCurrentSession();
		String sql="select emp_code from tbl_user_resignation where status=2";
		Query query=session.createSQLQuery(sql);
		List<String> listempcode=query.list();
		
		return listempcode;
	}


	@Override
	@Transactional
	public JSONObject getassetsdetails() {
		
		 JSONObject json=new JSONObject();
    org.hibernate.Session session=sessionfactory.getCurrentSession();
      String sql="select assets_name from mst_assests where department_id=1";
	  Query query=session.createSQLQuery(sql);	
      List listassets=query.list();
      
String[] arraystring=new String[listassets.size()];
      
     for(int i=0;i<listassets.size();i++)
     {
    	
    	 arraystring[0] = listassets.get(i).toString();
    	 /*arraystring[1] = listassets.get(i).toString();
    	 arraystring[2] = listassets.get(i).toString();*/
    	 String laptop=arraystring[0];
    	 String datacard=arraystring[1];
    	 String pendrive=arraystring[2];
    	 String itassets=arraystring[3];
    	 System.out.println(laptop);
    	 System.out.println(datacard);
    	 System.out.println(pendrive);
    	/* System.out.println(itassets);*/
    	 json.put("laptop", laptop);
    	
     }
     
      
      /*json.put("assets", listassets);*/
      return json;
	}

}
