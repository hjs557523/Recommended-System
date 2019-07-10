package com.dc.service;

import com.dc.pojo.TicketCount;

import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:  列车余票表业务逻辑层接口类
 * @date Created in 2019/7/7 18:06
 * @Modified By:
 */
public interface TicketCountService {
    boolean generateTicket();

    void saveTicketCountList(List<TicketCount> ticketCountList) throws Exception;
}
