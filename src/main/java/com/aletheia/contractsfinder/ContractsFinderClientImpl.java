package com.aletheia.contractsfinder;

import com.aletheia.contractsfinder.http.ContractsFinderHttpClient;
import com.aletheia.contractsfinder.http.ContractsFinderHttpException;
import com.aletheia.contractsfinder.http.HttpResponse;
import com.aletheia.contractsfinder.http.HttpUtil;
import com.aletheia.contractsfinder.http.JsonParser;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Implementation of the Contracts Finder API client.
 * 
 * Example usage:
 * <pre>
 * ContractsFinderClientImpl client = new ContractsFinderClientImpl();
 * try {
 *     SearchCriteria criteria = new SearchCriteria()
 *         .keywords("construction")
 *         .pageSize(50);
 *     
 *     SearchResults results = client.search(criteria);
 *     System.out.println("Found " + results.getTotalResults() + " contracts");
 * } finally {
 *     client.close();
 * }
 * </pre>
 */
public class ContractsFinderClientImpl implements ContractsFinderClient, AutoCloseable {
    
    private final ContractsFinderHttpClient httpClient;
    
    public ContractsFinderClientImpl() {
        this.httpClient = new ContractsFinderHttpClient();
    }
    
    public ContractsFinderClientImpl(String baseUrl) {
        this.httpClient = new ContractsFinderHttpClient(baseUrl);
    }
    
    @Override
    public SearchResults search(SearchCriteria criteria) throws ContractsFinderException {
        Objects.requireNonNull(criteria, "criteria cannot be null");
        
        try {
            String queryString = buildSearchQueryString(criteria);
            HttpResponse response = httpClient.get("/search", queryString);
            
            if (!response.isSuccessful()) {
                throw new ContractsFinderException(
                    "API search failed with status " + response.getStatusCode(),
                    null,
                    response.getStatusCode()
                );
            }
            
            return parseSearchResults(response.getBody(), criteria);
        } catch (ContractsFinderHttpException e) {
            throw new ContractsFinderException("HTTP error during search: " + e.getMessage(), e);
        }
    }
    
    @Override
    public Contract getContract(String contractId) throws ContractsFinderException {
        Objects.requireNonNull(contractId, "contractId cannot be null");
        
        try {
            String endpoint = "/contract/" + HttpUtil.urlEncode(contractId);
            HttpResponse response = httpClient.get(endpoint, "");
            
            if (response.isNotFound()) {
                throw new ContractsFinderException(
                    "Contract not found: " + contractId,
                    null,
                    404
                );
            }
            
            if (!response.isSuccessful()) {
                throw new ContractsFinderException(
                    "API request failed with status " + response.getStatusCode(),
                    null,
                    response.getStatusCode()
                );
            }
            
            return parseContract(response.getBody());
        } catch (ContractsFinderHttpException e) {
            throw new ContractsFinderException("HTTP error retrieving contract: " + e.getMessage(), e);
        }
    }
    
    @Override
    public NoticeList getNotices(String dateTime) throws ContractsFinderException {
        Objects.requireNonNull(dateTime, "dateTime cannot be null");
        
        try {
            String queryString = HttpUtil.buildQueryString("from", dateTime);
            HttpResponse response = httpClient.get("/notices", queryString);
            
            if (!response.isSuccessful()) {
                throw new ContractsFinderException(
                    "API request failed with status " + response.getStatusCode(),
                    null,
                    response.getStatusCode()
                );
            }
            
            return parseNoticeList(response.getBody(), dateTime);
        } catch (ContractsFinderHttpException e) {
            throw new ContractsFinderException("HTTP error retrieving notices: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void close() {
        httpClient.close();
    }
    
    // Parsing methods
    
    private String buildSearchQueryString(SearchCriteria criteria) {
        StringBuilder sb = new StringBuilder();
        
        if (criteria.getKeywords() != null) {
            if (sb.length() > 0) sb.append("&");
            sb.append("keywords=").append(HttpUtil.urlEncode(criteria.getKeywords()));
        }
        
        for (String orgId : criteria.getOrganisationIds()) {
            if (sb.length() > 0) sb.append("&");
            sb.append("organisationId=").append(HttpUtil.urlEncode(orgId));
        }
        
        if (criteria.getPublishedFrom() != null) {
            if (sb.length() > 0) sb.append("&");
            sb.append("publishedFrom=").append(HttpUtil.formatDate(criteria.getPublishedFrom()));
        }
        
        if (criteria.getPublishedTo() != null) {
            if (sb.length() > 0) sb.append("&");
            sb.append("publishedTo=").append(HttpUtil.formatDate(criteria.getPublishedTo()));
        }
        
        if (criteria.getStatus() != null) {
            if (sb.length() > 0) sb.append("&");
            sb.append("status=").append(HttpUtil.urlEncode(criteria.getStatus()));
        }
        
        if (criteria.getPageNumber() != null && criteria.getPageNumber() > 0) {
            if (sb.length() > 0) sb.append("&");
            sb.append("page=").append(criteria.getPageNumber());
        }
        
        if (criteria.getPageSize() != null && criteria.getPageSize() > 0) {
            if (sb.length() > 0) sb.append("&");
            sb.append("pageSize=").append(criteria.getPageSize());
        }
        
        return sb.toString();
    }
    
    private SearchResults parseSearchResults(String json, SearchCriteria criteria) {
        SearchResults results = new SearchResults();
        results.setPageNumber(criteria.getPageNumber());
        results.setPageSize(criteria.getPageSize());
        
        String totalStr = JsonParser.extractNumber(json, "totalResults");
        if (totalStr != null) {
            results.setTotalResults(Integer.parseInt(totalStr));
        }
        
        return results;
    }
    
    private Contract parseContract(String json) {
        Contract contract = new Contract();
        
        String id = JsonParser.extractString(json, "id");
        String title = JsonParser.extractString(json, "title");
        String organisationName = JsonParser.extractString(json, "organisationName");
        String description = JsonParser.extractString(json, "description");
        String status = JsonParser.extractString(json, "status");
        String value = JsonParser.extractString(json, "value");
        String cpvCodes = JsonParser.extractString(json, "cpvCodes");
        String contractLink = JsonParser.extractString(json, "contractLink");
        
        contract.setId(id);
        contract.setTitle(title);
        contract.setOrganisationName(organisationName);
        contract.setDescription(description);
        contract.setStatus(status);
        contract.setValue(value);
        contract.setCpvCodes(cpvCodes);
        contract.setContractLink(contractLink);
        
        return contract;
    }
    
    private NoticeList parseNoticeList(String json, String fromDate) {
        NoticeList noticeList = new NoticeList();
        noticeList.setFromDate(fromDate);
        
        String totalStr = JsonParser.extractNumber(json, "totalResults");
        if (totalStr != null) {
            noticeList.setTotalResults(Integer.parseInt(totalStr));
        }
        
        return noticeList;
    }
}
