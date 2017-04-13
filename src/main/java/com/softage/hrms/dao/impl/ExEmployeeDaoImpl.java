package com.softage.hrms.dao.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.softage.hrms.dao.ExEmployeeDao;
import com.softage.hrms.model.TblUserResignation;

@Repository
public class ExEmployeeDaoImpl implements ExEmployeeDao {
	
	@Autowired 
	private SessionFactory sessionFactory;

	@Override
	@Transactional
	public List<TblUserResignation> getExEmployeeBean(TblUserResignation resignBean) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public TblUserResignation getExEmployeeBeanByEmailID(String email, int status) {
		Session session=sessionFactory.getCurrentSession();
		Criteria criteria=session.createCriteria(TblUserResignation.class);
		Criterion criterion=Restrictions.eq("exEmpEmail",email);
		Criterion criterion2=Restrictions.gt("mstResignationStatus.statusId",status);
		criteria.add(criterion);
		criteria.add(criterion2);
		TblUserResignation resignationModel=(TblUserResignation)criteria.uniqueResult();
		return resignationModel;
	}

}
