package com.dc.mapper;

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
 * @date Created in 2019/7/7 16:08
 * @Modified By:
 */
public class TrainMapperTest {

    private ApplicationContext applicationContext;
    @Autowired
    private TrainMapper trainMapper;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        trainMapper = applicationContext.getBean(TrainMapper.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void selectByTrainNum() {
        System.out.println(this.trainMapper.selectByTrainNum("D886").getId());
    }

}