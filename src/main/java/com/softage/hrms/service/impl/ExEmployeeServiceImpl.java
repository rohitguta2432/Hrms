package com.softage.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.ExEmployeeDao;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ExEmployeeService;

@Service
public class ExEmployeeServiceImpl implements ExEmployeeService {
	
	@Autowired
	private ExEmployeeDao exemployeedao;
	
	@Override
	public boolean emailExists(String email, int status) {
		TblUserResignation resingation=exemployeedao.getExEmployeeBeanByEmailID(email, status);
		if(resingation!=null){
			return true;
		}else{
		return false;
		}
	}

}
