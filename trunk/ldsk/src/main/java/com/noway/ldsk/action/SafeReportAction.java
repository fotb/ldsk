package com.noway.ldsk.action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.noway.ldsk.bo.IDeviceControlActionBO;
import com.noway.ldsk.bo.IHipsActionBO;
import com.noway.ldsk.bo.ISafeReport;
import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.BranchPropertiesLocator;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.vo.SafeReportDTO;
import com.opensymphony.xwork2.ActionContext;

public class SafeReportAction extends DefaultAction {
	private static final long serialVersionUID = -532867721521720421L;
	private static final Logger logger = Logger.getLogger(SafeReportAction.class);
	
	private ISafeReport safeReport;
	private IHipsActionBO hipsActionBO;
	private IDeviceControlActionBO deviceControlActionBO;
	
	private String safeCountJson;
	private String safeDetailsJson;
	
	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
//		Map<String, String> branchMap = unmodeledDataBO
//				.findAllBranch(ReportPropertiesLocator.getInstance(true)
//						.getValue(Constants.PROPERTIES_BRANCH_KEY));

		Map<String, String> branchMap = BranchPropertiesLocator.getInstance(true).getAll();
		Map session = ActionContext.getContext().getSession();
		session.put("BranchMap", branchMap);

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
				branchBuffer.append(addJsonNode("branchName", dto.getBranchName(), false));
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
}
