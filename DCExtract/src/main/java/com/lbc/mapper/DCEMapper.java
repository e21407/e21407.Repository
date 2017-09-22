package com.lbc.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.lbc.entity.Content;
import com.lbc.entity.Disease;
import com.lbc.entity.Title;

public interface DCEMapper {

	@Select("select * from disease where name = #{name}")
	public List<Disease> getDiseaseByName(String name);

	@Insert("insert into disease (name, summary, remark1) values (#{name}, #{summary}, #{remark1})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addDisease(Disease bean);

	@Select("select * from title where title_name = #{title_name}")
	public List<Title> getTitleByName(String title_name);

	@Insert("insert into title (id, title_name, remark1) values(#{id}, #{title_name}, #{remark1})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addTitle(Title bean);

	@Insert("insert into content (id, title_id, disease_id, value, status, remark1) values(#{id}, #{title_id}, #{disease_id}, #{value}, #{status}, #{remark1})")
	@Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
	public void addContent(Content bean);
	
/*	@Select("select * from disease")
	public List<Disease> getAllDisease();
	
	@Select("select * from title where id = #{id}")
	public List<Title> getTitleById(Integer id);
	
	@Select("select * from content where title_id = #{titId} and disease_id = #{disId} and status = 1")
	public List<Content> getContentByTitIdIdAndDis(Integer titId, Integer disId);*/

}
