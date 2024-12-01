package com.example.mapper;

import com.example.entity.paper.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;
import java.util.List;

@Mapper
public interface PaperMapper {
    @Select("<script>" +
            "SELECT * FROM db_papers WHERE title LIKE CONCAT('%', #{query}, '%') OR abstracts LIKE CONCAT('%', #{query}, '%')" +
            " LIMIT #{page}, #{pageSize}" +
            "</script>")
    List<Paper> searchPapers(@Param("query") String query,
                             @Param("page") int page,
                             @Param("pageSize") int pageSize);

    // 查询符合条件的总记录数
    @Select("SELECT COUNT(*) FROM db_papers WHERE title LIKE CONCAT('%', #{query}, '%') OR abstracts LIKE CONCAT('%', #{query}, '%')")
    long countPapers(@Param("query") String query);
}

