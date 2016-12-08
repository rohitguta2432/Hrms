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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TblExEmployeeQuery generated by hbm2java
 */
@Entity
@Table(name = "tbl_ex_employee_query")
public class TblExEmployeeQuery implements java.io.Serializable {

	private Integer queryId;
	private TblUserResignation tblUserResignation;
	private String exEmpCode;
	private Integer departmentId;
	private String queryText;
	private Date createdOn;
	private String createdBy;
	private Set<TblExEmpCommunication> tblExEmpCommunications = new HashSet<TblExEmpCommunication>(0);

	public TblExEmployeeQuery() {
	}

	public TblExEmployeeQuery(TblUserResignation tblUserResignation, String exEmpCode, Integer departmentId,
			String queryText, Date createdOn, String createdBy, Set tblExEmpCommunications) {
		this.tblUserResignation = tblUserResignation;
		this.exEmpCode = exEmpCode;
		this.departmentId = departmentId;
		this.queryText = queryText;
		this.createdOn = createdOn;
		this.createdBy = createdBy;
		this.tblExEmpCommunications = tblExEmpCommunications;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "query_id", unique = true, nullable = false)
	public Integer getQueryId() {
		return this.queryId;
	}

	public void setQueryId(Integer queryId) {
		this.queryId = queryId;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ex_resign_id")
	public TblUserResignation getTblUserResignation() {
		return this.tblUserResignation;
	}

	public void setTblUserResignation(TblUserResignation tblUserResignation) {
		this.tblUserResignation = tblUserResignation;
	}

	@Column(name = "ex_emp_code", length = 15)
	public String getExEmpCode() {
		return this.exEmpCode;
	}

	public void setExEmpCode(String exEmpCode) {
		this.exEmpCode = exEmpCode;
	}

	@Column(name = "department_id")
	public Integer getDepartmentId() {
		return this.departmentId;
	}

	public void setDepartmentId(Integer departmentId) {
		this.departmentId = departmentId;
	}

	@Column(name = "query_text", length = 500)
	public String getQueryText() {
		return this.queryText;
	}

	public void setQueryText(String queryText) {
		this.queryText = queryText;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_on", length = 19)
	public Date getCreatedOn() {
		return this.createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Column(name = "created_by", length = 15)
	public String getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tblExEmployeeQuery")
	public Set<TblExEmpCommunication> getTblExEmpCommunications() {
		return this.tblExEmpCommunications;
	}

	public void setTblExEmpCommunications(Set<TblExEmpCommunication> tblExEmpCommunications) {
		this.tblExEmpCommunications = tblExEmpCommunications;
	}

}
