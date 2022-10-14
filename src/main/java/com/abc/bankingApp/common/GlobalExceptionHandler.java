package com.abc.bankingApp.common;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abc.bankingApp.model.ErrorResponse;
import com.abc.bankingApp.model.StatusResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = UserAlreadyExistException.class)
	public ResponseEntity<StatusResponse> userAlreadyExistExceptionHandler(Exception e){
		StatusResponse statusResponse = getStatusResponseInstance(e);
		statusResponse.getErrors().setCode(HttpStatus.NOT_ACCEPTABLE.value());
		return new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.NOT_ACCEPTABLE);
	}
	
	@ExceptionHandler(value = UpdationFailedException.class)
	public ResponseEntity<StatusResponse> updationFailedExceptionHandler(Exception e){
		StatusResponse statusResponse = getStatusResponseInstance(e);
		statusResponse.getErrors().setCode(HttpStatus.NOT_MODIFIED.value());
		return new ResponseEntity<StatusResponse>(statusResponse,HttpStatus.NOT_MODIFIED);
	}
	
	@ExceptionHandler(value = AccountnotFoundException.class)
	public ResponseEntity<StatusResponse> accountNotExistExceptionHandler(Exception e){
		StatusResponse response=getStatusResponseInstance(e);
		response.getErrors().setCode(HttpStatus.NO_CONTENT.value());
		return new ResponseEntity<StatusResponse>(response,HttpStatus.NO_CONTENT);
	}
	
	@ExceptionHandler(value = AuthenticationFailedException.class)
	public ResponseEntity<StatusResponse> authenticationFailedExceptionHandler(Exception e){
		StatusResponse response=getStatusResponseInstance(e);
		response.getErrors().setCode(HttpStatus.UNAUTHORIZED.value());
		return new ResponseEntity<StatusResponse>(response,HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(value = UserCredentialException.class)
	public ResponseEntity<StatusResponse> userCredentialExceptionHandler(Exception e){
		StatusResponse response=getStatusResponseInstance(e);
		response.getErrors().setCode(HttpStatus.FORBIDDEN.value());
		return new ResponseEntity<StatusResponse>(response,HttpStatus.FORBIDDEN);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<StatusResponse> insufficientFundsExceptionHandler(Exception e){
		StatusResponse response=getStatusResponseInstance(e);
		response.getErrors().setCode(HttpStatus.BAD_REQUEST.value());
		return new ResponseEntity<StatusResponse>(response,HttpStatus.BAD_REQUEST);
	}
	
	public StatusResponse getStatusResponseInstance(Exception e) {
		StatusResponse statusResponse=new StatusResponse();
		statusResponse.setStatus("Error");
		ErrorResponse errorResponse=new ErrorResponse();
		errorResponse.setMessage(e.getMessage());
		statusResponse.setErrors(errorResponse);
		return statusResponse;
	}
	
	
	
	
}
