package com.softage.hrms.dao;

import org.json.simple.JSONObject;

public interface PageDao {
	
	public JSONObject getPagesLinkDao(int role_id);
	public JSONObject getPagesBasedOnRoleId(String empcode,String hitdatetime,int roleid);

}
