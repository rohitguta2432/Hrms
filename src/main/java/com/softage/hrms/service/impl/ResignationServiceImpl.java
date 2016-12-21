package com.softage.hrms.service.impl;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.ISoftAgeEnterpriseProxy;

import com.softage.hrms.dao.ResignationDao;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;
//import com.softage.hrms.model.ResignationBean;
import com.softage.hrms.service.ResignationService;

@Service
public class ResignationServiceImpl implements ResignationService {

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
	public JSONObject getHrApprovalInitService(String empcode, int status) {
		// TODO Auto-generated method stub
		int count=1;
		JSONObject hrapprovaljson=new JSONObject();

		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		List<JSONObject> acceptedResignedList=new ArrayList<JSONObject>();
		List<TblUserResignation> approvedResignationList= resignationdao.getHrApprovalInitDao(empcode,status);
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
		return null;
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
}
