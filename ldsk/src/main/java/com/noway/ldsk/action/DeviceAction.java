package com.noway.ldsk.action;

import java.util.Map;

import org.apache.log4j.Logger;

import com.noway.ldsk.bo.IUnmodeledDataBO;
import com.noway.ldsk.util.Constants;
import com.noway.ldsk.util.ReportPropertiesLocator;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class DeviceAction extends ActionSupport {
	private static final long serialVersionUID = 5281845553348811536L;
	private static final Logger logger = Logger.getLogger(DeviceAction.class);

	private IUnmodeledDataBO unmodeledDataBO;

	public IUnmodeledDataBO getUnmodeledDataBO() {
		return unmodeledDataBO;
	}

	public void setUnmodeledDataBO(IUnmodeledDataBO unmodeledDataBO) {
		this.unmodeledDataBO = unmodeledDataBO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String execute() throws Exception {
		Map<String, String> branchMap = unmodeledDataBO
				.findAllBranch(ReportPropertiesLocator.getInstance(true)
						.getValue(Constants.PROPERTIES_BRANCH_KEY));
		
		Map session = ActionContext.getContext().getSession();
		session.put("BranchMap",branchMap);
		return SUCCESS;
	}
}
