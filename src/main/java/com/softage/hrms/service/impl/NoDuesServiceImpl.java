package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.NoDuesDao;
import com.softage.hrms.service.NoDuesService;


@Service
public class NoDuesServiceImpl  implements NoDuesService{


@Autowired
private NoDuesDao noduesdao;
	
	@Override
	public List<String> listrmacceptedempcode() {
		
		return noduesdao.getrmacceptedempcode();
	}

	@Override
	public JSONObject listassetsdetails() {
		
		return noduesdao.getassetsdetails();
	}

}
