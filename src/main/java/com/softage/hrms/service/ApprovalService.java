package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstQuestions;

public interface ApprovalService {
	
	public JSONObject getApprovalRequestService(String empcode);
	public List<MstQuestions> getQuestionService(int roleID);

}
