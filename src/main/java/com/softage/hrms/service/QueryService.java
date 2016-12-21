package com.softage.hrms.service;

import java.util.List;

import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmpCommunication;
import com.softage.hrms.model.TblExEmployeeQuery;

public interface QueryService {
	
	public List<MstDepartment> getDepartmentList();
	public int save(TblExEmployeeQuery employeeQuery);
	public List<TblExEmployeeQuery> getQueryList(String empcode);
	public TblExEmployeeQuery getById(int id);
	public String save(TblExEmpCommunication employeeCommunicatio);
	public List<TblExEmpCommunication> getByQueryId(int queryId);
	public MstDepartment getDepartmentById(int Id);

}
