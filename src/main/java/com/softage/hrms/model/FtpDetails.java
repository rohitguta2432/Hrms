package com.softage.hrms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_ftpdetails")
public class FtpDetails {
	
	private Integer ftpid;
	private String ftphost;
	private String ftpusername;
	private String password;
	
	@Id
	@GeneratedValue
	@Column(name="ftp_id")
	public Integer getFtpid() {
		return ftpid;
	}
	public void setFtpid(Integer ftpid) {
		this.ftpid = ftpid;
	}
	
	@Column(name="ftp_host")
	public String getFtphost() {
		return ftphost;
	}
	public void setFtphost(String ftphost) {
		this.ftphost = ftphost;
	}
	
	@Column(name="ftp_username")
	public String getFtpusername() {
		return ftpusername;
	}
	public void setFtpusername(String ftpusername) {
		this.ftpusername = ftpusername;
	}
	@Column(name="ftp_password")
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
