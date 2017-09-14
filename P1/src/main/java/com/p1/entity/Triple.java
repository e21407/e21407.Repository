package com.p1.entity;

public class Triple {
	private Integer id;
	
	private Integer subject_id;
	
	private Integer object_id;
	
	private Integer relationship_id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(Integer subject_id) {
		this.subject_id = subject_id;
	}

	public Integer getObject_id() {
		return object_id;
	}

	public void setObject_id(Integer object_id) {
		this.object_id = object_id;
	}

	public Integer getRelationship_id() {
		return relationship_id;
	}

	public void setRelationship_id(Integer relationship_id) {
		this.relationship_id = relationship_id;
	}

}
