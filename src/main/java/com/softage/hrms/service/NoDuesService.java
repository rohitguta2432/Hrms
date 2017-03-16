package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

public interface NoDuesService {

	public List<String> listrmacceptedempcode(String stageid,int departmentid,String officeid, int status);
public List<String> listemprmaccepted(int departmentid,int status);
	
	public List<JSONObject> listassetsdetails(int departmentid);
	/* public JSONObject listUserStatus(int resignation); */

	public JSONObject submitNoduesclearence(TblNoDuesClearence clearencebeanstatus);

	public JSONObject submitnoduesassets(TblAssetsManagement accountassertsbeaan);
    public void updateNoduesClearence(TblNoDuesClearence tblNoDuesClearence);
    TblNoDuesClearence getByResignationId(int resignationId,int departmentid);
	// Service created by Arpan to find the No Dues Pending Status
	public JSONObject getNoDuesPendingStatus(int resignationID);
}
