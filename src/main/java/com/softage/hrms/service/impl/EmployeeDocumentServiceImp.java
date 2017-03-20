package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.model.FtpDetails;
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

	@Override
	public String update(TblUploadedPath tblUploadedPath) {
		// TODO Auto-generated method stub
		return employeeDocumentDao.update(tblUploadedPath);
	}

	@Override
	public TblUploadedPath findByEmpCodeAndItemId(String empcode, int itemid) {
		return employeeDocumentDao.findByEmpCodeAndItemId(empcode, itemid);
	}

	@Override
	public JSONObject getFtpDetails() {
		JSONObject ftpJson=new JSONObject();
		List<FtpDetails> ftp_details=employeeDocumentDao.getFtpDetails();
		FtpDetails ftp=ftp_details.get(0);
		String ftphost=ftp.getFtphost();
		String username=ftp.getFtpusername();
		String pwd=ftp.getPassword();
		ftpJson.put("host", ftphost);
		ftpJson.put("username", username);
		ftpJson.put("password", pwd);
		return ftpJson;
	}

}
