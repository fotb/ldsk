package com.noway.ldsk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;


/*
Modification:End
*/

public class Utils {
	private static final Set<Character> ALLOWED_ILLEGAL_SYMBOLS_IN_EMAIL =
		StringUtil.chars2Set(new char[] { '@' });
	private static final Logger logger = Logger.getLogger(Utils.class);

	private static final Pattern pattern10d = Pattern.compile("\\d{10}");
	private static final Pattern pattern12d = Pattern.compile("\\d{12}");
	private static final String prefix88 = "88";
	private static final String prefix00 = "00";

	/**
	 * Constructing this object.
	 */
	protected Utils() {
	}

	
	/**
	 * Compare length of input String and input criteria
	 * @param sEntry Input String to compare
	 * @param iLength Target length
	 * @return If sEntry is null or shorter than iLength, return false; otherwise, return true
	 */
	public static boolean checkLength(String sEntry, int iLength) {
		if (sEntry == null)
			{return false;}
		if (sEntry.length() < iLength)
			{return false;}

		return true;
	}

	/**
	 * Check String of input String and input criteria is correct
	 * @param sEntry Input String to Check
	 * @param iType criteria
	 * @return If the check is correct, return true; otherwise, return false
	 */
	public static boolean checkEntry(String sEntry, int iType) {
		int i, iTest;
		char ch;
		boolean b = true;

		// 0 = Only Alpha's
		// 1 = Only Numbers
		// 2 = Only AlphaNumerics
		// 3 = Alpha's and spaces
		// 4 = Numerics and spaces
		// 5 = Alphanumerics and spaces

		if (iType > 2){
			iTest = iType - 3;
		}else{
			iTest = iType;
		}
		if (sEntry == null)
			{ b =  false;}

		for (i = 0; i < sEntry.length(); i++) {
			ch = sEntry.charAt(i);
			if ((iType > 2) && Character.isSpaceChar(ch)) {
				// Space is valid
				b = true;
			} else {
				switch (iTest) {
					case 0 :
						b = Character.isLetter(ch);
						break;
					case 1 :
						b = Character.isDigit(ch);
						break;
					case 2 :
						b = Character.isLetterOrDigit(ch);
						break;
					default :
						b = false;
						break;
				}
				if (!b) {
					break;
				}	
			}
		}
		return b;
	}

	/**
	 * remove String spaces
	 * @param sString 
	 * @return remove spaces of String 
	 */
	public static String removeNull(String sString) {
	    String s = "";
		if (sString != null){
			s =  sString;
		}
		return s;
	}
	
	/**
	 * remove String spaces
	 * @param sString 
	 * @return remove spaces of String 
	 */
	public static String removeNullTrim(String sString) {
	    String s = "";
		if (sString != null){
			s =  sString.trim();
		}
		return s;
	}

	/**
	 * convert String to int. 
	 * @param sValue sValue
	 * @return int
	 */
	public static int parseInt(String sValue) {
		int i;
		try {
			i = Integer.parseInt(sValue);
		} catch (NumberFormatException e) {
			i = 0;
		}
		return i;
	}

	/*public static String rightJustify(String sInput, int iLength) {
		if (iLength < 2)
			return "";
		sInput = sInput.trim();

		if (sInput.length() >= iLength)
			return sInput.substring(0, iLength - 1);
		int i = 0;
		StringBuffer sbTemp = new StringBuffer();
		for (i = 0; i < (iLength - sInput.length()); i++)
			sbTemp.append(" ");
		for (i = 0; i < sInput.length(); i++)
			sbTemp.append(sInput.charAt(i));
		return sbTemp.toString();
	}*/

	/** Validation on general eMail address format aaaaaaaaa@bbbbbb.ccc
	* where aaaaaaaaa is the user, bbbbbb is the domain and ccc is the top level domain
	* 1. the '@' sign must be within the e-mail address and allow enough room for user name
	* 2. user name must be at least 1 character
	* 3. domain name must be at least 3 characters
	* 4. top level domain name must be at least 2 characters
	* 5. '@' sign should appear only once
	* 6. No embedded colons, forward slashes, [,],<, >, |, &, ;, $, %, "'", ", (, ), +, \n, \r, ',', \
	*    Some of the above sybmols are blocked for penetration tests 
	* Created by:     Elaine Pang
	* Updated by :    Horace To
	* Creation Date:  06Mar02
	**/

