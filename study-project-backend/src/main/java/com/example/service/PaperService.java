package com.example.service;

import com.example.entity.PageResponse;
import com.example.entity.PaperDetail;
import com.example.entity.paper.Paper;

import java.util.List;

public interface PaperService {
    public PageResponse searchPapers(String query, int page, int pageSize);
    public PaperDetail getPaperById(int paperId);
    public List<Paper> getSimilarPaperById(int paperId);
}
