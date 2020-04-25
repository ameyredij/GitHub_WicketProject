package com.equitativa;

import org.apache.wicket.Session;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;

import com.equitativa.model.ProjectModel;

public class UserSession extends WebSession {

	private ProjectModel projectModel;
	
	public UserSession(Request request) {
		super(request);
	}
	
	public static UserSession getInstance() {
		return (UserSession) Session.get();
	}

	public ProjectModel getProjectModel() {
		return projectModel;
	}

	public void setProjectModel(ProjectModel projectModel) {
		this.projectModel = projectModel;
	}

	 
	
}
