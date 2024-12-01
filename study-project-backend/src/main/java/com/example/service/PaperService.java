package com.example.service;

import com.example.entity.PageResponse;

public interface PaperService {
    public PageResponse searchPapers(String query, int page, int pageSize);
}
