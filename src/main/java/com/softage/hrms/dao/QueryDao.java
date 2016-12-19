package com.softage.hrms.dao;

import java.util.List;

import com.softage.hrms.model.MstDepartment;
import com.softage.hrms.model.TblExEmployeeQuery;

public interface QueryDao {
	public List<MstDepartment> getDepartmentList();
	public String save(TblExEmployeeQuery employeeQuery);
}
