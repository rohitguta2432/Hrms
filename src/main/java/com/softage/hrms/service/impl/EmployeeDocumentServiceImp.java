package com.softage.hrms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.EmployeeDocumentDao;
import com.softage.hrms.model.MstUploadItem;
import com.softage.hrms.service.EmployeeDocumentService;

@Service
public class EmployeeDocumentServiceImp implements EmployeeDocumentService {
	@Autowired
	private EmployeeDocumentDao employeeDocumentDao;

	@Override
	public List<MstUploadItem> getUploadItems(int deptId) {
           return employeeDocumentDao.getUploadItems(deptId);		

	}

}
