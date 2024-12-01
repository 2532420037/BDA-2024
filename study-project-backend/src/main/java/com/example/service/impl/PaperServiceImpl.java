package com.example.service.impl;

import com.example.entity.PageResponse;
import com.example.entity.paper.Paper;
import com.example.mapper.PaperMapper;
import com.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PaperServiceImpl implements PaperService {
    @Autowired
    private PaperMapper paperMapper;

    // 搜索论文
    public PageResponse searchPapers(String query, int page, int pageSize) {
        int offset = (page - 1) * pageSize;  // 计算分页的偏移量
        List<Paper> papers = paperMapper.searchPapers(query, offset, pageSize);
        long totalCount = paperMapper.countPapers(query); // 获取总记录数
        return new PageResponse(papers, totalCount);
    }

    // 获取论文信息
    public Paper getPaperById(int paperId) {
        return paperMapper.getPaperById(paperId);
    }
}
