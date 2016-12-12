package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.softage.hrms.dao.NoDuesDao;
import com.softage.hrms.model.MstAssests;

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
	public List<String> getassetsdetails() {

		/*ArrayList<JSONObject> arrayjson=new ArrayList<JSONObject>();*/
		/*JSONObject listjson=new JSONObject();*/

		Session session=sessionfactory.getCurrentSession();
		JSONObject json =new JSONObject();
		String hql="from MstAssests"; 	 
		Query query=session.createQuery(hql);	
		List<String> stringlist=new ArrayList<String>();
		List<MstAssests> listassets=query.list();
		for(MstAssests assets:listassets)
		{

			stringlist.add(assets.getAssetsName());

			/*System.out.println("dao "+ stringlist);*/
		}

		/*json.put("listarray", arrayjson);*/


		return stringlist;
	}

}
