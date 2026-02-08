package com.aletheia.contractsfinder.http;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Minimal JSON parser using only JDK standard library.
 * Handles basic JSON parsing for API responses.
 */
public final class JsonParser {
    
    private JsonParser() {
        // Utility class
    }
    
    /**
     * Extract a string value from JSON by key.
     */
    public static String extractString(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\"([^\\\"]*)\"");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
    
    /**
     * Extract a numeric value from JSON by key.
     */
    public static String extractNumber(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*([0-9.]+)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? matcher.group(1) : null;
    }
    
    /**
     * Extract an array of strings from JSON by key.
     */
    public static List<String> extractStringArray(String json, String key) {
        List<String> result = new ArrayList<>();
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*\\[([^\\]]*)\\]");
        Matcher matcher = pattern.matcher(json);
        
        if (matcher.find()) {
            String arrayContent = matcher.group(1);
            Pattern itemPattern = Pattern.compile("\"([^\\\"]*)\"");
            Matcher itemMatcher = itemPattern.matcher(arrayContent);
            
            while (itemMatcher.find()) {
                result.add(itemMatcher.group(1));
            }
        }
        
        return result;
    }
    
    /**
     * Extract a boolean value from JSON by key.
     */
    public static Boolean extractBoolean(String json, String key) {
        Pattern pattern = Pattern.compile("\"" + Pattern.quote(key) + "\"\\s*:\\s*(true|false)");
        Matcher matcher = pattern.matcher(json);
        return matcher.find() ? Boolean.parseBoolean(matcher.group(1)) : null;
    }
}
