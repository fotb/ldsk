package com.noway.ldsk.bo;

import java.util.Map;

import com.noway.ldsk.util.AppException;
@SuppressWarnings("unchecked")
public interface IReportBO {
	int findAllComputerCount();

	Map findAllMacAddress();

	Map findAllIpAddress();

	Map findAllByMetaObjAttrRelationsIdn(String metaObjAttrRelationsIdn);

	Map getAllComputerWitchBranch() throws AppException;

}
