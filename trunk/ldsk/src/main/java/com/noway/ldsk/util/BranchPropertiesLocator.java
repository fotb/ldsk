package com.noway.ldsk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

final public class BranchPropertiesLocator {
	 private static final Logger logger = Logger.getLogger(BranchPropertiesLocator.class);

	private final static BranchPropertiesLocator INSTANCE = new BranchPropertiesLocator();
	Map<String, String> map = new HashMap<String, String>();
	Properties prop = null;
	private BranchPropertiesLocator() {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("branch_zh.properties");
			prop = new Properties();
			prop.load(inputStream);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally {
			if (inputStream != null) {
				try {
				inputStream.close();
				} catch (IOException e) {
					logger.error(e.getMessage());
				}
			}
		}
	}

	public static BranchPropertiesLocator getInstance(final boolean singleton) {
		final BranchPropertiesLocator instance;
		if (singleton) {
			instance = INSTANCE;
		} else {
			instance = new BranchPropertiesLocator();
		}
		return instance;
	}

	public String getValue(String key) {
		return prop.get(key) == null ? "" : prop.get(key).toString();
	}
	public Properties getAll() {
		return prop;
	}
}
