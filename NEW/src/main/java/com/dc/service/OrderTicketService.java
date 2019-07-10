package com.dc.service;

import com.dc.pojo.TrainAndTicket;
import com.dc.pojo.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:04
 * @Modified By:
 */
public interface OrderTicketService {

    //用户购票功能
    public boolean buyTrainTicket(String level, TrainAndTicket trainInfoList, User user);

    //用户查询所有历史订单（包括订票（生效）与退票（失效）记录）
    public ArrayList<HashMap<String,Object>> searchHistoryRecord(User user);

    //用户查询已成功订票的历史订单
    public ArrayList<HashMap<String,Object>> searchNeedRefundRecord(User user);

    //用户退票功能
    public boolean refundTrainTicket(Integer orderId);
}