	/**
	 * Validate the EmailAddr is Valid
	 * @param sEmailAddr Input String to Validate
	 * @return If sEmailAddr is valid,return true; otherwise, return false
	 */
	public static boolean validEmailAddr(String sEmailAddr) {
		/* added to check for double-byte characters */
		try{
			final byte[] ba = sEmailAddr.getBytes("UTF-8");
			if (sEmailAddr.length() != ba.length){
				return false;
			}
		}catch(UnsupportedEncodingException e){
			logger.error(e.getMessage());
		}
		
		boolean bValid = true;
		if (!StringUtil.isNull(sEmailAddr)) {
			final int p1 = sEmailAddr.indexOf('@') + 1;
			final int p2 = sEmailAddr.indexOf('.', p1) + 1;
			final int l = sEmailAddr.trim().length();

			if (p1 == 0 || p2 == 0)
				{bValid = false;}
			if (p1 <= 1)
				{bValid = false;}
      /* 
      Modification: Merge from Enhancement Code
      Author: Excel-GITS(HZ) Date: Jan 24, 2007
      */
			if (p2 - p1 <= 1)
				{bValid = false;}
			/*if (p2 - p1 <= 3)
				bValid = false;*/
			/*
			Modification:End
			*/
			if (l - p2 < 2)
				{bValid = false;}

			if (sEmailAddr.indexOf('@', p1) >= 0)
				{bValid = false;}
			if (sEmailAddr.trim().indexOf(' ') >= 0
				|| sEmailAddr.indexOf('/') >= 0
				|| sEmailAddr.indexOf(':') >= 0
				|| sEmailAddr.indexOf('[') >= 0
				|| sEmailAddr.indexOf(']') >= 0
				|| StringUtil.containsIllegalSymbolInEmail(
					sEmailAddr,
					ALLOWED_ILLEGAL_SYMBOLS_IN_EMAIL))
				{bValid = false;}
		} else {
			bValid = false;
		}
		return bValid;
	}

	/**
	 * Validate the StdEmailAddr is Valid
	 * @param emailAddress Input String to Validate
	 * @return If StdEmailAddr is valid,return true; otherwise, return false
	 * @throws 	Exception The class Exception and its subclasses are a form of 
	 * 			Throwable that indicates conditions that a reasonable 
	 * 			application might want to catch.
	 */
	public static boolean validateStdEmailAddress(String emailAddress) throws Exception {
	    //RFC 2822 token definitions for valid email - only used together to form a java Pattern object:
		final String sp = "!#$%&'*+-/=?^_`{|}~";
		final String atext = "[a-zA-Z0-9" + sp + "]";
		final String atom = atext + "+"; 						//one or more atext chars
		final String dotAtom = "\\." + atom;
		final String localPart = atom + "(" + dotAtom + ")*"; 	//one atom followed by 0 or more dotAtoms.
	    //RFC 1035 tokens for domain names:
		final String letter = "[a-zA-Z]";
		final String letDig = "[a-zA-Z0-9]";
		final String letDigHyp = "[a-zA-Z0-9-]";
		final String rfcLabel = letDig + letDigHyp + "{0,61}" + letDig;
		final String domain = rfcLabel + "(\\." + rfcLabel + ")*\\." + letter + "{2,6}";
	    //Combined together, these form the allowed email regexp allowed by RFC 2822:
		final String addrSpec = "^" + localPart + "@" + domain + "$";
	    //now compile it:
		final Pattern VALID_PATTERN = Pattern.compile( addrSpec );
	    return ( emailAddress != null ) && VALID_PATTERN.matcher( emailAddress ).matches();
	}

	/**
	 *  Convert amount/units/percentage fields to double
	 */
	public static double convertToDouble(String sEntry) {
		int iSign, iLength;
		boolean bNegative = false;
		double dValue;
		String sNoSignNum = sEntry;

		iLength = sEntry.trim().length();
		// Quit if empty string is passed
		if (sEntry == null || iLength == 0) {
			return 0;
		}

		iSign = sEntry.indexOf('-') + 1;

		// Check for negative sign
		if (iSign > 0) {
			bNegative = true;
			sNoSignNum = sEntry.replace('-', ' ');
		}
		//  System.out.println("before parser: " + sNoSignNum);
		try {
			dValue = Double.parseDouble(sNoSignNum);
		} catch (NumberFormatException e) {
			return 0;
		}
		if (bNegative) {
			return (dValue * -1);
		} else {
			return dValue;
		}
	}

