package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;


import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

public interface NoDuesDao {

	
	public List<String> getrmacceptedempcode(String stageid,int departmentid,String officeid,int status);
	public List<JSONObject> getassetsdetails(int departmentid);
    public JSONObject insertnoduesassetsdetails(TblAssetsManagement accountbean);
	/*public JSONObject getUserStatus(int resignation); */
    public JSONObject insertnoduesclearence(TblNoDuesClearence clearencebean);
    public TblNoDuesClearence updateNoduesClearence(TblNoDuesClearence tblNoDuesClearence);
    TblNoDuesClearence findByResignationId(int resignationId);
	//Dao created by Arpna to find the No Dues Pending Status
    public JSONObject getNoDuesPendingStatus(int resignationID);
}
