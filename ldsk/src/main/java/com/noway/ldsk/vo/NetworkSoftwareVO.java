package com.noway.ldsk.vo;

import java.io.Serializable;

public class NetworkSoftwareVO implements Serializable {

	private static final long serialVersionUID = -329714666312858433L;

	private int networkSoftwareIdn;
	private int computerIdn;
	private String nicAddress;

	public int getNetworkSoftwareIdn() {
		return networkSoftwareIdn;
	}

	public void setNetworkSoftwareIdn(int networkSoftwareIdn) {
		this.networkSoftwareIdn = networkSoftwareIdn;
	}

	public int getComputerIdn() {
		return computerIdn;
	}

	public void setComputerIdn(int computerIdn) {
		this.computerIdn = computerIdn;
	}

	public String getNicAddress() {
		return nicAddress;
	}

	public void setNicAddress(String nicAddress) {
		this.nicAddress = nicAddress;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + networkSoftwareIdn;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NetworkSoftwareVO other = (NetworkSoftwareVO) obj;
		if (networkSoftwareIdn != other.networkSoftwareIdn)
			return false;
		return true;
	}

}
