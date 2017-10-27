package com.lbc.entity;

/**
 * 目录结构
 * @author neu-lbc
 * @since 2017-10-19
 *
 */
public class Catalog {
	
	private Integer id;
	/** 目录名 */
	private String catalog_name;
	/** 在所有文档中出现次数 */
	private Integer times;
	/** 归类 */
	private String affiliation;
	/** 张月整理的归类*/
	private String affiliationTouch;
	
	public String getAffiliationTouch() {
		return affiliationTouch;
	}

	public void setAffiliationTouch(String affiliationTouch) {
		this.affiliationTouch = affiliationTouch;
	}

	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCatalog_name() {
		return catalog_name;
	}
	
	public void setCatalog_name(String catalog_name) {
		this.catalog_name = catalog_name;
	}
	
	public Integer getTimes() {
		return times;
	}
	
	public void setTimes(Integer times) {
		this.times = times;
	}
	
	public String getAffiliation() {
		return affiliation;
	}
	
	public void setAffiliation(String affiliation) {
		this.affiliation = affiliation;
	}

}
