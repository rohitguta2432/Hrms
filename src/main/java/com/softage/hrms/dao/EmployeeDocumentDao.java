package com.softage.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.model.TblUploadedPath;


public interface EmployeeDocumentDao {
	public List<MstUploadItem> getUploadItems(int deptId);
	public String save(TblUploadedPath tblUploadedPath);
	public MstUploadItem entityById(int id);
	public List<TblUploadedPath> getByEmpCode(String empcode);
	public TblUploadedPath getByResignId(int resignId,int itemId);
}
