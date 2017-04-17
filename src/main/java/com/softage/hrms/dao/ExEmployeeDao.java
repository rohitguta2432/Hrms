package com.softage.hrms.dao;

import java.util.List;

import com.softage.hrms.model.ApplicationLink;
import com.softage.hrms.model.TblResetPassword;
import com.softage.hrms.model.TblUserResignation;

public interface ExEmployeeDao {
	
	public List<TblUserResignation> getExEmployeeBean(TblUserResignation resignBean);
	public TblUserResignation getExEmployeeBeanByEmailID(String email, int status);
	public ApplicationLink getAppUrlLink();
	public String saveResetPasswordModel(TblResetPassword resetPassword);
	public TblResetPassword getUUID(String UUID);
	public TblUserResignation UpdatePassword(String UserEmail); 
}
