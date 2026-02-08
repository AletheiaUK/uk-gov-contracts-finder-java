package com.aletheia.contractsfinder.http;

/**
 * Represents an HTTP response from the API.
 */
public class HttpResponse {
    
    private final int statusCode;
    private final String body;
    private final long contentLength;
    
    public HttpResponse(int statusCode, String body) {
        this(statusCode, body, body != null ? body.length() : 0);
    }
    
    public HttpResponse(int statusCode, String body, long contentLength) {
        this.statusCode = statusCode;
        this.body = body;
        this.contentLength = contentLength;
    }
    
    public int getStatusCode() {
        return statusCode;
    }
    
    public String getBody() {
        return body;
    }
    
    public long getContentLength() {
        return contentLength;
    }
    
    public boolean isSuccessful() {
        return statusCode >= 200 && statusCode < 300;
    }
    
    public boolean isNotFound() {
        return statusCode == 404;
    }
    
    public boolean isServerError() {
        return statusCode >= 500;
    }
    
    @Override
    public String toString() {
        return "HttpResponse{" +
                "statusCode=" + statusCode +
                ", contentLength=" + contentLength +
                '}';
    }
}
