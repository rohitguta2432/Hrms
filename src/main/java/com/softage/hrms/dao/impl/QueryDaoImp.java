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
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblExEmpCommunication;
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
	public int save(TblExEmployeeQuery employeeQuery) {
		String result="";
		int id=0;
		try{
			Session session  =sessionFactory.getCurrentSession();
			   id=(Integer)session.save(employeeQuery);
			result="done";
			logger.info("  TblUploadedPath  Sucessfully updated ");
		}catch (Exception e) {
			result="error";
			logger.error(" TblUploadedPath error for  updating ");
		}

		return id;
	}

	@Override
	@Transactional
	public List<TblExEmployeeQuery> getQueryList(String empcode) {
		List<TblExEmployeeQuery> querys=null;
		try{
			
			Session session=this.sessionFactory.getCurrentSession();
			String hql="select querys from TblExEmployeeQuery querys where querys.queryAssigned=:empcode or querys.exEmpCode=:exEmpCode";
			Query query=session.createQuery(hql);
			query.setParameter("empcode", empcode);
			query.setParameter("exEmpCode", empcode);
			querys=query.list();
			logger.info("get Departmet List Sucessfully" + querys);
		}catch (Exception e) {
			logger.error("",e);
		}
		return querys;
	}

	@Override
	@Transactional
	public TblExEmployeeQuery getById(int id) {
		TblExEmployeeQuery question=null;
		try{
		Session session=this.sessionFactory.getCurrentSession();
	    question=(TblExEmployeeQuery)session.load(TblExEmployeeQuery.class, new Integer(id));
	    logger.info("get TblExEmpCommunication" + question);
		}catch(Exception e){
			logger.error("",e);
			
		}
		return question;
	}

	@Override
	@Transactional
	public String save(TblExEmpCommunication employeeCommunication) {
		String result="";
		try{
			Session session  =sessionFactory.getCurrentSession();
			session.save(employeeCommunication);
			result="done";
			logger.info("  employeeCommunication  Sucessfully updated ");
		}catch (Exception e) {
			result="error";
			logger.error(" employeeCommunication error for  updating ",e);
		}

		return result;
	}

	@Override
	@Transactional
	public List<TblExEmpCommunication> getByQueryId(int queryId) {
		List<TblExEmpCommunication> querys=null;
		try{
			Session session=this.sessionFactory.getCurrentSession();
			String hql="select querys from TblExEmpCommunication querys where querys.tblExEmployeeQuery.queryId=:queryId";
			Query query=session.createQuery(hql);
			query.setParameter("queryId", queryId);
			querys=query.list();
			logger.info("get TblExEmpCommunication  Sucessfully" + querys);
		}catch (Exception e) {
			logger.error("",e);
		}
		return querys;
	}

	@Override
	@Transactional
	public MstDepartment getDepartmentById(int Id) {
		MstDepartment department=null;
		try{
		Session session=this.sessionFactory.getCurrentSession();
		department=(MstDepartment)session.load(MstDepartment.class, new Integer(Id));
	    logger.info("get Department  " + department);
		}catch(Exception e){
			logger.error("",e);
			
		}
		return department;
	}



}
