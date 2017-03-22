package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.TblFeedbacks;

public interface ExitInterviewService {

	public List<JSONObject> listHrQuestion(int roleid,int stageid);
    public List<JSONObject> listempQuestion(int stageid);
    public JSONObject submithrfeedback(TblFeedbacks feedbackbean);
	public List<TblFeedbacks> listempfeedback(int resignationid,int stageid1,int stageid2);
	public List<TblFeedbacks> getEmpFeedback(int resignationid,int stageid);
}
