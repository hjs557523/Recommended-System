package com.dc.pojo;

import java.io.Serializable;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:13
 * @Modified By:
 */
public class TrainType implements Serializable {
    private Integer id;

    private Integer standNum;

    private Integer secondSeatNum;

    private Integer firstSeatNum;

    private Integer businessSeatNum;

    private Integer advancedSoftNum;

    private Integer softSleeperNum;

    private Integer highspeedSleeperNum;

    private Integer hardSleeperNum;

    private Integer softSeatNum;

    private Integer hardSeatNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStandNum() {
        return standNum;
    }

    public void setStandNum(Integer standNum) {
        this.standNum = standNum;
    }

    public Integer getSecondSeatNum() {
        return secondSeatNum;
    }

    public void setSecondSeatNum(Integer secondSeatNum) {
        this.secondSeatNum = secondSeatNum;
    }

    public Integer getFirstSeatNum() {
        return firstSeatNum;
    }

    public void setFirstSeatNum(Integer firstSeatNum) {
        this.firstSeatNum = firstSeatNum;
    }

    public Integer getBusinessSeatNum() {
        return businessSeatNum;
    }

    public void setBusinessSeatNum(Integer businessSeatNum) {
        this.businessSeatNum = businessSeatNum;
    }

    public Integer getAdvancedSoftNum() {
        return advancedSoftNum;
    }

    public void setAdvancedSoftNum(Integer advancedSoftNum) {
        this.advancedSoftNum = advancedSoftNum;
    }

    public Integer getSoftSleeperNum() {
        return softSleeperNum;
    }

    public void setSoftSleeperNum(Integer softSleeperNum) {
        this.softSleeperNum = softSleeperNum;
    }

    public Integer getHighspeedSleeperNum() {
        return highspeedSleeperNum;
    }

    public void setHighspeedSleeperNum(Integer highspeedSleeperNum) {
        this.highspeedSleeperNum = highspeedSleeperNum;
    }

    public Integer getHardSleeperNum() {
        return hardSleeperNum;
    }

    public void setHardSleeperNum(Integer hardSleeperNum) {
        this.hardSleeperNum = hardSleeperNum;
    }

    public Integer getSoftSeatNum() {
        return softSeatNum;
    }

    public void setSoftSeatNum(Integer softSeatNum) {
        this.softSeatNum = softSeatNum;
    }

    public Integer getHardSeatNum() {
        return hardSeatNum;
    }

    public void setHardSeatNum(Integer hardSeatNum) {
        this.hardSeatNum = hardSeatNum;
    }

    @Override
    public String toString() {
        return "TrainType{" +
                "id=" + id +
                ", standNum=" + standNum +
                ", secondSeatNum=" + secondSeatNum +
                ", firstSeatNum=" + firstSeatNum +
                ", businessSeatNum=" + businessSeatNum +
                ", advancedSoftNum=" + advancedSoftNum +
                ", softSleeperNum=" + softSleeperNum +
                ", highspeedSleeperNum=" + highspeedSleeperNum +
                ", hardSleeperNum=" + hardSleeperNum +
                ", softSeatNum=" + softSeatNum +
                ", hardSeatNum=" + hardSeatNum +
                '}';
    }
}
