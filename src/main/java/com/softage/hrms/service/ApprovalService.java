package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblUserResignation;

public interface ApprovalService {
	
	public JSONObject getApprovalRequestService(String empcode);
	public List<MstQuestions> getQuestionService(int roleID);
	public TblUserResignation getResignationUserService(String emp_code);

}
