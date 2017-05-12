package com.softage.hrms.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.SearchDao;
import com.softage.hrms.model.MstReason;
import com.softage.hrms.model.MstResignationStatus;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.SearchService;
@Service
public class SearchServiceImpl implements SearchService {
	@Autowired
	private SearchDao searchDao;

	@Override
	public JSONObject getDetails(String emp) {
		
		ArrayList<JSONObject> jsonArray=new ArrayList<JSONObject>();
		JSONObject json=new JSONObject();
		JSONObject jsob=searchDao.getDetailsDao(emp);
		List l=(List) jsob.get("val");
		Iterator i=l.iterator();
		while(i.hasNext()){
			Object[] obj=(Object[])i.next();
			JSONObject jsonsearch=new JSONObject();
			String empcode=(String)obj[0];
			String spokecode=(String)obj[1];
			Date resignationDate=(Date)obj[2];
			Date releivingDate=(Date)obj[3];
			Date hrApprovalDate=(Date)obj[4];
			Date rmApprovalDate=(Date)obj[5];
			String mstReason=(String)obj[6];
			String status=(String)obj[7];
			DateFormat df=new SimpleDateFormat("yyyy/MM/dd");
			String resgndate=df.format(resignationDate);
			String reldate=df.format(releivingDate);
			String hrappdate=df.format(hrApprovalDate);
			String rmappdate=df.format(rmApprovalDate);
			jsonsearch.put("empcode", empcode);
			jsonsearch.put("spokecode", spokecode);
			jsonsearch.put("resignationDate", resgndate);
			jsonsearch.put("releivingDate", reldate);
			jsonsearch.put("hrApprovalDate", hrappdate);
			jsonsearch.put("rmApprovalDate", rmappdate);
			jsonsearch.put("mstReason", mstReason);
			jsonsearch.put("status", status);
			jsonArray.add(jsonsearch);
			}
		json.put("jsonArray", jsonArray);
		return json;
	}
	



}
