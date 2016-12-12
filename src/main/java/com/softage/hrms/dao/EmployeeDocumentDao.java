package com.softage.hrms.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.softage.hrms.model.MstUploadItem;


public interface EmployeeDocumentDao {
	public List<MstUploadItem> getUploadItems(int deptId);
}
