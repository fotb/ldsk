package com.noway.ldsk.dao;

import java.util.Map;

public interface IUnmodeledDataDAO {
	/**
	 * Branchs and Positions
	 * 
	 * @param metaObjAttrRelationsIdn
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Map findAllByMetaObjAttrRelationsIdn(String metaObjAttrRelationsIdn);
}
