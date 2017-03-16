package com.softage.hrms.service.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.ISoftAgeEnterpriseProxy;

import com.softage.hrms.controller.HomeController;
import com.softage.hrms.dao.ResignationDao;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;
//import com.softage.hrms.model.ResignationBean;
import com.softage.hrms.service.ResignationService;

@Service
public class ResignationServiceImpl implements ResignationService {
	
	private static final Logger logger = LoggerFactory.getLogger(ResignationServiceImpl.class);
	
	@Autowired
	private ResignationDao resignationdao;

	@Override
	public JSONObject submitResignationService(TblUserResignation resignationbean) {
		return resignationdao.insertResignationDao(resignationbean);
	}

	@Override
	public String getRmEmail(String emp_Code) {

		return resignationdao.getRmEmailDao(emp_Code);
	}

	@Override
	public JSONObject resignationInitialization() {
		return resignationdao.resignationInitialisationDao();
	}

	@Override
	public int getNoticePeriodTime(String emp_code) {

		return resignationdao.getNoticeTime(emp_code);
	}

	@Override
	public JSONObject getReleivingDate(String empcode,int noticeperiod) {
		return resignationdao.getRelievingDateDao(empcode, noticeperiod);
	}

	@Override
	public int getRmID(String empcode) {
		return resignationdao.getRmIdDao(empcode);
	}

	@Override
	public int getHrID(String empcode) {
		return resignationdao.getHrIdDao(empcode);
	}

	@Override
	public MstReason getReason(int reasonVal) {
		return resignationdao.getReasonMast(reasonVal);
	}

	@Override
	public MstResignationStatus getStatus(int statusVal) {
		return resignationdao.getStatusMast(statusVal);
	}

	@Override
	public TblUserResignation getResignationUserService(String emp_code, int status) {
		return resignationdao.getResignationUserDao(emp_code,status);
	}

	@Override
	public JSONObject getHrApprovalInitService(String empcode, int status,int circode) {
		// TODO Auto-generated method stub
		int count=1;
		JSONObject hrapprovaljson=new JSONObject();

		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		List<JSONObject> acceptedResignedList=new ArrayList<JSONObject>();
		List<TblUserResignation> approvedResignationList= resignationdao.getHrApprovalInitDao(empcode,status,circode);
		try {
		for(TblUserResignation resignedUser : approvedResignationList){
			JSONObject acceptedResignation=new JSONObject();
			String employee_code=resignedUser.getEmpCode();
			ISoftAgeEnterpriseProxy emp=new ISoftAgeEnterpriseProxy();
		
				String firstname=emp.getUserDetail(employee_code).getFirstName();
				String lastname=emp.getUserDetail(employee_code).getLastName();
				String name=firstname+" "+lastname;
				MstReason reason=resignedUser.getMstReason();
				String reason_for_leaving=reason.getReason();
				int resignId=resignedUser.getResignationId();
				String remarks=resignedUser.getComments();
				//int notice_period=emp.getUserDetail(employee_code).getNoticePeriod(); FROM ESF Service
				int notice_period=60;
				Date resDate=resignedUser.getResignationDate();
				String resignDate=df.format(resDate);
				String rmempcode=resignedUser.getRmEmpcode();
				//String rm_email=emp.getUserDetail(rmempcode).getEmail(); FROM ESF SERVICE
				String rm_email="arpan.mathur@softageindia.com";
				acceptedResignation.put("sno", count);
				acceptedResignation.put("empname", name);
				acceptedResignation.put("empcode", employee_code);
				acceptedResignation.put("leaving_reason", reason_for_leaving);
				acceptedResignation.put("remarks", remarks);
				acceptedResignation.put("noticetime", notice_period);
				acceptedResignation.put("resignDate", resignDate);
				acceptedResignation.put("rm_empcode", rmempcode);
				acceptedResignation.put("resignId", resignId);
				acceptedResignation.put("rm_email", rm_email);
				acceptedResignedList.add(acceptedResignation);
				count=count+1;
			} 
			}catch (RemoteException e) {
				e.printStackTrace();
			}
			System.out.println(acceptedResignedList);
			hrapprovaljson.put("empinfo", acceptedResignedList);
			System.out.println(hrapprovaljson);




		
		return hrapprovaljson;
	}

