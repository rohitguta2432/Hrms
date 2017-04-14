package com.softage.hrms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_application_link")
public class ApplicationLink {
	
	private Integer app_id;
	private String application_url;
	private Integer status;
	@Id
	@GeneratedValue
	@Column(name="app_id")
	public Integer getApp_id() {
		return app_id;
	}
	public void setApp_id(Integer app_id) {
		this.app_id = app_id;
	}
	@Basic
	@Column(name="app_url")
	public String getApplication_url() {
		return application_url;
	}
	public void setApplication_url(String application_url) {
		this.application_url = application_url;
	}
	@Basic
	@Column(name="app_status")
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
