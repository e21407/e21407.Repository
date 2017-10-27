package com.lbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.lbc.entity.Catalog;
import com.lbc.entity.CheckMethod;
import com.lbc.entity.Disease;
import com.lbc.entity.Symptom;
import com.lbc.entity.Therapy;

public interface ExtractMapper {
	
	@Select("select * from catalog where affiliation = #{affiliation}")
	public List<Catalog> getCatalogByAffiliation(String affiliation);
	
	@Select("select * from catalog where affiliation_touch = #{affiliationTouch}")
	public List<Catalog> getCatalogByAffiliationTouch(String affiliationTouch);
	
	@Select("select catalog.catalog_name from catalog where affiliation = #{affiliation}")
	public List<String> getCatalogNameByAffiliation(String affiliation);
	
	@Select("select catalog.catalog_name from catalog where affiliation_touch = #{affiliationTouch}")
	public List<String> getCatalogNameByAffiliationTouch(String affiliationTouch);
	
	@Insert("insert into symptom (id, disease_id, disease_name, symptom, full_text, status, remark1, remark2) values(#{id}, #{disease_id}, #{disease_name}, #{symptom}, #{full_text}, #{status}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addSymptom(Symptom bean);
	
	@Insert("insert into check_method (id, disease_id, disease_name, check_method, full_text, status, remark1, remark2) values(#{id}, #{disease_id}, #{disease_name}, #{check_method}, #{full_text}, #{status}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addCheck(CheckMethod bean);
	
	@Insert("insert into therapy (id, disease_id, disease_name, therapy, full_text, status, remark1, remark2) values(#{id}, #{disease_id}, #{disease_name}, #{therapy}, #{full_text}, #{status}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addTherapy(Therapy bean);
	
	@Select("select * from disease where name = #{name}")
	public List<Disease> getDiseaseByName(String name);
	
	

}
