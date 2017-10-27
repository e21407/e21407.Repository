package com.lbc.entity;

/**
 * 疾病症状信息实体类
 * @author Liubaichuan
 * @since 2017-10-18
 *
 */
public class Symptom {
	
	private Integer id;
	
	private Integer disease_id;
	
	private String disease_name;
	/** 症状词 */
	private String symptom;
	/** 症状所在句子 */
	private String full_text;
	/** 1为刚创建 */
	private Integer status;
	
	private String remark1;
	
	private String remark2;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDisease_id() {
		return disease_id;
	}

	public void setDisease_id(Integer disease_id) {
		this.disease_id = disease_id;
	}

	public String getSymptom() {
		return symptom;
	}

	public void setSymptom(String symptom) {
		this.symptom = symptom;
	}

	public String getFull_text() {
		return full_text;
	}

	public void setFull_text(String full_text) {
		this.full_text = full_text;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
	}
	
	
}
