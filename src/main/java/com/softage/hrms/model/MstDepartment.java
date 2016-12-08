package com.softage.hrms.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="mst_department")
public class MstDepartment implements java.io.Serializable {

	private int depart_id;
	private String department_name;
	private Date created_on;
	private Set<MstAssests> mstassets = new HashSet<MstAssests>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "mstdepartment")
	public Set<MstAssests> getMstassets() {
		return mstassets;
	}
	public void setMstassets(Set<MstAssests> mstassets) {
		this.mstassets = mstassets;
	}
	public MstDepartment()
	{
		
	}
	public MstDepartment(int depart_id)
	{
		this.depart_id=depart_id;
	}
	public MstDepartment(int depart_id,String department_name,Date created_on)
	{
		this.depart_id=depart_id;
		this.department_name=department_name;
		this.created_on=created_on;
	}
	
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name="depart_id",unique=true,nullable=false)
	public int getDepart_id() {
		return depart_id;
	}

	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
@Column(name="department_name",length=45)
		public String getDepartment_name() {
		return department_name;
	}

	public void setDepartment_name(String department_name) {
		this.department_name = department_name;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_on")
	public Date getCreated_on() {
		return created_on;
	}

	public void setCreated_on(Date created_on) {
		this.created_on = created_on;
	}

	
}