package com.dc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:23
 * @Modified By:
 */
public class TicketCount implements Serializable {
    private Integer id;

    private Integer trainId;

    private Integer ticketRemain;

    private Date orderTime;

    private Integer standRemain;

    private Integer secondRemain;

    private Integer firstRemain;

    private Integer businessRemain;

    private Integer softSleeperRemain;

    private Integer advancedSoftRemain;

    private Integer highspeedSleeperRemain;

    private Integer hardSleeperRemain;

    private Integer softRemain;

    private Integer hardRemain;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public Integer getTicketRemain() {
        return ticketRemain;
    }

    public void setTicketRemain(Integer ticketRemain) {
        this.ticketRemain = ticketRemain;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getStandRemain() {
        return standRemain;
    }

    public void setStandRemain(Integer standRemain) {
        this.standRemain = standRemain;
    }

    public Integer getSecondRemain() {
        return secondRemain;
    }

    public void setSecondRemain(Integer secondRemain) {
        this.secondRemain = secondRemain;
    }

    public Integer getFirstRemain() {
        return firstRemain;
    }

    public void setFirstRemain(Integer firstRemain) {
        this.firstRemain = firstRemain;
    }

    public Integer getBusinessRemain() {
        return businessRemain;
    }

    public void setBusinessRemain(Integer businessRemain) {
        this.businessRemain = businessRemain;
    }

    public Integer getSoftSleeperRemain() {
        return softSleeperRemain;
    }

    public void setSoftSleeperRemain(Integer softSleeperRemain) {
        this.softSleeperRemain = softSleeperRemain;
    }

    public Integer getAdvancedSoftRemain() {
        return advancedSoftRemain;
    }

    public void setAdvancedSoftRemain(Integer advancedSoftRemain) {
        this.advancedSoftRemain = advancedSoftRemain;
    }

    public Integer getHighspeedSleeperRemain() {
        return highspeedSleeperRemain;
    }

    public void setHighspeedSleeperRemain(Integer highspeedSleeperRemain) {
        this.highspeedSleeperRemain = highspeedSleeperRemain;
    }

    public Integer getHardSleeperRemain() {
        return hardSleeperRemain;
    }

    public void setHardSleeperRemain(Integer hardSleeperRemain) {
        this.hardSleeperRemain = hardSleeperRemain;
    }

    public Integer getSoftRemain() {
        return softRemain;
    }

    public void setSoftRemain(Integer softRemain) {
        this.softRemain = softRemain;
    }

    public Integer getHardRemain() {
        return hardRemain;
    }

    public void setHardRemain(Integer hardRemain) {
        this.hardRemain = hardRemain;
    }

    @Override
    public String toString() {
        return "余票信息{" +
                "id=" + id +
                ", 列车id=" + trainId +
                ", 剩余总票数=" + ticketRemain +
                ", 对应时间=" + orderTime +
                ", 站票剩余=" + standRemain +
                ", 二等座剩余=" + secondRemain +
                ", 一等座剩余=" + firstRemain +
                ", 商务座剩余=" + businessRemain +
                ", 软卧剩余=" + softSleeperRemain +
                ", 高级软卧剩余=" + advancedSoftRemain +
                ", 动卧剩余=" + highspeedSleeperRemain +
                ", 硬卧剩余=" + hardSleeperRemain +
                ", 软座剩余=" + softRemain +
                ", 硬座剩余=" + hardRemain +
                '}';
    }
}
