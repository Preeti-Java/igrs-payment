/**
 * 
 */
package com.cg.neel.igrs.payment.exception;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cg.neel.igrs.payment.utils.GenericResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 
 */

 @RestControllerAdvice
 @Slf4j
 @RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler{
	 
	 private final MessageSource messageResource;
	 
	 @ExceptionHandler({ BadCredentialException.class })
		@ResponseStatus(value = HttpStatus.BAD_REQUEST)
		public ResponseEntity<Object> handlerBadCredentialsException(final BadCredentialException ex, final WebRequest request){
			log.error("400 Bad Request",ex);
			final GenericResponse bodyResponse = new GenericResponse(messageResource.getMessage("message.badCredentail", null, request.getLocale()));
			return handleExceptionInternal(ex, bodyResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
		}

}
