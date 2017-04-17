package com.softage.hrms.service;

import com.softage.hrms.model.TblResetPassword;
import com.softage.hrms.model.TblUserResignation;

public interface ExEmployeeService {
	
	public boolean emailExists(String email,int status);
	public String getAppUrlLink();
	public String saveResetPwdModel(TblResetPassword resetPwd);
	public boolean checkID(String uniqueID);
   public String  saveUpdatedPassword(String password,String uuid);
}
