package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.controller.HomeController;
import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.model.MstAssests;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblUploadedPath;


@Repository
public class EmployeeDocumentDaoImp implements EmployeeDocumentDao{
	private static final Logger logger = LoggerFactory.getLogger(EmployeeDocumentDao.class);

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

	@Override
	@Transactional
	public String save(TblUploadedPath tblUploadedPath) {
		String result="";
		try{
			Session session  =sessionfactory.getCurrentSession();
			session.saveOrUpdate(tblUploadedPath);
			result="done";
			logger.info("  TblUploadedPath  Sucessfully updated ");
		}catch (Exception e) {
			result="error";
			logger.error(" TblUploadedPath error for  updating ");
		}

		return result;
	}

	@Override
	@Transactional
	public MstUploadItem entityById(int id) {
		MstUploadItem mstUploadItem=null;
		try{

		 Session session=sessionfactory.getCurrentSession();
	     mstUploadItem=(MstUploadItem)session.get(MstUploadItem.class, new Integer(id));
	     logger.info("get Model Sucessfully  ");

		return mstUploadItem;
		}catch (Exception e) {
			logger.error("",e);
		}
		return mstUploadItem;
	}

	@Override
	@Transactional
	public List<TblUploadedPath> getByEmpCode(String empcode) {
		List<TblUploadedPath> pathList=null;
		try{
		Session session=this.sessionfactory.getCurrentSession();
		//String hql="select mstItem from TblUploadedPath mstItem where mstItem.empCode=:empCode";
		String hql="select mstItem from TblUploadedPath mstItem join fetch mstItem.mstUploadItem join fetch mstItem.tblUserResignation where mstItem.empCode=:empCode";
		Query query=session.createQuery(hql);
		query.setParameter("empCode", empcode);
	    pathList=query.list();
		}catch (Exception e) {
			// TODO: handle exception
		}
		return pathList;
	}

	@Override
	@Transactional
	public TblUploadedPath getByResignId(int resignId,int itemId) {
		TblUploadedPath tblUploadedPath=null;
		try{
		Session session=this.sessionfactory.getCurrentSession();
		//String hql="select mstItem from TblUploadedPath mstItem where mstItem.empCode=:empCode";
		String hql="select mstItem from TblUploadedPath mstItem join fetch mstItem.mstUploadItem join fetch mstItem.tblUserResignation where mstItem.mstUploadItem="+itemId+" and mstItem.tblUserResignation="+resignId;
		Query query=session.createQuery(hql);
	    tblUploadedPath=(TblUploadedPath)query.uniqueResult();
	    logger.info(" get Sucessfully  ");
		}catch (Exception e) {
			logger.error("",e);
			// TODO: handle exception
		}
		return tblUploadedPath;
	}

	@Override
	@Transactional
	public JSONObject getNotUploadedDocumentsById(int resignID) {
		JSONObject notUploadedDocsJson=new JSONObject();
		try{
		Session session=this.sessionfactory.getCurrentSession();
		String sql="call usp_getNotUploadedDocs(?)";
		Query query=session.createSQLQuery(sql).setParameter(0, resignID);
		List<String> notUploadedDocs=query.list();
		notUploadedDocsJson.put("docList", notUploadedDocs);
		}catch(Exception e){
			logger.error(" ",e);
		}
		return notUploadedDocsJson;
	}

	@Override
	@Transactional
	public String update(TblUploadedPath tblUploadedPath) {
		String result=null;
		try{
			Session session=this.sessionfactory.getCurrentSession();
			session.saveOrUpdate(tblUploadedPath);
			  logger.info("Update  Sucessfully  ");
			  result="done";
			}catch(Exception e){
				result="error";
				logger.error("",e);
			}
		return result;
	}

}
