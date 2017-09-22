package com.lbc.mapper;

import java.util.List;

import com.lbc.entity.Content;
import com.lbc.entity.Disease;
import com.lbc.entity.Title;

public interface IDECEMapper {
	
	public List<Disease> getDiseaseByExample(Disease example);
	
	public List<Title> getTitleByExample(Title example);
	
	public List<Content> getContentByExample(Content example);
	
	public void updateDiseaseByExample(Disease example);
	
	public void updateTitleByExample(Title example);
	
	public void updateContentByExample(Content example);
	
	public List<Integer> getTitIdsOfContentByDisId(Integer disease_id);

}