	/**
	 * Convert to Date object / Date format checking
	 */
	public static Date convertToDate(String sEntry, String sDateFmt) {
		final SimpleDateFormat sdf = new SimpleDateFormat(sDateFmt, Locale.ENGLISH);
		sdf.setLenient(false);

		try {
			return sdf.parse(sEntry);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Format Date
	 */
	public static String formatDate(Date dDate, String sDateFmt) {
		final SimpleDateFormat sdf = new SimpleDateFormat(sDateFmt, Locale.ENGLISH);
		return sdf.format(dDate);
	}

	public static Date stringToDate(String string,String formatStyle) throws Exception {
		final SimpleDateFormat format = new SimpleDateFormat(formatStyle, Locale.ENGLISH);
		return format.parse(string);
	}

	/**
	 *  Check Minimum Amount
	 */
	public static boolean belowMinimum(
		double dAmount,
		double dLimit,
		double dTolerance) {
		return (dAmount < dLimit * (1 - dTolerance / 10000));
	}

	/**
	*   Check Maximum Amount
	*/
	public static boolean exceedMaximum(
		double dAmount,
		double dLimit,
		double dTolerance) {

		return (dAmount > dLimit * (1 + dTolerance / 10000));

	}

	/**
	 * According to Application Code, Returns the Config Folder path
	 * @param applCode Application Code
	 * @return Returns the Config Folder path
	 */
	public static String getConfigFolder(String applCode) {
		String configFolder = null;
		try {
			final String systemPropertyFile =
				System.getProperty("mjf.systemParameterFile");

			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder =
				syProps.getProperty(applCode.trim() + "ConfigFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error(
				"Failed to retrieve system parameter mjf.systemPropertyFile "
					+ e.getMessage(), e);
		}
		return configFolder;

	}

	/**
	 * According to Application Code and param, Returns the Config Folder path
	 * @param applCode Application Code
	 * @param param param
	 * @return Returns the Config Folder path
	 */
	public static String getConfigFolder(String applCode,String param) {
		String configFolder = null;
		try {
			final String systemPropertyFile =
				System.getProperty(param);

			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder =
				syProps.getProperty(applCode.trim() + "ConfigFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error(
				"Failed to retrieve system parameter mjf.systemPropertyFile "
					+ e.getMessage(), e);
		}
		return configFolder;

	}

	/**
	 * According to Application Code, Returns the Log Folder path
	 * @param applCode Application Code
	 * @return Returns the Log Folder path
	 */
	public static String getLogFolder(String applCode) {
		String configFolder = null;
		try {
			final String systemPropertyFile =
				System.getProperty("mjf.systemParameterFile");
			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder =
				syProps.getProperty(applCode.trim() + "LogFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error(
				"Failed to retrieve system parameter mjf.systemPropertyFile "
					+ e.getMessage(), e);
		}
		return configFolder;
	}

	/**
	 * According to Application Code, Returns the Report Folder path
	 * @param applCode Application Code
	 * @return Returns the Report Folder path
	 */
	public static String getReportFolder(String applCode) {
		String configFolder = null;
		try {
			final String systemPropertyFile = System.getProperty("mjf.systemParameterFile");
			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder = syProps.getProperty(applCode.trim() + "ECDailyReportFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error("Failed to retrieve system parameter EC Daily Report Folder " + e.toString());
		}
		return configFolder;
	}

	/**
	 * According to Application Code, Returns the Retention Report Folder path
	 * @param applCode Application Code
	 * @return Returns the Retention Report Folder path
	 */
	public static String getRetentionReportFolder(String applCode) {
		String configFolder = null;
		try {
			final String systemPropertyFile = System.getProperty("mjf.systemParameterFile");
			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder = syProps.getProperty(applCode.trim() + "ECRetentionReportFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error("Failed to retrieve system parameter EC Retention Report Folder " + e.toString());
		}
		return configFolder;
	}

	/**
	 * get AS400Library
	 * @return AS400Library
	 */
	public static String getAS400Library() {

		String library = "";

		final String configFolder = Utils.getConfigFolder("mjf");
		File fConfig = null;
		final Properties dbConfigProps = new Properties();

		fConfig = new File(configFolder + "AS400Config.properties");

		try {

			final FileInputStream fConfigInput = new FileInputStream(fConfig);
			dbConfigProps.load(fConfigInput);
			library = dbConfigProps.getProperty("library");
			fConfigInput.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return library;
	}

	/**
	 * According to input Object, Returns the Object class name
	 * @param O Object
	 * @return Class Name
	 */
	public static String getClassName(Object o) {
		if (o == null) {
			return "";
		}

		final String asClass = o.getClass().getName();
		final StringTokenizer sToken = new StringTokenizer(asClass, ".");
		String asToken = asClass;
		while (sToken.hasMoreElements()) {
			asToken = sToken.nextToken();
		}
		return asToken;
	}

	/**
	 * get the collection of all objects name
	 * @param col collection
	 * @param colName String
	 * @return Returns the collection of all objects name
	 */
	public static String formSQLInList(Collection<String> col, String colName)
	{
		/* Oracle doesn't allow the 'in' list to have size > 1000, this routine should be
		 * changed to cater for this 
		 */
		final StringBuffer str = new StringBuffer(0);
		int index = 0;
		for (final Iterator<String> it = col.iterator(); it.hasNext();) {
			final String s = (String) it.next();
			if (index != 0) {
				str.append(String.valueOf(','));
			}
			str.append("'" + s + "'");
			index++;
		}
		return colName + " in (" + str.toString() + ")";
	}
	
	/**
	 * According to Application Code and folderName, Returns the Folder path
	 * @param appCode Application Code
	 * @param folderName folder name
	 * @return Returns the Folder path
	 */
	public static String getFolder(String appCode, String folderName) {
		// TODO To cached the loaded properties
	    String configFolder = null;
		try {
			final String systemPropertyFile =
				System.getProperty(appCode + ".systemParameterFile");
			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);

			configFolder =
				syProps.getProperty(folderName).trim();
			fInput.close();

		} catch (Exception e) {
			logger.error(
				"Failed to retrieve system parameter "+ folderName
					+ e.getMessage(), e);
		}
		return configFolder;
	}

	/**
	 * Get The Prefix Path of the EStatement PDF Files. 
	 * @return
	 * @throws Exception
	 */
	public static String getEStatementFolder(String applCode) {
		String configFolder = null;
		try {
			final String systemPropertyFile =
				System.getProperty("mjf.systemParameterFile");
			final File spFile = new File(systemPropertyFile);

			//connection pool properties
			final Properties syProps = new Properties();
			final FileInputStream fInput = new FileInputStream(spFile);
			syProps.load(fInput);
	
			configFolder =
				syProps.getProperty(applCode.trim() + "EStatementFolder").trim();
			fInput.close();

		} catch (Exception e) {
			logger.error(
				"Failed to retrieve system parameter mjf.systemPropertyFile "
					+ e.toString());
		}
		return configFolder;
	}

	/**
	 * generate reference number for account opening.
	 * @param date
	 * @param asAccountNo
	 * @param asDealNo
	 * @return reference number
	 */
	public static String generateReferenceNo(Date date,String asAccountNo){
		final String sSystemDate = DateUtil.format(date,"MMdd");
		return "88" + sSystemDate + asAccountNo.substring(2);
	}
	
	/**
	 * remove String CR("\r","\n")
	 * @param sString 
	 * @return remove CR of String 
	 */
	public static String removeCR(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}
		String s1 = s.replaceAll("\r","");
		s1 = s1.replaceAll("\n","");
		return s1;
	}
	
	/**
	 * remove String CR("\r","<p>","\n")
	 * @param sString 
	 * @return remove CR of String 
	 */
	public static String replaceCR(String s) {
		if (s == null || "".equals(s)) {
			return "";
		}
		String s1 = s.replaceAll("\r","<p>");
		s1 = s1.replaceAll("\n","");
		return s1;
	}	
	
	//add by 020110
	/**
	 * According to input filePath and content of Create file
	 * @param filePath  file path 
	 * @param content  file content
	 * @throws IOException	Signals that an I/O exception of some sort has occurred. This
	 * 						class is the general class of exceptions produced by failed or
	 * 						interrupted I/O operations.
	 */
	public static void createFile(String filePath, String content) throws IOException {
	    FileWriter fw = null;
	    try{
	    	final File file = new File(filePath);
            if (file.exists()) {
                //if file exists, delete it and create new file
                if (file.delete()) {
                    file.createNewFile();
                } 
            } else {
                file.createNewFile();
            }
            fw = new FileWriter(file.getPath());
            fw.write(content);
	    } finally{
            if (fw != null) {
                fw.close();
            }
	    }   
	}
	//add end
	/**
	 * According to input filePath of delete file
	 * @param filePath  file path 
	 */
	public static void deleteFiles(String filePath){
		final File f = new File(filePath);
		final File[] fs = f.listFiles();
		if(fs != null) {
			for (int i = 0; i < fs.length; i++){
				fs[i].delete();
			}
		}
	}
	
	/**
	 * According to input filePath of check the file exist
	 * @param filePath  file name 
	 * @return If check the file exist, return true; otherwise, return false
	 */
	public static boolean checkFileExist(String filename){
		final File f = new File(filename);
		return (f == null ? false : f.exists());
	}
	
	/**
     * @return the sAccountNumber display
     */
    public static String getTransactionNumberDisplay(String transactionNumber) {
        if(transactionNumber==null || "".equals(transactionNumber)){
            return "-";
        }
        if(transactionNumber.length() != 16) {
            return transactionNumber;
        } else {
            return transactionNumber.substring(0, 2) + " " + transactionNumber.substring(2, 9) + " " + transactionNumber.substring(9);
        }
    }
    
    /**
     * @return the sAccountNumber display
     */
    public static String getAccountNumberDisplay(String sAccountNumber) {
        if(sAccountNumber == null || sAccountNumber.length() != 12) {
            return sAccountNumber;
        } else {
            return sAccountNumber.substring(0, 4) + " " + sAccountNumber.substring(4, 8) + " " + sAccountNumber.substring(8);
        }
    }
    
	/* number in 10 digit */
	public static boolean isNumber10(final String inputStr){
		return (inputStr != null && pattern10d.matcher(inputStr).matches());
	}

	/* number in 12 digit */
	public static boolean isNumber12(final String inputStr){
		return (inputStr != null && pattern12d.matcher(inputStr).matches());
	}
	
	/* check whether a 10-digit eMA and a 12-digit Account number are equivalent */
	public static boolean isEquivEMA(final String input10, final String input12){
		boolean result = false;
		if (input10 != null && input12 != null){
			if (isNumber10(input10) && isNumber12(input12)){
				if (input10.equals(convert12To10(input12))){
					result = true; 
				}
			}
		}
		return result;
	}

	
	/* convert account no from 12 digits to 10 digits with checking */
	public static String convert12To10(final String inputStr){
		/* default to null */
		//String outputStr = null;
		/* default to original string */
		String outputStr = inputStr;
		if (inputStr != null && pattern12d.matcher(inputStr).matches()){
			final String str1 = inputStr.substring(0,2);
			final String str2 = inputStr.substring(2,4);
			final String str3 = inputStr.substring(4,12);
			
			if (prefix88.equals(str1) && prefix00.equals(str2)){
				outputStr = str1+str3;
				logger.debug("12-digit account no "+inputStr+" converted to "+outputStr);
			}
		}
		return outputStr;
	}
	
	/* convert account no from 10 digits to 12 digits with checking */
	public static String convert10To12(final String inputStr){
		/* default to null */
		//String outputStr = null;
		/* default to original string */
		String outputStr = inputStr;
		if (inputStr != null && pattern10d.matcher(inputStr).matches()){
			final String str1 = inputStr.substring(0,2);
			final String str2 = inputStr.substring(2,10);
			
			if (prefix88.equals(str1)){
				outputStr = str1+prefix00+str2;	
				logger.debug("10-digit account no "+inputStr+" converted to "+outputStr);
			}
		}
		return outputStr;
	}

}
