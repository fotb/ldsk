package com.noway.ldsk.action;

import com.opensymphony.xwork2.ActionSupport;

public class DefaultAction extends ActionSupport {

	private static final long serialVersionUID = -3259236786600998528L;

	public String addJsonNode(String nodeName, String nodeValue, boolean isEnd) {
		StringBuffer buffer = new StringBuffer(1000);
		buffer.append("\"" + nodeName + "\": ");
		buffer.append("\"" + nodeValue + "\"");
		if(!isEnd) {
			buffer.append(",");
		}
		return buffer.toString();
	}
}
