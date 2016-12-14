package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblUserResignation;

public interface ApprovalDao {
	
	public JSONObject getApprovalRequestDao(String empcode);
	public List<MstQuestions> getQuestionDao(int roleID);
	public MstQuestions getRmFeedbackQuestionDao(int quesID);
	//public TblUserResignation getResignationUserDao(String emp_code);
	public int saveRmFeedbackDao(TblFeedbacks feedback);
	public void updateResignationStatusDao(TblUserResignation resBean);
	//public List<TblUserResignation> getHrApprovalInitDao(String empcode);

}
