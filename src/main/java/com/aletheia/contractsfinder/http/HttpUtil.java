package com.aletheia.contractsfinder.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for HTTP operations and URL construction.
 */
public final class HttpUtil {
    
    private static final DateTimeFormatter ISO_DATE_FORMAT = DateTimeFormatter.ISO_DATE;
    
    private HttpUtil() {
        // Utility class
    }
    
    /**
     * Encode a string for use in URL query parameters.
     */
    public static String urlEncode(String value) {
        if (value == null) {
            return "";
        }
        try {
            return URLEncoder.encode(value, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            // UTF-8 is always supported
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Format a LocalDate as ISO-8601 date string.
     */
    public static String formatDate(LocalDate date) {
        return date.format(ISO_DATE_FORMAT);
    }
    
    /**
     * Build query string from key-value pairs.
     */
    public static String buildQueryString(String... params) {
        if (params.length == 0 || params.length % 2 != 0) {
            return "";
        }
        
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.length; i += 2) {
            String key = params[i];
            String value = params[i + 1];
            
            if (value != null && !value.isEmpty()) {
                if (sb.length() > 0) {
                    sb.append("&");
                }
                sb.append(urlEncode(key)).append("=").append(urlEncode(value));
            }
        }
        return sb.toString();
    }
}
