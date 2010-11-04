package com.noway.ldsk.action;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

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
	
	@SuppressWarnings("unchecked")
	public List getSortedKeyList(Properties branchProp) {
		List keyList = new ArrayList();
		for (final Iterator iter = branchProp.keySet().iterator(); iter.hasNext();) {
			final String key = (String) iter.next();
			keyList.add(key);
		}
		Collections.sort(keyList, new Comparator<String>(){
			@Override
			public int compare(String o1, String o2) {
				final String key1 = o1.substring(1, 3);
				final String key2 = o2.substring(1, 3);
				return key1.compareTo(key2);
			}
		});
		return keyList;
	}
}
