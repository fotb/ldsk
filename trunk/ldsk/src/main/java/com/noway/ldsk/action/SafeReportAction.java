package com.noway.ldsk.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.noway.ldsk.bo.IComputerBO;
import com.noway.ldsk.bo.IDeviceControlActionBO;
import com.noway.ldsk.bo.IHipsActionBO;
import com.noway.ldsk.bo.IReportBO;
import com.noway.ldsk.bo.ISafeReport;
import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.BranchBean;
import com.noway.ldsk.util.BranchPropertiesLocator;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.DateUtil;
import com.noway.ldsk.vo.ComputerVO;
import com.noway.ldsk.vo.DeviceControlActionVO;
import com.noway.ldsk.vo.HipsActionVO;
import com.noway.ldsk.vo.SafeReportDTO;
import com.noway.ldsk.vo.TcpVO;
import com.opensymphony.xwork2.ActionContext;

public class SafeReportAction extends DefaultAction {
	private static final long serialVersionUID = -532867721521720421L;
	private static final Logger logger = Logger.getLogger(SafeReportAction.class);
	
	private ISafeReport safeReport;
	private IHipsActionBO hipsActionBO;
	private IDeviceControlActionBO deviceControlActionBO;
	private IReportBO reportBO;
	private IComputerBO computerBO;
	
	private String safeCountJson;
	private String safeDetailsJson;
	private List<BranchBean> bList = new ArrayList<BranchBean>();
	public List<BranchBean> getBList() {
		return bList;
	}
	public void setBList(List<BranchBean> list) {
		bList = list;
	}
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
//		Map<String, String> branchMap = unmodeledDataBO
//				.findAllBranch(ReportPropertiesLocator.getInstance(true)
//						.getValue(Constants.PROPERTIES_BRANCH_KEY));

		Properties branchProp = BranchPropertiesLocator.getInstance(true).getAll();
		final List keyList = getSortedKeyList(branchProp);
		//TreeMap map = new TreeMap();
		for (Iterator iter = keyList.iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			BranchBean bean = new BranchBean();
			bean.setBranchName(key.substring(3, key.length()));
			bean.setBranchValue(branchProp.getProperty(key));
			bList.add(bean);
			//map.put(key.substring(3, key.length()), branchProp.getProperty(key));
		}
		
		Map session = ActionContext.getContext().getSession();
		//session.put("BranchList", map);
		session.put("SubBranchList", new ArrayList());
		
