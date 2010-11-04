package com.noway.ldsk.util;

import java.io.Serializable;

public class BranchBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2391052636115515039L;
	private String branchName;
	private String branchValue;
	public String getBranchName() {
		return branchName;
	}
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	public String getBranchValue() {
		return branchValue;
	}
	public void setBranchValue(String branchValue) {
		this.branchValue = branchValue;
	}
	
}
