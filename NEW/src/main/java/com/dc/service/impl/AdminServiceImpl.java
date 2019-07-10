package com.dc.service.impl;

import com.dc.mapper.RouteMapper;
import com.dc.mapper.TicketCountMapper;
import com.dc.mapper.TrainMapper;
import com.dc.mapper.TrainTypeMapper;
import com.dc.pojo.*;
import com.dc.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:09
 * @Modified By:
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private RouteMapper routeMapper;
    @Autowired
    private TicketCountMapper ticketCountMapper;
    @Autowired
    private TrainTypeMapper trainTypeMapper;

    public ArrayList<TrainAndTicket> selectAllTrainAndTicket(Date date) {
        return null;
    }

    public Boolean adminAddTrainInfo(HashMap<String, Object> add) {
        Boolean isSuccess = false;
        if (add == null) {
            return isSuccess;
        } else {
           /* Date startTime = (Date) addTrain.get("startTime");
            Date endTime = (Date) addTrain.get("endTime");
            String startStation = (String) addTrain.get("startStation");
            String endStation = (String) addTrain.get("endStation");
            Double totalDistance = (Double) addTrain.get("totalDistance");
            Integer trainTypeId = (Integer) addTrain.get("trainTypeId");
            Integer totalStationNum = (Integer) addTrain.get("totalStationNum");
            String trainNum = (String) addTrain.get("trainNum");
              Train train = new Train();
              train.setTrainNum(trainNum);
              train.setStartStationName(startStation);
              train.setEndStationName(endStation);
              train.setStartTime(startTime);
              train.setEndTime(endTime);
              train.setTotalDistance(totalDistance);
              train.setTotalStationNum(totalStationNum);
              train.setTrainTypeId(trainTypeId);
              this.trainMapper.insertSelective(train);*/

            Train addTrain = (Train) add.get("Train");
            if (addTrain == null) {
                return isSuccess;
            }else if(this.trainMapper.selectByTrainNum(addTrain.getTrainNum()) != null){ // 若train表里已经有该车次信息
                return isSuccess;
            } else if (this.trainMapper.insertSelective(addTrain) != 1) { //若插入到train表失败
                return isSuccess;
            } else {
                Train newAddTrain = this.trainMapper.selectByTrainNum(addTrain.getTrainNum());//再从数据库中拿到新加入的车次
                Integer trainId = newAddTrain.getId();//拿到数据库生成的车次id
                /* Integer trainTypeId = newAddTrain.getTrainTypeId();//拿到列车类型*/

                @SuppressWarnings("unchecked")
                ArrayList<Route> routelist = (ArrayList<Route>) add.get("routelist");
                if (routelist == null) {
                    return isSuccess;
                } else {
                    for (int i = 0; i < routelist.size(); i++) {
                        Route route = routelist.get(i);
                        route.setTrainId(trainId);//补上trainId
                        if (this.routeMapper.insertSelective(route) != 1) {
                            return isSuccess;
                        }else{
                            isSuccess = true;
                        }
                    }
                    /*TrainType trainType = this.trainTypeMapper.selectByPrimaryKey(trainTypeId);
                    int standNum = trainType.getStandNum();
                    int secondSeatNum = trainType.getSecondSeatNum();
                    int firstSeatNum = trainType.getFirstSeatNum();
                    int businessSeatNum = trainType.getBusinessSeatNum();
                    int advancedSoftNum = trainType.getAdvancedSoftNum();
                    int highspeedSleeperNum = trainType.getHighspeedSleeperNum();
                    int softSleeperNum = trainType.getSoftSleeperNum();
                    int hardSleeperNum = trainType.getHardSleeperNum();
                    int softSeatNum = trainType.getSoftSeatNum();
                    int hardSeatNum = trainType.getHardSeatNum();
                    int sum = standNum + secondSeatNum + firstSeatNum + businessSeatNum + advancedSoftNum +
                            highspeedSleeperNum + softSleeperNum + hardSleeperNum + softSeatNum + hardSeatNum;
                    TicketCount tkc = new TicketCount();
                    tkc.setTrainId(trainId);
                    tkc.setOrderTime((Date) add.get("date"));
                    tkc.setTicketRemain(sum);
                    tkc.setStandRemain(standNum);
                    tkc.setSecondRemain(secondSeatNum);
                    tkc.setFirstRemain(firstSeatNum);
                    tkc.setBusinessRemain(businessSeatNum);
                    tkc.setAdvancedSoftRemain(advancedSoftNum);
                    tkc.setHighspeedSleeperRemain(highspeedSleeperNum);
                    tkc.setSoftSleeperRemain(softSleeperNum);
                    tkc.setHardSleeperRemain(hardSleeperNum);
                    tkc.setSoftRemain(softSeatNum);
                    tkc.setHardRemain(hardSeatNum);*/

                }
            }
        }
        return isSuccess;
    }

    public Boolean adminAddTicketRemain(String trainNum, Date date) {
        Boolean isSuccess = false;
        Train train = this.trainMapper.selectByTrainNum(trainNum);
        if (train == null)
            return isSuccess;
        else {
            Integer trainId = train.getId();
            if (this.ticketCountMapper.searchTicket2(trainId,date) != null)//如果该日期该车次已经有余票信息了，就禁止添加
                return isSuccess;
            else {
                Integer trainTypeId = train.getTrainTypeId(); //拿到列车类型
                TrainType trainType = this.trainTypeMapper.selectByPrimaryKey(trainTypeId); //根据trainTypeId来拿到trainType表的对应参数
                int standNum = trainType.getStandNum();
                int secondSeatNum = trainType.getSecondSeatNum();
                int firstSeatNum = trainType.getFirstSeatNum();
                int businessSeatNum = trainType.getBusinessSeatNum();
                int advancedSoftNum = trainType.getAdvancedSoftNum();
                int highspeedSleeperNum = trainType.getHighspeedSleeperNum();
                int softSleeperNum = trainType.getSoftSleeperNum();
                int hardSleeperNum = trainType.getHardSleeperNum();
                int softSeatNum = trainType.getSoftSeatNum();
                int hardSeatNum = trainType.getHardSeatNum();
                int sum = standNum + secondSeatNum + firstSeatNum + businessSeatNum + advancedSoftNum +
                        highspeedSleeperNum + softSleeperNum + hardSleeperNum + softSeatNum + hardSeatNum;
                TicketCount tkc = new TicketCount();
                tkc.setTrainId(trainId);
                tkc.setOrderTime(date);
                tkc.setTicketRemain(sum);
                tkc.setStandRemain(standNum);
                tkc.setSecondRemain(secondSeatNum);
                tkc.setFirstRemain(firstSeatNum);
                tkc.setBusinessRemain(businessSeatNum);
                tkc.setAdvancedSoftRemain(advancedSoftNum);
                tkc.setHighspeedSleeperRemain(highspeedSleeperNum);
                tkc.setSoftSleeperRemain(softSleeperNum);
                tkc.setHardSleeperRemain(hardSleeperNum);
                tkc.setSoftRemain(softSeatNum);
                tkc.setHardRemain(hardSeatNum);
                if (this.ticketCountMapper.insertSelective(tkc) == 1) {
                    isSuccess = true;
                } else {
                    return isSuccess;//插入失败
                }
            }
        }
        return isSuccess;
    }
}
