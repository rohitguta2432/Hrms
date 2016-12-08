package com.softage.hrms.model;
// Generated Dec 6, 2016 10:45:57 AM by Hibernate Tools 5.2.0.Beta1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * MstResignationStatus generated by hbm2java
 */
@Entity
@Table(name = "mst_resignation_status")
public class MstResignationStatus implements java.io.Serializable {

	private Integer statusId;
	private String status;
	private Date createdOn;
	private Set<TblUserResignation> tblUserResignations = new HashSet<TblUserResignation>(0);

	public MstResignationStatus() {
	}

	public MstResignationStatus(String status, Date createdOn, Set tblUserResignations) {
		this.status = status;
		this.createdOn = createdOn;
		this.tblUserResignations = tblUserResignations;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "status_id", unique = true, nullable = false)
	public Integer getStatusId() {
		return this.statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	@Column(name = "status", length = 30)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mstResignationStatus")
	public Set<TblUserResignation> getTblUserResignations() {
		return this.tblUserResignations;
	}

	public void setTblUserResignations(Set<TblUserResignation> tblUserResignations) {
		this.tblUserResignations = tblUserResignations;
	}

}
