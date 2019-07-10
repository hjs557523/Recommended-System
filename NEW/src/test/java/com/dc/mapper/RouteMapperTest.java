package com.dc.mapper;

import com.dc.pojo.Route;
import com.dc.pojo.TrainInfo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:53
 * @Modified By:
 */
public class RouteMapperTest {

    private ApplicationContext applicationContext;
    @Autowired
    private RouteMapper routeMapper;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        routeMapper = applicationContext.getBean(RouteMapper.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void selectByStation() {
        try {
            if(routeMapper == null) {
                System.out.println("route mapper is null!!!!");
            }

            List<TrainInfo> list = routeMapper.selectByStation("无锡", "沈阳");
            System.out.println("测试：输入出发站、终点站");
            System.out.println("从数据库中获得的查询结果为"+list.size()+"条");
            for (int i = 0;i < list.size();i++) {
                System.out.println(list.get(i));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void selectTransferStation(){
        try {
            if(routeMapper == null) {
                System.out.println("route mapper is null!!!!");
            }
            /*SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
            Date chufashijian = adf.parse("2019-01-01");*/
            List<Route> list = routeMapper.selectTransferStation("深圳", "无锡");
            System.out.println("测试：输入出发站、终点站");
            System.out.println("从数据库中获得的查询结果为"+list.size()+"条");
            for (int i = 0;i < list.size();i++) {
                System.out.println(list.get(i));
                System.out.println(list.get(i).getStationName());
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    @Test
    public void selectByPrimaryKey() {
        Route route = routeMapper.selectByPrimaryKey(2);

        System.out.println(route);
    }

    @Test
    public void getRouteListForCount(){
        List<Route> lists = this.routeMapper.getRouteListForCount(1,2,4);
        int totalDistance=0;
        for (int i = 0; i < lists.size(); i++) {
            totalDistance = totalDistance + lists.get(i).getNextDistance();
            System.out.println(lists.get(i));
        }
        System.out.println(totalDistance);
        System.out.println(new Date());

    }

    @Test
    public void selectStationCountDistance() {
        Map<String, Integer> searchCondition = new HashMap<String, Integer>();
        searchCondition.put("trainId", 1);
        searchCondition.put("startSeq", 2);
        searchCondition.put("endSeq", 1);

        System.out.println(routeMapper.selectStationCountDistance(searchCondition));
    }
}