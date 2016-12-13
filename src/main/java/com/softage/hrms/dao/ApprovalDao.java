package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblUserResignation;

public interface ApprovalDao {
	
	public JSONObject getApprovalRequestDao(String empcode);
	public List<MstQuestions> getQuestionDao(int roleID);
	public TblUserResignation getResignationUserDao(String emp_code);

}
