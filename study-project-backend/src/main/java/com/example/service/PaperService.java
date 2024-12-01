package com.example.service;

import com.example.entity.PageResponse;
import com.example.entity.paper.Paper;

public interface PaperService {
    public PageResponse searchPapers(String query, int page, int pageSize);
    public Paper getPaperById(int paperId);
}
