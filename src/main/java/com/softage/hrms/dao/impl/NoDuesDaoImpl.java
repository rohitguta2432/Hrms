package com.softage.hrms.dao.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.softage.hrms.controller.HomeController;
import com.softage.hrms.dao.NoDuesDao;
import com.softage.hrms.model.MstAssests;
import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

@Repository
public class NoDuesDaoImpl implements NoDuesDao {
	private static final Logger logger = LoggerFactory.getLogger(NoDuesDaoImpl.class);

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	@Transactional
	public List<String> getrmacceptedempcode(String stageid,int departmentid,String officeid, int status) {
		List<String> listempcode = new ArrayList<String>();
			if(stageid.equalsIgnoreCase("1")){
			try {
				org.hibernate.Session session = sessionfactory.getCurrentSession();
				String hql = "select r.emp_code from tbl_user_resignation r where r.status="+status+" and r.office_id='"+officeid+"' and ((select count(clearence_id) from tbl_no_dues_clearence  c where c.resignation_id=r.resignation_id and c.department_final_status =2 and c.department_id="+departmentid+")<1)";
                Query query = session.createSQLQuery(hql);
				listempcode = query.list();
			} catch (Exception e) {
				logger.error("get RM Accepted employee List>>>>   ", e);
			}
		}else{
		
		try {
			org.hibernate.Session session = sessionfactory.getCurrentSession();
			String hql = "select empCode from TblUserResignation where status=:status and officeId=:officeId";
			Query query = session.createQuery(hql);
			query.setParameter("status", status);
			query.setParameter("officeId", officeid);
			listempcode = query.list();
		} catch (Exception e) {
			logger.error("get rm Accepted employee List>>>>   ", e);
		}
		}
	return listempcode;
	}
	@Override
	@Transactional
	public List<JSONObject> getassetsdetails(int departmentid) {
		org.hibernate.Session session = sessionfactory.getCurrentSession();
		JSONObject json = new JSONObject();
		String hql = "from MstAssests d where d.departmentId=:departmentid";
		Query query = session.createQuery(hql);
		query.setParameter("departmentid", departmentid);
		List<JSONObject> stringlist = new ArrayList<JSONObject>();
		List<MstAssests> listassets = query.list();
		for (MstAssests assets : listassets) {
			JSONObject JSONObject = new JSONObject();
			JSONObject.put("name", assets.getAssetsName());

			stringlist.add(JSONObject);
		}
		return stringlist;
	}

	@Override
	@Transactional
	public JSONObject insertnoduesassetsdetails(TblAssetsManagement accountbean) {
		JSONObject insertbean = new JSONObject();
		org.hibernate.Session session = sessionfactory.getCurrentSession();
		session.save(accountbean);

		return insertbean;
	}

	@Override
	@Transactional
	public JSONObject insertnoduesclearence(TblNoDuesClearence clearencebean) {
		JSONObject insertclearence = new JSONObject();
		Session session = sessionfactory.getCurrentSession();
		session.save(clearencebean);

		return insertclearence;
	}

	// DAOImpl created by Arpan to find the No Dues Pending Status
	@Override
	@Transactional
	public JSONObject getNoDuesPendingStatus(int resignationID) {
		JSONObject pendingNoDuesDeptJson = new JSONObject();
            List datalist=new ArrayList();
            datalist.add("All Nodues Completed");
		try {
			Session session = this.sessionfactory.getCurrentSession();
            String sql = "call usp_getNoDuesPendingDeptName(?)";
			Query query = session.createSQLQuery(sql).setParameter(0, resignationID);
			List<String> noDuesPendingDeptList = query.list();
			noDuesPendingDeptList.remove("Hr");
				pendingNoDuesDeptJson.put("noDuesPendingDept", noDuesPendingDeptList);
			
			logger.info("Nodues Pending List>>>>     " + noDuesPendingDeptList);
		} catch (Exception e) {
			logger.error("get noduesStatus", e);
		}
		return pendingNoDuesDeptJson;
	}


	@Override
	@Transactional
	public TblNoDuesClearence updateNoduesClearence(TblNoDuesClearence tblNoDuesClearence) {
		try {
			Session session = sessionfactory.getCurrentSession();			
			session.saveOrUpdate(tblNoDuesClearence);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tblNoDuesClearence;
	}

	@Override
	@Transactional
	public TblNoDuesClearence findByResignationId(int resignationId,int departmentid) {
		Session session = sessionfactory.getCurrentSession();
		String hibernateQuery = "from TblNoDuesClearence where tbluserresignation.resignationId=:res_id and departmentId=:depart_id";
		Query query = session.createQuery(hibernateQuery);
		query.setParameter("res_id", resignationId);
		query.setParameter("depart_id", departmentid);
	/*	Criteria criteria =  session.createCriteria(TblNoDuesClearence.class);
		TblNoDuesClearence tblNoDuesClearence = (TblNoDuesClearence)criteria.add(
				Restrictions
				.eq("tbluserresignation.resignationId", resignationId))
		.uniqueResult();*/
		
		TblNoDuesClearence tblNoDuesClearence = (TblNoDuesClearence) query.uniqueResult();
		return tblNoDuesClearence;
	}
	@Override
	@Transactional
	public List<String> getresignemplist( int departmentid, int status) {
		List<String> listempcode = new ArrayList<String>();
		try {
			org.hibernate.Session session = sessionfactory.getCurrentSession();
			String hql = "select r.emp_code from tbl_user_resignation r where r.status="+status+" and  ((select count(clearence_id) from tbl_no_dues_clearence  c where c.resignation_id=r.resignation_id and c.department_final_status =2 and c.department_id="+departmentid+")<1)";
            Query query = session.createSQLQuery(hql);
			listempcode = query.list();
		} catch (Exception e) {
			logger.error("get RM Accepted employee List>>>>   ", e);
		}

		return listempcode;
	}
}
