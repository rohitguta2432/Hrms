package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.TblFeedbacks;

public interface ExitInterviewDao {

	public List<JSONObject> getHrQuestions(int roleid,int stageid);
    public JSONObject inserthrfeedback(TblFeedbacks feedbackbean);
    public List<JSONObject> getEmpQuestions(int stageid);
    public List<TblFeedbacks> getEmpFeedbackStatus(int resignationid,int stageid1,int stageid2);
    public List<TblFeedbacks> getfeedbacklist(int resignationid,int stageid);
}
