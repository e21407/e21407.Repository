package com.p1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.p1.entity.*;

public interface P1Mapper {
	
	@Select("select * from sty where name = #{name}")
	public List<STY> getSTYByName(String name);
	
	@Select("select * from sty")
	public List<STY> getAllSTY();
	
	@Insert("insert into sty (id, old_id, name, remark1) values (#{id}, #{old_id}, #{name}, #{remark1})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addSTY(STY bean);
	
	@Select("select * from rl where name = #{name}")
	public List<RL> getRLByName(String name);
	
	@Select("select * from rl")
	public List<RL> getAllRL();
	
	@Insert("insert into rl (id, old_id, name, remark1) values (#{id}, #{old_id}, #{name}, #{remark1})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addRL(RL bean);
	
	@Select("select * from triple")
	public List<Triple> getAllTriple();
	
	@Insert("insert into triple (id, subject_id, object_id, relationship_id) values (#{id}, #{subject_id}, #{object_id}, #{relationship_id})")
	@Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
	public void addTriple(Triple bean);

}
