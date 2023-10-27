/**
 * 
 */
package com.cg.neel.igrs.payment.data;

import org.springframework.stereotype.Service;

import com.cg.neel.igrs.payment.external.service.Data_Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

@Service
@Slf4j
@AllArgsConstructor
public class DataService {
	
	private final Data_Service dataService;

}
