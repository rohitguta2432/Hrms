package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.model.MstAssests;
import com.softage.hrms.model.MstUploadItem;


@Repository
public class EmployeeDocumentDaoImp implements EmployeeDocumentDao{
	
	
	@Autowired
	private SessionFactory sessionfactory;

	@Override
	@Transactional
	public List<MstUploadItem> getUploadItems(int deptId) {


		/*ArrayList<JSONObject> arrayjson=new ArrayList<JSONObject>();*/
		/*JSONObject listjson=new JSONObject();*/

		Session session=sessionfactory.getCurrentSession();
		JSONObject json =new JSONObject();
		String hql="select m from MstUploadItem m where m.departmentId="+deptId; 	 
		Query query=session.createQuery(hql);	
		List<MstUploadItem> itemList=query.list();

		/*json.put("listarray", arrayjson);*/


		return itemList;
	
	}

}
