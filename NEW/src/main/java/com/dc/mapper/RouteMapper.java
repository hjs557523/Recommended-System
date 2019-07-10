package com.dc.mapper;

import com.dc.pojo.Route;
import com.dc.pojo.TrainInfo;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:00
 * @Modified By:
 */

@Repository
public interface RouteMapper {
    public int deleteByPrimaryKey(Integer id);

    public int insert(Route record);

    public int insertSelective(Route record);

    public Route selectByPrimaryKey(Integer id);

    public int updateByPrimaryKeySelective(Route record);

    public int updateByPrimaryKey(Route record);

    Integer selectStationCountDistance(Map<String, Integer> condition);

    /*基本查询---已实现
     * 根据出发站、目的站和出发时间查询车次信息*/
    public List<TrainInfo> selectByStation(String chufazhan, String mudizhan);

    /*高级查询1---未实现
     * 根据出发站、目的站查询最短路径的车次信息*/
    public List<TrainInfo> selectShortestPath(String chufazhan,String mudizhan);

    /*高级查询2---未实现
     * 根据出发站、目的站查询最便宜路径的车次信息*/
    public List<TrainInfo> selectCheapestPath(String chufazhan,String mudizhan);

    /*高级查询3----未实现
     * 根据出发站、目的站查询最少时间的车次信息*/
    public List<TrainInfo> selectLeastTime(String chufazhan,String mudizhan);

    /*高级查询4----已实现
      如果没有出发站和目的站的直达路线，使用中转站查询功能(具体实现在service层,只中转一次)
      子操作1 */
    public List<Route> selectTransferStation(String chufazhan,String mudizhan);

    /*高级查询4----已实现
      子操作2 */
    public List<Route> selectTransferStation2(int trainId1,int trainId2,int routeSeq1,int routeSeq2);

    /*高级查询4----已实现
      子操作3 */
    public Route selectTransferStation3(int trainId,String station);


    /*根据列车Id，出发站在该路线的序号 以及 目的站在该路线的序号 获得路线列表*/
    public List<Route> getRouteListForCount(int trainId,int routeSeq1,int routeSeq2);

    /*根据列车Id，站点在该路线的序号获得 该路线的该站点信息*/
    public Route getStationMessage(int trainId,int routeSeq);


}
