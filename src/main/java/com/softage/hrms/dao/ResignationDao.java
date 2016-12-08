package com.softage.hrms.dao;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;

//import com.softage.hrms.model.ResignationBean;

public interface ResignationDao {
	
	public JSONObject insertResignationDao(TblUserResignation resignationBean);
	public JSONObject resignationInitialisationDao();
	public int getNoticeTime(String empcode);
	public String getRmEmailDao(String emp_code);
	public JSONObject getRelievingDateDao(String empcode,int noticeperiod);
	public int getRmIdDao(String empcode);
	public int getHrIdDao(String empcode);
	public MstReason getReasonMast(int reasonValue);
	public MstResignationStatus getStatusMast(int statusValue);

}
