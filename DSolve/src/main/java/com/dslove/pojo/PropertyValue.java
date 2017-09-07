package com.dslove.pojo;

public class PropertyValue {
	
	private Integer id;

	private Integer property_id;
	
	private Integer disease_id;
	
	private String value;
	
	private String remark1;
	
	private String remark2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProperty_id() {
		return property_id;
	}

	public void setProperty_id(Integer property_id) {
		this.property_id = property_id;
	}

	public Integer getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(Integer disease_id) {
		this.disease_id = disease_id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getRemark1() {
		return remark1;
	}

	public void setRemark1(String remark1) {
		this.remark1 = remark1;
	}

	public String getRemark2() {
		return remark2;
	}

	public void setRemark2(String remark2) {
		this.remark2 = remark2;
	}
	
}
