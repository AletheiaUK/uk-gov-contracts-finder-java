package com.aletheia.contractsfinder;

import java.net.URI;
import java.net.http.HttpResponse;

/**
 * Client for accessing the UK Government Contracts Finder API.
 * Provides programmatic access to public sector contract information.
 * 
 * API Documentation: https://www.contractsfinder.service.gov.uk/
 */
public interface ContractsFinderClient {
    
    /**
     * Search for contracts using the given parameters.
     * 
     * @param criteria search criteria
     * @return search results
     * @throws ContractsFinderException if API communication fails
     */
    SearchResults search(SearchCriteria criteria) throws ContractsFinderException;
    
    /**
     * Retrieve a specific contract by ID.
     * 
     * @param contractId the contract ID
     * @return contract details
     * @throws ContractsFinderException if API communication fails
     */
    Contract getContract(String contractId) throws ContractsFinderException;
    
    /**
     * Retrieve notices published after a specific date.
     * 
     * @param dateTime start date-time in ISO-8601 format
     * @return list of notices
     * @throws ContractsFinderException if API communication fails
     */
    NoticeList getNotices(String dateTime) throws ContractsFinderException;
    
    /**
     * Close the client and release resources.
     */
    void close();
}
