package com.softage.hrms.model;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbl_resetpassword")
public class TblResetPassword {
	
	private Integer key_id;
	private String userkey;
	private Integer key_status;
	private String user_email;
	@Id
	@GeneratedValue
	@Column(name="key_id",nullable=false,insertable=true,updatable=true)
	public Integer getKey_id() {
		return key_id;
	}
	public void setKey_id(Integer key_id) {
		this.key_id = key_id;
	}
	@Basic
	@Column(name="user_key")
	public String getUserkey() {
		return userkey;
	}
	public void setUserkey(String userkey) {
		this.userkey = userkey;
	}
	@Basic
	@Column(name="key_status")
	public Integer getKey_status() {
		return key_status;
	}
	public void setKey_status(Integer key_status) {
		this.key_status = key_status;
	}
	@Basic
	@Column(name="user_email")
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	
	
}
