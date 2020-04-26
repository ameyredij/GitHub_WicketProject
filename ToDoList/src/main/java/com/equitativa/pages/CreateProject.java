package com.equitativa.pages;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.equitativa.dao.ProjectDao;
import com.equitativa.dao.impl.ProjectDaoImpl;
import com.equitativa.hibernate.entity.Project;
import com.equitativa.model.ProjectModel;


public class CreateProject extends WebPage{

	ProjectModel objModel=new ProjectModel();
	
	ModalWindow modalWindow;
	ProjectDao<Project >   customerHome=new ProjectDaoImpl<Project>();

	public CreateProject()
	{
		add(new CreateProjectForm());
	}

	public CreateProject(ModalWindow modalWindow)
	{

		this.modalWindow=modalWindow;
		add(new CreateProjectForm());
	}

	public class CreateProjectForm extends Form
	{

		public CreateProjectForm()
		{
			super("CreateProjectForm");
			
			final FeedbackPanel feedbackpanel=new FeedbackPanel("feedbackpanel");
			add(feedbackpanel);
			feedbackpanel.setOutputMarkupId(true);

			TextField projectName=new TextField("projectName",new PropertyModel(objModel,"projectName"));
			add(projectName);
			projectName.setOutputMarkupId(true);
			projectName.setRequired(true);

			TextArea projectDesc=new TextArea("projectDesc",new PropertyModel(objModel,"projectDesc"));
			add(projectDesc);
			projectDesc.setOutputMarkupId(true);
			projectDesc.setRequired(true);

		 
			AjaxButton btnSubmit=new AjaxButton("btnSubmit") {
				
				@Override
				protected void onError(AjaxRequestTarget target) {
					target.add(feedbackpanel);
				}
				
				protected void onSubmit(AjaxRequestTarget target) {

					super.onSubmit(target);
					Project project=new Project();
					project.setProjectName(objModel.getProjectName());
					project.setProjectDesc(objModel.getProjectDesc());
					project.setCreatedOn(new Date());
					customerHome.saveOrUpdate(project);
					modalWindow.close(target);
				}
			};
			add(btnSubmit);
			btnSubmit.setOutputMarkupId(true);

		}
	}

}
