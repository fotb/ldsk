package com.noway.ldsk.dao;

import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public interface IComputerDAO {
	int findAllCount();
	int findCountByModel(String model);
	List findAll();
	Map findAllToMap();
}
