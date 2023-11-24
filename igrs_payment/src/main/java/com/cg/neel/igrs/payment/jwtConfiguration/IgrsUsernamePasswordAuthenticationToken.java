/**
 * 
 */
package com.cg.neel.igrs.payment.jwtConfiguration;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

/**
 * 
 */
public class IgrsUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String userId;
	/**
	 * @param principal
	 * @param credentials
	 * @param authorities
	 */
	public IgrsUsernamePasswordAuthenticationToken(Object principal, Object credentials,
			Collection<? extends GrantedAuthority> authorities, String userId) {
		super(principal, credentials, authorities);
		this.userId =  userId;
		//super.setAuthenticated(true);
	}

	public String getUserId() {
		return this.userId;
	}

}
