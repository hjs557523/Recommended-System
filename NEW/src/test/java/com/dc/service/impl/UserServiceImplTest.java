package com.dc.service.impl;

import com.dc.pojo.User;
import com.dc.service.UserService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/8 1:01
 * @Modified By:
 */
public class UserServiceImplTest {
    @Autowired
    private UserService userService;
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext(new String[]{"classpath:spring-mybatis.xml","classpath:springMvc.xml"});
        //加载spring配置文件

        userService = applicationContext.getBean(UserService.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void isExistId() {
        System.out.println(userService.isExistId(1));
    }
}