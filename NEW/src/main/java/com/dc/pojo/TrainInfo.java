package com.dc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:19
 * @Modified By:
 */
public class TrainInfo implements Serializable {
    private int trainId;
    private String trainNum;
    private String chufazhan;
    private String mudizhan;
    private int routeSeq1;
    private int routeSeq2;
    private Integer distance;
    private Date departureTime;
    private Date arriveTime;

    public int getRouteSeq1() {
        return routeSeq1;
    }

    public void setRouteSeq1(int routeSeq1) {
        this.routeSeq1 = routeSeq1;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public int getRouteSeq2() {
        return routeSeq2;
    }

    public void setRouteSeq2(int routeSeq2) {
        this.routeSeq2 = routeSeq2;
    }

    public int getTrainId() {
        return trainId;
    }

    public void setTrainId(int trainId) {
        this.trainId = trainId;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    public String getChufazhan() {
        return chufazhan;
    }

    public void setChufazhan(String chufazhan) {
        this.chufazhan = chufazhan;
    }

    public String getMudizhan() {
        return mudizhan;
    }

    public void setMudizhan(String mudizhan) {
        this.mudizhan = mudizhan;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(Date arriveTime) {
        this.arriveTime = arriveTime;
    }

    @Override
    public String toString() {
        return "车次信息{" +
                "列车Id=" + trainId +
                ", 列车号='" + trainNum + '\'' +
                ", 出发站='" + chufazhan + '\'' +
                ", 出发站序号='" + routeSeq1 + '\'' +
                ", 目的站='" + mudizhan + '\'' +
                ", 目的站序号='" + routeSeq2 + '\'' +
                ", 出发时间=" + departureTime +
                ", 到达时间=" + arriveTime +
                '}';
    }
}
