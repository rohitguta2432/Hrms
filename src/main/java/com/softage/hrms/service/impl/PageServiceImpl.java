package com.softage.hrms.service.impl;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.PageDao;
import com.softage.hrms.service.PageService;

@Service
public class PageServiceImpl implements PageService {
	
	@Autowired
	private PageDao pagedao;

	@Override
	public JSONObject getPagesLink(int id) {
		return pagedao.getPagesLinkDao(id);
	}

}
