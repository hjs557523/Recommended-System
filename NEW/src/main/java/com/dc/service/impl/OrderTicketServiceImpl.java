package com.dc.service.impl;

import com.dc.mapper.*;
import com.dc.pojo.OrderTicket;
import com.dc.pojo.Route;
import com.dc.pojo.TrainAndTicket;
import com.dc.pojo.User;
import com.dc.service.OrderTicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:09
 * @Modified By:
 */
@Service
public class OrderTicketServiceImpl implements OrderTicketService {

    @Autowired
    private OrderTicketMapper orderTicketMapper;

    @Autowired
    private TicketCountMapper ticketCountMapper;
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private SeatTypeMapper seatTypeMapper;
    @Autowired
    private TrainMapper trainMapper;


    /**用户购票功能（由于座位表还有待确定，不产生具体座位信息）
     *
     * @param level  座位类型
     * @param trainAndTicket  用户选中的车次余票信息
     * @param user 当前使用用户
     * @return    返回成功与否的标志
     */
    public boolean buyTrainTicket(String level, TrainAndTicket trainAndTicket, User user) {
        Boolean isSuccess = false;//初始结果为购买失败

        //直达路线的购票逻辑
        if(trainAndTicket==null){
            return isSuccess;//没查到符合的车票，怎样购买都失败
        }else {
            try {
                SimpleDateFormat adf = new SimpleDateFormat("yyyy-MM-dd");
                String str = adf.format(trainAndTicket.getTicketCount().getOrderTime());
                Date sTime = adf.parse(str);
                HashMap<String, Object> input = new HashMap<String, Object>();
                input.put("sTime", sTime);
                input.put("level", level);
                input.put("tid", trainAndTicket.getTicketCount().getTrainId());
                /*input.put("nowNum",trainAndTicket.getTicketCount().);*/

                /*进行购票操作-1*/
                int result = this.ticketCountMapper.minusBySeatLevel(input);

                if (result == 1) {//购票成功
                    System.out.println("已经执行了对应车次的总票数-1，对应座位类型票数-1的子操作！");
                    List<Route> lists = this.routeMapper.getRouteListForCount(trainAndTicket.getTrainInfo().getTrainId(),
                            trainAndTicket.getTrainInfo().getRouteSeq1(),trainAndTicket.getTrainInfo().getRouteSeq2());
                    int totalDistance=0;
                    for (int i = 0; i < lists.size(); i++) {
                        totalDistance = totalDistance + lists.get(i).getNextDistance();//统计该直达路线的总距离(km)
                    }
                    BigDecimal perCost = this.seatTypeMapper.getPercostBySeatType(level);//获得对应座位的每公里运费(元/km)

                    BigDecimal price = new BigDecimal(Integer.toString(totalDistance)).multiply(perCost);//计算得到总票价

                    /*接下来进行生成订单操作*/
                    OrderTicket orderTicket = new OrderTicket();
                    /*orderTicket主键id自动生成并插入*/

                    orderTicket.setOrderTime(new Date());//设置用户当前购票时间
                    orderTicket.setTrainId(trainAndTicket.getTrainInfo().getTrainId());//设置用户所购车次id
                    orderTicket.setTicketPrice(price);//设置用户本次行程的总花费金额

                    //orderTicket.setSeatId(0);
                    //因为座位号码如何生成我们还没真正确定，所以我这一部分的赋值先忽略

                    orderTicket.setSeatType(level);//设置用户订购的座位类型
                    orderTicket.setUserId(user.getUser_id());//设置用户个人的id号
                    orderTicket.setTicketId(trainAndTicket.getTicketCount().getId());//设置ticketCount的Id
                    orderTicket.setDepatureRouteId(trainAndTicket.getTrainInfo().getRouteSeq1());//设置出发站在该路线的序号
                    orderTicket.setArriveRouteId(trainAndTicket.getTrainInfo().getRouteSeq2());//设置目的站在该路线的序号
                    orderTicket.setEffect(new Integer(1));//订票生效

                    int flag = 0;//订票操作结果标志
                    flag = this.orderTicketMapper.insertSelective(orderTicket);
                    if(flag == 1){
                        System.out.println("已经执行了生成订单的操作！");
                        isSuccess = true;
                    }else{
                        System.out.println("购买成功，但生成订单失败！");
                        isSuccess = false;
                    }
                } else {
                    isSuccess = false;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return isSuccess;
        }
    }


    /**用户查询的所有历史订单
     *
     * @param user  当前使用用户
     * @return  返回历史订单（订票与退票）列表
     */
    public ArrayList<HashMap<String,Object>> searchHistoryRecord(User user) {
        ArrayList<HashMap<String,Object>> hashMapArrayList = new ArrayList<HashMap<String, Object>>();
        ArrayList<OrderTicket> lists = this.orderTicketMapper.searchHistoryRecord(user.getUser_id());//获得当前用户所有orderTicket
        if(lists == null){
            System.out.println("您的历史订票与退票记录为空！");
        }else{
            for (int i = 0; i < lists.size(); i++) {
                HashMap<String,Object> input = new HashMap<String, Object>();
                Route chufazhan = this.routeMapper.getStationMessage(lists.get(i).getTrainId(),lists.get(i).getDepatureRouteId());//获得出发站信息
                Route mudizhan = this.routeMapper.getStationMessage(lists.get(i).getTrainId(),lists.get(i).getArriveRouteId());//获得目的站信息

                Date nyr = ticketCountMapper.selectByPrimaryKey(lists.get(i).getTicketId()).getOrderTime();

                Date chufashijian = chufazhan.getDepartureTime();
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
                String timestr = sdf.format(chufashijian);
                String strnyr = sdf2.format(nyr);
                String rstr = strnyr + " " + timestr;

                String trainNum = this.trainMapper.selectByPrimaryKey(lists.get(i).getTrainId()).getTrainNum();
                String seatType = lists.get(i).getSeatType();
                if (seatType.equals("stand_remain"))
                    seatType = "无座";
                else if (seatType.equals("second_remain"))
                    seatType = "二等座";
                else if (seatType.equals("first_remain"))
                    seatType = "一等座";
                else if (seatType.equals("business_remain"))
                    seatType = "商务座";
                else if (seatType.equals("soft_sleeper_remain"))
                    seatType = "软卧";
                else if (seatType.equals("advanced_soft_remain"))
                    seatType = "高级软卧";
                else if (seatType.equals("highspeed_sleeper_remain"))
                    seatType = "动卧";
                else if (seatType.equals("hard_sleeper_remain"))
                    seatType = "硬卧";
                else if (seatType.equals("soft_remain"))
                    seatType = "软座";
                else if (seatType.equals("hard_remain"))
                    seatType = "硬座";

                /*由于数据库表设计得可能不太合理，所以在这里只能这样子处理，把座位类型的判断在后台写死，然后可以
                  将字段进行转化方便用户查看，另外本来想用switch case，但是表达式不支持字符串类型......以此记录*/
                input.put("userName",user.getUser_name());//真实姓名
                input.put("orderId", lists.get(i).getId());
                //input.put("identityNum",user.getIdentityNum());//身份证
                input.put("trainNum",trainNum);//列车号
                input.put("startStation",chufazhan.getStationName());//出发站
                input.put("endStation",mudizhan.getStationName());//目的站
                input.put("startTime",rstr);//出发时间
                input.put("seatName",seatType);//座位类型
                input.put("price",lists.get(i).getTicketPrice());//票价
                if(lists.get(i).getEffect()==1)
                    input.put("isEffect", 1);
                else
                    input.put("isEffect", 0);
                hashMapArrayList.add(input);
            }
        }
        return hashMapArrayList;
    }


    //查询当前用户的所有订购生效的历史订单
    public ArrayList<HashMap<String,Object>> searchNeedRefundRecord(User user) {
        ArrayList<HashMap<String,Object>> hashMapArrayList = new ArrayList<HashMap<String, Object>>();
        ArrayList<OrderTicket> lists = this.orderTicketMapper.searchNeedRefundRecord(user.getUser_id());
        if(lists == null){
            System.out.println("您的订票记录为空！");
        }else{
            for (int i = 0; i < lists.size(); i++) {
                HashMap<String,Object> input = new HashMap<String, Object>();
                Route chufazhan = this.routeMapper.getStationMessage(lists.get(i).getTrainId(),lists.get(i).getDepatureRouteId());//获得出发站信息
                Route mudizhan = this.routeMapper.getStationMessage(lists.get(i).getTrainId(),lists.get(i).getArriveRouteId());//获得目的站信息
                Date chufashijian = chufazhan.getDepartureTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String timestr = sdf.format(chufashijian);
                String trainNum = this.trainMapper.selectByPrimaryKey(lists.get(i).getTrainId()).getTrainNum();
                String seatType = lists.get(i).getSeatType();
                if (seatType.equals("stand_remain"))
                    seatType = "无座";
                else if (seatType.equals("second_remain"))
                    seatType = "二等座";
                else if (seatType.equals("first_remain"))
                    seatType = "一等座";
                else if (seatType.equals("business_remain"))
                    seatType = "商务座";
                else if (seatType.equals("soft_sleeper_remain"))
                    seatType = "软卧";
                else if (seatType.equals("advanced_soft_remain"))
                    seatType = "高级软卧";
                else if (seatType.equals("highspeed_sleeper_remain"))
                    seatType = "动卧";
                else if (seatType.equals("hard_sleeper_remain"))
                    seatType = "硬卧";
                else if (seatType.equals("soft_remain"))
                    seatType = "软座";
                else if (seatType.equals("hard_remain"))
                    seatType = "硬座";
                input.put("订单号",lists.get(i).getId());
                input.put("姓名",user.getUser_account());//真实姓名
                //input.put("身份证号",user.getIdentityNum());//身份证
                input.put("列车id",lists.get(i).getTrainId());//列车id
                input.put("列车号",trainNum);//列车号
                input.put("出发站",chufazhan.getStationName());//出发站
                input.put("目的站",mudizhan.getStationName());//目的站
                input.put("出发时间",timestr);//出发时间
                input.put("座位类型",seatType);//座位类型
                input.put("票价",lists.get(i).getTicketPrice());//票价
                input.put("票号",lists.get(i).getTicketId());//票号
                Date now = new Date();//获取当前系统时间
                int judge = now.compareTo(chufashijian);//当前时间与该车次的出发时间进行比较
                String str = "未发车";
                if(judge >= 0){ //当前时间已经过了出发时间
                    str = "已发车";
                }
                input.put("是否过期",str);
                hashMapArrayList.add(input);
            }
        }
        return hashMapArrayList;
    }


    /** 用户退票功能
     *
     * @param orderId  用户经过“searchNeedRefundRecord(User user)”后获得的所有生效订票信息列表中选择的一条记录
     * @return             退票结果信息
     */
    public boolean refundTrainTicket(Integer orderId) {
        Boolean isSuccess = false;//初始退票结果为失败
/*        if(input == null){
            System.out.println("没有订购记录，所以没有退票操作");
            return isSuccess;//没有订购记录，所以没有退票操作
        }else if(input.get("是否过期").equals("已发车")){
            System.out.println("已经发车的车次不能退票");
            return isSuccess;//已经发车的车次不能退票
        }else{*/
        //先进行对订单记录的effect置为无效
        int flag = orderTicketMapper.updateTicketEffect(orderId);
        OrderTicket orderTicket = orderTicketMapper.selectByPrimaryKey(orderId);
        if(flag != 1) {
            System.out.println("effect转换失败");
            return isSuccess;//effect转换失败
        }else{
            //再进行退票操作
            HashMap<String,Object> data = new HashMap<String, Object>();
            /*String seatType = (String) input.get("座位类型");
            if (seatType.equals("无座"))
                seatType = "stand_remain";
            else if (seatType.equals("二等座"))
                seatType = "second_remain";
            else if (seatType.equals("一等座"))
                seatType = "first_remain";
            else if (seatType.equals("商务座"))
                seatType = "business_remain";
            else if (seatType.equals("软卧"))
                seatType = "soft_sleeper_remain";
            else if (seatType.equals("高级软卧"))
                seatType = "advanced_soft_remain";
            else if (seatType.equals("动卧"))
                seatType = "highspeed_sleeper_remain";
            else if (seatType.equals("硬卧"))
                seatType = "hard_sleeper_remain";
            else if (seatType.equals("软座"))
                seatType = "soft_remain";
            else if (seatType.equals("硬座"))
                seatType = "hard_remain";*/
            data.put("level",orderTicket.getSeatType());
            data.put("tid", orderTicket.getTrainId());
            data.put("ticketid", orderTicket.getTicketId());
            int flag2 = ticketCountMapper.addByOrderTicket(data);
            if (flag2 != 1){
                System.out.println("减票操作失败！");
                return isSuccess;
            }else {
                System.out.println("减票操作成功！");
                isSuccess = true;
                return isSuccess;
            }
        }
    }
}
