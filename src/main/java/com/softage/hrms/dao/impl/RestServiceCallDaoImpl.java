package com.softage.hrms.dao.impl;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.RestServiceCallDao;
import com.softage.hrms.model.RestServiceConfig;

@Repository
@Transactional
public class RestServiceCallDaoImpl implements RestServiceCallDao {

	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public String getServiceDetails() {

		String serviceServer = null;
		Long serviceId = (long) 1;
		try{
			Session session = sessionFactory.getCurrentSession();
			Criteria criteria = session.createCriteria(RestServiceConfig.class);
			criteria.add(Restrictions.eq("id", serviceId));
			RestServiceConfig serviceInfo = (RestServiceConfig) criteria.uniqueResult();
			serviceServer = serviceInfo.getServiceUrl();
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return serviceServer;
	}
}
