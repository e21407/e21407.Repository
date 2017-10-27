package com.lbc.entity;

/**
 * 检查信息实体类
 * 
 * @author neu-lbc
 * @since 2017-10-23
 *
 */
public class CheckMethod {
	private Integer id;

	private Integer disease_id;

	private String disease_name;
	/** 检查手段 */
	private String check_method;
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

	public String getDisease_name() {
		return disease_name;
	}

	public void setDisease_name(String disease_name) {
		this.disease_name = disease_name;
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

	public String getCheck_method() {
		return check_method;
	}

	public void setCheck_method(String check_method) {
		this.check_method = check_method;
	}
	
}
