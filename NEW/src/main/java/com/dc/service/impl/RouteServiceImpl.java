package com.dc.service.impl;

import com.dc.mapper.RouteMapper;
import com.dc.pojo.Route;
import com.dc.pojo.TrainAndTicket;
import com.dc.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:10
 * @Modified By:
 */
@Service
public class RouteServiceImpl implements RouteService {
    @Autowired
    private RouteMapper routeMapper;

/*
    @Autowired
    RouteService routeService;*/

    /*高级查询4：中转站查询算法的具体实现(只中转一次)*/
    public List<List<Route>> findTransferStation(String chufazhan, String mudizhan) {
        List<Route> cfzRouteList = (List<Route>)this.routeMapper.selectTransferStation(chufazhan , mudizhan);//出发站的route表
        List<Route> mdzRouteList = (List<Route>)this.routeMapper.selectTransferStation(mudizhan , chufazhan);//目的站的route表
        int length1 = cfzRouteList.size();//出发站表长度
        int length2 = mdzRouteList.size();//目的站表长度
        List<Route> cfzRouteResult = new ArrayList<Route>();//出发站到中转站的路线信息
        List<Route> mdzRouteResult = new ArrayList<Route>();//中转站到目的站的路线信息
        System.out.println("length1的长度为"+length1);
        System.out.println("length2的长度为"+length2);
        for (int i = 0; i < length1; i++) {
            Route cfzRouteListElem = cfzRouteList.get(i);
            for (int j = 0; j < length2; j++) {
                Route mdzRouteListElem = mdzRouteList.get(j);
                List<Route> cfzTransferList = (List<Route>)this.routeMapper.selectTransferStation2(cfzRouteListElem.getTrainId(),
                        mdzRouteListElem.getTrainId(),cfzRouteListElem.getRouteSeq(),mdzRouteListElem.getRouteSeq());
                if(cfzTransferList.size() > 0){
                    Route cfzTransferListElem = cfzTransferList.get(0);//得到“出发站-中转站”的中转站信息
                    Route mdzTransferListElem = this.routeMapper.selectTransferStation3(mdzRouteListElem.getTrainId(),
                            cfzTransferListElem.getStationName());//得到“中转站-目的站”的中转站信息
                    cfzRouteResult.add(cfzRouteListElem);
                    cfzRouteResult.add(cfzTransferListElem);
                    mdzRouteResult.add(mdzTransferListElem);
                    mdzRouteResult.add(mdzRouteListElem);

                }
            }
        }
        System.out.println(cfzRouteResult);
        System.out.println(mdzRouteResult);
        List<List<Route>> result = new ArrayList<List<Route>>();
        result.add(cfzRouteResult);
        result.add(mdzRouteResult);
        return result;
    }

    /**
     * 计算要购买的车票的行驶距离
     * @author CrazyWalker
     * @param trainAndTicket 包含车次和车票信息的实体类
     * @return 若计算成功则返回对应距离数值，若失败则返回0
     */
    public Integer getDistance(TrainAndTicket trainAndTicket) {
        Map<String, Integer> searchCondition = new HashMap<String, Integer>();
        searchCondition.put("trainId", trainAndTicket.getTrainInfo().getTrainId());
        searchCondition.put("startSeq", trainAndTicket.getTrainInfo().getRouteSeq1());
        searchCondition.put("endSeq", trainAndTicket.getTrainInfo().getRouteSeq2());

        Integer distance = routeMapper.selectStationCountDistance(searchCondition);
        return distance == null? 0 : distance;
    }


}
