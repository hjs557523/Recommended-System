package com.dc.service;

import com.dc.pojo.TrainAndTicket;

import java.util.ArrayList;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:05
 * @Modified By:
 */
public interface SearchTicketService {
    /*基本功能：整合车次信息加余票信息*/
    public ArrayList<TrainAndTicket> getTrainAndTicketList(String chufazhan, String mudizhan, Date chufashijian, int flag);
}
