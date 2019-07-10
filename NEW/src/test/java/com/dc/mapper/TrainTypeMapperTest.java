package com.dc.mapper;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 17:42
 * @Modified By:
 */
public class TrainTypeMapperTest {
    private ApplicationContext applicationContext;
    @Autowired
    private TrainTypeMapper trainTypeMapper;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        trainTypeMapper = applicationContext.getBean(TrainTypeMapper.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void selectByPrimaryKey(){
        System.out.println(this.trainTypeMapper.selectByPrimaryKey(1));
    }
}