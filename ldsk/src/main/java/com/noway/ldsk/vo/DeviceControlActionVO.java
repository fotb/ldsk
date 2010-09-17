package com.noway.ldsk.vo;

import java.io.Serializable;
import java.util.Date;

public class DeviceControlActionVO implements Serializable {
	private static final long serialVersionUID = 5817287267425814747L;

	private Integer deviceControlActionIdn;
	private Integer computerIdn;
	private String description;
	private String hardwareId;
	private String service;
	private String deviceClass;
	private String enumerator;
	private String vendorId;
	private String deviceId;
	private String valumeSerial;
	private Integer actionCode;
	private Date actionDate;
	private Integer actionType;
	private String userName;
	private String configGuid;

	public Integer getDeviceControlActionIdn() {
		return deviceControlActionIdn;
	}

	public void setDeviceControlActionIdn(Integer deviceControlActionIdn) {
		this.deviceControlActionIdn = deviceControlActionIdn;
	}

	public Integer getComputerIdn() {
		return computerIdn;
	}

	public void setComputerIdn(Integer computerIdn) {
		this.computerIdn = computerIdn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getHardwareId() {
		return hardwareId;
	}

	public void setHardwareId(String hardwareId) {
		this.hardwareId = hardwareId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getDeviceClass() {
		return deviceClass;
	}

	public void setDeviceClass(String deviceClass) {
		this.deviceClass = deviceClass;
	}

	public String getEnumerator() {
		return enumerator;
	}

	public void setEnumerator(String enumerator) {
		this.enumerator = enumerator;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getValumeSerial() {
		return valumeSerial;
	}

	public void setValumeSerial(String valumeSerial) {
		this.valumeSerial = valumeSerial;
	}

	public Integer getActionCode() {
		return actionCode;
	}

	public void setActionCode(Integer actionCode) {
		this.actionCode = actionCode;
	}

	public Date getActionDate() {
		return actionDate;
	}

	public void setActionDate(Date actionDate) {
		this.actionDate = actionDate;
	}

	public Integer getActionType() {
		return actionType;
	}

	public void setActionType(Integer actionType) {
		this.actionType = actionType;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getConfigGuid() {
		return configGuid;
	}

	public void setConfigGuid(String configGuid) {
		this.configGuid = configGuid;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((deviceControlActionIdn == null) ? 0
						: deviceControlActionIdn.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceControlActionVO other = (DeviceControlActionVO) obj;
		if (deviceControlActionIdn == null) {
			if (other.deviceControlActionIdn != null)
				return false;
		} else if (!deviceControlActionIdn.equals(other.deviceControlActionIdn))
			return false;
		return true;
	}

}
