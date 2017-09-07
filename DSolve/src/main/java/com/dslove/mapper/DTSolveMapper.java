package com.dslove.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.dslove.pojo.Disease;
import com.dslove.pojo.PropertyName;
import com.dslove.pojo.PropertyValue;

public interface DTSolveMapper {

	@Select("select * from disease where name = #{name}")
	public List<Disease> getDiseaseByName(String name);
	
	@Insert("insert into disease (name, summary, remark1, remark2) values (#{name}, #{summary}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addDisease(Disease bean);
	
	@Select("select * from property_name where (parent_id = #{parent_id} or parent_id is null) and property_name = #{property_name}")
	public List<PropertyName> getPropertyNameByExample(PropertyName example);
	
	@Insert("insert into property_name (parent_id, property_name, remark1, remark2) values (#{parent_id}, #{property_name}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addPropertyName(PropertyName bean);
	
	@Insert("insert into property_value (property_id, disease_id, value, remark1, remark2) values (#{property_id}, #{disease_id}, #{value}, #{remark1}, #{remark2})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addPropertyValue(PropertyValue bean);
	
}
