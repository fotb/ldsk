package com.noway.ldsk.dao;

import java.util.List;

public interface IDeviceControlActionDAO {
	@SuppressWarnings("unchecked")
	List findAll();
	int getCount();
}
