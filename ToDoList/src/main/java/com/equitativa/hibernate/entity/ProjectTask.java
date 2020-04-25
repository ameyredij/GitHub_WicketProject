package com.equitativa.hibernate.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="PROJECT_TASK")
public class ProjectTask implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID")
	private Integer id;

	@Column(name="PROJECT_ID")
	private Integer projectId;

	@Column(name="TASK_NAME", nullable=false )
	private String taskName;

	@Column(name="TASK_DESC")
	private String taskDesc;

	@Column(name="TASK_PRIORITY", nullable=false )
	private String taskPriority;
	
	@Column(name="TASK_OWNER", nullable=false )
	private String taskOwner;
	
	@Column(name="TASK_STATUS")
	private String taskStatus;

	@Temporal(TemporalType.DATE)
	@Column (name="TASK_CREATEDDATE")
	private Date taskCreatedDate;

	@Temporal(TemporalType.DATE)
	@Column (name="TASK_DUEDATE")
	private Date taskDueDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
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

	public Date getTaskCreatedDate() {
		return taskCreatedDate;
	}

	public void setTaskCreatedDate(Date taskCreatedDate) {
		this.taskCreatedDate = taskCreatedDate;
	}

	public Date getTaskDueDate() {
		return taskDueDate;
	}

	public void setTaskDueDate(Date taskDueDate) {
		this.taskDueDate = taskDueDate;
	}

	public String getTaskOwner() {
		return taskOwner;
	}

	public void setTaskOwner(String taskOwner) {
		this.taskOwner = taskOwner;
	}
	

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}

	@Override
	public String toString() {
		return "ProjectTask [id=" + id + ", projectId=" + projectId + ", taskName=" + taskName + ", taskDesc="
				+ taskDesc + ", taskPriority=" + taskPriority + ", taskOwner=" + taskOwner + ", taskCreatedDate="
				+ taskCreatedDate + ", taskDueDate=" + taskDueDate + "]";
	}
	

}
