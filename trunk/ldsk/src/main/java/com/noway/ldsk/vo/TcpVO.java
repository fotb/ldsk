package com.noway.ldsk.vo;

import java.io.Serializable;

public class TcpVO implements Serializable {

	private static final long serialVersionUID = -329714666312858433L;

	private int networkSoftwareIdn;
	private int computerIdn;
	private String address;
	private String hostName;

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

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
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
		TcpVO other = (TcpVO) obj;
		if (networkSoftwareIdn != other.networkSoftwareIdn)
			return false;
		return true;
	}

}
