package com.example.entity;

import com.example.entity.paper.Paper;

import java.util.List;
import lombok.Data;

@Data
public class PaperDetail {
    private Paper paper;
    private List<Paper> citedPapers;

    public PaperDetail(Paper paper, List<Paper> citedPapers) {
        this.paper = paper;
        this.citedPapers = citedPapers;
    }
}
