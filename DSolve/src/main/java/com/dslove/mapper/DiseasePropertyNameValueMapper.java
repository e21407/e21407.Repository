package com.dslove.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.dslove.pojo.old.DiseaseName;
import com.dslove.pojo.old.DiseaseProperty;
import com.dslove.pojo.old.PropertyName;
import com.dslove.pojo.old.PropertyNameValue;
import com.dslove.pojo.old.PropertyValue;

public interface DiseasePropertyNameValueMapper {
	
	@Select("select * from disease_name where disease_name = #{name}")
	public List<DiseaseName> getDiseaseByName(String name);
	
	@Insert("insert into disease_name (disease_name) values (#{diseaseName})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addDiseaseName(DiseaseName bean);
	
	@Insert("insert into disease_property (disease_id, property_id) values (#{diseaseId}, #{propertyId})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addDiseaseProperty(DiseaseProperty bean);
	
	@Select("select * from property_name where property_name = #{name}")
	public List<PropertyName> getPropertyNameByName(String name);
	
	@Insert("insert into property_name (property_name) values (#{propertyName})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addPropertyName(PropertyName bean);
	
	@Insert("insert into property_name_value (name_id, value_id) values (#{nameId}, #{valueId})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addPropertyNameValue(PropertyNameValue bean);
	
	@Insert("insert into property_value (disease_id, property_value) values (#{diseaseId}, #{propertyValue})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addPropertyValue(PropertyValue bean);

}
