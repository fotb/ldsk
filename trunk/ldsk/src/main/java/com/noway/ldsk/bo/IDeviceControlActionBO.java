package com.noway.ldsk.bo;

import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public interface IDeviceControlActionBO {
	List findAll();

	Map getDeviceControlActionWithActionCode();

	Map getHipsActionGroupByComputerIdn();
}
