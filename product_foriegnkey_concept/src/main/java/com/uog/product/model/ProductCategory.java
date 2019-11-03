package com.uog.product.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBLPRODUCTCATEGORY")

public class ProductCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long PRODUCTCATEGORY_ID;
	
	@Column(name = "PRODUCTCATEGORY_NAME")
	private String PRODUCTCATEGORY_NAME;

	@Column(name = "PRODUCTCATEGORY_DESC")
	private String PRODUCTCATEGORY_DESC;
	
	@Column(name = "ISACTIVE")
	private char ISACTIVE;
	
	@Column(name = "MODIFIED_BY")
	private Long MODIFIED_BY;

	@Column(name = "MODIFIED_WHEN")
	private String MODIFIED_WHEN;

	@Column(name = "MODIFIED_WORKSTATION")
	private String MODIFIED_WORKSTATION;

	public long getPRODUCTCATEGORY_ID() {
		return PRODUCTCATEGORY_ID;
	}

	public void setPRODUCTCATEGORY_ID(long pRODUCTCATEGORY_ID) {
		PRODUCTCATEGORY_ID = pRODUCTCATEGORY_ID;
	}

	public String getPRODUCTCATEGORY_NAME() {
		return PRODUCTCATEGORY_NAME;
	}

	public void setPRODUCTCATEGORY_NAME(String pRODUCTCATEGORY_NAME) {
		PRODUCTCATEGORY_NAME = pRODUCTCATEGORY_NAME;
	}

	public String getPRODUCTCATEGORY_DESC() {
		return PRODUCTCATEGORY_DESC;
	}

	public void setPRODUCTCATEGORY_DESC(String pRODUCTCATEGORY_DESC) {
		PRODUCTCATEGORY_DESC = pRODUCTCATEGORY_DESC;
	}

	public char getISACTIVE() {
		return ISACTIVE;
	}

	public void setISACTIVE(char iSACTIVE) {
		ISACTIVE = iSACTIVE;
	}

	public Long getMODIFIED_BY() {
		return MODIFIED_BY;
	}

	public void setMODIFIED_BY(Long mODIFIED_BY) {
		MODIFIED_BY = mODIFIED_BY;
	}

	public String getMODIFIED_WHEN() {
		return MODIFIED_WHEN;
	}

	public void setMODIFIED_WHEN(String mODIFIED_WHEN) {
		MODIFIED_WHEN = mODIFIED_WHEN;
	}

	public String getMODIFIED_WORKSTATION() {
		return MODIFIED_WORKSTATION;
	}

	public void setMODIFIED_WORKSTATION(String mODIFIED_WORKSTATION) {
		MODIFIED_WORKSTATION = mODIFIED_WORKSTATION;
	}

	public static long getDatabaseTableID() {
		return (long) 3;
	}

}