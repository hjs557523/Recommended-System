package com.dc.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:27
 * @Modified By:
 */
public class OrderTicket implements Serializable {
    private Integer id;

    private Date orderTime;

    private Integer trainId;

    private BigDecimal ticketPrice;

    private Integer seatId;

    private String seatType;

    private Integer userId;

    private Integer ticketId;

    private Integer arriveRouteId;

    private Integer depatureRouteId;

    private Integer effect;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public Integer getTrainId() {
        return trainId;
    }

    public void setTrainId(Integer trainId) {
        this.trainId = trainId;
    }

    public BigDecimal getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(BigDecimal ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    public Integer getArriveRouteId() {
        return arriveRouteId;
    }

    public void setArriveRouteId(Integer arriveRouteId) {
        this.arriveRouteId = arriveRouteId;
    }

    public Integer getDepatureRouteId() {
        return depatureRouteId;
    }

    public void setDepatureRouteId(Integer depatureRouteId) {
        this.depatureRouteId = depatureRouteId;
    }

    public Integer getEffect() {
        return effect;
    }

    public void setEffect(Integer effect) {
        this.effect = effect;
    }

    @Override
    public String toString() {
        return "OrderTicket{" +
                "订单id=" + id +
                ", 订购时间=" + orderTime +
                ", 列车id=" + trainId +
                ", 票价=" + ticketPrice +
                ", 座位号=" + seatId +
                ", 座位类型='" + seatType + '\'' +
                ", 用户编号=" + userId +
                ", ticketId=" + ticketId +
                ", arriveRouteId=" + arriveRouteId +
                ", depatureRouteId=" + depatureRouteId +
                ", 是否生效=" + effect +
                '}';
    }
}
