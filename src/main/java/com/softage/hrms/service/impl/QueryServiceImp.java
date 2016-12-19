package com.softage.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.QueryDao;
import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmployeeQuery;
import com.softage.hrms.service.QueryService;


@Service
public class QueryServiceImp implements QueryService{
	
	@Autowired
	private QueryDao querydao;

	@Override
	public List<MstDepartment> getDepartmentList() {
		// TODO Auto-generated method stub
		return querydao.getDepartmentList();
	}

	@Override
	public String save(TblExEmployeeQuery employeeQuery) {
		// TODO Auto-generated method stub
		return querydao.save(employeeQuery);
	}

}
