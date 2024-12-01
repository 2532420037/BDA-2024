package com.example.entity;

import com.example.entity.paper.Paper;

import java.util.List;

public class PageResponse {

    private List<Paper> papers;
    private long totalCount;

    public PageResponse(List<Paper> papers, long totalCount) {
        this.papers = papers;
        this.totalCount = totalCount;
    }

    public List<Paper> getPapers() {
        return papers;
    }

    public void setPapers(List<Paper> papers) {
        this.papers = papers;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }
}