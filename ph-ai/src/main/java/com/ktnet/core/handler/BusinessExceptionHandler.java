package com.ktnet.core.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.ktnet.common.codes.ErrorCode;
import com.ktnet.common.response.ErrorResponse;
import com.ktnet.fta.common.service.CommonService;

import jakarta.annotation.Resource;

@ControllerAdvice
public class BusinessExceptionHandler {
	
	Logger logger = LoggerFactory.getLogger(getClass());

    private final HttpStatus HTTP_STATUS_OK = HttpStatus.OK;
    
    @Resource(name = "commonService")
    private CommonService commonService;
    
    /**
     * [Exception] NoResourceFoundException 404
     *
     * @param e NoResourceFoundException
     * @return view html page
     */
 	@ExceptionHandler(NoResourceFoundException.class)
 	@ResponseStatus(HttpStatus.NOT_FOUND) 
 	public String noResourceFoundException404(NoResourceFoundException e) {
 		logger.error("==================== NoResourceFoundException ====================");
	 	return "error/404";
 	}
 	
 	/**
     * [Exception] NoHandlerFoundException 404
     *
     * @param e NoHandlerFoundException
     * @return view html page
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected String NoHandlerFoundException404(NoHandlerFoundException e) {
    	logger.error("==================== NoHandlerFoundException404 ====================");
    	return "error/404";
    }
    
    /**
     * [Exception] 모든 Exception 경우 발생
     *
     * @param ex Exception
     * @return ResponseEntity<ErrorResponse>
     */
    @ExceptionHandler(Exception.class)
    protected final ResponseEntity<ErrorResponse> handleAllExceptions(Exception ex) {
        logger.error("Exception", ex);
        logger.debug("==================== BusinessExceptionHandler ====================");
        
        final ErrorResponse response = ErrorResponse.of(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage());
        
        try {
        	Map<String, Object> param = new HashMap<String, Object>();
        	param.put("errorResponse", response);
        	
        	commonService.saveErrorMessage(param);
		} catch (NullPointerException e) {
			logger.debug("saveErrorMessage ERROR - NullPointerException");
		} catch (Exception e) {
			logger.debug("saveErrorMessage ERROR - Exception");
		}
        
        return new ResponseEntity<>(response, HTTP_STATUS_OK);
    }
}