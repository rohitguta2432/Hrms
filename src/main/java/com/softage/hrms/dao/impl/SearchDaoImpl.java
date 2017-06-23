package com.softage.hrms.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.SessionFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.SearchDao;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;

@Repository
public class SearchDaoImpl implements SearchDao {


@Autowired
 private SessionFactory sessionFactory;

@SuppressWarnings("unchecked")
@Override
@Transactional
public JSONObject getDetailsDao(String emp) {
	Session session=sessionFactory.getCurrentSession();
	JSONObject jsob=new JSONObject();
	Query query=session.createQuery("select  t.empCode,t.officeId,t.resignationDate,t.releivingDate,t.hrApprovalDate,t.rmApprovalDate,t.mstReason.reason,t.mstResignationStatus.status from  TblUserResignation t where t.empCode= :employeecode");
	query.setParameter("employeecode",emp);
	List details=query.list();
	jsob.put("val",details);
	return jsob;
}
}

