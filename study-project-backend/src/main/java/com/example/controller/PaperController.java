package com.example.controller;

import com.example.entity.PageResponse;
import com.example.entity.RestBean;
import com.example.entity.paper.Paper;
import com.example.entity.user.AccountUser;
import com.example.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public RestBean<Paper> getPaperDetail(@PathVariable("id") int paperId,
                                          @SessionAttribute("account") AccountUser user) {
        System.out.println("111");
        // 获取论文详情，只返回标题和摘要
        Paper paper = paperService.getPaperById(paperId);
        System.out.println(paper.id);

        // 返回论文信息
        return RestBean.success(paper);
    }
}

