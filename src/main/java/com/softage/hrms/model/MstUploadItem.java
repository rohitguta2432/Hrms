package com.softage.hrms.model;
// Generated Dec 6, 2016 10:45:57 AM by Hibernate Tools 5.2.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MstUploadItem generated by hbm2java
 */
@Entity
@Table(name = "mst_upload_item")
public class MstUploadItem implements java.io.Serializable {

	private int uploadId;
	private String item;
	private int departmentId;
	private String createdBy;
	private Date createdOn;
	private Set<TblUploadedPath> tblUploadedPaths = new HashSet<TblUploadedPath>(0);

	public MstUploadItem() {
	}

	public MstUploadItem(int uploadId) {
		this.uploadId = uploadId;
	}

	public MstUploadItem(int uploadId, String item, int departmentId, String createdBy, Date createdOn,
			Set tblUploadedPaths) {
		this.uploadId = uploadId;
		this.item = item;
		this.departmentId = departmentId;
		this.createdBy = createdBy;
		this.createdOn = createdOn;
		this.tblUploadedPaths = tblUploadedPaths;
	}

	@Id

	@Column(name = "upload_id", unique = true, nullable = false)
	public int getUploadId() {
		return this.uploadId;
	}

	public void setUploadId(int uploadId) {
		this.uploadId = uploadId;
	}

	@Column(name = "item", length = 100)
	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	@Column(name = "department_id", length = 15)
	public int getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "created_by", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mstUploadItem")
	public Set<TblUploadedPath> getTblUploadedPaths() {
		return this.tblUploadedPaths;
	}

	public void setTblUploadedPaths(Set<TblUploadedPath> tblUploadedPaths) {
		this.tblUploadedPaths = tblUploadedPaths;
	}

}
