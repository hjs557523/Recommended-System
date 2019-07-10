package com.dc.service.impl;

import com.dc.base.pojo.BaseModel;
import com.dc.base.util.PythonUtil;
import com.dc.mapper.ArticleDao;
import com.dc.mapper.UserDao;
import com.dc.pojo.Article;
import com.dc.pojo.Comment;
import com.dc.pojo.Scenic;
import com.dc.pojo.User;
import com.dc.service.ArticleService;
import com.dc.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserService userService;

    public BaseModel scenicList() {
        BaseModel baseModel = new BaseModel();
        try {
            List<Scenic> scenics = articleDao.scenicList();
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("查询景点列表成功！");
            baseModel.setData(scenics);
            return baseModel;
        } catch (Exception e) {
            e.printStackTrace();
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("数据错误！");
            return baseModel;
        }
    }

    public BaseModel addArticle(Article article) {
        BaseModel baseModel = new BaseModel();
        try {
            if (article.getArticle_text() == null && article.getArticle_text().equals("") && article.getArticle_title() == null && article.getArticle_title().equals("") && article.getAuthor_id() <= 0 && article.getScenic_id() <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (userService.isExistId(article.getAuthor_id()).getResultCode() != 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("该用户不存在，新增文章失败！");
                return baseModel;
            } else if (articleDao.isExistScenic(article.getScenic_id()).size() <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("该景点不存在，新增文章失败！");
                return baseModel;
            }
            User user = userDao.selectUserId(article.getAuthor_id());
            article.setAuthor_account(user.getUser_account());
            int num = articleDao.addArticle(article);
            if (num <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("新增新闻失败！");
                return baseModel;
            }
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("新增新闻成功！");
            return baseModel;
        } catch (Exception e) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("新增新闻失败！");
            return baseModel;
        }
    }

    public BaseModel selectArticleAll(String title, Integer page, Integer maxSize, String user_id, String num) {
        BaseModel baseModel = new BaseModel();
        try {
            String scenicIds = PythonUtil.getScenicIdByPython(user_id, num,articleDao.getPythonAp());
            if (Integer.parseInt(num) <= 0 || num == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("查询所有新闻失败！num不能小于1！");
                return baseModel;
            }
            if (scenicIds == null || scenicIds.equals("")) {
                scenicIds = null;
            }
            PageHelper.startPage(page, maxSize);
            List<Article> articles = articleDao.selectArticleAll(title, scenicIds);
            PageInfo articleList = new PageInfo(articles, 5);
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("查询所有新闻成功！");
            baseModel.setData(articleList);
            return baseModel;
        } catch (Exception e) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("查询所有新闻失败！");
            return baseModel;
        }
    }

    /**
     * author:hjs
     * @param title
     * @param page
     * @param maxSize
     * @param user_id
     * @param num
     * @return
     */
    public BaseModel selectArticleAll2(String title, Integer page, Integer maxSize, String user_id, String num) {
        BaseModel baseModel = new BaseModel();
        try{
            if (Integer.parseInt(num) <=0 || num == null){
                baseModel.setResultCode(1);//错误码1
                baseModel.setMessage("查询所有新闻失败！num值不能小于1");
                return baseModel;
            }
            PageHelper.startPage(page,maxSize);
            List<Article> articles = articleDao.selectArticleAll2(title);
            PageInfo articleList = new PageInfo(articles,5);
            baseModel.setResultCode(0);//成功
            baseModel.setMessage("查询所有新闻成功！");
            baseModel.setData(articleList);
            return baseModel;
        }catch (Exception e){
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("查询所有新闻失败");
            return baseModel;
        }
    }


    public BaseModel selectArticleById(int id) {
        BaseModel baseModel = new BaseModel();
        try {
            Article article = articleDao.selectArticleById(id);
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("根据id查询新闻成功！");
            baseModel.setData(article);
            return baseModel;
        } catch (Exception e) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("根据id查询新闻失败！");
            return baseModel;
        }
    }

    public BaseModel commitComment(int scenic_id, int user_id, String comment_text) {
        BaseModel baseModel = new BaseModel();
        try {
            comment_text = comment_text.trim();
            if (scenic_id <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (user_id <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (comment_text == null) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (comment_text.equals("")) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("参数错误！");
                return baseModel;
            }
            if (userService.isExistId(user_id).getResultCode() != 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("该用户不存在，评论失败！");
                return baseModel;
            } else if (articleDao.isExistScenic(scenic_id).size() <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("该景点不存在，评论失败！");
                return baseModel;
            }
            User user = userDao.selectUserId(user_id);
            int res = articleDao.commitComment(scenic_id, user_id, user.getUser_account(), comment_text);
            if (res <= 0) {
                baseModel.setResultCode(1);//如果为0则表示成功
                baseModel.setMessage("评论失败！");
                return baseModel;
            }
            baseModel.setResultCode(0);//如果为0则表示成功
            baseModel.setMessage("评论成功！");
            return baseModel;
        } catch (Exception e) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("评论失败！");
            return baseModel;
        }
    }

    public BaseModel selectAllCommentByScenicId(int scenic_id) {
        BaseModel baseModel = new BaseModel();
        if (scenic_id <= 0) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("参数错误！");
            return baseModel;
        }
        try {
            List<Comment> comments = articleDao.selectAllCommentByScenicId(scenic_id);
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("查询景点评论成功！");
            baseModel.setData(comments);
            return baseModel;
        } catch (Exception e) {
            baseModel.setResultCode(1);//如果为0则表示成功
            baseModel.setMessage("查询景点评论失败！");
            return baseModel;
        }
    }


}
