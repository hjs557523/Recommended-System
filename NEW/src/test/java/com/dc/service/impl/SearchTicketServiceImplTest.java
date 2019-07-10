package com.dc.service.impl;

import com.dc.pojo.TrainAndTicket;
import com.dc.service.RouteService;
import com.dc.service.SearchTicketService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/9 11:41
 * @Modified By:
 */
public class SearchTicketServiceImplTest {
    private ApplicationContext applicationContext;

    private SearchTicketServiceImpl searchTicketService;

    @Before
    public void setUp() throws Exception {

        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        searchTicketService = applicationContext.getBean(SearchTicketServiceImpl.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTrainAndTicketList() throws Exception {
        if (searchTicketService == null) {
            System.out.println("searchTicketServiceImpl 为空！");
        }
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
        Date chufashijian = adf.parse("2019-07-9");
        ArrayList<TrainAndTicket> resultList = this.searchTicketService.getTrainAndTicketList("深圳",
                "广州", chufashijian, 2);//直达路线余票查询
        System.out.println("车次余票信息记录有：" + resultList.size());
        for (int i = 0; i < resultList.size(); i++) {
            System.out.println(resultList.get(i));
        }
    }
}