	@Override
	public List<String> getAllResignedUserRMs() {
		return resignationdao.getAllResignedUserRMs();
	}

	@Override
	public JSONObject getAllResignedUsers(Set<String> setRM) {
		return resignationdao.getAllResignedUsers(setRM);
	}

	@Override
	public List<String> getAllResignedUsersHrs() {
		return resignationdao.getAllResignedUsersHrs();
	}

	@Override
	public JSONObject getAllResignedUsersHR(Set<String> setHR) {
		JSONObject hrapprovaljson=new JSONObject();
		List<ArrayList<TblUserResignation>> list=resignationdao.getAllResignedUsersHR(setHR);
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		List<JSONObject> acceptedResignedList=new ArrayList<JSONObject>();
		for(ArrayList<TblUserResignation> alist : list){
			try {
			for(TblUserResignation resignedUser : alist){
				int count=1;
				JSONObject acceptedResignation=new JSONObject();
				String employee_code=resignedUser.getEmpCode();
				ISoftAgeEnterpriseProxy emp=new ISoftAgeEnterpriseProxy();
			
					String firstname=emp.getUserDetail(employee_code).getFirstName();
					String lastname=emp.getUserDetail(employee_code).getLastName();
					String name=firstname+" "+lastname;
					MstReason reason=resignedUser.getMstReason();
					String reason_for_leaving=reason.getReason();
					int resignId=resignedUser.getResignationId();
					String remarks=resignedUser.getComments();
					//int notice_period=emp.getUserDetail(employee_code).getNoticePeriod(); FROM ESF Service
					int notice_period=60;
					String[] keys={"empcode"};
					String[] values={employee_code};
					String empinfostring=emp.enterPriseDataService("EVM", "EmpInfo", keys, values);
					
					Date resDate=resignedUser.getResignationDate();
					String resignDate=df.format(resDate);
					String rmempcode=resignedUser.getRmEmpcode();
					//String rm_email=emp.getUserDetail(rmempcode).getEmail(); FROM ESF SERVICE
					String rm_email="arpan.mathur@softageindia.com";
					acceptedResignation.put("sno", count);
					acceptedResignation.put("empname", name);
					acceptedResignation.put("empcode", employee_code);
					acceptedResignation.put("leaving_reason", reason_for_leaving);
					acceptedResignation.put("remarks", remarks);
					acceptedResignation.put("noticetime", notice_period);
					acceptedResignation.put("resignDate", resignDate);
					acceptedResignation.put("rm_empcode", rmempcode);
					acceptedResignation.put("resignId", resignId);
					acceptedResignation.put("rm_email", rm_email);
					acceptedResignedList.add(acceptedResignation);
					count=count+1;
				} 
				}catch (RemoteException e) {
					e.printStackTrace();
				}
		}
		hrapprovaljson.put("empinfo", acceptedResignedList);
		return hrapprovaljson;
	}

	@Override
	public TblUserResignation getById(int id) {
		// TODO Auto-generated method stub
		return resignationdao.getById(id);
	}

