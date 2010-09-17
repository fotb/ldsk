package com.noway.ldsk.bo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.ClassPathResource;

import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.ReportPropertiesLocator;
import com.noway.ldsk.vo.CompSystemVO;
import com.noway.ldsk.vo.ComputerVO;
import com.noway.ldsk.vo.TcpVO;
@SuppressWarnings("unchecked")
public class ComputerReportImpl implements IComputerReport{
	private static final Logger logger = Logger.getLogger(ComputerReportImpl.class);
	
	private IReportBO reportBO;
	private ICompSystemBO compSystemBO;
	
	public ICompSystemBO getCompSystemBO() {
		return compSystemBO;
	}

	public void setCompSystemBO(ICompSystemBO compSystemBO) {
		this.compSystemBO = compSystemBO;
	}

	public IReportBO getReportBO() {
		return reportBO;
	}

	public void setReportBO(IReportBO reportBO) {
		this.reportBO = reportBO;
	}

	public HSSFWorkbook renderExcelReport() throws AppException {
        POIFSFileSystem fs;
        HSSFWorkbook wb = null;
        final FileInputStream fis = null;
        try {
        	final String templateFile = ReportPropertiesLocator.getInstance(true).getValue(Constants.REPORT_ALL_COMPUTER_KEY);
        	final ClassPathResource resource = new ClassPathResource(templateFile);
			fs = new POIFSFileSystem(resource.getInputStream());
			wb = new HSSFWorkbook(fs);

			final String sheet1Name = "设备详细衄1�7";
			final String sheet2Name = "设备汇�1�7�表";
          
	        wb.setSheetName(0, sheet1Name);
	        wb.setSheetName(1, sheet2Name);
	        
	        final HSSFSheet sheet = wb.getSheetAt(0);
	        final HSSFRow firstRow = sheet.getRow(0);  
	        final HSSFWorkbookWriter writer = new HSSFWorkbookWriter(wb, firstRow);
	        
	        final HSSFSheet sheet2 = wb.getSheetAt(1);
//	        final HSSFRow firstRow2 = sheet2.getRow(0);  
//	        final HSSFWorkbookWriter writer2 = new HSSFWorkbookWriter(wb, firstRow2);
//	        
	        
	        HSSFCellStyle cellStyle = sheet2.getRow(1).getCell(0).getCellStyle();
	        HSSFCellStyle tempCellStyle = sheet2.getRow(1).getCell(1).getCellStyle();
	                
	        
	        final int computerCount = reportBO.findAllComputerCount();
	        final int dellCount = compSystemBO.findCountByModel("Dell");
	        final int lenovoCount = compSystemBO.findCountByModel("Lenovo");
	        final int hpCount = compSystemBO.findCountByModel("HP") + compSystemBO.findCountByModel("Hewlett-Packard");
	        writer.setCurrSheetNum(1);
	        writer.writeTo(1, 1, Short.valueOf("1").shortValue(), String.valueOf(computerCount));
	        
	        writer.writeTo(1, 2, Short.valueOf("1").shortValue(), String.valueOf(dellCount));
	        writer.writeTo(1, 2, Short.valueOf("3").shortValue(), String.valueOf(lenovoCount));
	        writer.writeTo(1, 2, Short.valueOf("5").shortValue(), String.valueOf(hpCount));
	        writer.writeTo(1, 2, Short.valueOf("7").shortValue(), String.valueOf(computerCount - dellCount - lenovoCount - hpCount));
//	        writer2.newRow();
//	        writer2.newCell(cellStyle);
//	        writer2.writeToCurrentCell("其他品牌设备总数	:");
//	        writer2.newCell();
//	        writer2.writeToCurrentCell(String.valueOf(computerCount - dellCount - lenovoCount - hpCount));
	        
	        
	        final Map branchMap = reportBO.getAllComputerWitchBranch();
	        final Map ipMap = reportBO.findAllIpAddress();
	        final Map macMap = reportBO.findAllMacAddress();
	        final Map positionMap = reportBO.findAllByMetaObjAttrRelationsIdn(ReportPropertiesLocator.getInstance(true).getValue(Constants.PROPERTIES_POSITION_KEY));
	        final Map modelMap = compSystemBO.findAllToMap();
	        for (Iterator iterator = branchMap.keySet().iterator(); iterator.hasNext();) {
				final String key = (String) iterator.next();
				List computerVOList = (List)branchMap.get(key);
				writer.setCurrSheetNum(0);
				writer.newRow();
		        writer.newCell(cellStyle);
		        if(Constants.OTHER_DEPT_KEY.equals(key)) {
		        	writer.writeToCurrentCell("其他部门:");	
		        } else {
		        	writer.writeToCurrentCell(key + ":");
		        }
		        createHeader(writer, cellStyle);
		        int dCount = 0;
		        int lCount = 0;
		        int hCount = 0;
		        int otherCount = 0;
		        int index = 1;
		        for (Iterator iter = computerVOList.iterator(); iter.hasNext();) {
					ComputerVO vo = (ComputerVO) iter.next();
					writer.newRow();
					writer.newCell(tempCellStyle);
					writer.writeToCurrentCell(String.valueOf(index));
					writer.newCell(tempCellStyle);
					writer.writeToCurrentCell(vo.getDeviceName());
					writer.newCell(tempCellStyle);
					final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn()));
					writer.writeToCurrentCell(tcpVO.getAddress());
					writer.newCell(tempCellStyle);
					writer.writeToCurrentCell(macMap.get(String.valueOf(vo.getComputerIdn())));
					writer.newCell(tempCellStyle);
					CompSystemVO compSystemVO = (CompSystemVO)modelMap.get(String.valueOf(vo.getComputerIdn()));
					writer.writeToCurrentCell(compSystemVO.getModel());
					writer.newCell(tempCellStyle);
					writer.writeToCurrentCell(positionMap.get(String.valueOf(vo.getComputerIdn())));
					if(null != compSystemVO.getManufacturer() && compSystemVO.getManufacturer().indexOf("Dell") != -1) {
						dCount++;
					} else if(null != compSystemVO.getManufacturer() && compSystemVO.getManufacturer().indexOf("Lenovo") != -1) {
						lCount++;
					} else if(null != compSystemVO.getManufacturer() 
							&& (compSystemVO.getManufacturer().indexOf("HP") != -1
							|| compSystemVO.getManufacturer().indexOf("Hewlett-Packard") != -1)) {
						hCount++;
					}else {
						otherCount++;
					}
					index++;
				}
		        writer.newRow();
		        writer.setCurrSheetNum(1);
		        writer.newRow();
		        writer.newRow();
		        writer.newCell(cellStyle);
		        if(Constants.OTHER_DEPT_KEY.equals(key)) {
		        	writer.writeToCurrentCell("其他部门:");	
		        } else {
		        	writer.writeToCurrentCell(key + ":");
		        }
		        writer.newCell(tempCellStyle);
		        writer.writeToCurrentCell(String.valueOf(lCount + dCount + hCount + otherCount));
		        
		        writer.newRow();
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("Dell设备总数:");
		        writer.newCell(tempCellStyle);
		        writer.writeToCurrentCell(String.valueOf(dCount));
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("Lenovo设备总数:");
		        writer.newCell(tempCellStyle);
		        writer.writeToCurrentCell(String.valueOf(lCount));
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("HP设备总数:");
		        writer.newCell(tempCellStyle);
		        writer.writeToCurrentCell(String.valueOf(hCount));
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("其他品牌设备总数:");
		        writer.newCell(tempCellStyle);
		        writer.writeToCurrentCell(String.valueOf(otherCount));
			}
	        
        } catch(Exception e) {
        	logger.error(e.getMessage(), e);
        	throw new AppException(e.getMessage(), e);
        } finally{
            try {
                if(fis != null){                    
                    fis.close();                    
                }
            } catch (IOException e1) {
                logger.error(e1.getMessage(), e1);
            }
        } 
		return wb;
	}
	
	private void createHeader(HSSFWorkbookWriter writer, HSSFCellStyle style) {
		//ReportPropertiesLocator properties = ReportPropertiesLocator.getInstance(true);
		writer.newRow();
		writer.newCell(style);
		writer.writeToCurrentCell("序号");
		writer.newCell(style);
		//writer.writeToCurrentCell(properties.getValue(Constants.REPORT_HEADER_COMPUTERNAME));
		writer.writeToCurrentCell("机器各1�7");
		writer.newCell(style);
//		writer.writeToCurrentCell(properties.getValue(Constants.REPORT_HEADER_COMPUTERIP));
		writer.writeToCurrentCell("IP");
		writer.newCell(style);
	//	writer.writeToCurrentCell(properties.getValue(Constants.REPORT_HEADER_MAC));
		writer.writeToCurrentCell("MAC地址");
		writer.newCell(style);
		//writer.writeToCurrentCell(properties.getValue(Constants.REPORT_HEADER_BRANCHNAME));
		writer.writeToCurrentCell("机器类型");
		writer.newCell(style);
		//writer.writeToCurrentCell(properties.getValue(Constants.REPORT_HEADER_POSITION));
		writer.writeToCurrentCell("位置");
	}
}
