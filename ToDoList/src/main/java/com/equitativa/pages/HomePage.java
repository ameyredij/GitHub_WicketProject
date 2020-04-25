package com.equitativa.pages;

import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;



public class HomePage extends WebPage{
	
	public HomePage() {
		add(new Label("logo", new Model<String>("Golden Real Estate")));
		
		final ModalWindow addCustomerModelWindow;
		add(addCustomerModelWindow=new ModalWindow("addCustomerModelWindow"));
		addCustomerModelWindow.setResizable(true);
		addCustomerModelWindow.setCssClassName(ModalWindow.CSS_CLASS_GRAY);
		addCustomerModelWindow.setWidthUnit("em");
		addCustomerModelWindow.setHeightUnit("em");
		
		addCustomerModelWindow.setCloseButtonCallback(new ModalWindow.CloseButtonCallback()
		{

			@Override
			public boolean onCloseButtonClicked(AjaxRequestTarget arg0) 
			{
				return true;
			}

		});

		addCustomerModelWindow.setWindowClosedCallback(new ModalWindow.WindowClosedCallback()
		{

			@Override
			public void onClose(AjaxRequestTarget arg0)
			{
				setResponsePage(new ProjectList());
			}

		});
		
		
		AjaxLink btnCreateToDoProject;
		add(btnCreateToDoProject = new AjaxLink<String>("btnCreateToDoProject") {

			@Override
			public void onClick(AjaxRequestTarget target) {
				addCustomerModelWindow.setPageCreator(new ModalWindow.PageCreator()
				{
					@Override
					public Page createPage() 
					{
						return new CreateProject(addCustomerModelWindow);
					}

				});
				addCustomerModelWindow.show(target);
			}
			
		});
		
		btnCreateToDoProject.setOutputMarkupId(true);
		
		WebMarkupContainer informationBox = new WebMarkupContainer ("informationBox");
		add(informationBox);
		 
		
		AjaxLink btnProjectView;
		add(btnProjectView= new AjaxLink<Void>( "btnProjectView" )
        {
            private static final long serialVersionUID = 1L;
            @Override
            public void onClick( AjaxRequestTarget target )
            {
            	setResponsePage(new ProjectList());
            }
        } );
		btnProjectView.setOutputMarkupId(true);
		
		AjaxLink btnHome;
		add(btnHome= new AjaxLink<Void>( "btnHome" )
		{
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick( AjaxRequestTarget target )
			{
				setResponsePage(new HomePage());
			}
		} );
		btnHome.setOutputMarkupId(true);
		
	}

}
