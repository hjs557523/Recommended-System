package com.dc.service.impl;

import com.dc.pojo.Route;
import com.dc.service.RouteService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/8 0:14
 * @Modified By:
 */
@RunWith(JUnit4.class)
public class RouteServiceImplTest {

    private ApplicationContext applicationContext;

    private RouteService routeServiceImpl;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        routeServiceImpl = applicationContext.getBean(RouteService.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void findTransferStation() {
        try{
            if(routeServiceImpl == null) {
                System.out.println("routeServiceImpl 为空！");
            }
            /*SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
            Date chufashijian = adf.parse("2019-01-01");*/
            List<List<Route>> lists = routeServiceImpl.findTransferStation("深圳","无锡");
            System.out.println("从数据库中获得的查询结果为"+(lists.size()/2)+"条");
          /*  System.out.println(lists.get(0).get(0));
            System.out.println(lists.get(0).get(1));
            System.out.println(lists.get(1).get(0));
            System.out.println(lists.get(1).get(1));
            System.out.println(lists.get(0).get(2));
            System.out.println(lists.get(0).get(3));
            System.out.println(lists.get(1).get(2));
            System.out.println(lists.get(1).get(3));*/
            for (int i = 0; i < lists.get(0).size(); i=i+2) {
                System.out.println(lists.get(0).get(i));
                System.out.println(lists.get(0).get(i+1));
                System.out.println(lists.get(1).get(i));
                System.out.println(lists.get(1).get(i+1));
            }



        }catch(Exception e) {
            e.printStackTrace();
        }
    }
}