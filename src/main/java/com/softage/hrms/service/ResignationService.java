package com.softage.hrms.service;

import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;

//import com.softage.hrms.model.ResignationBean;

public interface ResignationService {
	
	public JSONObject submitResignationService(TblUserResignation resignationbean);
	public JSONObject resignationInitialization();
	public int getNoticePeriodTime(String emp_code);
	public String getRmEmail(String emp_Code);
	public JSONObject getReleivingDate(String empcode,int noticeperiod);
	public int getRmID(String empcode);
	public int getHrID(String empcode);
	public MstReason getReason(int reasonVal);
	public MstResignationStatus getStatus(int statusVal);
	public TblUserResignation getResignationUserService(String emp_code,int status);
	public JSONObject getHrApprovalInitService(String empcode,int status);
	public List<String> getAllResignedUserRMs();
	public JSONObject getAllResignedUsers(Set<String> setRM);
	public List<String> getAllResignedUsersHrs();
	public JSONObject getAllResignedUsersHR(Set<String> setHR);
}
