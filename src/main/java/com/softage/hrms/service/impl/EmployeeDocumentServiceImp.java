package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblUploadedPath;
import com.softage.hrms.service.EmployeeDocumentService;

@Service
public class EmployeeDocumentServiceImp implements EmployeeDocumentService {
	@Autowired
	private EmployeeDocumentDao employeeDocumentDao;

	@Override
	public List<MstUploadItem> getUploadItems(int deptId) {
           return employeeDocumentDao.getUploadItems(deptId);		

	}

	@Override
	public String save(TblUploadedPath tblUploadedPath) {
		// TODO Auto-generated method stub
		return employeeDocumentDao.save(tblUploadedPath);
	}

	@Override
	public MstUploadItem entityById(int id) {
		// TODO Auto-generated method stub
		return employeeDocumentDao.entityById(id);
	}

	@Override
	public List<TblUploadedPath> getByEmpCode(String empcode) {
		// TODO Auto-generated method stub
		return employeeDocumentDao.getByEmpCode(empcode);
	}

	@Override
	public TblUploadedPath getByResignId(int resignId,int itemId) {
		// TODO Auto-generated method stub
		return employeeDocumentDao.getByResignId(resignId,itemId);
	}

	@Override
	public JSONObject getNotUploadedDocumentsById(int resignationID) {
		return employeeDocumentDao.getNotUploadedDocumentsById(resignationID);
	}

}
