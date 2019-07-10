package com.dc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:06
 * @Modified By:
 */
public class Train implements Serializable {
    private Integer id;

    private Date startTime;

    private Date endTime;

    private String startStationName;

    private String endStationName;

    private Double totalDistance;

    private Integer trainTypeId;

    private Integer totalStationNum;

    private String trainNum;

    public Train(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getStartStationName() {
        return startStationName;
    }

    public void setStartStationName(String startStationName) {
        this.startStationName = startStationName;
    }

    public String getEndStationName() {
        return endStationName;
    }

    public void setEndStationName(String endStationName) {
        this.endStationName = endStationName;
    }

    public Double getTotalDistance() {
        return totalDistance;
    }

    public void setTotalDistance(Double totalDistance) {
        this.totalDistance = totalDistance;
    }

    public Integer getTrainTypeId() {
        return trainTypeId;
    }

    public void setTrainTypeId(Integer trainTypeId) {
        this.trainTypeId = trainTypeId;
    }

    public Integer getTotalStationNum() {
        return totalStationNum;
    }

    public void setTotalStationNum(Integer totalStationNum) {
        this.totalStationNum = totalStationNum;
    }

    public String getTrainNum() {
        return trainNum;
    }

    public void setTrainNum(String trainNum) {
        this.trainNum = trainNum;
    }

    @Override
    public String toString() {
        return "Train{" +
                "id=" + id +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", startStationName='" + startStationName + '\'' +
                ", endStationName='" + endStationName + '\'' +
                ", totalDistance=" + totalDistance +
                ", trainTypeId=" + trainTypeId +
                ", totalStationNum=" + totalStationNum +
                ", trainNum='" + trainNum + '\'' +
                '}';
    }
}
