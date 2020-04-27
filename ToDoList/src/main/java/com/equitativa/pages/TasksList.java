package com.equitativa.pages;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxCheckBox;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

import com.equitativa.UserSession;
import com.equitativa.dao.ProjectDao;
import com.equitativa.dao.impl.ProjectDaoImpl;
import com.equitativa.hibernate.entity.ProjectTask;
import com.equitativa.model.ProjectModel;
import com.equitativa.model.TaskModel;


public class TasksList extends HomePage {

	private ProjectModel projectModel;
	List<TaskModel> lstTaskModel =  new ArrayList<TaskModel>();;
	ProjectDao<ProjectTask> projectDao=new ProjectDaoImpl<ProjectTask>();
	SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd"); 
	int count;
	BigInteger completedCount ;
	BigInteger pendingCount;
	public TasksList() {
		add(new TasksListForm());
	}

	public TasksList(ProjectModel model) {
		this.projectModel=model;
		add(new TasksListForm());
		UserSession.getInstance().setProjectModel(projectModel);
	}

	class TasksListForm extends Form{

		@SuppressWarnings("unchecked")
		public TasksListForm(){
			super("TasksListForm");

			final WebMarkupContainer container = new WebMarkupContainer("container");
			add(container);
			container.setOutputMarkupId(true);


			try {

				if(projectModel==null) {
					projectModel =UserSession.getInstance().getProjectModel();
				}

				String strQuery    =    " select id, ( SELECT COUNT(*)  FROM   PROJECT_TASK where TASK_STATUS='Completed' ) AS TASK_completed,"
						+ " ( SELECT COUNT(*)  FROM   PROJECT_TASK where TASK_STATUS='Pending' ) AS TASK_Pending, " 
						+ "TASK_NAME, TASK_DESC, TASK_PRIORITY,TASK_OWNER,TASK_STATUS,TASK_CREATEDDATE,TASK_DUEDATE from PROJECT_TASK "
						+ " where PROJECT_ID="+projectModel.getId()+" order by id ";

				List lstTask =  projectDao.searchBySql(strQuery);

				for (Iterator iterator = lstTask.iterator(); iterator.hasNext();) {
					Object []obj = (Object[]) iterator.next();
					completedCount=(BigInteger) obj[1];
					pendingCount=(BigInteger) obj[2];
					lstTaskModel.add(new TaskModel( (Integer) obj[0], obj[1]+"",obj[2]+"",obj[3]+"",obj[4]+"",obj[5]+"",obj[6]+"",obj[7]+"",obj[8]+"",obj[9]+""));
					count++;
				}


			} catch (Exception e) {
				e.printStackTrace();
			}

			container.add(new Label("completedCount", new Model<BigInteger>(completedCount)));
			container.add(new Label("pendingCount", new Model<BigInteger>(pendingCount)));
			container.add(new Label("taskCount", new Model<Integer>(count)));
			
			PageableListView  listView = new PageableListView("listView",lstTaskModel,5)
			{
				@Override
				protected void populateItem(final ListItem item) {
					final TaskModel model = (TaskModel)item.getModelObject(); 
					
					int srNo=item.getIndex()+1;

					item.add(new Label("srNo",srNo));
					item.add(new Label("taskName",model.getTaskName()));
					item.add(new Label("taskDesc",model.getTaskDesc()));
					item.add(new Label("taskOwner",model.getTaskOwner()));
					item.add(new Label("taskPriority",model.getTaskPriority()));
					item.add(new Label("taskStatus",model.getTaskStatus()));
					
					item.add(new Label("taskCreatedDate",parseStartDate(model)));
					item.add(new Label("taskDueDate",parseDueDate(model)));

					item.add( new AjaxLink<Void>( "delete" )
					{
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick( AjaxRequestTarget target )
						{


							ProjectTask projectTask = mapPojoToDTO(model);
						
							projectDao.delete(projectTask);
							System.out.println("innnn delete updated");
							setResponsePage(new TasksList()); 
						}
					} );

					if(model.getTaskStatus()!=null && model.getTaskStatus().equalsIgnoreCase("Completed")) {
						AjaxCheckBox checkBox = new AjaxCheckBox("checkbox", Model.of(Boolean.TRUE)) {
							private static final long serialVersionUID = 1L;

							@Override
							protected void onUpdate(AjaxRequestTarget target) {
								
								ProjectTask projectTask = mapPojoToDTO(model);
								projectTask.setTaskStatus("Pending");
								
								projectDao.saveOrUpdate(projectTask);
								setResponsePage(new TasksList()); 
							}
						}; 
						item.add( checkBox );
					}else {
						AjaxCheckBox checkBox = new AjaxCheckBox("checkbox", Model.of(Boolean.FALSE)) {
							private static final long serialVersionUID = 1L;

							@Override
							protected void onUpdate(AjaxRequestTarget target) {
								System.out.println("innnn checkbox");
								 
								ProjectTask projectTask = mapPojoToDTO(model);
								projectTask.setTaskStatus("Completed");
							
								projectDao.saveOrUpdate(projectTask);
								System.out.println("innnn checkbox updated");
								setResponsePage(new TasksList()); 
							}

						}; 
						item.add( checkBox );
					}

				}
				
 
				private ProjectTask mapPojoToDTO(final TaskModel model) {
					ProjectTask projectTask=new ProjectTask();
					projectTask.setId(model.getId());
					projectTask.setProjectId(projectModel.getId());
					projectTask.setTaskStatus("Completed");
					projectTask.setTaskName(model.getTaskName());
					projectTask.setTaskDesc(model.getTaskDesc());
					projectTask.setTaskOwner(model.getTaskOwner());
					projectTask.setTaskPriority(model.getTaskPriority());
					projectTask.setTaskDueDate(parseDueDate(model));
					projectTask.setTaskCreatedDate(parseStartDate(model));
					return projectTask;
				}

				private Date parseStartDate(final TaskModel model) {
					Date startDate1 = null;
					try {
						startDate1 = dateFormat.parse(model.getTaskCreatedDate());

					} catch (ParseException e) {
						e.printStackTrace();
					}
					return startDate1;
				}

				private Date parseDueDate(final TaskModel model) {
					Date dueDate = null;
					try {
						dueDate = dateFormat.parse(model.getTaskDueDate());

					} catch (ParseException e) {
						e.printStackTrace();
					}
					return dueDate;
				}
			};
			container.add(listView);
			listView.setOutputMarkupId(true);

			container.add(new AjaxPagingNavigator("nav", listView)).setOutputMarkupId(true);

			Label projectName=new Label("projectName",new Model<String>(projectModel.getProjectName()));
			add(projectName);
			projectName.setOutputMarkupId(true);
			container.add(projectName);
			
			WebMarkupContainer informationBox = new WebMarkupContainer ("informationBox");
			add(informationBox);
			informationBox.setVisible(false);
			informationBox.add(new AttributeModifier("style", "display: none;"));

			final ModalWindow addTaskModelWindow;
			add(addTaskModelWindow=new ModalWindow("addTaskModelWindow"));
			addTaskModelWindow.setResizable(true);
			addTaskModelWindow.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
			addTaskModelWindow.setWidthUnit("em");
			addTaskModelWindow.setHeightUnit("em");

			addTaskModelWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback()
			{
				@Override
				public boolean onCloseButtonClicked(AjaxRequestTarget arg0) 
				{
					return true;
				}
			});

			addTaskModelWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
			{
				@Override
				public void onClose(AjaxRequestTarget arg0)
				{
					setResponsePage(new TasksList());
				}
			});

			AjaxLink btnCreateToDoTask;
			add(btnCreateToDoTask = new AjaxLink<String>("btnCreateToDoTask") {
				@Override
				public void onClick(AjaxRequestTarget target) {
					addTaskModelWindow.setPageCreator(new ModalWindow.PageCreator()
					{
						@Override
						public Page createPage() 
						{
							return new CreateTask(addTaskModelWindow,projectModel);
						}
					});
					addTaskModelWindow.show(target);
				}
			});
			btnCreateToDoTask.setOutputMarkupId(true);
			container.add(btnCreateToDoTask);
			
			
		}
	}
}

