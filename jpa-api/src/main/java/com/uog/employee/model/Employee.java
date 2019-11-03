package com.uog.employee.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBLEMPLOYEE")

public class Employee {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long EMPLOYEE_ID;
	
	@Column(name = "EMPLOYEE_NAME")
	private String EMPLOYEE_NAME;
	
	@Column(name = "EMPLOYEE_AGE")
	private int EMPLOYEE_AGE;

	@Column(name = "ISACTIVE")
	private char ISACTIVE;
	
	
	public Long getEMPLOYEE_ID() {
		return EMPLOYEE_ID;
	}

	public void setEMPLOYEE_ID(Long eMPLOYEE_ID) {
		EMPLOYEE_ID = eMPLOYEE_ID;
	}

	public String getEMPLOYEE_NAME() {
		return EMPLOYEE_NAME;
	}

	public void setEMPLOYEE_NAME(String eMPLOYEE_NAME) {
		EMPLOYEE_NAME = eMPLOYEE_NAME;
	}

	public int getEMPLOYEE_AGE() {
		return EMPLOYEE_AGE;
	}

	public void setEMPLOYEE_AGE(int eMPLOYEE_AGE) {
		EMPLOYEE_AGE = eMPLOYEE_AGE;
	}

	public char getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(char iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}
		
}

















