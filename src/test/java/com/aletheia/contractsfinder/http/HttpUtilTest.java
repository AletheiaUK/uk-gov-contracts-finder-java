package com.aletheia.contractsfinder.http;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for HTTP utility classes.
 */
public class HttpUtilTest {
    
    @Test
    public void testUrlEncoding() {
        String encoded = HttpUtil.urlEncode("hello world");
        assertEquals("hello+world", encoded);
    }
    
    @Test
    public void testUrlEncodingSpecialChars() {
        String encoded = HttpUtil.urlEncode("test & special=chars");
        assertTrue(encoded.contains("%26"));
        assertTrue(encoded.contains("%3D"));
    }
    
    @Test
    public void testUrlEncodingNull() {
        String encoded = HttpUtil.urlEncode(null);
        assertEquals("", encoded);
    }
    
    @Test
    public void testBuildQueryString() {
        String query = HttpUtil.buildQueryString("key1", "value1", "key2", "value2");
        assertTrue(query.contains("key1=value1"));
        assertTrue(query.contains("key2=value2"));
        assertTrue(query.contains("&"));
    }
    
    @Test
    public void testBuildQueryStringWithNull() {
        String query = HttpUtil.buildQueryString("key1", "value1", "key2", null);
        assertTrue(query.contains("key1=value1"));
        assertFalse(query.contains("key2"));
    }
    
    @Test
    public void testHttpResponseSuccess() {
        HttpResponse response = new HttpResponse(200, "ok");
        assertTrue(response.isSuccessful());
        assertFalse(response.isNotFound());
        assertFalse(response.isServerError());
    }
    
    @Test
    public void testHttpResponseNotFound() {
        HttpResponse response = new HttpResponse(404, "Not found");
        assertFalse(response.isSuccessful());
        assertTrue(response.isNotFound());
        assertFalse(response.isServerError());
    }
    
    @Test
    public void testHttpResponseServerError() {
        HttpResponse response = new HttpResponse(500, "Server error");
        assertFalse(response.isSuccessful());
        assertFalse(response.isNotFound());
        assertTrue(response.isServerError());
    }
}
