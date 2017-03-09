package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jpa.criteria.expression.SearchedCaseExpression;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.fasterxml.jackson.core.JsonFactory;
import com.softage.hrms.dao.ExitInterviewDao;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblFeedbacks;
import com.sun.mail.util.LineOutputStream;

@Repository
public class ExitInterviewDaoImpl implements ExitInterviewDao {

	@Autowired
	private SessionFactory sessionfactory;

	@Override
	@Transactional
	public List<JSONObject> getHrQuestions(int roleid,int stageid) {
		List<JSONObject> questionslist=new ArrayList<JSONObject>();
		int count=1;
	try{
		Session session=sessionfactory.getCurrentSession();
       String	hql="from MstQuestions q where q.roleId=:roleid and q.stageId=:stageid";
        Query query=session.createQuery(hql);
	    query.setParameter("roleid", roleid);
	    query.setParameter("stageid", stageid);
	    List<MstQuestions> hrqueslist=query.list();
	    for(MstQuestions question:hrqueslist)
	    {
	        JSONObject jsonObject=new JSONObject();
	        jsonObject.put("question", question.getQuestionText());
	        jsonObject.put("qtype", question.getQuestionType());
	        jsonObject.put("qid",question.getQid());
	        jsonObject.put("value", "");
	      
	    	questionslist.add(jsonObject);
	    	count=count+1;
	    }
	  }
		
		catch (Exception e) {
			e.printStackTrace();
		}
		
	return questionslist;
	}

	@Override
	@Transactional
	public JSONObject inserthrfeedback(TblFeedbacks feedbackbean) {
		JSONObject feedbackhr=new JSONObject();
		
		try {
			
			Session session=sessionfactory.getCurrentSession();
		    session.save(feedbackbean);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return feedbackhr;
	
}

	@Override
	@Transactional
	public List<JSONObject> getEmpQuestions(int stageid) {
		List<JSONObject> questionsemp=new ArrayList<JSONObject>();
		int count=1;
		try{
		Session session=sessionfactory.getCurrentSession();
        String hql="from MstQuestions q where q.stageId=:stageid";
		Query query=session.createQuery(hql);
	    query.setParameter("stageid", stageid);
	    List<MstQuestions> hrqueslist=query.list();
	    for(MstQuestions question:hrqueslist)
	    {
	   JSONObject jsonObject=new JSONObject();
	    	jsonObject.put("question", question.getQuestionText());
	        jsonObject.put("qtype", question.getQuestionType());
	        jsonObject.put("qid",question.getQid());
	        jsonObject.put("value", "");
	        questionsemp.add(jsonObject);
	    	count=count+1;
	    }
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	    	return questionsemp;
	}
    @Override
	@Transactional
	public JSONObject getEmpFeedbackStatus(int resignationid) {
		JSONObject liststageid=new JSONObject();
		Session session=sessionfactory.getCurrentSession(); 
		JSONObject blankjson=new JSONObject();
		Set<JSONObject> listarray=new HashSet<JSONObject>();
		List<TblFeedbacks> empfeedback=null;
		JSONObject feedbackstatus=null;
		try {
			
			String hql="from TblFeedbacks a where a.tblUserResignation.resignationId=:resignationid";
			Query query=session.createQuery(hql)
					.setParameter("resignationid", resignationid);
			empfeedback=query.list();
			for(TblFeedbacks status:empfeedback)
			{
				feedbackstatus=new JSONObject();
//				feedbackstatus.put("empstatus", status.getStageId());
				if(status.getStageId()==3){
					feedbackstatus.put("statuslbl", "Employee Feedback Status");
				}else if(status.getStageId()==4){
					feedbackstatus.put("statuslbl", "Employee Rating Status");
				}
				
				if(status.getStageId()==3)
				{
					feedbackstatus.put("statustext", "Feedback Completed");
				}
				else{
					if(status.getStageId()!=4){
						feedbackstatus.put("statustext", "Feedback Pending");
					}
				}
				
				if(status.getStageId()==4)
				{
					feedbackstatus.put("statustext", "Rating Completed");
				}
			else{
					if(status.getStageId()!=3){
						feedbackstatus.put("statustext", "Rating Pending");
					}
				}
				
			}
			if(empfeedback.isEmpty()){
				blankjson.put("statustext", "feedback Pending");
				listarray.add(blankjson);
			}
			liststageid.put("statusemp", listarray);
			} catch (Exception e) {
			e.printStackTrace();
		}
		return liststageid;
	}
		}