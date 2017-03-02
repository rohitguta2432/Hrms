package com.softage.hrms.service;

import java.util.List;

import org.json.simple.JSONObject;

import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblUploadedPath;
import com.softage.hrms.model.TblUserResignation;

public interface EmployeeDocumentService {
	
	public List<MstUploadItem> getUploadItems(int deptId);
	public String save(TblUploadedPath tblUploadedPath);
	public MstUploadItem entityById(int id);
	public List<TblUploadedPath> getByEmpCode(String empcode);
	public TblUploadedPath getByResignId(int resignId,int itemId);
	public JSONObject getNotUploadedDocumentsById(int resignationID);
	public String update(TblUploadedPath tblUploadedPath);
	public TblUploadedPath findByEmpCodeAndItemId(String empcode,int itemid);
}
