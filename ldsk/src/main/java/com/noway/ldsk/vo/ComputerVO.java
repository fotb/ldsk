package com.noway.ldsk.vo;

import java.io.Serializable;
import java.util.Date;

public class ComputerVO implements Serializable {
	private static final long serialVersionUID = 7555138342758616578L;
	
	private Integer computerIdn;
	private String deviceId;
	private String type;
	private String ntDomainController;
	private String deviceName;
	private String description;
	private String loginName;
	private String fullName;
	private String displayName;
	private Integer swNumFiles;
	private Date hwLastScanDate;
	private Date swLastScanDate;
	private Date vaLastScanDate;
	private Date spywarelastScanDate;
	private Date securitylastScanDate;
	private Date updateLastScanDate;
	private Date userDefinedlastScanDate;
	private Date blockedLastScanDate;
	private Date patchSwLastScanDate;
	private Date patchDriversLastScanDate;
	private Date patchAvLastScanDate;
	private Date complianceLastScanDate;
	private String inventoryServer;
	private Integer notifyOnChange;
	private String primaryOwner;
	private Date lastUpdInvSvr;
	private String scanType;
	private String domainName;
	private String model;
	private String manufacturer;
	private String workgroup;
	private String modelNum;
	private String flpyDrvCnt;
	private Integer RamCache;
	private String os2Comment;
	private String netwareAddr;
	private Integer mumMultiMedFiles;
	private Integer multiMedSize;
	private String multiMedExt;
	private Integer computerExistsTime;
	private String lastDeployedOsImage;
	private String coprocessor;
	private String pmmu;
	private String computerLocation;
	private String ldapLocation;
	private String ownerPhone;
	private String ownerEmail;
	private String companyName;
	private String ipmiGuid;
	private String amtGuid;
	private String serverType;
	private String hwMonitoringType;
	private String mainBoardOemName;
	private String systemOemName;
	private String workFlowStatusId;
	private Integer dsViewPort;
	private Date recordDate;
	private String computerName;
	public Integer getComputerIdn() {
		return computerIdn;
	}
	public void setComputerIdn(Integer computerIdn) {
		this.computerIdn = computerIdn;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNtDomainController() {
		return ntDomainController;
	}
	public void setNtDomainController(String ntDomainController) {
		this.ntDomainController = ntDomainController;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	public Integer getSwNumFiles() {
		return swNumFiles;
	}
	public void setSwNumFiles(Integer swNumFiles) {
		this.swNumFiles = swNumFiles;
	}
	public Date getHwLastScanDate() {
		return hwLastScanDate;
	}
	public void setHwLastScanDate(Date hwLastScanDate) {
		this.hwLastScanDate = hwLastScanDate;
	}
	public Date getSwLastScanDate() {
		return swLastScanDate;
	}
	public void setSwLastScanDate(Date swLastScanDate) {
		this.swLastScanDate = swLastScanDate;
	}
	public Date getVaLastScanDate() {
		return vaLastScanDate;
	}
	public void setVaLastScanDate(Date vaLastScanDate) {
		this.vaLastScanDate = vaLastScanDate;
	}
	public Date getSpywarelastScanDate() {
		return spywarelastScanDate;
	}
	public void setSpywarelastScanDate(Date spywarelastScanDate) {
		this.spywarelastScanDate = spywarelastScanDate;
	}
	public Date getSecuritylastScanDate() {
		return securitylastScanDate;
	}
	public void setSecuritylastScanDate(Date securitylastScanDate) {
		this.securitylastScanDate = securitylastScanDate;
	}
	public Date getUpdateLastScanDate() {
		return updateLastScanDate;
	}
	public void setUpdateLastScanDate(Date updateLastScanDate) {
		this.updateLastScanDate = updateLastScanDate;
	}
	public Date getUserDefinedlastScanDate() {
		return userDefinedlastScanDate;
	}
	public void setUserDefinedlastScanDate(Date userDefinedlastScanDate) {
		this.userDefinedlastScanDate = userDefinedlastScanDate;
	}
	public Date getBlockedLastScanDate() {
		return blockedLastScanDate;
	}
	public void setBlockedLastScanDate(Date blockedLastScanDate) {
		this.blockedLastScanDate = blockedLastScanDate;
	}
	public Date getPatchSwLastScanDate() {
		return patchSwLastScanDate;
	}
	public void setPatchSwLastScanDate(Date patchSwLastScanDate) {
		this.patchSwLastScanDate = patchSwLastScanDate;
	}
	public Date getPatchDriversLastScanDate() {
		return patchDriversLastScanDate;
	}
	public void setPatchDriversLastScanDate(Date patchDriversLastScanDate) {
		this.patchDriversLastScanDate = patchDriversLastScanDate;
	}
	public Date getPatchAvLastScanDate() {
		return patchAvLastScanDate;
	}
	public void setPatchAvLastScanDate(Date patchAvLastScanDate) {
		this.patchAvLastScanDate = patchAvLastScanDate;
	}
	public Date getComplianceLastScanDate() {
		return complianceLastScanDate;
	}
	public void setComplianceLastScanDate(Date complianceLastScanDate) {
		this.complianceLastScanDate = complianceLastScanDate;
	}
	public String getInventoryServer() {
		return inventoryServer;
	}
	public void setInventoryServer(String inventoryServer) {
		this.inventoryServer = inventoryServer;
	}
	public Integer getNotifyOnChange() {
		return notifyOnChange;
	}
	public void setNotifyOnChange(Integer notifyOnChange) {
		this.notifyOnChange = notifyOnChange;
	}
	public String getPrimaryOwner() {
		return primaryOwner;
	}
	public void setPrimaryOwner(String primaryOwner) {
		this.primaryOwner = primaryOwner;
	}
	public Date getLastUpdInvSvr() {
		return lastUpdInvSvr;
	}
	public void setLastUpdInvSvr(Date lastUpdInvSvr) {
		this.lastUpdInvSvr = lastUpdInvSvr;
	}
	public String getScanType() {
		return scanType;
	}
	public void setScanType(String scanType) {
		this.scanType = scanType;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getWorkgroup() {
		return workgroup;
	}
	public void setWorkgroup(String workgroup) {
		this.workgroup = workgroup;
	}
	public String getModelNum() {
		return modelNum;
	}
	public void setModelNum(String modelNum) {
		this.modelNum = modelNum;
	}
	public String getFlpyDrvCnt() {
		return flpyDrvCnt;
	}
	public void setFlpyDrvCnt(String flpyDrvCnt) {
		this.flpyDrvCnt = flpyDrvCnt;
	}
	public Integer getRamCache() {
		return RamCache;
	}
	public void setRamCache(Integer ramCache) {
		RamCache = ramCache;
	}
	public String getOs2Comment() {
		return os2Comment;
	}
	public void setOs2Comment(String os2Comment) {
		this.os2Comment = os2Comment;
	}
	public String getNetwareAddr() {
		return netwareAddr;
	}
	public void setNetwareAddr(String netwareAddr) {
		this.netwareAddr = netwareAddr;
	}
	public Integer getMumMultiMedFiles() {
		return mumMultiMedFiles;
	}
	public void setMumMultiMedFiles(Integer mumMultiMedFiles) {
		this.mumMultiMedFiles = mumMultiMedFiles;
	}
	public Integer getMultiMedSize() {
		return multiMedSize;
	}
	public void setMultiMedSize(Integer multiMedSize) {
		this.multiMedSize = multiMedSize;
	}
	public String getMultiMedExt() {
		return multiMedExt;
	}
	public void setMultiMedExt(String multiMedExt) {
		this.multiMedExt = multiMedExt;
	}
	public Integer getComputerExistsTime() {
		return computerExistsTime;
	}
	public void setComputerExistsTime(Integer computerExistsTime) {
		this.computerExistsTime = computerExistsTime;
	}
	public String getLastDeployedOsImage() {
		return lastDeployedOsImage;
	}
	public void setLastDeployedOsImage(String lastDeployedOsImage) {
		this.lastDeployedOsImage = lastDeployedOsImage;
	}
	public String getCoprocessor() {
		return coprocessor;
	}
	public void setCoprocessor(String coprocessor) {
		this.coprocessor = coprocessor;
	}
	public String getPmmu() {
		return pmmu;
	}
	public void setPmmu(String pmmu) {
		this.pmmu = pmmu;
	}
	public String getComputerLocation() {
		return computerLocation;
	}
	public void setComputerLocation(String computerLocation) {
		this.computerLocation = computerLocation;
	}
	public String getLdapLocation() {
		return ldapLocation;
	}
	public void setLdapLocation(String ldapLocation) {
		this.ldapLocation = ldapLocation;
	}
	public String getOwnerPhone() {
		return ownerPhone;
	}
	public void setOwnerPhone(String ownerPhone) {
		this.ownerPhone = ownerPhone;
	}
	public String getOwnerEmail() {
		return ownerEmail;
	}
	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getIpmiGuid() {
		return ipmiGuid;
	}
	public void setIpmiGuid(String ipmiGuid) {
		this.ipmiGuid = ipmiGuid;
	}
	public String getAmtGuid() {
		return amtGuid;
	}
	public void setAmtGuid(String amtGuid) {
		this.amtGuid = amtGuid;
	}
	public String getServerType() {
		return serverType;
	}
	public void setServerType(String serverType) {
		this.serverType = serverType;
	}
	public String getHwMonitoringType() {
		return hwMonitoringType;
	}
	public void setHwMonitoringType(String hwMonitoringType) {
		this.hwMonitoringType = hwMonitoringType;
	}
	public String getMainBoardOemName() {
		return mainBoardOemName;
	}
	public void setMainBoardOemName(String mainBoardOemName) {
		this.mainBoardOemName = mainBoardOemName;
	}
	public String getSystemOemName() {
		return systemOemName;
	}
	public void setSystemOemName(String systemOemName) {
		this.systemOemName = systemOemName;
	}
	public String getWorkFlowStatusId() {
		return workFlowStatusId;
	}
	public void setWorkFlowStatusId(String workFlowStatusId) {
		this.workFlowStatusId = workFlowStatusId;
	}
	public Integer getDsViewPort() {
		return dsViewPort;
	}
	public void setDsViewPort(Integer dsViewPort) {
		this.dsViewPort = dsViewPort;
	}
	public Date getRecordDate() {
		return recordDate;
	}
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	public String getComputerName() {
		return computerName;
	}
	public void setComputerName(String computerName) {
		this.computerName = computerName;
	}
	
}
