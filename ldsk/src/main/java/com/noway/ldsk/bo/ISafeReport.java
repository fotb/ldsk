package com.noway.ldsk.bo;

import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.noway.ldsk.util.AppException;

public interface ISafeReport {
	HSSFWorkbook renderExcelReport() throws AppException;
	@SuppressWarnings("unchecked")
	List getSafeReportDataSource() throws AppException;
	@SuppressWarnings("unchecked")
	List getSafeDetailBySubBranchBranchAndActionType(final String subBranchName, final String safeType, String actionCode) throws AppException;
}
