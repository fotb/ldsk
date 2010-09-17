package com.noway.ldsk.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

final public class ReportPropertiesLocator {
	 private static final Logger logger = Logger.getLogger(ReportPropertiesLocator.class);

	private final static ReportPropertiesLocator INSTANCE = new ReportPropertiesLocator();

	Properties prop = new Properties();

	private ReportPropertiesLocator() {
		InputStream inputStream = null;
		try {
			inputStream = this.getClass().getClassLoader().getResourceAsStream("report.properties");
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

	public static ReportPropertiesLocator getInstance(final boolean singleton) {
		final ReportPropertiesLocator instance;
		if (singleton) {
			instance = INSTANCE;
		} else {
			instance = new ReportPropertiesLocator();
		}
		return instance;
	}

	public String getValue(String key) {
		return prop.getProperty(key);
	}
}
