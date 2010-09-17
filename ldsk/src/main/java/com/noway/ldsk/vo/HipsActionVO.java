package com.noway.ldsk.vo;

import java.io.Serializable;
import java.util.Date;

public class HipsActionVO implements Serializable {

	private static final long serialVersionUID = -9154550495648145L;

	private Integer hipsActionIdn;
	private Integer computerIdn;
	private String actionTaken;
	private Integer actionCode;
	private Date actionDate;
	private String application;

	public Integer getHipsActionIdn() {
		return hipsActionIdn;
	}

	public void setHipsActionIdn(Integer hipsActionIdn) {
		this.hipsActionIdn = hipsActionIdn;
	}

	public Integer getComputerIdn() {
		return computerIdn;
	}

	public void setComputerIdn(Integer computerIdn) {
		this.computerIdn = computerIdn;
	}

	public String getActionTaken() {
		return actionTaken;
	}

	public void setActionTaken(String actionTaken) {
		this.actionTaken = actionTaken;
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

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((hipsActionIdn == null) ? 0 : hipsActionIdn.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HipsActionVO other = (HipsActionVO) obj;
		if (hipsActionIdn == null) {
			if (other.hipsActionIdn != null)
				return false;
		} else if (!hipsActionIdn.equals(other.hipsActionIdn))
			return false;
		return true;
	}

}
