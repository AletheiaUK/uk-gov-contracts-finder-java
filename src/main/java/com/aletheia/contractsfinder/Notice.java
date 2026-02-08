package com.aletheia.contractsfinder;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a published notice from the Contracts Finder API.
 */
public class Notice {
    
    private String id;
    private String title;
    private String organisationName;
    private LocalDateTime publishedDate;
    private String noticeType;
    private String noticeLink;
    private String status;
    
    public Notice() {
    }
    
    public Notice(String id, String title, LocalDateTime publishedDate) {
        this.id = id;
        this.title = title;
        this.publishedDate = publishedDate;
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
    
    public String getNoticeType() {
        return noticeType;
    }
    
    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }
    
    public String getNoticeLink() {
        return noticeLink;
    }
    
    public void setNoticeLink(String noticeLink) {
        this.noticeLink = noticeLink;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notice notice = (Notice) o;
        return Objects.equals(id, notice.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    @Override
    public String toString() {
        return "Notice{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", organisationName='" + organisationName + '\'' +
                ", noticeType='" + noticeType + '\'' +
                ", publishedDate=" + publishedDate +
                '}';
    }
}
