package com.dc.controller;


import com.dc.base.controller.BaseController;
import com.dc.base.pojo.BaseModel;
import com.dc.pojo.Article;
import com.dc.service.ArticleService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequestMapping("/article")
public class ArticleController extends BaseController {
    @Autowired
    private ArticleService articleService;

    @RequestMapping("/selectArticleAll")
    @ResponseBody
    @ApiOperation(value = "查询所有文章", notes = "查询", httpMethod = "GET")
    public BaseModel selectArticleAll(@RequestParam(value = "title", required = false) String title
            , @RequestParam(value = "userId", required = true) String userId
            , @RequestParam(value = "num", required = false, defaultValue = "10") String num
            , @RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "maxSize", defaultValue = "10") Integer maxSize) {
        BaseModel baseModel = articleService.selectArticleAll(title, page, maxSize, userId, num);
        return baseModel;
    }

    @RequestMapping("/selectArticleAll2")
    @ResponseBody
    @ApiOperation(value = "查询所有文章", notes = "查询", httpMethod = "GET")
    public BaseModel selectArticleAll2(@RequestParam(value = "title",required = false) String title
            , @RequestParam(value = "userId", required = false) String userId
            , @RequestParam(value = "num", required = false, defaultValue = "10") String num
            , @RequestParam(value = "page",defaultValue = "1") Integer page
            , @RequestParam(value = "maxSize", defaultValue = "10") Integer maxSize) {
        BaseModel baseModel = articleService.selectArticleAll2(title, page, maxSize, userId, num);
        return baseModel;
    }


    @RequestMapping("/selectArticleAllPOST")
    @ResponseBody
    @ApiOperation(value = "查询所有文章POST", notes = "查询", httpMethod = "POST")
    public BaseModel selectArticleAllPost(@RequestParam(value = "title", required = false) String title
            , @RequestParam(value = "userId", required = true) String userId
            , @RequestParam(value = "num", required = false, defaultValue = "10") String num
            , @RequestParam(value = "page", defaultValue = "1") Integer page
            , @RequestParam(value = "maxSize", defaultValue = "10") Integer maxSize) {
        BaseModel baseModel = articleService.selectArticleAll(title, page, maxSize, userId, num);
        return baseModel;
    }


    @RequestMapping("/selectArticleById")
    @ResponseBody
    @ApiOperation(value = "根据id查询新闻", notes = "查询", httpMethod = "GET")
    public BaseModel selectArticleById(@RequestParam(value = "article_id") int article_id) {
        BaseModel baseModel = articleService.selectArticleById(article_id);
        return baseModel;
    }

    @RequestMapping("/addArticle")
    @ResponseBody
    @ApiOperation(value = "新增新闻", notes = "新增", httpMethod = "GET")
    public BaseModel addArticle(@RequestParam(value = "author_id") int author_id, @RequestParam(value = "article_text") String article_text, @RequestParam(value = "article_title") String article_title, @RequestParam(value = "scenic_id") int scenic_id) {
        Article article1 = new Article();
        article1.setArticle_text(article_text);
        article1.setArticle_title(article_title);
        article1.setAuthor_id(author_id);
        article1.setScenic_id(scenic_id);
        BaseModel baseModel = articleService.addArticle(article1);
        return baseModel;
    }

    @RequestMapping("/commitComment")
    @ResponseBody
    @ApiOperation(value = "提交评论", notes = "新增", httpMethod = "GET")
    public BaseModel commitComment(@RequestParam(value = "scenic_id") int scenic_id, @RequestParam(value = "user_id") int user_id, @RequestParam(value = "comment_text") String comment_text) {
        BaseModel baseModel = articleService.commitComment(scenic_id, user_id, comment_text);
        return baseModel;
    }

    @RequestMapping("/selectAllCommentByScenicId")
    @ResponseBody
    @ApiOperation(value = "查询景点评论", notes = "查询", httpMethod = "GET")
    public BaseModel selectAllCommentByScenicId(@RequestParam(value = "scenic_id") int scenic_id) {
        BaseModel baseModel = articleService.selectAllCommentByScenicId(scenic_id);
        return baseModel;
    }

    @RequestMapping("/scenicList")
    @ResponseBody
    @ApiOperation(value = "景点列表", notes = "查询", httpMethod = "GET")
    public BaseModel scenicList() {
        BaseModel baseModel = articleService.scenicList();
        return baseModel;
    }
}
