package com.softage.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.RestServiceCallDao;
import com.softage.hrms.service.RestServiceCall;

@Service
public class RestServiceCallImpl implements RestServiceCall{

	@Autowired
	private RestServiceCallDao restDao;
	@Override
	public String getServiceDetails() {
		
		return restDao.getServiceDetails();
	}
	
	

}
