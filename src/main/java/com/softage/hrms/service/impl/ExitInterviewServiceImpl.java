package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.ExitInterviewDao;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.service.ExitInterviewService;

@Service
public class ExitInterviewServiceImpl implements ExitInterviewService {

@Autowired	
private ExitInterviewDao exitinterviewdao;
	
	
@Override
	public List<JSONObject> listHrQuestion(int roleid,int stageid) {
   return exitinterviewdao.getHrQuestions(roleid,stageid);
	}
   @Override
	public JSONObject submithrfeedback(TblFeedbacks feedbackbean) {
	return exitinterviewdao.inserthrfeedback(feedbackbean);
	}
   @Override
public List<JSONObject> listempQuestion(int stageid) {
return exitinterviewdao.getEmpQuestions(stageid);
	}
@Override
public List<TblFeedbacks> listempfeedback(int resignationid,int stageid1,int stageid2) {
return exitinterviewdao.getEmpFeedbackStatus(resignationid, stageid1, stageid2);
	}
@Override
public List<TblFeedbacks> getEmpFeedback(int resignationid, int stageid) {
	
	return exitinterviewdao.getfeedbacklist(resignationid, stageid);
}

}
