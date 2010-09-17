package com.noway.ldsk.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class FormatUtil {
	private static String sYieldFmt = "#0.00";

	private static String sPercFmt = "#,##0.00";

	private static String sPriceFmt = "#,##0.00";

	private static String sUnitFmt = "#,##0.000";

	private static String sFxFmt = "#,##0.000000";

	private static String sScheduleDealNoFmt = "0000000000000000";
	
	private static String sNewClientNoFmt = "0000000";
	
	private static String sEPointsFmt = "#,##0";

	private FormatUtil() {

	}

	/**
	 * Convert ePoints to string.
	 * @param ePoint ePoint
	 * @return String
	 */
	public static String ePointsToString(final BigDecimal ePoint) {
		return longToString(ePoint.longValue(),sEPointsFmt);
	}

	/**
	 * Convert Deal number to string.
	 * @param dScheduleDealNo dScheduleDealNo
	 * @return String
	 */
	public static String scheduleDealNoToString(final int dScheduleDealNo) {
		return integerToString(dScheduleDealNo, sScheduleDealNoFmt);
	}
	
	/**
	 * Convert Integer number to a string with leading zeros.
	 * @param iNumber iNumber
	 * @param sFormat sFormat
	 * @return String
	 * 
	 */
	public static String integerToString(final int iNumber, final String sFormat) {
		final NumberFormat nfc = new DecimalFormat(sFormat);
		return nfc.format(iNumber);
	}
	
	/**
	 * Convert long number to a string with a format.
	 * @param number number
	 * @param sFormat sFormat
	 * @return String 
	 */
	public static String longToString(final long number, final String sFormat) {
		final NumberFormat nfc = new DecimalFormat(sFormat);
		return nfc.format(number);
	}

	/**
	 * Extract list of elements from parameter string in which each element is
	 * specified in [].
	 * @param sList sList
	 * @return List
	 */
	public static List<String> extractFromList(final String sList) {
		final List<String> vElem = new ArrayList<String>();
		String sElem = null;
		String sTemp = sList.replace('[', ' ');
		sTemp = sTemp.replace(']', ',');

		final java.util.StringTokenizer st = new java.util.StringTokenizer(sTemp, ",");
		while (st.hasMoreTokens()) {
			sElem = st.nextToken().trim();
			vElem.add(sElem);
		}
		return vElem;
	}

	/**
	 * Convert Double number to a string in specific format.
	 * @param dAmount dAmount
	 * @param sFormat sFormat
	 * @return String
	 */
	public static String doubleToString(final double dAmount, final String sFormat) {
		final NumberFormat nfc = new DecimalFormat(sFormat);
		return nfc.format(dAmount);
	}

	/**
	 * Convert Yield to string.
	 * @param dYield dYield
	 * @return String
	 */
	public static String yieldToString(final double dYield) {
		return doubleToString(dYield, sYieldFmt);
	}

	/**
	 * Convert Percentage to string.
	 * @param dPc dPc
	 * @return String
	 */
	public static String percentToString(final double dPc) {
		return doubleToString(dPc, sPercFmt);
	}

	/**
	 * Convert Price to String.
	 * @param dPrice dPrice
	 * @return String
	 */
	public static String priceToString(final double dPrice) {
		return doubleToString(dPrice, sPriceFmt);
	}

	/**
	 * Convert Unit field to string.
	 * @param dUnit dUnit
	 * @return String
	 */
	public static String unitToString(final double dUnit) {
		return doubleToString(dUnit, sUnitFmt);
	}

	/**
	 * Convert Fx field to string.
	 * @param dFx dFx
	 * @return String
	 */
	public static String fxToString(final double dFx) {
		return doubleToString(dFx, sFxFmt);
	}

	/**
	 * Get no. of units/shares fields formatting string.
	 * @return String
	 */
	public static String getUnitFmt() {
		return sUnitFmt;
	}

	/**
	 * Get price fields formatting string.
	 * @return String
	 */
	public static String getPriceFmt() {
		return sPriceFmt;
	}

	/**
	 * Returns a BigDecimal whose scale is the specified value.
	 * @param dNumber dNumber
	 * @param iNumDecimals iNumDecimals 
	 * @return If dNumber is null, return zero; otherwise, return double
	 */
	public static double halfAdjDouble(final double dNumber, final int iNumDecimals) {
		final double returnDouble;
		final BigDecimal bdResult;
		if (dNumber == 0) {
			returnDouble = 0;
		}else{
			bdResult = new BigDecimal(dNumber);
			returnDouble = bdResult.setScale(iNumDecimals, 
			                BigDecimal.ROUND_HALF_EVEN).doubleValue();
		}
		return returnDouble;
	}

	/**
	 * lookup item in list
	 * @param sEntry sEntry
	 * @param sList sList
	 * @param iLength iLength
	 * @return boolean
	 */
	public static boolean existInList(final String sEntry, 
	    final String sList, final int iLength) {
		char[] caKey;
		String sKey, sElem;

		final boolean returnBoolean;
		if (sList == null || sEntry == null) {
			returnBoolean = false;
		}else{

			sElem = sEntry.trim();
			if (sList.trim().length() <= 0 || sElem.length() <= 0) {
				returnBoolean = false;
			}else{
				// format [key]
				caKey = new char[iLength + 2];
				for (int i = 0; i < caKey.length; i++) {
					caKey[i] = ' ';
				}
				caKey[0] = '[';
				System.arraycopy(sElem.toCharArray(), 0, caKey, 1, sElem.length());
				caKey[iLength + 1] = ']';

				// lookup item in list
				sKey = new String(caKey);
				returnBoolean = (sList.indexOf(sKey) >= 0);
			}
		}
		return returnBoolean;
	}

	/**
	 * 
	 * Validate the character of String is Valid
	 * @param sEntry Input String to Valid
	 * @return If Validate is valid, return true; otherwise, return false
	 */
	public static boolean isAllValidCharacters(final String sEntry) {
		
		int i, iLength;
		char ch;
		boolean bValid = true;
		boolean returnBoolean = true;
		boolean stopCheck = false;
		
		iLength = sEntry.trim().length();

		for (i = 0; i < iLength; i++) {
			if (!stopCheck){
				ch = sEntry.charAt(i);
				bValid = (Character.isDigit(ch) || ch == '-' || ch == '.');
				if (!bValid) {
					returnBoolean = false;
					stopCheck = true;
				}
			}
		}
		return returnBoolean;
	}

	/**
	 * 
	 * Validate the number is Valid
	 * @param sEntry sEntry
	 * @param iDigits iDigits
	 * @param iDecimals iDecimals
	 * @param bAllowNegative bAllowNegative
	 * @return If Validate is valid, return true; otherwise, return false
	 */
	public static boolean checkNumber(final String sEntry, 
	    final int iDigits, final int iDecimals, final boolean bAllowNegative) {
		
		int iSign, iDecPoint, iLength, iNumDigits, iNumDecimals;
		boolean bValid = true;
		boolean returnBoolean = true;

		iLength = sEntry.trim().length();

		// Quit if empty string is passed
		if (sEntry == null || iLength == 0) {
			returnBoolean = false;
		}else{
			iSign = sEntry.indexOf('-') + 1;
			iDecPoint = sEntry.indexOf('.') + 1;

			if (iDecPoint > 0) {
				iNumDigits = iDecPoint - 1;
				iNumDecimals = iLength - iDecPoint;
			} else {
				iNumDigits = iLength;
				iNumDecimals = 0;
			}

			// Check for negative sign
			if (iSign > 0 && !bAllowNegative) {
				bValid = false;
			}
			if (iSign > 0 && sEntry.indexOf('-', iSign) >= 0) {
				bValid = false;
			}
			if (iSign > 1 && iSign < iLength) {
				bValid = false;
			}
			if (iSign == 1 && iNumDigits > 0) {
				iNumDigits--;
			}
			if (iSign == iLength && iNumDecimals > 0) {
				iNumDecimals--;
			}

			// Check for decimal points
			if (iDecPoint > 0 && iDecimals == 0) {
				bValid = false;
			}
			if (iDecPoint > 0 && sEntry.indexOf('.', iDecPoint) >= 0) {
				bValid = false;
			}
			if (iNumDecimals > iDecimals) {
				bValid = false;
			}

			// Check for number of digits
			if (iNumDigits > iDigits) {
				bValid = false;
			}

			if (bValid) {
                // Check for invalid characters
                /*
                 * for (i = 0; i < iLength; i++) { ch = sEntry.charAt(i); bValid
                 * = (Character.isDigit(ch) || ch == '-' || ch == '.'); if
                 * (!bValid) { return false; } }
                 */
                if (!isAllValidCharacters(sEntry)) {
                    returnBoolean = false;
                }
            } else {
                returnBoolean = false;
            }
		}
		return returnBoolean;
	}

	/**
	 * 
	 * According to input String convert to Date
	 * @param asDate asDate
	 * @return Date
	 */
	public static Date getDate(final String asDate) {
	    Date returnDate = null;
        try {
            final SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.ENGLISH);
            sdf.setLenient(false);
            returnDate = sdf.parse(asDate);
        } catch (Exception ex) {
            returnDate = null;
        }
        return returnDate;
	}

	/**
	 * Please input your comments here.
	 * @return String
	 */
	public static String getFxFmt() {
		return sFxFmt;
	}

	/**
	 * Please input your comments here.
	 * @param string string
	 */
	public static void setFxFmt(final String string) {
		sFxFmt = string;
	}

	/*
	 * public static String putEmptyCharacter(String string, int sLength){ if
	 * (string == null) string = ""; StringBuffer s = new StringBuffer(string);
	 * for (int i=0;i <(sLength-string.length());i++){ s.append(" "); } return
	 * s.toString(); }
	 */
	 
	public static String newClientNoToString(final String sNewClientNo) {
		return integerToString(Integer.parseInt(sNewClientNo),sNewClientNoFmt);
	}
}
