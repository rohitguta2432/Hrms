package com.softage.hrms.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.dao.QueryDao;
import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmployeeQuery;
import com.softage.hrms.model.TblUserResignation;

@Repository
public class QueryDaoImp implements QueryDao {

	private static final Logger logger = LoggerFactory.getLogger(QueryDaoImp.class);

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<MstDepartment> getDepartmentList() {
		List<MstDepartment> mastDepartments=null;
		try{
			Session session=this.sessionFactory.getCurrentSession();
			String hql="from MstDepartment";
			Query query=session.createQuery(hql);
			mastDepartments=query.list();
			logger.info("get Departmet List Sucessfully" + mastDepartments);
		}catch (Exception e) {
			logger.error("",e);
		}
		return mastDepartments;
	}

	@Override
	@Transactional
	public String save(TblExEmployeeQuery employeeQuery) {
		String result="";
		try{
			Session session  =sessionFactory.getCurrentSession();
			session.saveOrUpdate(employeeQuery);
			result="done";
			logger.info("  TblUploadedPath  Sucessfully updated ");
		}catch (Exception e) {
			result="error";
			logger.error(" TblUploadedPath error for  updating ");
		}

		return result;
	}



}
