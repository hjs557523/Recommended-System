package com.dc.mapper;

import com.dc.base.pojo.BaseModel;
import com.dc.pojo.Article;
import com.dc.pojo.Comment;
import com.dc.pojo.Scenic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
@Repository
public interface ArticleDao {

    public List<Article> selectArticleAll(@Param("title") String title,@Param("scenicIds") String scenicIds) throws SQLException;

    Article selectArticleById(@Param("Id") int id) throws SQLException;

    public int commitComment(@Param("scenic_id") int scenic_id, @Param("user_id") int user_id,@Param("user_account") String user_account, @Param("comment_text") String comment_text) throws SQLException;

    public List<Comment> selectAllCommentByScenicId(@Param("scenic_id") int scenic_id) throws SQLException;

    public int addArticle(Article article) throws SQLException;

    public List<Scenic> isExistScenic(@Param("scenic_id") int scenic_id) throws SQLException;

    public List<Scenic> scenicList() throws SQLException;

    public String getPythonAp() throws SQLException;

    public List<Article> selectArticleAll2(@Param("title") String title) throws SQLException;
}