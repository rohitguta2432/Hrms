package com.softage.hrms.service;

import com.softage.hrms.model.TblResetPassword;

public interface ExEmployeeService {
	
	public boolean emailExists(String email,int status);
	public String getAppUrlLink();
	public String saveResetPwdModel(TblResetPassword resetPwd);

}
