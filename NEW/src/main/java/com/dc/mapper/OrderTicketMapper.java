package com.dc.mapper;

import com.dc.pojo.OrderTicket;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:00
 * @Modified By:
 */

@Repository
public interface OrderTicketMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(OrderTicket record);

    int insertSelective(OrderTicket record);

    OrderTicket selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(OrderTicket record);

    int updateByPrimaryKey(OrderTicket record);

    /*用户查询所有历史订单*/
    ArrayList<OrderTicket> searchHistoryRecord(Integer userid);

    /*查询历史订票中effect为1的项目*/
    ArrayList<OrderTicket> searchNeedRefundRecord(Integer userid);

    /*修改订票表记录的有效性*/
    int updateTicketEffect(Integer id);




}
