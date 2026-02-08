package com.aletheia.contractsfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the results of a contract search.
 */
public class SearchResults {
    
    private List<Contract> contracts;
    private int totalResults;
    private int pageNumber;
    private int pageSize;
    
    public SearchResults() {
        this.contracts = new ArrayList<>();
        this.pageNumber = 1;
        this.pageSize = 10;
        this.totalResults = 0;
    }
    
    public List<Contract> getContracts() {
        return contracts;
    }
    
    public void setContracts(List<Contract> contracts) {
        this.contracts = contracts;
    }
    
    public void addContract(Contract contract) {
        this.contracts.add(contract);
    }
    
    public int getTotalResults() {
        return totalResults;
    }
    
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    
    public int getPageNumber() {
        return pageNumber;
    }
    
    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }
    
    public int getTotalPages() {
        return (totalResults + pageSize - 1) / pageSize;
    }
    
    public boolean hasNextPage() {
        return pageNumber < getTotalPages();
    }
    
    public boolean hasPreviousPage() {
        return pageNumber > 1;
    }
    
    @Override
    public String toString() {
        return "SearchResults{" +
                "totalResults=" + totalResults +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", contractCount=" + contracts.size() +
                '}';
    }
}
