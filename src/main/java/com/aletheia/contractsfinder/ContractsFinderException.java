package com.aletheia.contractsfinder;

/**
 * Exception thrown when communication with the Contracts Finder API fails.
 */
public class ContractsFinderException extends Exception {
    
    private static final long serialVersionUID = 1L;
    
    private final int statusCode;
    
    public ContractsFinderException(String message) {
        this(message, null, -1);
    }
    
    public ContractsFinderException(String message, Throwable cause) {
        this(message, cause, -1);
    }
    
    public ContractsFinderException(String message, Throwable cause, int statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
}
