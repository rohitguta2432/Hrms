package com.softage.hrms.dao.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.softage.hrms.dao.ExitInterviewDao;
import com.softage.hrms.model.MstQuestions;
import com.softage.hrms.model.TblFeedbacks;

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
	    String hql="from MstQuestions q where q.roleId=:roleid and q.stageId=:stageid";
	    
	    Query query=session.createQuery(hql);
	    query.setParameter("roleid", roleid);
	    query.setParameter("stageid", stageid);
	    
	    
	    List<MstQuestions> hrqueslist=query.list();
	    for(MstQuestions question:hrqueslist)
	    {
	    	
	    	JSONObject jsonObject=new JSONObject();
	    	/*jsonObject.put("sno", count);*/
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
			
		}
		
		return feedbackhr;
	}
	}
