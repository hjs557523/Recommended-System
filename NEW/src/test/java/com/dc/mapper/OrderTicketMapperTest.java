package com.dc.mapper;

import com.dc.pojo.OrderTicket;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 19:47
 * @Modified By:
 */
public class OrderTicketMapperTest {

    private ApplicationContext applicationContext;
    @Autowired
    private OrderTicketMapper orderTicketMapper;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        orderTicketMapper = applicationContext.getBean(OrderTicketMapper.class);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void searchHistoryRecord() {
        try {
            if(this.orderTicketMapper == null){
                System.out.println("orderTicketMapper is null!");
            }else{
                /*假设当前操作用户的id为1*/
                ArrayList<OrderTicket> lists = this.orderTicketMapper.searchHistoryRecord(1);
                System.out.println("您的历史订票记录如下");
                System.out.println("历史记录有"+lists.size());
                for (int i = 0; i < lists.size(); i++) {
                    System.out.println(lists.get(i));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}