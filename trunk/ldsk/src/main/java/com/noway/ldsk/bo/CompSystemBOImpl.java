package com.noway.ldsk.bo;

import java.util.Map;

import com.noway.ldsk.dao.ICompSystemDAO;
@SuppressWarnings("unchecked")
public class CompSystemBOImpl implements ICompSystemBO {
	private ICompSystemDAO compSystemDAO;

	public Map findAllToMap() {
		return compSystemDAO.findAllToMap();
	}

	public int findCountByModel(String model) {
		return compSystemDAO.findCountByModel(model);
	}

	public ICompSystemDAO getCompSystemDAO() {
		return compSystemDAO;
	}

	public void setCompSystemDAO(ICompSystemDAO compSystemDAO) {
		this.compSystemDAO = compSystemDAO;
	}

}
