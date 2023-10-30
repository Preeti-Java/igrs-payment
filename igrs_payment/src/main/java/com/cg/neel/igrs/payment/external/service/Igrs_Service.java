/**
 * 
 */
package com.cg.neel.igrs.payment.external.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 */

//@FeignClient("IGRS_SERVICE")
@FeignClient("IGRS-SERVICE-Preeti")
@Service
public interface Igrs_Service {

	/**
	 * @return userId
	 */
	@PostMapping(value = "query_user/userId/", consumes = "application/json")
	public Long getUserIdByPrincipal(@RequestParam Object object);
}
