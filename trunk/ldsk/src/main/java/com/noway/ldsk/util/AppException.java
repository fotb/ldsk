package com.noway.ldsk.util;

/**
 * @author v025407
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;
	
	/**
     * Initialization class,constructing the superclass constructor
     * @param asAppError Error messages
     */
	public AppException(final String asAppError) {
		super(asAppError);
	}
	
	/**
     * Initialization class,constructing the superclass constructor
     * @param asAppError Error messages
     * @param e Cause
     */
	public AppException(final String asAppError, final Exception e) {
		super(asAppError, e);
	}
}
