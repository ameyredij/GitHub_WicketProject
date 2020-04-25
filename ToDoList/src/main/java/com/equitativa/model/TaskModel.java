package com.equitativa.model;

import java.io.Serializable;
import java.util.Date;

public class TaskModel   implements Serializable{
	
	private Integer id;
	private String taskName;
	private String taskDesc;
	private String taskPriority;
	private String taskOwner;
	private String taskCreatedDate;
	private String taskDueDate;
	private String taskStatus;
	private String completedCount;
	private String pendingCount;
	
	
	
	public TaskModel(Integer id, String completedCount, String pendingCount, String taskName, String taskDesc,String taskPriority ,String taskOwner ,
			String taskStatus,String taskCreatedDate,String taskDueDate) {
		super();
		this.id = id;
		this.taskName = taskName;
		this.taskDesc = taskDesc;
		this.taskOwner = taskOwner;
		this.taskPriority = taskPriority;
		this.taskCreatedDate = taskCreatedDate;
		this.taskDueDate = taskDueDate;
		this.taskStatus = taskStatus;
		this.completedCount = completedCount;
		this.pendingCount = pendingCount;
	}



	public TaskModel() {
	}



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getTaskName() {
		return taskName;
	}



	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}



	public String getTaskDesc() {
		return taskDesc;
	}



	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}



	public String getTaskPriority() {
		return taskPriority;
	}



	public void setTaskPriority(String taskPriority) {
		this.taskPriority = taskPriority;
	}



	public String getTaskOwner() {
		return taskOwner;
	}



	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}



	public String getTaskCreatedDate() {
		return taskCreatedDate;
	}



	public void setTaskCreatedDate(String taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}



	public String getTaskDueDate() {
		return taskDueDate;
	}



	public void setTaskDueDate(String taskDueDate) {
		this.taskDueDate = taskDueDate;
	}



	public String getTaskStatus() {
		return taskStatus;
	}



	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}



	public String getCompletedCount() {
		return completedCount;
	}



	public void setCompletedCount(String completedCount) {
		this.completedCount = completedCount;
	}



	public String getPendingCount() {
		return pendingCount;
	}



	public void setPendingCount(String pendingCount) {
		this.pendingCount = pendingCount;
	}


 
	

}
