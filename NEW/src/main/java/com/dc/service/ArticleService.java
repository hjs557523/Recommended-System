package com.dc.service;

import com.dc.base.pojo.BaseModel;
import com.dc.pojo.Article;


public interface ArticleService {
    public BaseModel selectArticleAll(String title, Integer page, Integer maxSize, String user_id, String num);

    public BaseModel selectArticleAll2(String title, Integer page, Integer maxSize, String user_id, String num);

    public BaseModel selectArticleById(int id);

    public BaseModel commitComment(int scenic_id, int user_id, String comment_text);

    public BaseModel selectAllCommentByScenicId(int scenic_id);

    public BaseModel addArticle(Article article);

    public BaseModel scenicList();
}
