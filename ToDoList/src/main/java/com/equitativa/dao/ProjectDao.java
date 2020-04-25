package com.equitativa.dao;

import java.util.Collections;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.equitativa.hibernate.entity.Project;
import com.equitativa.hibernate.entity.ProjectTask;
import com.equitativa.hibernate.util.HibernateUtil;

public abstract class ProjectDao<T> implements Transactional<T> 
{

	private static final long serialVersionUID = 1L;

	 
	public boolean saveOrUpdate(T object) {

		Session sessionFactory =  HibernateUtil.currentSession();
		Transaction tx = sessionFactory.beginTransaction();
		try {
//			tx.begin();
			sessionFactory.saveOrUpdate(object);            
			tx.commit();
			System.out.println(" SaveOrUpdate is success on " + object.toString());
			
			return true;
		}catch (RuntimeException re) {
			re.printStackTrace();
			tx.rollback();
			System.out.println(" SaveOrUpdate is Fail on " + object.toString());
			return false;
		}finally
		{
			HibernateUtil.closeSession();
		}

	}
	public boolean delete(T object) {
		
		Session sessionFactory =  HibernateUtil.currentSession();
		Transaction tx = sessionFactory.beginTransaction();
		try {
//			tx.begin();
			sessionFactory.delete(object);            
			tx.commit();
			System.out.println(" delete is success on " + object.toString());
			
			return true;
		}catch (RuntimeException re) {
			re.printStackTrace();
			tx.rollback();
			System.out.println(" delete is Fail on " + object.toString());
			return false;
		}finally
		{
			HibernateUtil.closeSession();
		}
		
	}

	public List<T> searchBySql(String query) {
		try{
			Query strQuery = HibernateUtil.currentSession().createSQLQuery(query);	    			
			
			System.out.println("list searchBySql "+strQuery.list());
			return strQuery.list();
		}catch(RuntimeException re)
		{
			System.out.println( "Error to find List by SQL");
			re.printStackTrace();
			return Collections.EMPTY_LIST;
		}finally
		{
			HibernateUtil.closeSession();
		}
	}


}
 