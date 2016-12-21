package com.softage.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.QueryDao;
import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmpCommunication;
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
	public int save(TblExEmployeeQuery employeeQuery) {
		// TODO Auto-generated method stub
		return querydao.save(employeeQuery);
	}

	@Override
	public List<TblExEmployeeQuery> getQueryList(String empcode) {
		// TODO Auto-generated method stub
		return querydao.getQueryList(empcode);
	}

	@Override
	public TblExEmployeeQuery getById(int id) {
		// TODO Auto-generated method stub
		return querydao.getById(id);
	}

	@Override
	public String save(TblExEmpCommunication employeeCommunicatio) {
		// TODO Auto-generated method stub
		return querydao.save(employeeCommunicatio);
	}

	@Override
	public List<TblExEmpCommunication> getByQueryId(int queryId) {
		// TODO Auto-generated method stub
		return querydao.getByQueryId(queryId);
	}

	@Override
	public MstDepartment getDepartmentById(int Id) {
		// TODO Auto-generated method stub
		return  querydao.getDepartmentById(Id);
	}

}
