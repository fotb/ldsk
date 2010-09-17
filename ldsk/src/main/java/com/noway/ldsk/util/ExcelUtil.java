package com.noway.ldsk.util;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * This is a 1-sentence description of this class. This is high level description of this class's
 * capability.
 * 
 * <pre>
 * Pattern : Value Object
 * Thread Safe : No
 * 
 * Change History
 * 
 * Name                 Date                    Description
 * -------              -------                 -----------------
 * 020110              2010-5-12            Create the class
 * 
 * </pre>
 * 
 * @author 020110
 * @version 1.0
 */
@SuppressWarnings("unchecked")
public class ExcelUtil {

    private static final String EXCEL_CELL_EMPTY = "-";

    private static final String DATE_FORMAT = "yyyy/MM/dd HH:mm:ss";

    private static final String CHAR_SET_UTF8 = "UTF-8";


	public static void main(String[] args) {
        try {
            List list = parse("F:\\temp\\test.xls", 1);
            for (Iterator iter = list.iterator(); iter.hasNext();) {
                List tmpList = (List) iter.next();
                for(Iterator tmpIter = tmpList.iterator(); tmpIter.hasNext();) {
                    String str = (String) tmpIter.next();
                    System.out.println(str);
                }
            }
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static List parse(String filePath, int headerRowCount) throws IOException,
                    FileNotFoundException {
        FileInputStream fiStream = null;
        ByteArrayInputStream baiStream = null;
        try {
            fiStream = new FileInputStream(filePath);
            byte buf[] = org.apache.commons.io.IOUtils.toByteArray(fiStream);
            baiStream = new ByteArrayInputStream(buf);
            return parse(baiStream, headerRowCount);
        } finally {
            try {
                if (fiStream != null) {
                    fiStream.close();
                }
                if (baiStream != null) {
                    baiStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List parse(ByteArrayInputStream baiStream, int headerRowCount)
                    throws IOException {
        final HSSFWorkbook hssfWorkbook = new HSSFWorkbook(baiStream);
        final HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        int readRows = 0;
        final int physicalNumOfRows = hssfSheet.getPhysicalNumberOfRows();
        List list = new ArrayList();
        for (int i = headerRowCount; i <= hssfSheet.getLastRowNum(); i++) {
            final HSSFRow hssfRow = hssfSheet.getRow(i);
            if (hssfRow != null) {
                readRows++;
                List lineList = new ArrayList();
                for (int j = 0; j < hssfRow.getLastCellNum(); j++) {
                    final HSSFCell cell = hssfRow.getCell((short) j);
                    if (cell != null) {
                        switch (cell.getCellType()) {
                            case HSSFCell.CELL_TYPE_NUMERIC:
                                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                                    Date date = cell.getDateCellValue();
                                    lineList.add(DateUtil.format(date, DATE_FORMAT));
                                } else {
                                    Integer num = new Integer((int) cell.getNumericCellValue());
                                    lineList.add(String.valueOf(num));
                                }
                                break;
                            case HSSFCell.CELL_TYPE_STRING:
                                lineList.add(cell.getStringCellValue());
                                break;
                            default:
                                lineList.add(EXCEL_CELL_EMPTY);
                        }
                    } else {
                        lineList.add(EXCEL_CELL_EMPTY);
                    }
                }
                list.add(lineList);
            }
            if (readRows >= physicalNumOfRows - headerRowCount) {
                break;
            }
        }
        return list;
    }
}
