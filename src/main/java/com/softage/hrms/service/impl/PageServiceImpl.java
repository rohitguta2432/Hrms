package com.softage.hrms.service.impl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.PageDao;
import com.softage.hrms.service.PageService;

@Service
public class PageServiceImpl implements PageService {
	
	@Autowired
	private PageDao pagedao;/*int id*/

	@Override
	public JSONObject getPagesLink() {
		return pagedao.getPagesLinkDao();
	}
	/*id*/
}
