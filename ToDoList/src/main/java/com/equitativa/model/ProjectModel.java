package com.equitativa.model;

import java.io.Serializable;

public class ProjectModel   implements Serializable{
	
	private Integer id;
	private String projectName;
	private String projectDesc;
	
	
	public ProjectModel(Integer id, String projectName, String projectDesc) {

		this.id=id;
		this.projectName=projectName;
		this.projectDesc=projectDesc;
	}
	
	
	public ProjectModel() {
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectDesc() {
		return projectDesc;
	}
	public void setProjectDesc(String projectDesc) {
		this.projectDesc = projectDesc;
	}
	
}
