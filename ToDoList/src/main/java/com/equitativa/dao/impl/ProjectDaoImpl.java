package com.equitativa.dao.impl;

import java.io.Serializable;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.equitativa.dao.ProjectDao;
import com.equitativa.hibernate.entity.Project;
import com.equitativa.hibernate.util.HibernateUtil;



public class ProjectDaoImpl<T> extends ProjectDao<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(Project.class);
	public Integer saveOrUpdate(Project customer) 
	{
		Session sessionFactory =  HibernateUtil.currentSession();
		Transaction tx = sessionFactory.beginTransaction();
		try {
			tx.begin();
			sessionFactory.saveOrUpdate(customer);            
			tx.commit();
			return customer.getId();
		}catch (RuntimeException re) {
			re.printStackTrace();
			tx.rollback();
			return 0;
		}finally
		{
			HibernateUtil.closeSession();
		}

	}
	 
	
}
