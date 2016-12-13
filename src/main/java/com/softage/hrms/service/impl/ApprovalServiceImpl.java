package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.ApprovalDao;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {
	
	@Autowired
	private ApprovalDao approvaldao;

	@Override
	public JSONObject getApprovalRequestService(String empcode) {
		return approvaldao.getApprovalRequestDao(empcode);
	}

	@Override
	public List<MstQuestions> getQuestionService(int roleID) {
		return approvaldao.getQuestionDao(roleID);
	}

	@Override
	public TblUserResignation getResignationUserService(String emp_code) {
		return null;
	}

}
