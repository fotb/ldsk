package com.noway.ldsk.bo;

import java.util.List;
import java.util.Map;
@SuppressWarnings("unchecked")
public interface IHipsActionBO {
	List findAll();

	Map getHipsActionGroupByComputerIdn();

	Map getHipsActionWithActionCode();
}
