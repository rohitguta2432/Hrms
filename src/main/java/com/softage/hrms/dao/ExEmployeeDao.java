package com.softage.hrms.dao;

import java.util.List;

import com.softage.hrms.model.TblUserResignation;

public interface ExEmployeeDao {
	
	public List<TblUserResignation> getExEmployeeBean(TblUserResignation resignBean);
	public TblUserResignation getExEmployeeBeanByEmailID(String email, int status);
}
