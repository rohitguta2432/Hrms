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
		// TODO Auto-generated method stub
		return exitinterviewdao.inserthrfeedback(feedbackbean);
	}

}
