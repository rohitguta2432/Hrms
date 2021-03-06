package com.softage.hrms.dao;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Repository;

import com.softage.hrms.model.FtpDetails;
import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblUploadedPath;


public interface EmployeeDocumentDao {
	public List<MstUploadItem> getUploadItems(int deptId);
	public String save(TblUploadedPath tblUploadedPath);
	public MstUploadItem entityById(int id);
	public List<TblUploadedPath> getByEmpCode(String empcode);
	public TblUploadedPath getByResignId(int resignId,int itemId);
	public JSONObject getNotUploadedDocumentsById(int resignID);
	public String update(TblUploadedPath tblUploadedPath);
	public TblUploadedPath findByEmpCodeAndItemId(String empcode,int itemid);
	public List<FtpDetails> getFtpDetails();
}
