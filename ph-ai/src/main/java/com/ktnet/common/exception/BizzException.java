package com.ktnet.common.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
 
public class BizzException extends Exception {

    private static final long serialVersionUID = 9151628266215721942L;

    Logger logger = LoggerFactory.getLogger( BizzException.class );

    public BizzException() {
        super();
    }

    /**
     * @param message
     */
    public BizzException( String message ) {

        super( message );
    }

    /**
     * @param cause
     */
    public BizzException( Throwable cause ) {
        super( cause );
    }

    /**
     * @param message
     * @param cause
     */
    public BizzException( String message, Throwable cause ) {
        super( message, cause );
    }

    /**
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public BizzException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace ) {
        super( message, cause, enableSuppression, writableStackTrace );
    }

}
