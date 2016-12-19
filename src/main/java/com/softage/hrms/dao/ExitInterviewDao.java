package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.TblFeedbacks;

public interface ExitInterviewDao {

	public List<JSONObject> getHrQuestions(int roleid,int stageid);
	
	public JSONObject inserthrfeedback(TblFeedbacks feedbackbean);
	
}
