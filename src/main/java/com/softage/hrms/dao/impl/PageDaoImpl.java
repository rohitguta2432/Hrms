package com.softage.hrms.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.PageDao;

@Repository
public class PageDaoImpl implements PageDao {
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	@Transactional
	public JSONObject getPagesLinkDao(int role_id) {
		JSONObject jsob=new JSONObject();
		Session session=this.sessionfactory.getCurrentSession();
		String sql="select distinct(w.page_name) from mst_page_authority a join mst_webpage w on w.page_id=a.page_id where a.role_id="+role_id+" and w.active=1";
		Query query=session.createSQLQuery(sql);
		List list=query.list();
		jsob.put("pages",list);
		//System.out.println(jsob);
		return jsob;
	}

	@Override
	@Transactional
	public JSONObject getPagesBasedOnRoleId(String empcode, String hitdatetime, int roleid) {
		JSONObject jsonObject=new JSONObject();
		Session session=this.sessionfactory.getCurrentSession();
		String sql="Call usp_rolepage_mapping(?,?,?)";
		Query query=session.createSQLQuery(sql).setParameter(0,empcode).setParameter(1,hitdatetime).setParameter(2,roleid);
		List list=query.list();
		jsonObject.put("pages", list);
		return jsonObject;
	}

}
