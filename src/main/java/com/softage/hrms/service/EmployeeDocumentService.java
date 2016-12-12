package com.softage.hrms.service;

import java.util.List;

import com.softage.hrms.model.MstUploadItem;

public interface EmployeeDocumentService {
	
	public List<MstUploadItem> getUploadItems(int deptId);

}
