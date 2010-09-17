package com.noway.ldsk.util;

import java.util.HashMap;
import java.util.Map;

public class Constants {
	public static final String PROPERTIES_BRANCH_KEY = "metaobjattrrelationsidn.branch";
	public static final String PROPERTIES_POSITION_KEY = "metaobjattrrelationsidn.position";
	
	public static final String REPORT_ALL_COMPUTER_KEY = "template.allcomputer";
	public static final String REPORT_SAFE_INFO_KEY = "template.safereport";
	
	public static final String REPORT_OUTPUT_PATH = "report.output.path";
	
	public static final String OTHER_DEPT_KEY = "otherDept";
	
//	113 -----未授权程序程序试图执衄1�7
//	104---- 程序添加到系统启动项盄1�7
//	100---- 棄1�7测到不允许的文件访问
//	109-----棄1�7测到尝试修改执行文件
//	106------不允许注册表写尝评1�7
//	105---- 此软件已安装
	public static final String HIPS_ACTION_CODE_113 = "113";
	public static final String HIPS_ACTION_CODE_104 = "104";
	public static final String HIPS_ACTION_CODE_100 = "100";
	public static final String HIPS_ACTION_CODE_109 = "109";
	public static final String HIPS_ACTION_CODE_106 = "106";
	public static final String HIPS_ACTION_CODE_105 = "105";
	public static final Map<String, String> HIPS_ACTION_MAP = new HashMap<String, String>();
	static {
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_113, "未授权程序程序试图执衄1�7");
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_104, "程序添加到系统启动项盄1�7");
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_100, "棄1�7测到不允许的文件访问");
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_109, "棄1�7测到尝试修改执行文件");
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_106, "不允许注册表写尝评1�7");
		HIPS_ACTION_MAP.put(HIPS_ACTION_CODE_105, "此软件已安装");
	};
	
	
//	115-----已禁用存储设处1�7
//	116---- 已禁用CD/DVD设备
//	117-----其他禁用设备
	public static final String DEVICE_CONTROL_ACTION_CODE_115 = "115";
	public static final String DEVICE_CONTROL_ACTION_CODE_116 = "116";
	public static final String DEVICE_CONTROL_ACTION_CODE_117 = "117";
	public static final Map<String, String> DEVICE_CONTROL_ACTION_MAP = new HashMap<String, String>();
	static {
		DEVICE_CONTROL_ACTION_MAP.put(DEVICE_CONTROL_ACTION_CODE_115, "已禁用存储设处1�7");
		DEVICE_CONTROL_ACTION_MAP.put(DEVICE_CONTROL_ACTION_CODE_116, "已禁用CD/DVD设备");
		DEVICE_CONTROL_ACTION_MAP.put(DEVICE_CONTROL_ACTION_CODE_117, "其他禁用设备");
	};
		
	public static final String REPORT_HEADER_COMPUTERNAME = "report.header.computername";
	public static final String REPORT_HEADER_COMPUTERIP = "report.header.computerip";
	public static final String REPORT_HEADER_MAC = "report.header.mac";
	public static final String REPORT_HEADER_BRANCHNAME = "report.header.branckname";
	public static final String REPORT_HEADER_POSITION = "report.header.position";
}
