package com.softage.hrms.dao.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.tempuri.ISoftAgeEnterpriseProxy;

import com.softage.hrms.dao.ApprovalDao;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.impl.ResignationServiceImpl;

@Repository
public class ApprovalDaoImpl implements ApprovalDao {
	
	private static final Logger logger = LoggerFactory.getLogger(ApprovalDaoImpl.class);
	
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
		Date relievingDate=null;
		String sql="select res.emp_code,res.comments,r.reason,res.releiving_date from tbl_user_resignation res join mst_reason r on res.leaving_reason=r.reason_id where res.rm_empcode='"+empcode+"' and res.status=1";
		Query query=session.createSQLQuery(sql);
		List<Object[]> emp_approval_list=query.list();
		Iterator itr=emp_approval_list.iterator();
		for (Object object[] : emp_approval_list) {
			emp_code=(String)object[0];
			comments=(String)object[1];
			leaving_reason=(String)object[2];
			relievingDate=(Date)object[3];
			DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
			String reldate=df.format(relievingDate);
			//Date lwd_date=new Date(reldate);
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
			jsonemp.put("releivingDate", reldate);
			jsonArray.add(jsonemp);
			count=count+1;
			
		}
		
		jsob.put("jsonArray", jsonArray);
		return jsob;
	}

	@Override
	@Transactional
	public List<MstQuestions> getQuestionDao(int roleID) {
		Session session=this.sessionfactory.getCurrentSession();
		String hql="select quesMast from MstQuestions quesMast where quesMast.roleId=:roleid and quesMast.isActive=1 and quesMast.stageId=1";
		Query query=session.createQuery(hql);
		query.setParameter("roleid", roleID);
		List<MstQuestions> questionList=query.list();
		return questionList;
	}

	/*@Override
	@Transactional
	public TblUserResignation getResignationUserDao(String emp_code) {
		Session session=this.sessionfactory.getCurrentSession();
		String hql="select resignation from TblUserResignation resignation where resignation.empCode=:employeecode and resignation.mstResignationStatus=1";
		Query query=session.createQuery(hql);
		query.setParameter("employeecode",emp_code );
		//List<TblUserResignation> resignedUser=query
		TblUserResignation resignedUser=(TblUserResignation)query.uniqueResult();
		return resignedUser;
	}*/

	@Override
	@Transactional
	public MstQuestions getRmFeedbackQuestionDao(int quesID) {
		Session session=this.sessionfactory.getCurrentSession();
		MstQuestions question=(MstQuestions)session.load(MstQuestions.class, new Integer(quesID));
		return question;
	}

	@Override
	@Transactional
	public int saveRmFeedbackDao(TblFeedbacks feedback) {
		Session session=this.sessionfactory.getCurrentSession();
		int saveStatus=(Integer)session.save(feedback);
		return saveStatus;
	}

	@Override
	@Transactional
	public void updateResignationStatusDao(TblUserResignation resBean) {
		Session session=this.sessionfactory.getCurrentSession();
		session.saveOrUpdate(resBean);
	}

/*	@Override
	@Transactional
	public List<TblUserResignation> getHrApprovalInitDao(String empcode) {
		Session session=this.sessionfactory.getCurrentSession();
		String hql="select resignUser from TblUserResignation resignUser join fetch resignUser.mstReason where resignUser.hrEmpcode=:hr_emp_code and resignUser.mstResignationStatus=2";
		Query query=session.createQuery(hql);
		query.setParameter("hr_emp_code", empcode);
		List<TblUserResignation> approvedResignationList=query.list();
		System.out.println("In approval daos hr init" + approvedResignationList);
		return approvedResignationList;
	}
*/
	@Override
	@Transactional
	public String insertHrLwdDao(TblUserResignation resignation){
		Session session=this.sessionfactory.getCurrentSession();
		String status="";
		try{
		session.saveOrUpdate(resignation);
		status="successful";
		}catch(Exception e){
			status="error";
		}
		return status;
	}

	@Override
	@Transactional
	public String insertHrLwdCommentDao(TblFeedbacks feedbacks) {
		Session session=this.sessionfactory.getCurrentSession();
		String lwdCommentFeedback="";
		try{
			session.saveOrUpdate(feedbacks);
			lwdCommentFeedback="successful";
		}catch(Exception e){
			lwdCommentFeedback="error";
		}
		return lwdCommentFeedback;
	}

	@Override
	@Transactional
	public List<TblUserResignation> getResignedUsersForRm(int status) {
		Session session=sessionfactory.getCurrentSession();
		String hql="Select res from TblUserResignation res join fetch res.mstReason where res.mstResignationStatus.statusId=:statusid";
		Query query=session.createQuery(hql);
		query.setParameter("statusid", status);
		List<TblUserResignation> resignation=(List<TblUserResignation>)query.list();
		return resignation;
	}
}
