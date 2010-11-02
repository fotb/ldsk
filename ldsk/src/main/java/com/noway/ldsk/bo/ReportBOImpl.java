package com.noway.ldsk.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.noway.ldsk.dao.IComputerDAO;
import com.noway.ldsk.dao.INetworkSoftwareDAO;
import com.noway.ldsk.dao.ITcpDAO;
import com.noway.ldsk.dao.IUnmodeledDataDAO;
import com.noway.ldsk.util.AppException;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.ReportPropertiesLocator;
import com.noway.ldsk.vo.ComputerVO;
@SuppressWarnings("unchecked")
public class ReportBOImpl implements IReportBO {
	private static final Logger logger = Logger.getLogger(ReportBOImpl.class);
	
	private IComputerDAO computerDAO;
	private INetworkSoftwareDAO networkSoftwareDAO;
	private ITcpDAO tcpDAO;
	private IUnmodeledDataDAO unmodeledDataDAO;
	
	public Map findAllByMetaObjAttrRelationsIdn(String metaObjAttrRelationsIdn) {
		return unmodeledDataDAO.findAllByMetaObjAttrRelationsIdn(metaObjAttrRelationsIdn);
	}

	public int findAllComputerCount() {
		return computerDAO.findAllCount();
	}

	public Map findAllIpAddress() {
		return tcpDAO.findAll();
	}

	public Map findAllMacAddress() {
		return networkSoftwareDAO.findAll();
	}

	public Map getAllComputerWitchBranch() throws AppException{
		final Map branchMap = unmodeledDataDAO.findAllByMetaObjAttrRelationsIdn(ReportPropertiesLocator.getInstance(true).getValue(Constants.PROPERTIES_BRANCH_KEY));
		final List computerList = computerDAO.findAll();
		Map map = new HashMap();
		for (Iterator iterator = computerList.iterator(); iterator.hasNext();) {
			final ComputerVO vo = (ComputerVO) iterator.next();
			Object obj = branchMap.get(String.valueOf(vo.getComputerIdn()));
			String branchName = "";
			if(null != obj) {
				branchName = obj.toString();
			} else {
				branchName = Constants.OTHER_DEPT_KEY;
				logger.info("No branch name found with computer_idn = " + vo.getComputerIdn());
			}			
			if(map.containsKey(branchName)) {
				List list = (List) map.get(branchName);
				list.add(vo);
			} else {
				List list = new ArrayList();
				list.add(vo);
				map.put(branchName, list);
			}
		}
		return map;
	}
	
	
	
	public INetworkSoftwareDAO getNetworkSoftwareDAO() {
		return networkSoftwareDAO;
	}

	public void setNetworkSoftwareDAO(INetworkSoftwareDAO networkSoftwareDAO) {
		this.networkSoftwareDAO = networkSoftwareDAO;
	}

	public ITcpDAO getTcpDAO() {
		return tcpDAO;
	}

	public void setTcpDAO(ITcpDAO tcpDAO) {
		this.tcpDAO = tcpDAO;
	}

	public IUnmodeledDataDAO getUnmodeledDataDAO() {
		return unmodeledDataDAO;
	}

	public void setUnmodeledDataDAO(IUnmodeledDataDAO unmodeledDataDAO) {
		this.unmodeledDataDAO = unmodeledDataDAO;
	}

	public IComputerDAO getComputerDAO() {
		return computerDAO;
	}

	public void setComputerDAO(IComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
}
