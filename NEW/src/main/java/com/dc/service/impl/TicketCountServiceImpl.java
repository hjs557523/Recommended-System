package com.dc.service.impl;

import com.dc.mapper.TicketCountMapper;
import com.dc.mapper.TrainMapper;
import com.dc.mapper.TrainTypeMapper;
import com.dc.pojo.TicketCount;
import com.dc.pojo.TicketCountProxy;
import com.dc.pojo.Train;
import com.dc.pojo.TrainType;
import com.dc.service.TicketCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author 黄继升 16041321
 * @Description: 火车余票表业务逻辑层实现类
 * @date Created in 2019/7/7 18:13
 * @Modified By:
 */

@Service
public class TicketCountServiceImpl implements TicketCountService {

    @Autowired
    private TicketCountMapper ticketCountMapper;
    @Autowired
    private TrainMapper trainMapper;
    @Autowired
    private TrainTypeMapper trainTypeMapper;
   /* @Autowired 这里被我注释掉了，我用this
    private TicketCountService ticketCountService;*/

    private static final long DAY_MILLS = 24 * 60 * 60 * 1000;

    public boolean generateTicket() {
        Date nowDate = new Date();
        Date sTime = new Date(nowDate.getTime() / DAY_MILLS * DAY_MILLS);
        Date eTime = new Date(sTime.getTime() + (30 * DAY_MILLS));
        Map<String, Date> condition = new HashMap<String, Date>();
        condition.put("sTime", sTime);
        condition.put("eTime", eTime);

        Set<Date> orderTimeSet = ticketCountMapper.selectAllGroupByTime(condition);
        List<Date> missingDateList = new ArrayList<Date>();
        for(int i = 0; i < 30; ++i) {
            Date date = new Date(sTime.getTime() + (i * DAY_MILLS));

            if(!orderTimeSet.contains(date)) {
                missingDateList.add(date);
            }
        }

        List<TicketCount> ticketCountList = new ArrayList<TicketCount>();
        List<Train> trainList = trainMapper.selectAll();
        Map<Integer, TrainType> trainTypeMap = new HashMap<Integer, TrainType>();
        for (Date date : missingDateList) {
            for (Train trainEle: trainList) {
                TrainType trainType = trainTypeMap.get(trainEle.getTrainTypeId());
                if(trainType == null) {
                    trainType = trainTypeMapper.selectByPrimaryKey(trainEle.getTrainTypeId());
                    trainTypeMap.put(trainEle.getTrainTypeId(), trainType);
                }

                TicketCountProxy ticketCountProxy = new TicketCountProxy();
                ticketCountProxy.setTrainId(trainEle.getId());
                ticketCountProxy.setOrderTime(date);
                ticketCountProxy.setStandRemain(trainType.getStandNum());
                ticketCountProxy.setSecondRemain(trainType.getSecondSeatNum());
                ticketCountProxy.setFirstRemain(trainType.getFirstSeatNum());
                ticketCountProxy.setBusinessRemain(trainType.getBusinessSeatNum());
                ticketCountProxy.setSoftSleeperRemain(trainType.getSoftSleeperNum());
                ticketCountProxy.setAdvancedSoftRemain(trainType.getAdvancedSoftNum());
                ticketCountProxy.setHighspeedSleeperRemain(trainType.getHighspeedSleeperNum());
                ticketCountProxy.setHardSleeperRemain(trainType.getHardSleeperNum());
                ticketCountProxy.setSoftRemain(trainType.getSoftSeatNum());
                ticketCountProxy.setHardRemain(trainType.getHardSeatNum());

                ticketCountList.add(ticketCountProxy.getTicketCountInstance());
            }
        }

        try {
            if (ticketCountList.size() > 0) {
               /* ticketCountService.saveTicketCountList(ticketCountList);*/
                //就这里改成this.XXX();
                this.saveTicketCountList(ticketCountList);
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("saveTicketCountList(ticketCountList)出错");
            return false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveTicketCountList(List<TicketCount> ticketCountList) throws Exception {
        if(ticketCountMapper.insertBatch(ticketCountList)) {
            return ;
        } else {
            throw new Exception("数据库批量插入失败");
        }
    }
}
