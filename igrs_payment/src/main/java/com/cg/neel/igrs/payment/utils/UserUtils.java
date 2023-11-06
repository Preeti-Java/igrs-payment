/**
 * 
 */
package com.cg.neel.igrs.payment.utils;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.cg.neel.igrs.payment.exception.BadCredentialException;
import com.cg.neel.igrs.payment.jwtConfiguration.IgrsUser;

/**
 * 
 */
@Component
public class UserUtils {

	/**
	 * @return user id
	 */
	public static Long getUserDetails() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		final IgrsUser user = (IgrsUser) auth.getPrincipal();
		if(user == null)
			throw new BadCredentialException("Invalid/Expired Token Received");
		return Long.parseLong(user.getUserId());
	}

}
