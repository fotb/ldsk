package com.noway.ldsk.bo;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.noway.ldsk.util.AppException;

public interface ISafeReport {
	HSSFWorkbook renderExcelReport() throws AppException;
}
