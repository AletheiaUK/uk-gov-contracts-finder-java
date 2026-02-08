package com.aletheia.contractsfinder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Search criteria for querying the Contracts Finder API.
 */
public class SearchCriteria {
    
    private String keywords;
    private List<String> organisationIds;
    private LocalDate publishedFrom;
    private LocalDate publishedTo;
    private String status;
    private Integer pageNumber;
    private Integer pageSize;
    
    public SearchCriteria() {
        this.organisationIds = new ArrayList<>();
        this.pageSize = 10;
        this.pageNumber = 1;
    }
    
    public String getKeywords() {
        return keywords;
    }
    
    public SearchCriteria keywords(String keywords) {
        this.keywords = keywords;
        return this;
    }
    
    public List<String> getOrganisationIds() {
        return organisationIds;
    }
    
    public SearchCriteria addOrganisation(String organisationId) {
        this.organisationIds.add(organisationId);
        return this;
    }
    
    public LocalDate getPublishedFrom() {
        return publishedFrom;
    }
    
    public SearchCriteria publishedFrom(LocalDate publishedFrom) {
        this.publishedFrom = publishedFrom;
        return this;
    }
    
    public LocalDate getPublishedTo() {
        return publishedTo;
    }
    
    public SearchCriteria publishedTo(LocalDate publishedTo) {
        this.publishedTo = publishedTo;
        return this;
    }
    
    public String getStatus() {
        return status;
    }
    
    public SearchCriteria status(String status) {
        this.status = status;
        return this;
    }
    
    public Integer getPageNumber() {
        return pageNumber;
    }
    
    public SearchCriteria pageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
        return this;
    }
    
    public Integer getPageSize() {
        return pageSize;
    }
    
    public SearchCriteria pageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SearchCriteria that = (SearchCriteria) o;
        return Objects.equals(keywords, that.keywords) &&
               Objects.equals(organisationIds, that.organisationIds) &&
               Objects.equals(publishedFrom, that.publishedFrom) &&
               Objects.equals(publishedTo, that.publishedTo) &&
               Objects.equals(status, that.status) &&
               Objects.equals(pageNumber, that.pageNumber) &&
               Objects.equals(pageSize, that.pageSize);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(keywords, organisationIds, publishedFrom, publishedTo, status, pageNumber, pageSize);
    }
    
    @Override
    public String toString() {
        return "SearchCriteria{" +
                "keywords='" + keywords + '\'' +
                ", organisationIds=" + organisationIds +
                ", publishedFrom=" + publishedFrom +
                ", publishedTo=" + publishedTo +
                ", status='" + status + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                '}';
    }
}
