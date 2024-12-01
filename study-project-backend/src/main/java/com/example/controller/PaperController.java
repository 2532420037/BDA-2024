package com.example.controller;

import com.example.entity.PageResponse;
import com.example.entity.RestBean;
import com.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/api/paper")
public class PaperController {

    @Autowired
    private PaperService paperService;

    // 搜索论文接口
    @GetMapping("/search")
    public RestBean< PageResponse> searchPapers(
            @RequestParam String query,  // 搜索关键词
            @RequestParam int page,      // 当前页码
            @RequestParam int pageSize   // 每页显示的条数
    ) {
        return RestBean.success(paperService.searchPapers(query, page, pageSize));
    }
}

