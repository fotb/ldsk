package com.noway.ldsk.action;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.noway.ldsk.bo.ICompSystemBO;
import com.noway.ldsk.bo.IReportBO;
import com.noway.ldsk.bo.IUnmodeledDataBO;
import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.ReportPropertiesLocator;
import com.noway.ldsk.vo.CompSystemVO;
import com.noway.ldsk.vo.ComputerVO;
import com.noway.ldsk.vo.TcpVO;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeviceAction extends ActionSupport {
	private static final long serialVersionUID = 5281845553348811536L;
	private static final Logger logger = Logger.getLogger(DeviceAction.class);

	private IUnmodeledDataBO unmodeledDataBO;
	private IReportBO reportBO;
	private ICompSystemBO compSystemBO;
	
	private String branchsJson;
	private String branchDeviceJson;
	
	public String getBranchDeviceJson() {
		return branchDeviceJson;
	}

	public void setBranchDeviceJson(String branchDeviceJson) {
		this.branchDeviceJson = branchDeviceJson;
	}

	public String getBranchsJson() {
		return branchsJson;
	}

	public void setBranchsJson(String branchsJson) {
		this.branchsJson = branchsJson;
	}

	public IUnmodeledDataBO getUnmodeledDataBO() {
		return unmodeledDataBO;
	}

	public void setUnmodeledDataBO(IUnmodeledDataBO unmodeledDataBO) {
		this.unmodeledDataBO = unmodeledDataBO;
	}

	public IReportBO getReportBO() {
		return reportBO;
	}

	public void setReportBO(IReportBO reportBO) {
		this.reportBO = reportBO;
	}

	public ICompSystemBO getCompSystemBO() {
		return compSystemBO;
	}

	public void setCompSystemBO(ICompSystemBO compSystemBO) {
		this.compSystemBO = compSystemBO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		Map<String, String> branchMap = unmodeledDataBO
				.findAllBranch(ReportPropertiesLocator.getInstance(true)
						.getValue(Constants.PROPERTIES_BRANCH_KEY));

		Map session = ActionContext.getContext().getSession();
		session.put("BranchMap", branchMap);

//		logger.info(getAllCount());
		return SUCCESS;
	}

	@SuppressWarnings("unchecked")
	public String getAllCount() {
		StringBuffer buffer = new StringBuffer(10000);
		try {
			final int computerCount = reportBO.findAllComputerCount();
			final int dellCount = compSystemBO.findCountByModel("Dell");
			final int lenovoCount = compSystemBO.findCountByModel("Lenovo");
			final int hpCount = compSystemBO.findCountByModel("HP")
					+ compSystemBO.findCountByModel("Hewlett-Packard");
			buffer.append("{");
			
			buffer.append(addJsonNode("computerCount", String.valueOf(computerCount), false));
			buffer.append(addJsonNode("dellCount", String.valueOf(dellCount), false));
			buffer.append(addJsonNode("lenovoCount", String.valueOf(lenovoCount), false));
			buffer.append(addJsonNode("hpCount", String.valueOf(hpCount), false));
			buffer.append(addJsonNode("otherCount", String.valueOf(computerCount - dellCount - lenovoCount - hpCount), false));
			
			
			
			final Map branchMap = reportBO.getAllComputerWitchBranch();
			final Map modelMap = compSystemBO.findAllToMap();
			
			StringBuffer branchBuffer = new StringBuffer(10000);
			branchBuffer.append("[");
			int count = 0;
			for (Iterator iterator = branchMap.keySet().iterator(); iterator
					.hasNext();) {
				final String key = (String) iterator.next();
				List computerVOList = (List) branchMap.get(key);
				branchBuffer.append("{");
				if(Constants.OTHER_DEPT_KEY.equals(key)) {
		        	branchBuffer.append(addJsonNode("BranchName", "其他部门", false));	
		        } else {
		        	branchBuffer.append(addJsonNode("BranchName", key, false));
		        }
		        int dCount = 0;
		        int lCount = 0;
		        int hCount = 0;
		        int otherCount = 0;
		        for (Iterator iter = computerVOList.iterator(); iter.hasNext();) {
					ComputerVO vo = (ComputerVO) iter.next();
					CompSystemVO compSystemVO = (CompSystemVO)modelMap.get(String.valueOf(vo.getComputerIdn()));
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
		        }
		        count++;
		        branchBuffer.append(addJsonNode("dCount", String.valueOf(dCount), false));
		        branchBuffer.append(addJsonNode("lCount", String.valueOf(lCount), false));
		        branchBuffer.append(addJsonNode("hCount", String.valueOf(hCount), false));
		        branchBuffer.append(addJsonNode("oCount", String.valueOf(otherCount), true));
				branchBuffer.append("}");
				if(count < branchMap.size()) {
					branchBuffer.append(",");	
				}
			}
			branchBuffer.append("]");
			buffer.append("\"Branchs\": "); 
			buffer.append(branchBuffer.toString());
			buffer.append("}");
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		branchsJson = buffer.toString();
		logger.info(buffer.toString());
		return SUCCESS;
	}

	private String addJsonNode(String nodeName, String nodeValue, boolean isEnd) {
		StringBuffer buffer = new StringBuffer(1000);
		buffer.append("\"" + nodeName + "\": ");
		buffer.append("\"" + nodeValue + "\"");
		if(!isEnd) {
			buffer.append(",");
		}
		return buffer.toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getBranchDevices() {
		try {
			HttpServletRequest request = (HttpServletRequest) ActionContext
					.getContext().get(ServletActionContext.HTTP_REQUEST);
			final String branchName = request.getParameter("branchName");			
			
			final Map branchMap = reportBO.getAllComputerWitchBranch();
			final Map ipMap = reportBO.findAllIpAddress();
	        final Map macMap = reportBO.findAllMacAddress();
	        final Map positionMap = reportBO.findAllByMetaObjAttrRelationsIdn(ReportPropertiesLocator.getInstance(true).getValue(Constants.PROPERTIES_POSITION_KEY));
	        final Map modelMap = compSystemBO.findAllToMap();	
			
			final List computerVOList = (List)branchMap.get(branchName);
			StringBuffer buffer = new StringBuffer(10000);
			
			
			
	        int dCount = 0;
	        int lCount = 0;
	        int hCount = 0;
	        int otherCount = 0;
			int count = 0;
			StringBuffer deviceBuffer = new StringBuffer(1000);
			deviceBuffer.append("[");
			for (Iterator iter = computerVOList.iterator(); iter.hasNext();) {
				ComputerVO vo = (ComputerVO) iter.next();
				deviceBuffer.append("{");
				deviceBuffer.append(addJsonNode("deviceName", vo.getDeviceName(), false));

				final TcpVO tcpVO = (TcpVO)ipMap.get(String.valueOf(vo.getComputerIdn()));
				deviceBuffer.append(addJsonNode("tcpAddress", tcpVO.getAddress() == null ? "-" : tcpVO.getAddress(), false));

				
				final Object macObj = macMap.get(String.valueOf(vo.getComputerIdn()));
				
				deviceBuffer.append(addJsonNode("macAddress", null == macObj ? "-" : (String)macObj, false));
				final Object compSystemObj = modelMap.get(String.valueOf(vo.getComputerIdn()));
				final CompSystemVO compSystemVO = null == compSystemObj ? null : (CompSystemVO)compSystemObj;
				final String model = null == compSystemVO ? "-" : compSystemVO.getModel();
				deviceBuffer.append(addJsonNode("model", null == model ? "-" : model, false));
				

				final Object positionObj = positionMap.get(String.valueOf(vo.getComputerIdn()));
				
				
				deviceBuffer.append(addJsonNode("position", null == positionObj ? "-" : (String)positionObj, true));
				if(null != compSystemVO) {
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
				} else {
					otherCount++;
				}
				count++;
				deviceBuffer.append("}");
				if(count < computerVOList.size()) {
					deviceBuffer.append(",");	
				}
			}
			deviceBuffer.append("]");
			buffer.append("{");
			buffer.append(addJsonNode("branchName", branchName, false));
			buffer.append(addJsonNode("totalCount", String.valueOf(dCount + lCount + hCount + otherCount), false));
			buffer.append(addJsonNode("dCount", String.valueOf(dCount), false));
			buffer.append(addJsonNode("lCount", String.valueOf(lCount), false));
			buffer.append(addJsonNode("hCount", String.valueOf(hCount), false));
			buffer.append(addJsonNode("oCount", String.valueOf(otherCount), false));
//			buffer.append(addJsonNode("devices", deviceBuffer.toString(), true));
			buffer.append("\"devices\":");
			buffer.append(deviceBuffer.toString());
			buffer.append("}");
			branchDeviceJson = buffer.toString();
			logger.info(branchDeviceJson);
		} catch (AppException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		return SUCCESS;
	}
}
