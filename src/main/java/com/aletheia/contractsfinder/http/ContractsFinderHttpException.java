package com.aletheia.contractsfinder.http;

/**
 * Exception thrown when HTTP communication fails.
 */
public class ContractsFinderHttpException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    public ContractsFinderHttpException(String message) {
        super(message);
    }
    
    public ContractsFinderHttpException(String message, Throwable cause) {
        super(message, cause);
    }
}
