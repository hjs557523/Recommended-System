package com.dc.pojo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:20
 * @Modified By:
 */
public class TrainAndTicket implements Serializable {
    private TrainInfo trainInfo;
    private TicketCount ticketCount;

    public TrainInfo getTrainInfo() {
        return trainInfo;
    }

    public void setTrainInfo(TrainInfo trainInfo) {
        this.trainInfo = trainInfo;
    }

    public TicketCount getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(TicketCount ticketCount) {
        this.ticketCount = ticketCount;
    }

    @Override
    public String toString() {
        return "TrainAndTicket{" +
                "车次信息为：" + trainInfo +
                ", 余票信息为：" + ticketCount +
                '}';
    }
}
