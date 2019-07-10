package com.dc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:17
 * @Modified By:
 */
public class TrainTransferInfo implements Serializable {
    private int id1;//路线1的中转站id
    private int id2;//路线2的中转站id
    private int trainId1;//路线1的列车id
    private int trainId2;//路线2的列车id
    private String stationName;//中转站名称
    private int routeSeq1;//中转站在路线1的顺序号
    private int routeSeq2;//中转站在路线2的顺序号
    private Date departureTime1;//中转站在路线1的出发时间
    private Date departureTime2;//中转站在路线2的出发时间
    private Date arriveTime1;//中转站在路线1的到达时间
    private Date arriveTime2;//中转站在路线2的到达时间
    private int nextDistance2;//中转站在路线2到下一站的距离

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public int getId2() {
        return id2;
    }

    public void setId2(int id2) {
        this.id2 = id2;
    }

    public int getTrainId1() {
        return trainId1;
    }

    public void setTrainId1(int trainId1) {
        this.trainId1 = trainId1;
    }

    public int getTrainId2() {
        return trainId2;
    }

    public void setTrainId2(int trainId2) {
        this.trainId2 = trainId2;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public int getRouteSeq1() {
        return routeSeq1;
    }

    public void setRouteSeq1(int routeSeq1) {
        this.routeSeq1 = routeSeq1;
    }

    public int getRouteSeq2() {
        return routeSeq2;
    }

    public void setRouteSeq2(int routeSeq2) {
        this.routeSeq2 = routeSeq2;
    }

    public Date getDepartureTime1() {
        return departureTime1;
    }

    public void setDepartureTime1(Date departureTime1) {
        this.departureTime1 = departureTime1;
    }

    public Date getDepartureTime2() {
        return departureTime2;
    }

    public void setDepartureTime2(Date departureTime2) {
        this.departureTime2 = departureTime2;
    }

    public Date getArriveTime1() {
        return arriveTime1;
    }

    public void setArriveTime1(Date arriveTime1) {
        this.arriveTime1 = arriveTime1;
    }

    public Date getArriveTime2() {
        return arriveTime2;
    }

    public void setArriveTime2(Date arriveTime2) {
        this.arriveTime2 = arriveTime2;
    }

    public int getNextDistance2() {
        return nextDistance2;
    }

    public void setNextDistance2(int nextDistance2) {
        this.nextDistance2 = nextDistance2;
    }

    @Override
    public String toString() {
        return "TrainTransferInfo{" +
                "id1=" + id1 +
                ", id2=" + id2 +
                ", trainId1=" + trainId1 +
                ", trainId2=" + trainId2 +
                ", stationName='" + stationName + '\'' +
                ", routeSeq1=" + routeSeq1 +
                ", routeSeq2=" + routeSeq2 +
                ", departureTime1=" + departureTime1 +
                ", departureTime2=" + departureTime2 +
                ", arriveTime1=" + arriveTime1 +
                ", arriveTime2=" + arriveTime2 +
                ", nextDistance2=" + nextDistance2 +
                '}';
    }
}
