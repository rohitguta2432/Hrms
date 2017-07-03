package com.softage.hrms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="tbl_restConfig")
public class RestServiceConfig {
	
	private Long id;
	private String serviceUrl;
	private String projectConfig;
	
	@Id
	@GeneratedValue
	@Column(name="id")
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	
	@NotNull
	@Column(name="serviceUrl")
	public String getServiceUrl() {
		return serviceUrl;
	}
	public void setServiceUrl(String serviceUrl) {
		this.serviceUrl = serviceUrl;
	}
	
	@NotNull
	@Column(name="projectConfig")
	public String getProjectConfig() {
		return projectConfig;
	}
	public void setProjectConfig(String projectConfig) {
		this.projectConfig = projectConfig;
	}		
}
