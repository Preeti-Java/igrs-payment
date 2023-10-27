/**
 * 
 */
package com.cg.neel.igrs.payment.users;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.cg.neel.igrs.payment.external.service.Igrs_Service;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
	
	private final Igrs_Service IGRS_Service;

	@RateLimiter(name = "userIdByPrincipalLimiter_Payment_User", fallbackMethod = "userIdByPrinciaplFallback_Payment_User")
	public Long getUserId() {
		//Get Principal from SecurityContext
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//Getting userId from IGRS_Service microservice
		return IGRS_Service.getUserIdByPrincipal(object);
	}
	
	//Creating fallback method for getUserId()
	public Long userIdByPrinciaplFallback_Payment_User(Exception ex) {
		log.info("Fallback is executed because service is down :"+ ex.getMessage());
		return 0L;
	}
	
}
