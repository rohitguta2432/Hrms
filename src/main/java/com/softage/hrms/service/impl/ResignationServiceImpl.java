package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	public List<TblUserResignation> getHrApprovalInitService(String empcode, int status) {
		// TODO Auto-generated method stub
		return resignationdao.getHrApprovalInitDao(empcode,status);
	}

	
}
