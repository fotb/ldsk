package com.noway.ldsk.dao;

import java.util.Map;
@SuppressWarnings("unchecked")
public interface ICompSystemDAO {
	public int findCountByModel(String model);

	public Map findAllToMap();
}
