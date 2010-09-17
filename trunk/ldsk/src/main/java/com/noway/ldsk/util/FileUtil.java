package com.noway.ldsk.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;


public class FileUtil {

    private static final Logger logger = Logger.getLogger(FileUtil.class);
    
    /**
     * The mothod of Converts HTML document content for the string.
     * @param resourceName	HTML file
     * @return After the conversion of the String 
     * @throws IOException	Signals that an I/O exception of some sort has occurred. This
     * 						class is the general class of exceptions produced by failed or
     * 						interrupted I/O operations.
     */
    public static String getStringFromResource(final String resourceName) throws IOException {

        final StringBuffer fileContent = new StringBuffer(1000);
        InputStreamReader isr = null;
        try {
            final ClassPathResource resource = new ClassPathResource(resourceName);
            isr = new InputStreamReader(resource.getInputStream());
            final BufferedReader br = new BufferedReader(isr);
            String s;
            while ((s = br.readLine()) != null) {
                fileContent.append(s);
                fileContent.append("\n");
            }
        } finally {
            try {
                if(isr != null) {
                    isr.close();
                }
            } catch (IOException ioe) {
                logger.error("Failed to close FileReader", ioe);
            }
        }

        return fileContent.toString();

    }
    
    public static void closeFileInputStream(FileInputStream fis) {
        try {
            if(fis != null) {
                fis.close();
            }
        } catch(IOException ioe) {
            logger.warn("Failed to close FileInputStream");
        }
    }

    public static void closeWriter(Writer writer) {
        try {
            if(writer != null) {
                writer.close();
            }
        } catch(IOException ioe) {
            logger.warn("Failed to close writer");
        }
    }
        
    /**
     * Delete file is correnct
     * @param fileName file 
     * @return If Method is correct, return true; otherwise, return false
     */
    public static boolean deleteFile(final String fileName) {
        boolean result = false;
        final File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            result = file.delete();
        }

        return result;
    }
}
