package com.softage.hrms.service;

import org.json.simple.JSONObject;

public interface PageService {
	
	public JSONObject getPagesLink(int id);
	public JSONObject getPagesBasedOnRoleId(String empcode,String hitDatetime,int roleid);

}
