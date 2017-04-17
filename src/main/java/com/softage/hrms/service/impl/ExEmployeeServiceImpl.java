package com.softage.hrms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.ExEmployeeDao;
import com.softage.hrms.model.ApplicationLink;
import com.softage.hrms.model.TblResetPassword;
import com.softage.hrms.model.TblUserResignation;
import com.softage.hrms.service.ExEmployeeService;

@Service
public class ExEmployeeServiceImpl implements ExEmployeeService {
	
	@Autowired
	private ExEmployeeDao exemployeedao;
	

	
	@Override
	public boolean emailExists(String email, int status) {
		TblUserResignation resingation=exemployeedao.getExEmployeeBeanByEmailID(email, status);
		if(resingation!=null){
			return true;
		}else{
		return false;
		}
	}

	@Override
	public String getAppUrlLink() {
		ApplicationLink appLink=exemployeedao.getAppUrlLink();
		String appURL=appLink.getApplication_url();
		return appURL;
	}

	@Override
	public String saveResetPwdModel(TblResetPassword resetPwd) {
		return exemployeedao.saveResetPasswordModel(resetPwd);
	}

	@Override
	public boolean checkID(String uniqueID) {
		TblResetPassword uniqueid=exemployeedao.getUUID(uniqueID);
		
		if(uniqueid!=null){
			return true;
		}else{
		return false;
		}
	}

	@Override
	public String saveUpdatedPassword(String password,String uuid) {
		TblResetPassword UUid=exemployeedao.getUUID(uuid);
		String msg=null;
		if(UUid!=null){
			String UserEmailId=UUid.getUser_email();
			TblUserResignation updatedPassword=exemployeedao.UpdatePassword(UserEmailId,password);
			msg="success";	
		}
		return msg;
	}

}
