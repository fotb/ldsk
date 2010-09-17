package com.noway.ldsk.bo;

import java.util.Map;
@SuppressWarnings("unchecked")
public interface ICompSystemBO {
	public int findCountByModel(String model);

	public Map findAllToMap();
}
