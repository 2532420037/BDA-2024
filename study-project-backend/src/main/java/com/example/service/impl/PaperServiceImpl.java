package com.example.service.impl;

import com.example.entity.PageResponse;
import com.example.entity.PaperDetail;
import com.example.entity.paper.Paper;
import com.example.mapper.PaperMapper;
import com.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.ArrayList;

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
    public PaperDetail getPaperById(int paperId) {
        Paper now = paperMapper.getPaperById(paperId);
        String cits = now.getCitations();
        if (cits == null) { // 如果没有引用，则直接返回当前论文
            return new PaperDetail(now, new ArrayList<>());
        }

        String[] citIds = cits.split(",");

        // 创建一个存放 Paper 对象的列表
        List<Paper> citationPapers = new ArrayList<>();

        // 遍历 citIds 数组，获取对应的 Paper 对象
        for (String citId : citIds) {
            int id = Integer.parseInt(citId.trim()); // 转换为 int 类型，去除可能的空格
            Paper citationPaper = paperMapper.getPaperById(id); // 获取 Paper 对象
            citationPapers.add(citationPaper); // 添加到列表
        }

        return new PaperDetail(now, citationPapers);
    }
}
