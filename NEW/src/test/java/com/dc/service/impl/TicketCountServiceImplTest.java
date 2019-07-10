package com.dc.service.impl;

import com.dc.service.TicketCountService;
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
 * @date Created in 2019/7/7 22:47
 * @Modified By:
 */
public class TicketCountServiceImplTest {

    private ApplicationContext applicationContext;

    @Autowired
    private TicketCountService ticketCountService;


    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        ticketCountService = applicationContext.getBean(TicketCountService.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    /*测试查票逻辑（将车次信息+余票信息整合并传给前端）*/
    public void getTrainAndTicketList() throws Exception{
        ticketCountService.generateTicket();
    }
}