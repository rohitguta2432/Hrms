package com.softage.hrms.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tempuri.ISoftAgeEnterpriseProxy;

import com.softage.hrms.dao.ApprovalDao;
import com.softage.hrms.dao.impl.ResignationDaoImpl;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.TblFeedbacks;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ApprovalService;

@Service
public class ApprovalServiceImpl implements ApprovalService {
	
	private static final Logger logger = LoggerFactory.getLogger(ResignationDaoImpl.class);
	
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
	public MstQuestions getRmFeedbackQuestionService(int quesID) {
		return approvaldao.getRmFeedbackQuestionDao(quesID);
	}
	
	/*@Override
	public TblUserResignation getResignationUserService(String emp_code) {
		return approvaldao.getResignationUserDao(emp_code);
	}*/

	@Override
	public int saveRmFeedbackService(TblFeedbacks feedback) {
		return approvaldao.saveRmFeedbackDao(feedback);
	}

	@Override
	public void updateResignationStatus(TblUserResignation resBean) {
		approvaldao.updateResignationStatusDao(resBean);
	}

	@Override
	public String insertHrLwdService(TblUserResignation resignation) {
		return approvaldao.insertHrLwdDao(resignation);
	}

	@Override
	public String insertHrLwdCommentService(TblFeedbacks hr_lwd_feedback) {
		return approvaldao.insertHrLwdCommentDao(hr_lwd_feedback);
	}

	@Override
	public JSONObject getResignedUsersForRm(String empcode,int status) {
		List<TblUserResignation> reslist=approvaldao.getResignedUsersForRm(status);
		List<JSONObject> jsonArray=new ArrayList<JSONObject>();
		JSONObject jsob=new JSONObject();
		ISoftAgeEnterpriseProxy employee=new ISoftAgeEnterpriseProxy();
		JSONParser parser=new JSONParser();
		int count=1;
		String rm_rmEmpcode=null;
		JSONObject rm_empJson=new JSONObject();
		DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
		Date relievingDate=null;
		try{
		for (TblUserResignation tblUserResignation : reslist) {
			String resigned_empcode=tblUserResignation.getEmpCode();
			String[] keys={"empcode"};
			String[] values={resigned_empcode};
			String empinfostring=employee.enterPriseDataService("EVM","EmpInfo", keys, values);
			JSONObject empinfojson=(JSONObject)parser.parse(empinfostring);
			String rm_empcode=(String)empinfojson.get("ManagerCode");
			if(rm_empcode!=null && rm_empcode!=""){
				String[] rm_value={rm_empcode};
				String rmempinfo=employee.enterPriseDataService("EVM", "EmpInfo", keys, rm_value);
				rm_empJson=(JSONObject)parser.parse(rmempinfo);
				rm_rmEmpcode=(String)rm_empJson.get("ManagerCode");
			}
			if(empcode.equalsIgnoreCase(rm_empcode) || empcode.equalsIgnoreCase(rm_rmEmpcode)){
				JSONObject jsonemp=new JSONObject();
				String first_name=(String)empinfojson.get("EmployeeName");
				MstReason reasonMast=tblUserResignation.getMstReason();
				String leaving_reason=reasonMast.getReason();
				String comments=tblUserResignation.getComments();
				int noticePeriod=((Long)empinfojson.get("NoticePeriod")).intValue();
				relievingDate=tblUserResignation.getReleivingDate();
				String reldate=df.format(relievingDate);
				jsonemp.put("sno", count);
				jsonemp.put("first_name", first_name);
				//jsonemp.put("last_name", last_name);
				jsonemp.put("leaving_reason", leaving_reason);
				jsonemp.put("comments", comments);
				jsonemp.put("empcode", resigned_empcode);
				jsonemp.put("noticePeriod", noticePeriod);
				jsonemp.put("releivingDate", reldate);
				jsonArray.add(jsonemp);
			}	
		}
		jsob.put("jsonArray",jsonArray);
		}catch(Exception e){
			logger.error(">>>>>>>>>>>>>>> Error in retrieving values for rm approval"+ e.getMessage());
		}
		return jsob;
	}

	/*@Override
	public List<TblUserResignation> getHrApprovalInitService(String empcode) {
		return approvaldao.getHrApprovalInitDao(empcode);
	}
*/

}
