package com.dc.controller;


import com.dc.base.controller.BaseController;
import com.dc.base.pojo.BaseModel;
import com.dc.pojo.User;
import com.dc.service.ArticleService;
import com.dc.service.UserService;
import com.wordnik.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Controller
@ResponseBody
@RequestMapping("/user")
public class UserController extends BaseController {
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    @ResponseBody
    @ApiOperation(value = "登录", notes = "查询", httpMethod = "GET")
    public BaseModel login(@RequestParam(value = "user_account") String user_account, @RequestParam(value = "user_password") String user_password, HttpSession session) {
        BaseModel baseModel = userService.login(user_account,user_password);
        session.setAttribute("user", ((List<User>)baseModel.getData()).get(0));
        return baseModel;
    }
    @RequestMapping("/register")
    @ResponseBody
    @ApiOperation(value = "注册", notes = "新增", httpMethod = "GET")
    public BaseModel register(@RequestParam(value = "user_account") String user_account,@RequestParam(value = "user_password") String user_password) {
        BaseModel baseModel = userService.register(user_account,user_password);
        return baseModel;
    }
    @RequestMapping("/isexist")
    @ResponseBody
    @ApiOperation(value = "账号是否存在", notes = "查询", httpMethod = "GET")
    public BaseModel isexist(@RequestParam(value = "user_account") String user_account) {
        BaseModel baseModel = userService.isexist(user_account);
        return baseModel;
    }
    @RequestMapping("/userList")
    @ResponseBody
    @ApiOperation(value = "用户列表", notes = "查询", httpMethod = "GET")
    public BaseModel userList() {
        BaseModel baseModel = userService.userList();
        return baseModel;
    }
    @RequestMapping("/insertUserByCsv")
    @ResponseBody
    @ApiOperation(value = "通过CSV插入用户", notes = "新增", httpMethod = "GET")
    public BaseModel insertUserByCsv() {
        BaseModel baseModel = userService.insertUserByCsv();
        return baseModel;
    }
}