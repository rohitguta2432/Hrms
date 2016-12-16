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
import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

@Repository
public class NoDuesDaoImpl implements NoDuesDao {

	@Autowired
	private SessionFactory sessionfactory;


	@Override
	@Transactional
	public List<String> getrmacceptedempcode() {

		org.hibernate.Session session=sessionfactory.getCurrentSession();
		String hql="select empCode from TblUserResignation where status=2";
		Query query=session.createQuery(hql);
		List<String> listempcode=query.list();

		return listempcode;
	}


	@Override
	@Transactional
	public List<JSONObject> getassetsdetails(int departmentid) {
	
		
    org.hibernate.Session session=sessionfactory.getCurrentSession();
    JSONObject json =new JSONObject();
      String hql="from MstAssests d where d.departmentId=:departmentid"; 	 
     Query query=session.createQuery(hql);	
     query.setParameter("departmentid", departmentid);
     
     List<JSONObject> stringlist=new ArrayList<JSONObject>();
     List<MstAssests> listassets=query.list();
     for(MstAssests assets:listassets)
     {
    	 JSONObject JSONObject=new JSONObject();
    	 JSONObject.put("name", assets.getAssetsName());
       	 stringlist.add(JSONObject);    	 
      
     }    
      return stringlist;

	}


	@Override
	@Transactional
	public JSONObject insertnoduesassetsdetails(TblAssetsManagement accountbean) {
		
		JSONObject insertbean=new JSONObject();
		org.hibernate.Session session=sessionfactory.getCurrentSession();
	    
		
		
		session.save(accountbean);
		
		
		return insertbean;
	}
    
	@Override
	@Transactional
	public JSONObject insertnoduesclearence(TblNoDuesClearence clearencebean) {
		JSONObject insertclearence=new JSONObject();
		
		org.hibernate.Session session=sessionfactory.getCurrentSession();
		session.save(clearencebean);
		
		return insertclearence;
	}

	//DAOImpl created by Arpan to find the No Dues Pending Status
	@Override
	public JSONObject getNoDuesPendingStatus(int resignationID) {
		Session session=this.sessionfactory.getCurrentSession();
		JSONObject pendingNoDuesDeptJson=new JSONObject();
		String sql="call usp_getNoDuesPendingDeptName(?)";
		Query query=session.createSQLQuery(sql).setParameter(1, resignationID);
		List<String> noDuesPendingDeptList=query.list();
		pendingNoDuesDeptJson.put("noDuesPendingDept", noDuesPendingDeptList);
		return pendingNoDuesDeptJson;
	}

}
