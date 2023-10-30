/**
 * 
 */
package com.cg.neel.igrs.payment.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 
 */
public class UserUtils {

	/**
	 * @return user id
	 */
	public static Long getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return null;
	}

}
