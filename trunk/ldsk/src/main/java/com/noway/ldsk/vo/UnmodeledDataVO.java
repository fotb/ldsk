package com.noway.ldsk.vo;

import java.io.Serializable;

public class UnmodeledDataVO implements Serializable {

	private static final long serialVersionUID = -329714666312858433L;

	private int unmodeledDateIdn;
	private int computerIdn;
	private int metaObjAttrRelationsIdn;
	private String dataString;

	public int getUnmodeledDateIdn() {
		return unmodeledDateIdn;
	}

	public void setUnmodeledDateIdn(int unmodeledDateIdn) {
		this.unmodeledDateIdn = unmodeledDateIdn;
	}

	public int getComputerIdn() {
		return computerIdn;
	}

	public void setComputerIdn(int computerIdn) {
		this.computerIdn = computerIdn;
	}

	public int getMetaObjAttrRelationsIdn() {
		return metaObjAttrRelationsIdn;
	}

	public void setMetaObjAttrRelationsIdn(int metaObjAttrRelationsIdn) {
		this.metaObjAttrRelationsIdn = metaObjAttrRelationsIdn;
	}

	public String getDataString() {
		return dataString;
	}

	public void setDataString(String dataString) {
		this.dataString = dataString;
	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + unmodeledDateIdn;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UnmodeledDataVO other = (UnmodeledDataVO) obj;
		if (unmodeledDateIdn != other.unmodeledDateIdn)
			return false;
		return true;
	}

}
