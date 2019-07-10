package com.dc.service;

import com.dc.pojo.Route;
import com.dc.pojo.TrainAndTicket;

import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:04
 * @Modified By:
 */
public interface RouteService {
    List<List<Route>> findTransferStation(String chufazhan, String mudizhan);

    Integer getDistance(TrainAndTicket trainAndTicket);
}
