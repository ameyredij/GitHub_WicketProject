package com.equitativa.pages;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.PropertyModel;

import com.equitativa.dao.ProjectDao;
import com.equitativa.dao.impl.ProjectDaoImpl;
import com.equitativa.hibernate.entity.ProjectTask;
import com.equitativa.model.ProjectModel;
import com.equitativa.model.TaskModel;


public class CreateTask extends WebPage{

	TaskModel objModel=new TaskModel();

	ModalWindow modalWindow;
	ProjectModel projectModel;
	ProjectDao<ProjectTask > projectHome=new ProjectDaoImpl<ProjectTask>();
	SimpleDateFormat dateFormat=new  SimpleDateFormat("MM/dd/yyyy");

	public CreateTask()
	{
		add(new CreateTaskForm());
	}

	public CreateTask(ModalWindow modalWindow)
	{
		this.modalWindow=modalWindow;
		add(new CreateTaskForm());
	}

	public CreateTask(ModalWindow addtaskModelWindow, ProjectModel projectModel) {
		this.modalWindow=addtaskModelWindow;
		this.projectModel=projectModel;
		add(new CreateTaskForm());
	}

	public class CreateTaskForm extends Form
	{
		public CreateTaskForm()
		{
			super("CreateTaskForm");

			final FeedbackPanel feedbackpanel=new FeedbackPanel("feedbackpanel");
			add(feedbackpanel);
			feedbackpanel.setOutputMarkupId(true);

			TextField taskName=new RequiredTextField("taskName",new PropertyModel(objModel,"taskName"));
			add(taskName);
			taskName.setOutputMarkupId(true);

			TextField taskOwner=new RequiredTextField("taskOwner",new PropertyModel(objModel,"taskOwner"));
			add(taskOwner);
			taskOwner.setOutputMarkupId(true);

			TextArea taskDesc=new TextArea("taskDesc",new PropertyModel(objModel,"taskDesc"));
			add(taskDesc);
			taskDesc.setOutputMarkupId(true);
			taskDesc.setRequired(true);

			List<String> priorityList = Arrays.asList("Low", "Medium", "High","Critical"); 

			DropDownChoice<String> priority = new DropDownChoice<String>(
					"priority", new PropertyModel<String>(objModel, "taskPriority"), priorityList);
			add(priority);
			priority.setOutputMarkupId(true);
			priority.setRequired(true);

			TextField taskDueDate=new TextField("taskDueDate",new PropertyModel(objModel,"taskDueDate"));
			add(taskDueDate);
			taskDueDate.setOutputMarkupId(true);
			taskDueDate.setRequired(true);
			AjaxButton btnSubmit=new AjaxButton("btnSubmit") {

				@Override
				protected void onError(AjaxRequestTarget target) {
					target.add(feedbackpanel);
				}
				protected void onSubmit(AjaxRequestTarget target) {					System.out.println("in submit");					super.onSubmit(target);					try {
						ProjectTask projectTask=new ProjectTask();
						projectTask.setProjectId(projectModel.getId());
						projectTask.setTaskName(objModel.getTaskName());
						projectTask.setTaskDesc(objModel.getTaskDesc());
						projectTask.setTaskOwner(objModel.getTaskOwner());
						projectTask.setTaskPriority(objModel.getTaskPriority());
						projectTask.setTaskCreatedDate(new Date());
						
						Date date = dateFormat.parse(objModel.getTaskDueDate());
						projectTask.setTaskDueDate(date);
						projectTask.setTaskStatus("Pending");
						projectHome.saveOrUpdate(projectTask);
						target.add(feedbackpanel);						modalWindow.close(target);
					} catch (ParseException e) {
						e.printStackTrace();
					}				}			};			add(btnSubmit);			btnSubmit.setOutputMarkupId(true);

		}
	}

}
