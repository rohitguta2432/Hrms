package com.softage.hrms.dao.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.dao.ResignationDao;
//import com.softage.hrms.model.ResignationBean;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;

@Repository
public class ResignationDaoImpl implements ResignationDao {
	private static final Logger logger = LoggerFactory.getLogger(ResignationDaoImpl.class);

	
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public JSONObject insertResignationDao(TblUserResignation resignationBean) {
		JSONObject jsonObject=new JSONObject();
		Session session=this.sessionFactory.getCurrentSession();
		int returned=(Integer)session.save(resignationBean);
		//System.out.println(returned);
		if(returned<1){
			jsonObject.put("result", "unsuccessful");
		}
		else{
			jsonObject.put("result", "successful");
		}
		
		return jsonObject;
	}

	@Override
	@Transactional
	public String getRmEmailDao(String emp_code) {
		Session session=this.sessionFactory.getCurrentSession();
		String sql="select r.rm_email from tbl_emp_rm_mapping e join mst_reporting_manager r on r.id=e.rm_id where e.emp_code="+"'"+emp_code+"'";
		Query query=session.createSQLQuery(sql);
		String email="";
		List<String> email_list=query.list();
		for(String rm_email : email_list){
			email=rm_email;
		}
		return email;
	}

	@Override
	@Transactional
	public JSONObject resignationInitialisationDao() {
		
		Session session=this.sessionFactory.getCurrentSession();
		//String sql="select reason from mst_reason";
		//Query query=session.createSQLQuery(sql);
		JSONObject jsonResult=new JSONObject();
		List<JSONObject> jsonList=new ArrayList<JSONObject>();
		Query query=session.createQuery("select resignReason.id,resignReason.reason from MstReason resignReason");
		List<Object[]> results=query.list();
		for(Object[] reasonOnj : results){
			JSONObject json=new JSONObject();
			int id=(Integer)reasonOnj[0];
			String reason=(String)reasonOnj[1];
			json.put("id", id);
			json.put("reason",reason );
			jsonList.add(json);
		}
		jsonResult.put("reasonList", jsonList);
		return jsonResult;
	}

	@Override
	@Transactional
	public int getNoticeTime(String empcode) {
		int no_of_days=0;
		//JSONObject jsonobj=new JSONObject();
		Session session=this.sessionFactory.getCurrentSession();
		String sql1="select notice_period from user_details where emp_code='"+empcode+"'";
		Query query=session.createSQLQuery(sql1);
		List<Integer> noticePeriod= query.list();
		for(int noticeTime : noticePeriod){
			no_of_days= noticeTime;
		}
		//jsonobj.put("NoticePeriod", no_of_days);
		System.out.println("The notice period is : "+no_of_days);
		return no_of_days;
	}

	@Override
	@Transactional
	public JSONObject getRelievingDateDao(String empcode,int noticeperiod) {
		JSONObject jsob=new JSONObject();
		Date releiving_date=null;
		//String releiving_date=null;
		Session session=this.sessionFactory.getCurrentSession();
		String sp_query="call usp_getReleivingDate(?,?)";
		Query query=session.createSQLQuery(sp_query).setParameter(0, empcode).setParameter(1, noticeperiod);
		List releaseDate_list=query.list();
		for(Object relDate : releaseDate_list){
			 releiving_date=(Date)relDate;
			
		}
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		formatter.format(releiving_date);
		//jsob.put("releaseDate", releiving_date);
		jsob.put("releaseDate", formatter.format(releiving_date));
		return jsob;
	}

	@Override
	@Transactional
	public int getRmIdDao(String empcode) {
		Session session=this.sessionFactory.getCurrentSession();
		String sql="select r.id from mst_reporting_manager r join tbl_emp_rm_mapping m on m.rm_id=r.id where m.emp_code='"+empcode+"'";
		int rmid=0;
		Query query=session.createSQLQuery(sql);
		List rm_id_list=query.list();
		for(Object rid : rm_id_list){
			rmid=(Integer)rid;
		}
		return rmid;
	}

	@Override
	@Transactional
	public int getHrIdDao(String empcode) {
		Session session=this.sessionFactory.getCurrentSession();
		int hr_id=0;
		String sql="select h.id from tbl_emp_rm_mapping m join mst_hr_manager h on h.id=m.hr_id where m.emp_code='"+ empcode+"'";
		Query query=session.createSQLQuery(sql);
		List<Integer> hr_list=query.list();
		for(int hid : hr_list){
			hr_id=hid;
		}
		return hr_id;
	}

	@Override
	@Transactional
	public MstReason getReasonMast(int reasonValue) {
		Session session=this.sessionFactory.getCurrentSession();
		MstReason reasonmast=(MstReason)session.load(MstReason.class, new Integer(reasonValue));
		return reasonmast;
	}

	@Override
	@Transactional
	public MstResignationStatus getStatusMast(int statusValue) {
		// TODO Auto-generated method stub
		Session session=this.sessionFactory.getCurrentSession();
		MstResignationStatus statusmast=(MstResignationStatus)session.load(MstResignationStatus.class, new Integer(statusValue));
		return statusmast;
	}

	@Override
	@Transactional
	public TblUserResignation getResignationUserDao(String emp_code, int status) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="select resignation from TblUserResignation resignation where resignation.empCode=:employeecode and resignation.mstResignationStatus="+status;
		Query query=session.createQuery(hql);
		query.setParameter("employeecode",emp_code );
	//	query.setParameter("emp_status", status);
		//List<TblUserResignation> resignedUser=query
		TblUserResignation resignedUser=(TblUserResignation)query.uniqueResult();
		return resignedUser;
	}

	@Override
	@Transactional
	public List<TblUserResignation> getHrApprovalInitDao(String empcode, int status) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="select resignUser from TblUserResignation resignUser join fetch resignUser.mstReason where resignUser.hrEmpcode=:hr_emp_code and resignUser.mstResignationStatus="+status;
		Query query=session.createQuery(hql);
		query.setParameter("hr_emp_code", empcode);
		//query.setParameter("hr_emp_status", status);
		List<TblUserResignation> approvedResignationList=query.list();
		System.out.println("In approval daos hr init" + approvedResignationList);
		return approvedResignationList;
	}


}