		Map hipsActionMap = Constants.HIPS_ACTION_MAP;
		hipsActionMap.put("other", "其他事件");
		Map deviceControlMap = Constants.DEVICE_CONTROL_ACTION_MAP;
		deviceControlMap.put("other", "其他事件");
		session.put("hipsActionMap", hipsActionMap);
		session.put("deviceControlMap", deviceControlMap);
		

//		logger.info(getAllCount());
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	public String getAllCount() {
		try {

			final List<SafeReportDTO> list = safeReport.getSafeReportDataSource();
			
	        final int totalHipsCount = hipsActionBO.getCount();
	        final int totalDeviceControlCount = deviceControlActionBO.getCount();
	        StringBuffer jsonBuffer = new StringBuffer(1000);
	        jsonBuffer.append("{");
	        jsonBuffer.append(addJsonNode("totalCount", String.valueOf(totalHipsCount + totalDeviceControlCount), false));
	        jsonBuffer.append(addJsonNode("totalHipsCount", String.valueOf(totalHipsCount), false));
	        jsonBuffer.append(addJsonNode("totalDeviceControlCount", String.valueOf(totalDeviceControlCount), false));
	        
			StringBuffer branchBuffer = new StringBuffer(10000);
			branchBuffer.append("[");
			int count = 0;
			for (Iterator iter = list.iterator(); iter.hasNext();) {
				SafeReportDTO dto = (SafeReportDTO) iter.next();
				branchBuffer.append("{");
				branchBuffer.append(addJsonNode("branchName", dto.getBranchName().substring(3, dto.getBranchName().length()), false));
				branchBuffer.append(getBranchStatsInfo(dto.getHipsActionMap(), Constants.HIPS_ACTION_MAP, "hips"));
				branchBuffer.append(addJsonNode("hipsTotalCount", String.valueOf(dto.getHipsActionCount()), false));
				branchBuffer.append(getBranchStatsInfo(dto.getDeviceControlActionMap(), Constants.DEVICE_CONTROL_ACTION_MAP, "dc"));
				branchBuffer.append(addJsonNode("dcTotalCount", String.valueOf(dto.getHipsActionCount()), true));
				branchBuffer.append("}");
				count++;
				if(count < list.size()) {
					branchBuffer.append(",");
				}
			}
			branchBuffer.append("]");
			jsonBuffer.append("\"Branchs\": "); 
			jsonBuffer.append(branchBuffer.toString());
			jsonBuffer.append("}");
			safeCountJson = jsonBuffer.toString();
	        logger.info(jsonBuffer.toString());
		} catch (AppException e) {
			logger.error("Error occured:" + e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error occured:" + e.getMessage(), e);
		}
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	private String getBranchStatsInfo(Map actionMap, Map constantsMap, String flag) {
		StringBuffer buffer = new StringBuffer(500);
		List otherList = new ArrayList();
		for (Iterator iter1 = actionMap.keySet().iterator(); iter1
				.hasNext();) {
			String actionCode = (String) iter1.next();
			List tempList = (List) actionMap.get(actionCode);
			if(constantsMap.containsKey(actionCode)) {
				buffer.append(addJsonNode("actionCode_" + actionCode, String.valueOf(tempList.size()), false));
			} else {
				otherList.addAll(tempList);
			}
		}
		if(!otherList.isEmpty()) {
			buffer.append(addJsonNode(flag + "other", String.valueOf(otherList.size()), false));
		}
		return buffer.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getSafeDetails() {
		HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(ServletActionContext.HTTP_REQUEST);
		final String branchName = request.getParameter("branchName");
		final String safeType = request.getParameter("safeType");
		final String hipsActionCode = request.getParameter("hipsActionType");
		final String deviceControlCode = request.getParameter("deviceControlType");

		logger.info("branchName: --------------" + branchName + "-safeType:-" + safeType + "--hipsActionCode:-" + hipsActionCode + "-deviceControlCode:-" + deviceControlCode  );
		try {
			Map ipMap = reportBO.findAllIpAddress();
	    	Map computerMap = computerBO.findAllToMap();
	    	
			List list = new ArrayList();
			String jsonStr = "";
			if("1".equals(safeType)) {
				list = safeReport.getSafeDetailBySubBranchBranchAndActionType(branchName, safeType, hipsActionCode);
				jsonStr = generateHipsJson(ipMap, computerMap, list);
			} else {
				list = safeReport.getSafeDetailBySubBranchBranchAndActionType(branchName, safeType, deviceControlCode);
				jsonStr = generateDCJson(ipMap, computerMap, list);
			}
			safeDetailsJson = jsonStr;
			logger.info(jsonStr);
		} catch(Exception e) {
			logger.error("Error occured: " + e.getMessage(), e);
		}
		
		
		return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	private String generateHipsJson(Map ipMap, Map computerMap, List list) {
		StringBuffer jsonBuffer = new StringBuffer(1000);
		jsonBuffer.append("{\"details\": [");
		int index = 1;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			HipsActionVO vo = (HipsActionVO) iter.next();
			jsonBuffer.append("{");
			jsonBuffer.append(addJsonNode("index", String.valueOf(index), false));
			final ComputerVO computerVO = (ComputerVO) computerMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			jsonBuffer.append(addJsonNode("deviceName", computerVO.getDeviceName(), false));
			final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			jsonBuffer.append(addJsonNode("IP", tcpVO.getAddress(), false));
			jsonBuffer.append(addJsonNode("actionDate", DateUtil.format(vo.getActionDate(), "yyyy-MM-dd HH:mm:ss"), false));
			jsonBuffer.append(addJsonNode("appName", vo.getApplication().replace("\\", "-"), true));
			jsonBuffer.append("}");
			index++;
			if(index <= list.size()) {
				jsonBuffer.append(",");	
			}
		}
		jsonBuffer.append("]}");
		return jsonBuffer.toString();
	}
	
	@SuppressWarnings("unchecked")
	private String generateDCJson(Map ipMap, Map computerMap, List list) {
		StringBuffer jsonBuffer = new StringBuffer(1000);
		jsonBuffer.append("{\"details\": [");
		int index = 1;
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			DeviceControlActionVO vo = (DeviceControlActionVO) iter.next();
			jsonBuffer.append("{");
			jsonBuffer.append(addJsonNode("index", String.valueOf(index), false));
			final ComputerVO computerVO = (ComputerVO) computerMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			jsonBuffer.append(addJsonNode("deviceName", computerVO.getDeviceName(), false));
			final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn().intValue()));
			jsonBuffer.append(addJsonNode("IP", tcpVO.getAddress(), false));
			jsonBuffer.append(addJsonNode("actionDate", DateUtil.format(vo.getActionDate(), "yyyy-MM-dd HH:mm:ss"), false));
			jsonBuffer.append(addJsonNode("desc", vo.getDescription(), false));
			jsonBuffer.append(addJsonNode("deviceId", vo.getDeviceId(), true));
			jsonBuffer.append("}");
			index++;
			if(index <= list.size()) {
				jsonBuffer.append(",");	
			}
		}
		jsonBuffer.append("]}");
		return jsonBuffer.toString();
	}
	public ISafeReport getSafeReport() {
		return safeReport;
	}

	public void setSafeReport(ISafeReport safeReport) {
		this.safeReport = safeReport;
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
	public String getSafeCountJson() {
		return safeCountJson;
	}

	public void setSafeCountJson(String safeCountJson) {
		this.safeCountJson = safeCountJson;
	}

	public String getSafeDetailsJson() {
		return safeDetailsJson;
	}

	public void setSafeDetailsJson(String safeDetailsJson) {
		this.safeDetailsJson = safeDetailsJson;
	}
	public IReportBO getReportBO() {
		return reportBO;
	}
	public void setReportBO(IReportBO reportBO) {
		this.reportBO = reportBO;
	}
	public IComputerBO getComputerBO() {
		return computerBO;
	}
	public void setComputerBO(IComputerBO computerBO) {
		this.computerBO = computerBO;
	}
}
