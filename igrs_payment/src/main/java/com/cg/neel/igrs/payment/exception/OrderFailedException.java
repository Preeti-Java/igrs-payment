/**
 * 
 */
package com.cg.neel.igrs.payment.exception;

/**
 * 
 */
public class OrderFailedException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public OrderFailedException() {
		super("Order Failed");
	}
	
	public  OrderFailedException(String message) {
		 super(message);
	}

}
