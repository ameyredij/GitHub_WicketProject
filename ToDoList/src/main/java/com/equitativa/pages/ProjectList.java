package com.equitativa.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.navigation.paging.AjaxPagingNavigator;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.model.Model;

import com.equitativa.dao.ProjectDao;
import com.equitativa.dao.impl.ProjectDaoImpl;
import com.equitativa.hibernate.entity.Project;
import com.equitativa.model.ProjectModel;


public class ProjectList extends HomePage {

	public ProjectList() {
		add(new ProjectListForm());
	}

	List<ProjectModel> lstProjectModel=  new ArrayList<ProjectModel>();
	ProjectDao<Project>  projectDao=new ProjectDaoImpl<Project>();
	int count;

	class ProjectListForm extends Form{

		@SuppressWarnings("unchecked")
		public ProjectListForm(){
			super("ProjectListForm");

			final WebMarkupContainer container = new WebMarkupContainer("container");
			add(container);
			container.setOutputMarkupId(true);
			
			String strQuery    =    " select id, PROJECT_NAME ,PROJECT_DESC, CREATED_DATE from project order by id " ;
			List lstProject =  projectDao.searchBySql(strQuery);
			
			for (Iterator iterator = lstProject.iterator(); iterator.hasNext();) {
				Object []obj = (Object[]) iterator.next();
				lstProjectModel.add(new ProjectModel( (Integer) obj[0], obj[1]+"",obj[2]+""));
				count++;
			}
			
			container.add(new Label("projectCount", new Model<Integer>(count)));

			System.out.println("lstProjectModel "+lstProjectModel);

			PageableListView  listView = new PageableListView("listView",lstProjectModel,5)
			{
				@Override
				protected void populateItem(final ListItem item) {
					final ProjectModel model = (ProjectModel)item.getModelObject(); 

					int srNo=item.getIndex()+1;
					item.add(new Label("srNo",srNo));
					item.add(new Label("projectName",model.getProjectName()));
					item.add(new Label("projectDesc",model.getProjectDesc()));

					item.add( new AjaxLink<Void>( "delete" )
					{
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick( AjaxRequestTarget target )
						{
							System.out.println("innnn delete");
							Project Project=new Project();
							Project.setId(model.getId());
							Project.setProjectName(model.getProjectName());
							Project.setProjectDesc(model.getProjectDesc());
							projectDao.delete(Project);
							System.out.println("innnn delete updated");
							setResponsePage(new ProjectList());
						}
					} );
					item.add( new AjaxLink<Void>( "view" )
					{
						private static final long serialVersionUID = 1L;
						@Override
						public void onClick( AjaxRequestTarget target )
						{
							setResponsePage(new TasksList(model));
						}
					} );

				}
			};
			container.add(listView);
			listView.setOutputMarkupId(true);
			
			WebMarkupContainer informationBox = new WebMarkupContainer ("informationBox");
			add(informationBox);
			informationBox.setOutputMarkupId(true);
			informationBox.setOutputMarkupPlaceholderTag(true);
			informationBox.setVisible(false);
			informationBox.add(new AttributeModifier("style", "display: none;"));

			container.add(new AjaxPagingNavigator("nav", listView)).setOutputMarkupId(true);

		}
	}
}

