package com.noway.ldsk.bo;

import java.util.Map;

import com.noway.ldsk.dao.IUnmodeledDataDAO;

public class UnmodeledDataBOImpl implements IUnmodeledDataBO {
	private IUnmodeledDataDAO unmodeledDataDAO;
	
	public IUnmodeledDataDAO getUnmodeledDataDAO() {
		return unmodeledDataDAO;
	}

	public void setUnmodeledDataDAO(IUnmodeledDataDAO unmodeledDataDAO) {
		this.unmodeledDataDAO = unmodeledDataDAO;
	}

	@Override
	public Map<String, String> findAllBranch(final String metaObjAttrRelationsIdn) {
		return unmodeledDataDAO.findAllBranch(metaObjAttrRelationsIdn);
	}
}
