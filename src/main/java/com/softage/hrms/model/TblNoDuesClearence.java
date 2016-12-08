package com.softage.hrms.model;
// Generated Dec 6, 2016 10:45:57 AM by Hibernate Tools 5.2.0.Beta1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * TblNoDuesClearence generated by hbm2java
 */
@Entity
@Table(name = "tbl_no_dues_clearence")
public class TblNoDuesClearence implements java.io.Serializable {

	private Integer clearenceId;
	private TblAssetsManagement tblAssetsManagement;
	private Integer departmentFinalStatus;
	private String comments;

	public TblNoDuesClearence() {
	}

	public TblNoDuesClearence(TblAssetsManagement tblAssetsManagement, Integer departmentFinalStatus, String comments) {
		this.tblAssetsManagement = tblAssetsManagement;
		this.departmentFinalStatus = departmentFinalStatus;
		this.comments = comments;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "clearence_id", unique = true, nullable = false)
	public Integer getClearenceId() {
		return this.clearenceId;
	}

	public void setClearenceId(Integer clearenceId) {
		this.clearenceId = clearenceId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resignation_id")
	public TblAssetsManagement getTblAssetsManagement() {
		return this.tblAssetsManagement;
	}

	public void setTblAssetsManagement(TblAssetsManagement tblAssetsManagement) {
		this.tblAssetsManagement = tblAssetsManagement;
	}

	@Column(name = "department_final_status")
	public Integer getDepartmentFinalStatus() {
		return this.departmentFinalStatus;
	}

	public void setDepartmentFinalStatus(Integer departmentFinalStatus) {
		this.departmentFinalStatus = departmentFinalStatus;
	}

	@Column(name = "comments", length = 45)
	public String getComments() {
		return this.comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

}