package com.noway.ldsk.bo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.core.io.ClassPathResource;

import com.noway.ldsk.dao.IComputerDAO;
import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.BranchPropertiesLocator;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.DateUtil;
import com.noway.ldsk.util.ReportPropertiesLocator;
import com.noway.ldsk.vo.ComputerVO;
import com.noway.ldsk.vo.DeviceControlActionVO;
import com.noway.ldsk.vo.HipsActionVO;
import com.noway.ldsk.vo.SafeReportDTO;
import com.noway.ldsk.vo.TcpVO;
@SuppressWarnings("unchecked")
public class SafeReportImpl implements ISafeReport {
	private static final Logger logger = Logger.getLogger(SafeReportImpl.class);
	
	private IHipsActionBO hipsActionBO;
	private IDeviceControlActionBO deviceControlActionBO;
	private IReportBO reportBO;
	private IComputerDAO computerDAO;
	
	private Map ipMap = new HashMap();
	private Map computerMap = new HashMap();

	public IComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(IComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public IReportBO getReportBO() {
		return reportBO;
	}

	public void setReportBO(IReportBO reportBO) {
		this.reportBO = reportBO;
	}

	public IHipsActionBO getHipsActionBO() {
		return hipsActionBO;
	}

	public void setHipsActionBO(IHipsActionBO hipsActionBO) {
		this.hipsActionBO = hipsActionBO;
	}

	public IDeviceControlActionBO getDeviceControlActionBO() {
		return deviceControlActionBO;
	}

	public void setDeviceControlActionBO(
			IDeviceControlActionBO deviceControlActionBO) {
		this.deviceControlActionBO = deviceControlActionBO;
	}
	
	public List getSafeDetailBySubBranchBranchAndActionType(final String subBranchName, final String safeType, final String actionCode) throws AppException {
		final Map branchComputerMap = reportBO.getAllComputerWitchBranch();
		final Map hipsGroupByComputerIdnMap = hipsActionBO.getHipsActionGroupByComputerIdn();
		final Map deviceControlActionByComputerIdnMap = deviceControlActionBO.getHipsActionGroupByComputerIdn();
		Properties branchProp = BranchPropertiesLocator.getInstance(true).getAll();
		List keyList = getSortedKeyList(branchProp);

		List List = new ArrayList();
		
		for (Iterator iter = keyList.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			final String[] branchNameAry = ((String)branchProp.get(key)).split(",");
			for (int i = 0; i < branchNameAry.length; i++) {
				final String branchName = branchNameAry[i];
				if(subBranchName.equals(branchName)) {
					final List computerVOList = (List) branchComputerMap.get(branchName);
					if(null != computerVOList) {
						for (Iterator iter1 = computerVOList.iterator(); iter1.hasNext();) {
							final ComputerVO computerVO = (ComputerVO)iter1.next();
							if("1".equals(safeType)) {
								final List tempList = (List)hipsGroupByComputerIdnMap.get(computerVO.getComputerIdn());
								if(null != tempList) {
									List.addAll(tempList);
								}
							} else {
								final List dcList = (List) deviceControlActionByComputerIdnMap.get(computerVO.getComputerIdn());
								if (null != dcList) {
									List.addAll(dcList);
								}
							}
						}
					}
					break;
				} else {
					continue;
				}
			}
		}
		
		List resultList = new ArrayList();
		for (Iterator iter = List.iterator(); iter.hasNext();) {
			Object object = (Object) iter.next();
			if("1".equals(safeType)) {
				HipsActionVO vo = (HipsActionVO)object;
				if(String.valueOf(vo.getActionCode()).equals(actionCode)) {
					resultList.add(vo);
				}
			} else {
				DeviceControlActionVO vo = (DeviceControlActionVO)object;
				if(String.valueOf(vo.getActionCode()).equals(actionCode)) {
					resultList.add(vo);
				}
			}
		}
		return resultList;
	}

	public List<SafeReportDTO> getSafeReportDataSource() throws AppException {
		final Map branchComputerMap = reportBO.getAllComputerWitchBranch();
		final Map hipsGroupByComputerIdnMap = hipsActionBO.getHipsActionGroupByComputerIdn();
		final Map deviceControlActionByComputerIdnMap = deviceControlActionBO.getHipsActionGroupByComputerIdn();
		Properties branchProp = BranchPropertiesLocator.getInstance(true).getAll();
		List keyList = getSortedKeyList(branchProp);

		Map hipsMap = new HashMap();
		Map deviceControlMap = new HashMap();
		for (Iterator iter = keyList.iterator(); iter.hasNext();) {
			String key = (String) iter.next();

			final String[] branchNameAry = ((String)branchProp.get(key)).split(",");
			List hipsTempList = new ArrayList();
			List dcTempList = new ArrayList();
			for (int i = 0; i < branchNameAry.length; i++) {
				final String branchName = branchNameAry[i];
				final List computerVOList = (List) branchComputerMap.get(branchName);
				if(null != computerVOList) {
					for (Iterator iter1 = computerVOList.iterator(); iter1.hasNext();) {
						final ComputerVO computerVO = (ComputerVO)iter1.next();
						final List list = (List)hipsGroupByComputerIdnMap.get(computerVO.getComputerIdn());
						if(null != list) {
							hipsTempList.addAll(list);
						}
						
						final List dcList = (List) deviceControlActionByComputerIdnMap.get(computerVO.getComputerIdn());
						if (null != dcList) {
							dcTempList.addAll(dcList);
						}
					}
				}
			}
			hipsMap.put(key, hipsTempList);
			deviceControlMap.put(key, dcTempList);
		}
//		for (final Iterator iterator = branchComputerMap.keySet().iterator(); iterator.hasNext();) {
//			final String branch = (String) iterator.next();
//			final List computerVOList = (List) branchComputerMap.get(branch);
//			for (Iterator iter1 = computerVOList.iterator(); iter1.hasNext();) {
//				final ComputerVO computerVO = (ComputerVO)iter1.next();
//				final List list = (List)hipsGroupByComputerIdnMap.get(computerVO.getComputerIdn());
//				if(null != list) {
//					if(hipsMap.containsKey(branch)) {
//						final List temp = (List)hipsMap.get(branch);
//						temp.addAll(list);
//					} else {
//						List temp = new ArrayList();
//						temp.addAll(list);
//						hipsMap.put(branch, temp);
//					}
//				}
//				
//				final List dcList = (List) deviceControlActionByComputerIdnMap.get(computerVO.getComputerIdn());
//				if (null != dcList) {
//					if (deviceControlMap.containsKey(branch)) {
//						final List temp = (List) deviceControlMap.get(branch);
//						temp.addAll(dcList);
//					} else {
//						List temp = new ArrayList();
//						temp.addAll(dcList);
//						deviceControlMap.put(branch, temp);
//					}
//				}
//			}
//		}
		
		
		List result = new ArrayList();
		
		for (Iterator iter = keyList.iterator(); iter.hasNext();) {
			final String branchName = (String)iter.next();
			SafeReportDTO dto = new SafeReportDTO();
			dto.setBranchName(branchName);
			final List list = (List)hipsMap.get(branchName);
			
			dto.setHipsActionCount(list.size());
			Map hipsByCodeMap = new HashMap();
			for (Iterator iter2 = list.iterator(); iter2.hasNext();) {
				HipsActionVO hipsActionVO = (HipsActionVO) iter2.next();
				final String actionCode = String.valueOf(hipsActionVO.getActionCode().intValue());
				if(hipsByCodeMap.containsKey(actionCode)) {
					final List temp = (List)hipsByCodeMap.get(actionCode);
					temp.add(hipsActionVO);
				} else {
					List temp = new ArrayList();
					temp.add(hipsActionVO);
					hipsByCodeMap.put(actionCode, temp);
				}
			}
			
			dto.setHipsActionMap(hipsByCodeMap);
			final List dcList = (List)deviceControlMap.get(branchName);
			dto.setDeviceControlActionCount(dcList.size());
			Map dcByCodeMap = new HashMap();
			for (Iterator iter2 = dcList.iterator(); iter2.hasNext();) {
				DeviceControlActionVO vo = (DeviceControlActionVO) iter2.next();
				final String actionCode = String.valueOf(vo.getActionCode().intValue());
				if(dcByCodeMap.containsKey(actionCode)) {
					final List temp = (List)dcByCodeMap.get(actionCode);
					temp.add(vo);
				} else {
					List temp = new ArrayList();
					temp.add(vo);
					dcByCodeMap.put(actionCode, temp);
				}
			}
			dto.setDeviceControlActionMap(dcByCodeMap);
			result.add(dto);
		}
		return result;
	}

	private List getSortedKeyList(Properties branchProp) {
		List keyList = new ArrayList();
		for (final Iterator iter = branchProp.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			keyList.add(key);
		}
		Collections.sort(keyList, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				final String key1 = o1.substring(1, 3);
				final String key2 = o2.substring(1, 3);
				return key1.compareTo(key2);
			}
		});
		return keyList;
	}

	public HSSFWorkbook renderExcelReport() throws AppException {
		
        POIFSFileSystem fs;
        HSSFWorkbook wb = null;
        final FileInputStream fis = null;
        try {
        	
        	ipMap = reportBO.findAllIpAddress();
        	computerMap = computerDAO.findAllToMap();
        	
        	final String templateFile = ReportPropertiesLocator.getInstance(true).getValue(Constants.REPORT_SAFE_INFO_KEY);
        	final ClassPathResource resource = new ClassPathResource(templateFile);
			fs = new POIFSFileSystem(resource.getInputStream());
			wb = new HSSFWorkbook(fs);

			final String sheet1Name = "安全汇�1�7�表";
			final String sheet2Name = "安全活动详细衄1�7";
          
	        wb.setSheetName(0, sheet1Name);
	        wb.setSheetName(1, sheet2Name);
	        
	        final HSSFSheet sheet = wb.getSheetAt(0);
	        final HSSFRow firstRow = sheet.getRow(0);  
	        final HSSFWorkbookWriter writer = new HSSFWorkbookWriter(wb, firstRow);
	        
	        HSSFCellStyle cellStyle = sheet.getRow(1).getCell(0).getCellStyle();
	        HSSFCellStyle tempCellStyle = sheet.getRow(1).getCell(1).getCellStyle();
	        
	        final int totalHipsCount = hipsActionBO.findAll().size();
	        final int totalDeviceControlCount = deviceControlActionBO.findAll().size();
	        
//	        HSSFRow hssfRow = sheet.getRow(0);
	        writer.writeTo(0, 1, 1, String.valueOf(totalHipsCount + totalDeviceControlCount));
	        writer.writeTo(0, 1, 4, String.valueOf(totalHipsCount));
	        writer.writeTo(0, 1, 6, String.valueOf(totalDeviceControlCount));
	        writer.newRow();
	        
	        List sourceList = getSafeReportDataSource();
	        for (Iterator iter = sourceList.iterator(); iter.hasNext();) {
				SafeReportDTO dto = (SafeReportDTO) iter.next();
				writer.setCurrSheetNum(0);
				writer.newRow();
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell(dto.getBranchName());
		        writer.setCurrSheetNum(1);
		        writer.newRow();
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell(dto.getBranchName());
		        
		        writerSatInfo(writer, cellStyle, tempCellStyle, 
		        		"主机侵入保护安全活动次数＄1�7", String.valueOf(dto.getHipsActionCount()), 
		        		dto.getHipsActionMap(), Constants.HIPS_ACTION_MAP);
		        
		        writerSatInfo(writer, cellStyle, tempCellStyle, 
		        		"设备控制次数＄1�7", String.valueOf(dto.getDeviceControlActionCount()), 
		        		dto.getDeviceControlActionMap(), Constants.DEVICE_CONTROL_ACTION_MAP);
		        
		        writer.setCurrSheetNum(1);
		        writer.newRow();
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("主机侵入保护详细信息");
		        writerHipsDetailInfo(writer, cellStyle, tempCellStyle, dto.getHipsActionMap());

		        writer.newRow();
		        writer.setCurrSheetNum(1);
		        writer.newRow();
		        writer.newCell(cellStyle);
		        writer.writeToCurrentCell("设备控制详细信息");
		        writerDeviceControlDetailInfo(writer, cellStyle, tempCellStyle, dto.getDeviceControlActionMap());
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

	private void writerSatInfo(final HSSFWorkbookWriter writer,
			HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle,
			String title, String totalCount, Map actionMap, Map constantsMap) {
        writer.setCurrSheetNum(0);
        writer.newRow();
		writer.newCell(cellStyle);
		//writer.writeToCurrentCell("主机侵入保护安全活动次数＄1�7");
		writer.writeToCurrentCell(title);
		writer.newCell(tempCellStyle);
		writer.writeToCurrentCell(totalCount);
		
//		Map hipsByCodeMap = dto.getHipsActionMap();
		List otherList = new ArrayList();
		int index = 1;
		for (Iterator iter1 = actionMap.keySet().iterator(); iter1
				.hasNext();) {
			String actionCode = (String) iter1.next();
			List tempList = (List) actionMap.get(actionCode);
			if(constantsMap.containsKey(actionCode)) {
				writer.newRow();
				writer.newCell(tempCellStyle);
				writer.newCell(tempCellStyle);
				writer.newCell(tempCellStyle);
				writer.writeToCurrentCell(String.valueOf(index));
				writer.newCell(cellStyle);
				writer.writeToCurrentCell(constantsMap.get(actionCode) + ":");					
				writer.newCell(tempCellStyle);
				writer.writeToCurrentCell(String.valueOf(tempList.size()));		
				
				index++;
			} else {
				otherList.addAll(tempList);
			}
		}
		if(!otherList.isEmpty()) {
		    writer.newRow();
			writer.newCell(tempCellStyle);
			writer.newCell(tempCellStyle);
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(String.valueOf(index++));
			writer.newCell(cellStyle);
			writer.writeToCurrentCell("其他事件:");					
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(String.valueOf(otherList.size()));
		}
		writer.newRow();
	}
	
	private void writerHipsDetailInfo(final HSSFWorkbookWriter writer,HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle,Map actionMap) {
		writer.setCurrSheetNum(1);
		List otherList = new ArrayList();
		
		for (Iterator iter1 = actionMap.keySet().iterator(); iter1
				.hasNext();) {
			String actionCode = (String) iter1.next();
			List tempList = (List) actionMap.get(actionCode);
			if(Constants.HIPS_ACTION_MAP.containsKey(actionCode)) {
				writerHipsHeader(writer, cellStyle, tempCellStyle, Constants.HIPS_ACTION_MAP.get(actionCode).toString());
				writerHipsRowInfo(writer, tempCellStyle,tempList);
			} else {
				otherList.addAll(tempList);
			}
		}
		if(!otherList.isEmpty()) {
			writerHipsHeader(writer, cellStyle, tempCellStyle, "其他事件");
			writerHipsRowInfo(writer, tempCellStyle, otherList);
		}
	}

	private void writerHipsRowInfo(final HSSFWorkbookWriter writer,
			HSSFCellStyle tempCellStyle, List tempList) {
		int index = 1;
		for (Iterator iter = tempList.iterator(); iter
				.hasNext();) {
			HipsActionVO vo = (HipsActionVO) iter.next();
			writer.newRow();
			writer.newCell(tempCellStyle);
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(String.valueOf(index));
			writer.newCell(tempCellStyle);
			final ComputerVO computerVO = (ComputerVO) computerMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			writer.writeToCurrentCell(computerVO.getDeviceName());
			writer.newCell(tempCellStyle);					
			final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			writer.writeToCurrentCell(tcpVO.getAddress());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(DateUtil.format(vo.getActionDate(), "yyyy-MM-dd HH:mm:ss"));
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getApplication());
			index++;
		}
		writer.newRow();
	}
	
	
	private void writerDeviceControlDetailInfo(final HSSFWorkbookWriter writer,HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle,Map actionMap) {
		writer.setCurrSheetNum(1);
		
		List otherList = new ArrayList();
		
		for (Iterator iter1 = actionMap.keySet().iterator(); iter1
				.hasNext();) {
			String actionCode = (String) iter1.next();
			List tempList = (List) actionMap.get(actionCode);
			if(Constants.DEVICE_CONTROL_ACTION_MAP.containsKey(actionCode)) {
				if(Constants.DEVICE_CONTROL_ACTION_CODE_115.equals(actionCode) || Constants.DEVICE_CONTROL_ACTION_CODE_116.equals(actionCode)) {
					writerDeviceControlHeader(writer, cellStyle, tempCellStyle,Constants.DEVICE_CONTROL_ACTION_MAP.get(actionCode).toString());
					writerDeviceControlRowInfo(writer, tempCellStyle, ipMap, tempList);
				} else {
					writerDeviceControlSpecialHeader(writer, cellStyle, tempCellStyle,Constants.DEVICE_CONTROL_ACTION_MAP.get(actionCode).toString());
					writerDeviceControlSpecialRowInfo(writer, tempCellStyle, ipMap, tempList);
				}
				
			} else {
				otherList.addAll(tempList);
			}
		}
		if(!otherList.isEmpty()) {
			writerDeviceControlHeader(writer, cellStyle, tempCellStyle,"其他事件");
			writerDeviceControlRowInfo(writer, tempCellStyle, ipMap, otherList);
		}
		
	}
	
	
	private void writerDeviceControlRowInfo(final HSSFWorkbookWriter writer,
			HSSFCellStyle tempCellStyle, final Map ipMap, List tempList) {
		int index = 1;
		for (Iterator iter = tempList.iterator(); iter
				.hasNext();) {
			DeviceControlActionVO vo = (DeviceControlActionVO) iter.next();
			writer.newRow();
			writer.newCell(tempCellStyle);
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(String.valueOf(index));
			writer.newCell(tempCellStyle);
			final ComputerVO computerVO = (ComputerVO) computerMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			writer.writeToCurrentCell(computerVO.getDeviceName());
			writer.newCell(tempCellStyle);					
			final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			writer.writeToCurrentCell(tcpVO.getAddress());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(DateUtil.format(vo.getActionDate(), "yyyy-MM-dd HH:mm:ss"));
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getDescription());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getDeviceId());
			index++;
		}
		writer.newRow();
	}
	
	
	private void writerDeviceControlSpecialRowInfo(final HSSFWorkbookWriter writer,
			HSSFCellStyle tempCellStyle, final Map ipMap, List tempList) {
		int index = 1;
		for (Iterator iter = tempList.iterator(); iter
				.hasNext();) {
			DeviceControlActionVO vo = (DeviceControlActionVO) iter.next();
			writer.newRow();
			writer.newCell(tempCellStyle);
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(String.valueOf(index));
			writer.newCell(tempCellStyle);
			final ComputerVO computerVO = (ComputerVO) computerMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			writer.writeToCurrentCell(computerVO.getDeviceName());
			writer.newCell(tempCellStyle);					
			final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn()));
			writer.writeToCurrentCell(tcpVO.getAddress());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(DateUtil.format(vo.getActionDate(), "yyyy-MM-dd HH:mm:ss"));
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getDescription());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getHardwareId());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getService());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getDeviceClass());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getEnumerator());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getVendorId());
			writer.newCell(tempCellStyle);
			writer.writeToCurrentCell(vo.getDeviceId());
			index++;
		}
		writer.newRow();
	}
	
	private void writerHipsHeader(final HSSFWorkbookWriter writer, HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle, String subject) {	
        writer.setCurrSheetNum(1);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell(subject);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("序号");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("设备名称");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("ip地址");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("操作日期");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("应用程序名称");
	}
	
	private void writerDeviceControlHeader(final HSSFWorkbookWriter writer, HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle, String subject) {	
        writer.setCurrSheetNum(1);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell(subject);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("序号");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("设备名称");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("ip地址");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("操作日期");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("说明");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("硬件ID");
	}
	
	
	private void writerDeviceControlSpecialHeader(final HSSFWorkbookWriter writer, HSSFCellStyle cellStyle, HSSFCellStyle tempCellStyle, String subject) {	
        writer.setCurrSheetNum(1);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell(subject);
		writer.newRow();
		writer.newCell(tempCellStyle);
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("序号");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("设备名称");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("ip地址");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("操作日期");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("说明");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("硬件ID");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("服务");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("籄1�7");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("枚举噄1�7");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("供应商ID");
		writer.newCell(cellStyle);
		writer.writeToCurrentCell("设备ID");
	}
}
