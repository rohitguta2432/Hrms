package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;


import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;

public interface NoDuesService {

	public List<String> listrmacceptedempcode(int circleid,int status);
	
	public List<JSONObject> listassetsdetails(int departmentid);


/*public JSONObject listUserStatus(int resignation);*/

public JSONObject submitNoduesclearence(TblNoDuesClearence clearencebeanstatus);

JSONObject submitnoduesassets(TblAssetsManagement accountassertsbeaan);

// Service created by Arpan to find the No Dues Pending Status
public JSONObject getNoDuesPendingStatus(int resignationID);
}
