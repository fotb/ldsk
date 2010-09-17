package com.noway.ldsk.dao;

import java.util.Map;

public interface INetworkSoftwareDAO {
	/**
	 * Find all data to map
	 * key:computerIdn
	 * value:nicAddress
	 * @return
	 */
	@SuppressWarnings("unchecked")
	Map findAll();
}
