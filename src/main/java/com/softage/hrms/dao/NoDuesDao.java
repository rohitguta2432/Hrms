package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;


import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

public interface NoDuesDao {

	
	public List<String> getrmacceptedempcode();
	
	public List<JSONObject> getassetsdetails(int departmentid);
	
	public JSONObject insertnoduesassetsdetails(TblAssetsManagement accountbean);
	
	/*public JSONObject inserthrassertsdetails(TblAssetsManagement hrbean);*/
    
    public JSONObject insertnoduesclearence(TblNoDuesClearence clearencebean);
	
}
