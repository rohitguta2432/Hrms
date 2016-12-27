package com.softage.hrms.dao;

import java.util.List;

import com.softage.hrms.model.TblUserResignation;

public interface ExExmployeeDao {
	
	public List<TblUserResignation> getExEmployeeBean(TblUserResignation resignBean);
}
