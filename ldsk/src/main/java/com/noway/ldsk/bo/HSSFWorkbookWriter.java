package com.noway.ldsk.bo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
@SuppressWarnings("unchecked")
class HSSFWorkbookWriter {

    private static final String DATA_NOT_AVAIL = "--";
    
    private HSSFWorkbook targetWB;
    private HSSFRow rowTemplate;
    private int currSheetNum = 0;
    private int currRowNum = 0;
    private int currColNum = -1;
    private Map sheetRowMap = new HashMap();
    private Map styleMap = new HashMap();
    
    /**
     * Constructing this object.
     * @param wb HSSFWorkbook
     * @param rowTemplate HSSFRow
     */
    public HSSFWorkbookWriter(final HSSFWorkbook wb, final HSSFRow rowTemplate) {
        this.targetWB = wb;
        this.rowTemplate = rowTemplate;
    }
        
    /**
     * write to excel
     * @param sheetNo sheet number
     * @param rowNum row number
     * @param colNum column number
     * @param value value
     */
    public void writeTo(final int sheetNo, final int rowNum, final int colNum, final Object value) {
        final HSSFSheet sheet = targetWB.getSheetAt(sheetNo);
        final HSSFRow row = sheet.getRow(rowNum);
        final HSSFCell cell = row.getCell(colNum);
                
        if(value == null || "".equals(value)) {
        	HSSFRichTextString richString = new HSSFRichTextString(DATA_NOT_AVAIL);
            cell.setCellValue(richString);
        } else if(value instanceof BigDecimal) {
            cell.setCellValue(((BigDecimal) value).doubleValue());
        } else if(value instanceof Date) {
            cell.setCellValue(((Date) value));
        } else if(value instanceof String) {
        	HSSFRichTextString richString = new HSSFRichTextString(value.toString());
            cell.setCellValue(richString);         
        } else {
        	HSSFRichTextString richString = new HSSFRichTextString(value.toString());
            cell.setCellValue(richString);      
        }
        Object obj = sheetRowMap.get(String.valueOf(sheetNo));
        if(null != obj) {
        	String sheetRowNum = obj.toString();
        	if(rowNum > Integer.valueOf(sheetRowNum).intValue()) {
        		sheetRowMap.put(String.valueOf(sheetNo), String.valueOf(rowNum));
        	}
        } else {
        	sheetRowMap.put(String.valueOf(sheetNo), String.valueOf(rowNum));
        }
    }
    /**
     * write to current cell of excel
     * @param value value
     */
    public void writeToCurrentCell(final Object value) {
        writeTo(currSheetNum, currRowNum, currColNum, value);
    }
    /**
     * create Row for excel
     * @return HSSFRow
     */
    public HSSFRow newRow() {
        currRowNum++;
        if(sheetRowMap.containsKey(String.valueOf(currSheetNum))) {
        	int sheetRowNum = Integer.valueOf(sheetRowMap.get(String.valueOf(currSheetNum)).toString()).intValue();
        	sheetRowNum++;
        	sheetRowMap.put(String.valueOf(currSheetNum), Integer.valueOf(sheetRowNum));
        	currRowNum = sheetRowNum;
        } else {
        	sheetRowMap.put(String.valueOf(currSheetNum), "0");
        	currRowNum = 0;
        }
        currColNum = -1;
        return getCurrentSheet().createRow(currRowNum);
    }
    /**
     * create Cell for excel
     * @return HSSFCell
     */
    public HSSFCell newCell(){
        currColNum++;
        HSSFCellStyle style = (HSSFCellStyle) styleMap.get(String.valueOf(currColNum));
        if(style == null) {
            HSSFCell firstCell = rowTemplate.getCell(currColNum);
            style = targetWB.createCellStyle();
            style.setDataFormat(firstCell.getCellStyle().getDataFormat());
            style.setFillForegroundColor(firstCell.getCellStyle().getFillForegroundColor());
            style.setBorderBottom(firstCell.getCellStyle().getBorderBottom());
            style.setBorderLeft(firstCell.getCellStyle().getBorderLeft());
            style.setBorderRight(firstCell.getCellStyle().getBorderRight());
            style.setBorderTop(firstCell.getCellStyle().getBorderTop());
            style.setBottomBorderColor(firstCell.getCellStyle().getBottomBorderColor());   
            style.setAlignment(firstCell.getCellStyle().getAlignment());   
            styleMap.put(String.valueOf(currColNum), style);
        }
        final HSSFRow row  = getCurrentRow();
        final HSSFCell cell = row.createCell(currColNum);
        cell.setCellStyle(style);
        return cell;
    }
    
    public HSSFCell newCell(HSSFCellStyle style) {
    	currColNum++;
    	final HSSFRow row  = getCurrentRow();
        final HSSFCell cell = row.createCell(currColNum);
        cell.setCellStyle(style);
        return cell;
    }
    /**
     * get current sheet
     * @return HSSFSheet
     */
    public HSSFSheet getCurrentSheet() {
        return targetWB.getSheetAt(currSheetNum);
    }
    public int getCurrSheetNum() {
        return currSheetNum;
    }
    /**
     * set current sheet number
     * @param currSheetNum current sheet number
     */
    public void setCurrSheetNum(final int currSheetNum) {
        this.currSheetNum = currSheetNum;
    }
    /**
     * get current row
     * @return HSSFRow
     */
    public HSSFRow getCurrentRow() {           
        HSSFRow row = getCurrentSheet().getRow(currRowNum);
        return row;
    }

	public int getCurrRowNum() {
		return currRowNum;
	}
} // end of class HSSFWorkbookWriter