	@Override
	public JSONObject getResignationModelByCircleID(String officeCode) {
		int count=1;
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		JSONObject resListJson=new JSONObject();
		List<JSONObject> jsonList=new ArrayList<JSONObject>();
		List<TblUserResignation> resignedUsers=resignationdao.getResignationModelByCircleID(officeCode);
		for(TblUserResignation resbean : resignedUsers){
			JSONObject resJson=new JSONObject();
			String empcode=resbean.getEmpCode();
			ISoftAgeEnterpriseProxy emp=new ISoftAgeEnterpriseProxy();
			try {
				String fname = emp.getUserDetail(empcode).getFirstName();
				String lname=emp.getUserDetail(empcode).getLastName();
				String name=fname+lname;
				MstReason reason=resbean.getMstReason();
				String reason_for_leaving=reason.getReason();
				int resignId=resbean.getResignationId();
				String remarks=resbean.getComments();
				int notice_period=60;
				String[] keys={"empcode"};
				String[] values={empcode};
				//String empinfo=emp.enterPriseDataService("EVM", "EmpInfo", keys, values);
				//JSONParser parser=new JSONParser();
				//JSONObject empInformation=(JSONObject)parser.parse(empinfo);
				//int notice_period=(Integer)empInformation.get("NoticePeriod");
				//String rm_email=(String)empInformation.get("ManagerEmail");
				Date resDate=resbean.getResignationDate();
				String resignDate=df.format(resDate);
				String rmempcode=resbean.getRmEmpcode();
				//String rm_email=emp.getUserDetail(rmempcode).getEmail(); FROM ESF SERVICE
				String rm_email="arpan.mathur@softageindia.com";
				resJson.put("sno", count);
				resJson.put("empname", name);
				resJson.put("empcode", empcode);
				resJson.put("leaving_reason", reason_for_leaving);
				resJson.put("remarks", remarks);
				resJson.put("noticetime", notice_period);
				resJson.put("resignDate", resignDate);
				resJson.put("rm_empcode", rmempcode);
				resJson.put("resignId", resignId);
				resJson.put("rm_email", rm_email);
				jsonList.add(resJson);
				count=count+1;
				
			} catch (Exception e) {
				e.printStackTrace();
			}

			
		}
		resListJson.put("empinfo", jsonList);
		return resListJson;
	}

	@Override
	public TblUserResignation getExEmpResignationUserService(String empcode, String pwd,int status) {
		TblUserResignation resignation= resignationdao.getExEmpResignationUserService(empcode,pwd ,status);
		//DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		if(resignation!=null){
		Date lwd=resignation.getHrLwdDate();
		Date currDate=new Date();
		long diff=currDate.getTime()-lwd.getTime();
		Long days=TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
		if(!(days<180 && days>=0)){
			resignation=null;
		}
		}
		return resignation;
	}

	@Override
	public JSONObject getUsersForHrApproval(String officecode,int status) {
		List<JSONObject> resUserslist=new ArrayList<JSONObject>();
		JSONObject userListJson=new JSONObject();
		List<TblUserResignation> resuUsers= resignationdao.getUsersForHrApproval(officecode,status);
		ISoftAgeEnterpriseProxy employee=new ISoftAgeEnterpriseProxy();
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		int count=1;
		try{
		for (TblUserResignation tblUserResignation : resuUsers) {
			JSONParser parser=new JSONParser();
			JSONObject jsonObject=new JSONObject();
			String empcode=(String)tblUserResignation.getEmpCode();
			MstReason reason=tblUserResignation.getMstReason();
			String reason_for_leaving=reason.getReason();
			String remarks=tblUserResignation.getComments();
			Date resDate=tblUserResignation.getResignationDate();
			String resignDate=df.format(resDate);
			int resignId=tblUserResignation.getResignationId();
			String[] keys={"empcode"};
			String[] values={empcode};
			String empinfoString=employee.enterPriseDataService("EVM", "EmpInfo", keys, values);
			JSONObject empinfoJson=(JSONObject)parser.parse(empinfoString);
			String empname=(String)empinfoJson.get("EmployeeName");
			int notice_period=((Long)empinfoJson.get("NoticePeriod")).intValue();
			String rmempcode=(String)empinfoJson.get("ManagerCode");
			String rm_email=(String)empinfoJson.get("ManagerEmail");
			jsonObject.put("sno", count);
			jsonObject.put("empname", empname);
			jsonObject.put("empcode", empcode);
			jsonObject.put("leaving_reason", reason_for_leaving);
			jsonObject.put("remarks", remarks);
			jsonObject.put("noticetime", notice_period);
			jsonObject.put("resignDate", resignDate);
			jsonObject.put("rm_empcode", rmempcode);
			jsonObject.put("resignId", resignId);
			jsonObject.put("rm_email", rm_email);
			resUserslist.add(jsonObject);
			count=count+1;
		}
		userListJson.put("empinfo", resUserslist);
		}catch(Exception e){
			logger.error(">>>>>>>>>>>>>>> Error in iterating json of resigned users on office code for hr"+e.getMessage());
			e.printStackTrace();
		}
		return userListJson;
	}
}
