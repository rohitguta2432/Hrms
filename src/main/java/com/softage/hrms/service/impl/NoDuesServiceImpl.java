package com.softage.hrms.service.impl;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.softage.hrms.dao.NoDuesDao;

import com.softage.hrms.model.TblAssetsManagement;
import com.softage.hrms.model.TblNoDuesClearence;
import com.softage.hrms.service.NoDuesService;

@Service
public class NoDuesServiceImpl implements NoDuesService {

	@Autowired
	private NoDuesDao noduesdao;

	@Override
	public List<String> listrmacceptedempcode(String stageid,int departmentid,String officeid, int status) {

		return noduesdao.getrmacceptedempcode(stageid,departmentid,officeid, status);
	}

	@Override
	public List<JSONObject> listassetsdetails(int departmentid) {

		return noduesdao.getassetsdetails(departmentid);
	}

	@Override
	public JSONObject submitnoduesassets(TblAssetsManagement accountassertsbeaan) {

		return noduesdao.insertnoduesassetsdetails(accountassertsbeaan);
	}

	@Override
	public JSONObject submitNoduesclearence(TblNoDuesClearence clearencebeanstatus) {

		return noduesdao.insertnoduesclearence(clearencebeanstatus);
	}

	// Service to find the pending No Dues Status by Arpan
	@Override
	public JSONObject getNoDuesPendingStatus(int resignationID) {
		return noduesdao.getNoDuesPendingStatus(resignationID);
	}

	@Override
	public void updateNoduesClearence(TblNoDuesClearence tblNoDuesClearence) {
		noduesdao.updateNoduesClearence(tblNoDuesClearence);
		
	}

	@Override
	public TblNoDuesClearence getByResignationId(int resignationId,int departmentid) {
	return noduesdao.findByResignationId(resignationId,departmentid);
	}

	@Override
	public List<String> listemprmaccepted(int departmentid, int status) {
		
		return noduesdao.getresignemplist(departmentid, status);
	}

	/*@Override
	public JSONObject listUserStatus(int resignation) {
		
		
		
        return  noduesdao.getUserStatus(resignation);
	}
*/
}
