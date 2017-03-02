package com.softage.hrms.dao.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
import org.tempuri.ISoftAgeEnterpriseProxy;

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
		//int returned=(Integer)session.save(resignationBean);
		//System.out.println(returned);
		try{
		session.save(resignationBean);
		jsonObject.put("result", "successful");
		}catch(Exception e){
			jsonObject.put("result", "unsuccessful");
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
		try{
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
		}catch(Exception e){
			logger.error(">>>>>>>>>>>>>>> Exception in resignationInitializationDao to get reasons"+e.getMessage());
		}
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
		String flagResult=null;
		Boolean flag=null;
		try{
		Session session=this.sessionFactory.getCurrentSession();
		String sp_query="call usp_getReleivingDate(?,?)";
		Query query=session.createSQLQuery(sp_query).setParameter(0, empcode).setParameter(1, noticeperiod);
		Object[] releaseDate_list=(Object[])query.uniqueResult();
		/*for(Object relDate : releaseDate_list){
			 releiving_date=(Date)relDate;			
		}*/
		releiving_date=(Date)releaseDate_list[0];
		flagResult=(String)releaseDate_list[1];
		if(flagResult.equalsIgnoreCase("enabled")){
			flag=false;
		}else{
			flag=true;
		}
		DateFormat formatter=new SimpleDateFormat("dd/MM/yyyy");
		formatter.format(releiving_date);
		//jsob.put("releaseDate", releiving_date);
		jsob.put("releaseDate", formatter.format(releiving_date));
		jsob.put("flag",flag);
		}catch(Exception e){
			logger.error(">>>>>>>>>>>>>>> Exception in retreiving the relieving date in dao"+e.getMessage());
		}
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
		TblUserResignation resignedUser=new TblUserResignation();
		if(status!=0){
		String hql="select resignation from TblUserResignation resignation where resignation.empCode=:employeecode and resignation.mstResignationStatus="+status;
		Query query=session.createQuery(hql);
		query.setParameter("employeecode",emp_code );
	//	query.setParameter("emp_status", status);
		//List<TblUserResignation> resignedUser=query
		resignedUser=(TblUserResignation)query.uniqueResult();
		}
		else{
			String hql="select resignation from TblUserResignation resignation join fetch resignation.mstReason join fetch resignation.mstResignationStatus where resignation.empCode=:employeecode order by resignation.resignationDate desc";
			Query query=session.createQuery(hql).setMaxResults(1);
			query.setParameter("employeecode",emp_code );
			resignedUser=(TblUserResignation)query.uniqueResult();
		}
		return resignedUser;
	}

	@Override
	@Transactional
	public List<TblUserResignation> getHrApprovalInitDao(String empcode, int status,int circode) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="select resignUser from TblUserResignation resignUser join fetch resignUser.mstReason where resignUser.hrEmpcode=:hr_emp_code and resignUser.mstResignationStatus="+status+" and resignUser.circleId="+circode;
		Query query=session.createQuery(hql);
		query.setParameter("hr_emp_code", empcode);
		//query.setParameter("hr_emp_status", status);
		List<TblUserResignation> approvedResignationList=query.list();
		System.out.println("In approval daos hr init" + approvedResignationList);
		return approvedResignationList;
	}

	@Override
	@Transactional
	public List<String> getAllResignedUserRMs() {
		Session session=this.sessionFactory.getCurrentSession();
		String sql="select rm_empcode from tbl_user_resignation where status=1";
		Query query=session.createSQLQuery(sql);
		List<String> list_of_rm=query.list();
		return list_of_rm;
	}

	@Override
	@Transactional
	public JSONObject getAllResignedUsers(Set<String> setRMs) {
		Session session=this.sessionFactory.getCurrentSession();
		JSONObject jsonemp=new JSONObject();
		ArrayList<JSONObject> jsonArray=new ArrayList<JSONObject>();
		JSONObject jsob=new JSONObject();
		int count=1;
		String emp_code;
		String first_name=null;
		String last_name=null;
		String leaving_reason=null;
		String comments=null;
		String noticePeriod=null;;
		Date relievingDate=null;
		if(setRMs.isEmpty()){
			return jsob;
		}
		else{
		for(String rm_empcode : setRMs){
		String sql="select res.emp_code,res.comments,r.reason,res.releiving_date from tbl_user_resignation res join mst_reason r on res.leaving_reason=r.reason_id where res.rm_empcode='"+rm_empcode+"' and res.status=1";
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
				e.printStackTrace();
			}	
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
		}
		}
		jsob.put("jsonArray", jsonArray);
		return jsob;
	}

	@Override
	@Transactional
	public List<String> getAllResignedUsersHrs() {
		Session session=this.sessionFactory.getCurrentSession();
		String sql="select hr_empcode from tbl_user_resignation where status=2";
		Query query=session.createSQLQuery(sql);
		List<String> list_of_hr=query.list();
		return list_of_hr;
	}

	@Override
	@Transactional
	public List<ArrayList<TblUserResignation>> getAllResignedUsersHR(Set<String> setHRs) {
		Session session=this.sessionFactory.getCurrentSession();
		List<ArrayList<TblUserResignation>> resignedLists=new ArrayList<ArrayList<TblUserResignation>>();
		for(String hrempcode : setHRs){
			String hql="select resignUser from TblUserResignation resignUser join fetch resignUser.mstReason where resignUser.hrEmpcode=:hr_emp_code and resignUser.mstResignationStatus=2";
	 		Query query=session.createQuery(hql);
	 		ArrayList<TblUserResignation> list_resignedUsers_hr=(ArrayList<TblUserResignation>) query.list();
	 		resignedLists.add(list_resignedUsers_hr);
		}
		return resignedLists;
	}

	@Override
	@Transactional
	public TblUserResignation getById(int id) {

		TblUserResignation tblresignation=null;
		try{
			Session session=this.sessionFactory.getCurrentSession();
			tblresignation=(TblUserResignation)session.get(TblUserResignation.class, new Integer(id));
			//tblresignation=(TblUserResignation)session.load(TblUserResignation.class, new Integer(id));

		}catch (Exception e) {
			logger.error("",e);
		}


		return  tblresignation;
	}

	@Override
	@Transactional
	public List<TblUserResignation> getResignationModelByCircleID(int cirID) {
		Session session=this.sessionFactory.getCurrentSession();
		String hql="Select resBean from TblUserResignation resBean join fetch resBean.mstReason where resBean.circleId=:cirID";
		Query query=session.createQuery(hql);
		query.setParameter("cirID", cirID);
		List<TblUserResignation> resignedUsers=query.list();
		return resignedUsers;
	}





}
