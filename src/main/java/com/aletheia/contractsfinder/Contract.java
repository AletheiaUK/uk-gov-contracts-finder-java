package com.aletheia.contractsfinder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a government contract from the Contracts Finder API.
 */
public class Contract {
    
    private String id;
    private String title;
    private String description;
    private String organisationId;
    private String organisationName;
    private LocalDateTime publishedDate;
    private LocalDateTime closingDate;
    private String status;
    private String value;
    private String cpvCodes;
    private String contractLink;
    
    public Contract() {
    }
    
    public Contract(String id, String title, String organisationName) {
        this.id = id;
        this.title = title;
        this.organisationName = organisationName;
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getOrganisationId() {
        return organisationId;
    }
    
    public void setOrganisationId(String organisationId) {
        this.organisationId = organisationId;
    }
    
    public String getOrganisationName() {
        return organisationName;
    }
    
    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
    
    public LocalDateTime getPublishedDate() {
        return publishedDate;
    }
    
    public void setPublishedDate(LocalDateTime publishedDate) {
        this.publishedDate = publishedDate;
    }
    
    public LocalDateTime getClosingDate() {
        return closingDate;
    }
    
    public void setClosingDate(LocalDateTime closingDate) {
        this.closingDate = closingDate;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getCpvCodes() {
        return cpvCodes;
    }
    
    public void setCpvCodes(String cpvCodes) {
        this.cpvCodes = cpvCodes;
    }
    
    public String getContractLink() {
        return contractLink;
    }
    
    public void setContractLink(String contractLink) {
        this.contractLink = contractLink;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Contract contract = (Contract) o;
        return Objects.equals(id, contract.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Contract{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", organisationName='" + organisationName + '\'' +
                ", status='" + status + '\'' +
                ", publishedDate=" + publishedDate +
                ", closingDate=" + closingDate +
                '}';
    }
}
