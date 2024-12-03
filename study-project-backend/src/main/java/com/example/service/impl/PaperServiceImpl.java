package com.example.service.impl;

import com.example.entity.PageResponse;
import com.example.entity.PaperDetail;
import com.example.entity.paper.Paper;
import com.example.mapper.PaperMapper;
import com.example.service.PaperService;
import io.milvus.v2.service.collection.request.LoadCollectionReq;
import io.milvus.v2.service.vector.response.GetResp;
import io.milvus.v2.service.vector.response.QueryResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import io.milvus.v2.client.ConnectConfig;
import io.milvus.v2.client.MilvusClientV2;
import io.milvus.v2.service.vector.request.SearchReq;
import io.milvus.v2.service.vector.request.data.FloatVec;
import io.milvus.v2.service.vector.response.SearchResp;
import io.milvus.v2.service.vector.request.GetReq;

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
        List<Paper> relatedPapers = null;

        relatedPapers = paperMapper.getRelatedPapers(now.getCategory(), paperId);  // 获取同类论文
        if (cits == null) { // 如果没有引用，则直接返回当前论文
            return new PaperDetail(now, new ArrayList<>(),relatedPapers);
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
        // 获取同类论文，仅VIP用户可见



        return new PaperDetail(now, citationPapers, relatedPapers);

    }

    public List<Paper> getSimilarPaperById(int paperId) {
        MilvusClientV2 client = new MilvusClientV2(ConnectConfig.builder()
                .uri("http://localhost:19530")
                .token("root:Milvus")
                .build());

        client.loadCollection(LoadCollectionReq.builder()
                .collectionName("db_vector")
                .build());

        GetReq getReq = GetReq.builder()
                .collectionName("db_vector")
                .ids(List.of(paperId))
                .outputFields(Arrays.asList("vector"))
                .build();
        GetResp getResp = client.get(getReq);
        List<QueryResp.QueryResult> results = getResp.getGetResults();
        String tem = results.get(0).getEntity().get("vector").toString();
        String[] strArray = tem.substring(1, tem.length() - 1).split(",");
        // 创建一个浮点数组
        float[] targetVector = new float[strArray.length];
        // 将每个字符串元素转换为 float
        for (int i = 0; i < strArray.length; i++) {
            targetVector[i] = Float.parseFloat(strArray[i]);
        }
        SearchReq searchReq = SearchReq.builder()
                .collectionName("db_vector")
                .data(Collections.singletonList(new FloatVec(targetVector)))
                .topK(11)
                .build();
        SearchResp searchResp = client.search(searchReq);
        List<Long> similarPaperIds = new ArrayList<>();
        for (SearchResp.SearchResult result : searchResp.getSearchResults().get(0)) {
            similarPaperIds.add((Long) result.getId());
        }
        if (similarPaperIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Paper> similarPapers = new ArrayList<>();
        for (Long id : similarPaperIds) {
            similarPapers.add(paperMapper.getPaperById(id.intValue()));
        }
        similarPapers.remove(0);
        return similarPapers;
    }
}
