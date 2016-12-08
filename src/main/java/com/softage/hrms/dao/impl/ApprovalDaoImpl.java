package com.softage.hrms.dao.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tempuri.ISoftAgeEnterpriseProxy;

import com.softage.hrms.dao.ApprovalDao;

@Repository
public class ApprovalDaoImpl implements ApprovalDao {
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	@Transactional
	public JSONObject getApprovalRequestDao(String empcode) {
		JSONObject jsonemp=new JSONObject();
		ArrayList<JSONObject> jsonArray=new ArrayList<JSONObject>();
		JSONObject jsob=new JSONObject();
		Session session=this.sessionfactory.getCurrentSession();
		int count=1;
		String emp_code;
		String first_name=null;
		String last_name=null;
		String leaving_reason=null;
		String comments=null;
		String noticePeriod=null;;
		String releivingDate=null;
		String sql="select res.emp_code,res.comments,r.reason from tbl_user_resignation res join mst_reason r on res.leaving_reason=r.reason_id where res.rm_empcode='"+empcode+"' and res.status=1";
		Query query=session.createSQLQuery(sql);
		List<Object[]> emp_approval_list=query.list();
		Iterator itr=emp_approval_list.iterator();
		for (Object object[] : emp_approval_list) {
			emp_code=(String)object[0];
			comments=(String)object[1];
			leaving_reason=(String)object[2];
			ISoftAgeEnterpriseProxy emp=new ISoftAgeEnterpriseProxy();
			try {
				first_name=emp.getUserDetail(emp_code).getFirstName();
				last_name=emp.getUserDetail(emp_code).getLastName();
				//noticePeriod=emp.getUserDetail(emp_code).getNoticePeriod();
				noticePeriod="60";//Using ESF service
				
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//int len=object.length;
/*			first_name=(String)object[0];
			last_name=(String)object[1];
			leaving_reason=(String)object[2];
			comments=(String)object[3];
			noticePeriod=String.valueOf(object[5]);
			releivingDate=String.valueOf(object[6]);*/
			
			
			jsonemp.put("sno", count);
			jsonemp.put("first_name", first_name);
			jsonemp.put("last_name", last_name);
			jsonemp.put("leaving_reason", leaving_reason);
			jsonemp.put("comments", comments);
			jsonemp.put("empcode", emp_code);
			jsonemp.put("noticePeriod", noticePeriod);
			jsonemp.put("releivingDate", releivingDate);
			jsonArray.add(jsonemp);
			count=count+1;
			
		}
		
		jsob.put("jsonArray", jsonArray);
		return jsob;
	}

}
