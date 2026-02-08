package com.aletheia.contractsfinder;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a list of published notices.
 */
public class NoticeList {
    
    private List<Notice> notices;
    private int totalResults;
    private String fromDate;
    private int pageNumber;
    private int pageSize;
    
    public NoticeList() {
        this.notices = new ArrayList<>();
        this.pageNumber = 1;
        this.pageSize = 10;
        this.totalResults = 0;
    }
    
    public List<Notice> getNotices() {
        return notices;
    }
    
    public void setNotices(List<Notice> notices) {
        this.notices = notices;
    }
    
    public void addNotice(Notice notice) {
        this.notices.add(notice);
    }
    
    public int getTotalResults() {
        return totalResults;
    }
    
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
    
    public String getFromDate() {
        return fromDate;
    }
    
    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
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
    
    @Override
    public String toString() {
        return "NoticeList{" +
                "totalResults=" + totalResults +
                ", fromDate='" + fromDate + '\'' +
                ", pageNumber=" + pageNumber +
                ", pageSize=" + pageSize +
                ", noticeCount=" + notices.size() +
                '}';
    }
}
