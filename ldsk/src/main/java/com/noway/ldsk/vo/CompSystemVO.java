package com.noway.ldsk.vo;

import java.io.Serializable;

public class CompSystemVO implements Serializable {

	private static final long serialVersionUID = 1572674471722182820L;

	private Integer computerIdn;
	private String model;
	private String manufacturer;

	public Integer getComputerIdn() {
		return computerIdn;
	}

	public void setComputerIdn(Integer computerIdn) {
		this.computerIdn = computerIdn;
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

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerIdn == null) ? 0 : computerIdn.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompSystemVO other = (CompSystemVO) obj;
		if (computerIdn == null) {
			if (other.computerIdn != null)
				return false;
		} else if (!computerIdn.equals(other.computerIdn))
			return false;
		return true;
	}
}
