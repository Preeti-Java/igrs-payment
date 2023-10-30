/**
 * 
 */
package com.cg.neel.igrs.payment.exception;

/**
 * 
 */
public class BadCredentialException extends RuntimeException{

private static final long serialVersionUID = 1L;
	
	public  BadCredentialException() {
		super("Bad Credentials Exception on server");
	}
	
	public BadCredentialException(String message) {
		super(message);
	}
}
