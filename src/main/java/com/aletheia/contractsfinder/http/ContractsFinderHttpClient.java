package com.aletheia.contractsfinder.http;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.time.Duration;
import java.util.Objects;

/**
 * HTTP client for communicating with the Contracts Finder API.
 * Uses JDK's built-in java.net.http.HttpClient.
 */
public class ContractsFinderHttpClient implements AutoCloseable {
    
    private static final String BASE_URL = "https://www.contractsfinder.service.gov.uk/api/v1";
    private static final Duration REQUEST_TIMEOUT = Duration.ofSeconds(30);
    private static final String USER_AGENT = "ContractsFinderJavaClient/0.1.0";
    
    private final HttpClient httpClient;
    private final String baseUrl;
    
    public ContractsFinderHttpClient() {
        this(BASE_URL);
    }
    
    public ContractsFinderHttpClient(String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl cannot be null");
        this.httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .connectTimeout(REQUEST_TIMEOUT)
            .build();
    }
    
    /**
     * Make a GET request to the API.
     */
    public HttpResponse get(String endpoint, String queryString) throws ContractsFinderHttpException {
        String url = buildUrl(endpoint, queryString);
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(REQUEST_TIMEOUT)
            .header("User-Agent", USER_AGENT)
            .header("Accept", "application/json")
            .GET()
            .build();
        
        try {
            java.net.http.HttpResponse<String> response = httpClient.send(request, 
                java.net.http.HttpResponse.BodyHandlers.ofString());
            
            return new HttpResponse(response.statusCode(), response.body(), 
                response.headers().firstValueAsLong("content-length").orElse(-1));
        } catch (IOException e) {
            throw new ContractsFinderHttpException("IO error: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ContractsFinderHttpException("Request interrupted", e);
        }
    }
    
    /**
     * Make a POST request to the API.
     */
    public HttpResponse post(String endpoint, String body) throws ContractsFinderHttpException {
        String url = buildUrl(endpoint, "");
        
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .timeout(REQUEST_TIMEOUT)
            .header("User-Agent", USER_AGENT)
            .header("Content-Type", "application/json")
            .header("Accept", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();
        
        try {
            java.net.http.HttpResponse<String> response = httpClient.send(request, 
                java.net.http.HttpResponse.BodyHandlers.ofString());
            
            return new HttpResponse(response.statusCode(), response.body(), 
                response.headers().firstValueAsLong("content-length").orElse(-1));
        } catch (IOException e) {
            throw new ContractsFinderHttpException("IO error: " + e.getMessage(), e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new ContractsFinderHttpException("Request interrupted", e);
        }
    }
    
    /**
     * Build the full URL from endpoint and query string.
     */
    private String buildUrl(String endpoint, String queryString) {
        StringBuilder url = new StringBuilder(baseUrl);
        
        if (!endpoint.startsWith("/")) {
            url.append("/");
        }
        url.append(endpoint);
        
        if (queryString != null && !queryString.isEmpty()) {
            url.append("?").append(queryString);
        }
        
        return url.toString();
    }
    
    /**
     * Close the HTTP client and release resources.
     */
    @Override
    public void close() {
        // HttpClient doesn't have a close method, but we implement AutoCloseable
        // for try-with-resources compatibility
    }
}
