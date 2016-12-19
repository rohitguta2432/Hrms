package com.softage.hrms.service;

import java.util.List;

import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmployeeQuery;

public interface QueryService {
	
	public List<MstDepartment> getDepartmentList();
	public String save(TblExEmployeeQuery employeeQuery);

}
