package com.noway.ldsk.bo;

import java.util.Map;

import com.noway.ldsk.dao.IComputerDAO;

public class ComputerBOImpl implements IComputerBO {
	private IComputerDAO computerDAO;
	
	public IComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(IComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map findAllToMap() {
		return computerDAO.findAllToMap();
	}

}
