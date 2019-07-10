package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;


public class Article implements Serializable {
    @ApiModelProperty(value = "文章id")
    private int article_id;
    @ApiModelProperty(value = "文章标题")
    private String article_title;
    @ApiModelProperty(value = "文章内容")
    private String article_text;
    @ApiModelProperty(value = "作者id")
    private int author_id;
    @ApiModelProperty(value = "作者账号")
    private String author_account;
    @ApiModelProperty(value = "文章时间")
    private String article_time;
    @ApiModelProperty(value = "文章图片")
    private String article_photo;
    @ApiModelProperty(value = "景点id")
    private int scenic_id;

    public String getAuthor_account() {
        return author_account;
    }

    public void setAuthor_account(String author_account) {
        this.author_account = author_account;
    }

    public int getScenic_id() {
        return scenic_id;
    }

    public int getArticle_id() {
        return article_id;
    }

    public void setArticle_id(int article_id) {
        this.article_id = article_id;
    }

    public String getArticle_title() {
        return article_title;
    }

    public void setArticle_title(String article_title) {
        this.article_title = article_title;
    }

    public String getArticle_text() {
        return article_text;
    }

    public void setArticle_text(String article_text) {
        this.article_text = article_text;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public String getArticle_time() {
        return article_time;
    }

    public void setArticle_time(String article_time) {
        this.article_time = article_time;
    }

    public String getArticle_photo() {
        return article_photo;
    }

    public void setArticle_photo(String article_photo) {
        this.article_photo = article_photo;
    }

    public void setScenic_id(int scenic_id) {
        this.scenic_id = scenic_id;
    }
}
