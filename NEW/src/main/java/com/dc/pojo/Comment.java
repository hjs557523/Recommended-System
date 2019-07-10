package com.dc.pojo;

import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class Comment implements Serializable {
    @ApiModelProperty(value = "评论id")
    private int comment_id;
    @ApiModelProperty(value = "评论内容")
    private String comment_text;
    @ApiModelProperty(value = "评论时间")
    private String comment_time;
    @ApiModelProperty(value = "景点id")
    private int comment_aim_id;
    @ApiModelProperty(value = "用户id")
    private int commentator_id;
    @ApiModelProperty(value = "用户账号")
    private String commentator_account;


    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public String getComment_text() {
        return comment_text;
    }

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public int getComment_aim_id() {
        return comment_aim_id;
    }

    public void setComment_aim_id(int comment_aim_id) {
        this.comment_aim_id = comment_aim_id;
    }

    public int getCommentator_id() {
        return commentator_id;
    }

    public void setCommentator_id(int commentator_id) {
        this.commentator_id = commentator_id;
    }

    public String getCommentator_account() {
        return commentator_account;
    }

    public void setCommentator_account(String commentator_account) {
        this.commentator_account = commentator_account;
    }
}
