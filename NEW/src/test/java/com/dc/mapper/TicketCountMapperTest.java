package com.dc.mapper;

import com.dc.pojo.TicketCount;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:22
 * @Modified By:
 */
public class TicketCountMapperTest {
    private ApplicationContext applicationContext;
    private TicketCountMapper ticketCountMapper;

    private static final long DAY_MILLS = 24 * 60 * 60 * 1000;

    @Before
    public void setUp() throws Exception {
        applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        //加载spring配置文件

        ticketCountMapper = applicationContext.getBean(TicketCountMapper.class);
        //在这里导入要测试的
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void selectByPrimaryKey() {
        try{
            if(ticketCountMapper == null)
                System.out.println("ticketCountMapper is null!");
            TicketCount tc = this.ticketCountMapper.selectByPrimaryKey(739);
            System.out.println(tc);
        }catch(Exception e){
            e.printStackTrace();

        }
    }



    @Test
    public void searchTicket() throws Exception {
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
        Date sTime = adf.parse("2019-01-15");
        Date eTime = adf.parse("2019-01-16");

        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("sTime", sTime);
        input.put("eTime", eTime);
        input.put("tid", new Integer(1));

        TicketCount ticketCount = ticketCountMapper.searchTicket(input);
        System.out.println(ticketCount);
    }


    @Test
    public void minusBySeatLevel() throws Exception{
        SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
        Date sTime = adf.parse("2019-01-01");
        /*Date eTime = adf.parse("2019-01-02");*/

        HashMap<String, Object> input = new HashMap<String, Object>();
        input.put("sTime",sTime);
        /*input.put("eTime",eTime);*/

        input.put("tid", new Integer(1));
        /*TicketCount ticketCount = this.ticketCountMapper.searchTicket(input);*/
        /*int nowNum = ticketCount.getStandRemain();*/
        /* input.put("nowNum",new Integer(nowNum));*/
        input.put("level","second_remain");//选择二等座
        int result = this.ticketCountMapper.minusBySeatLevel(input);
        if(result==1){
            System.out.println("购票成功！");
        }else{
            System.out.println("购票失败！");
        }

        /*时间转换测试*/
        /*Date time = ticketCount.getOrderTime();
        System.out.println(time);
        String s = adf.format(time);
        Date time2 = adf.parse(s);
        System.out.println(time2);
        System.out.println(sTime);*/

    }



    /*应该把ticket_Count表的所有剩余座位类型改为varchar？*/
    @Test
    /*时间转换测试*/
    public void dateTest(){
        SimpleDateFormat adf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat adf2 = new SimpleDateFormat("HH:mm:ss");
        SimpleDateFormat adf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date nowTime = new Date();

        String time1 = adf1.format(nowTime);
        String time2 = adf2.format(nowTime);
        System.out.println(time1);
        System.out.println(time2);
        String time3 = time1+" "+time2;

        try {
            Date time = adf3.parse(time3);
            System.out.println(time3);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*try {



         *//*System.out.println(otime);
            System.out.println(stime);*//*
        } catch (ParseException e) {
            e.printStackTrace();
        }*/

    }


    @Test
    public void selectAllGroupByTime() {
        Date nowDate = new Date();
        Date eTime = new Date(nowDate.getTime() + (30 * DAY_MILLS));

        Map<String, Date> map = new HashMap<String, Date>();
        map.put("sTime", nowDate);
        map.put("eTime", eTime);
        Set<Date> dateSet = ticketCountMapper.selectAllGroupByTime(map);
        System.out.println(dateSet);
    }
}