package com.equitativa.dao;

import java.io.Serializable;
import java.util.List;
 

public interface Transactional<T> extends Serializable {

	public boolean saveOrUpdate(T object);
	public List<?> searchBySql(String query);
	boolean delete(T object);
	
}
