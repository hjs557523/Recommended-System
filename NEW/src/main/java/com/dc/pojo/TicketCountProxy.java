package com.dc.pojo;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 黄继升 16041321
 * @Description: TicketCount实体代理类
 * @date Created in 2019/7/7 15:21
 * @Modified By:
 */
public class TicketCountProxy implements Serializable {
    private TicketCount ticketCount = new TicketCount();

    private Integer ticketRemain = new Integer(0);

    public TicketCount getTicketCountInstance() {
        if(ticketCount.getTicketRemain() == null || ticketCount.getTicketRemain() == 0) {
            ticketCount.setTicketRemain(ticketRemain);
        }
        return ticketCount;
    }

    public Integer getId() {
        return ticketCount.getId();
    }

    public void setId(Integer id) {
        ticketCount.setId(id);
    }

    public Integer getTrainId() {
        return ticketCount.getTrainId();
    }

    public void setTrainId(Integer trainId) {
        ticketCount.setTrainId(trainId);
    }

    public Integer getTicketRemain() {
        if(ticketCount.getTicketRemain() == null || ticketCount.getTicketRemain() == 0) {
            ticketCount.setTicketRemain(ticketRemain);
        }
        return ticketCount.getTicketRemain();
    }

    public void setTicketRemain(Integer ticketRemain) {
        ticketCount.setTicketRemain(ticketRemain);
    }

    public Date getOrderTime() {
        return ticketCount.getOrderTime();
    }

    public void setOrderTime(Date orderTime) {
        ticketCount.setOrderTime(orderTime);
    }

    public Integer getStandRemain() {
        return ticketCount.getStandRemain();
    }

    public void setStandRemain(Integer standRemain) {
        beforeUpdateRemain(ticketCount.getStandRemain());
        ticketCount.setStandRemain(standRemain);
        ticketRemain += standRemain;
    }

    public Integer getSecondRemain() {
        return ticketCount.getSecondRemain();
    }

    public void setSecondRemain(Integer secondRemain) {
        beforeUpdateRemain(ticketCount.getSecondRemain());
        ticketCount.setSecondRemain(secondRemain);
        ticketRemain += secondRemain;
    }

    public Integer getFirstRemain() {
        return ticketCount.getFirstRemain();
    }

    public void setFirstRemain(Integer firstRemain) {
        beforeUpdateRemain(ticketCount.getFirstRemain());
        ticketCount.setFirstRemain(firstRemain);
        ticketRemain += firstRemain;
    }

    public Integer getBusinessRemain() {
        return ticketCount.getBusinessRemain();
    }

    public void setBusinessRemain(Integer businessRemain) {
        beforeUpdateRemain(ticketCount.getBusinessRemain());
        ticketCount.setBusinessRemain(businessRemain);
        ticketRemain += businessRemain;
    }

    public Integer getSoftSleeperRemain() {
        return ticketCount.getSoftSleeperRemain();
    }

    public void setSoftSleeperRemain(Integer softSleeperRemain) {
        beforeUpdateRemain(ticketCount.getSoftSleeperRemain());
        ticketCount.setSoftSleeperRemain(softSleeperRemain);
        ticketRemain += softSleeperRemain;
    }

    public Integer getAdvancedSoftRemain() {
        return ticketCount.getAdvancedSoftRemain();
    }

    public void setAdvancedSoftRemain(Integer advancedSoftRemain) {
        beforeUpdateRemain(ticketCount.getAdvancedSoftRemain());
        ticketCount.setAdvancedSoftRemain(advancedSoftRemain);
        ticketRemain += advancedSoftRemain;
    }

    public Integer getHighspeedSleeperRemain() {
        return ticketCount.getHighspeedSleeperRemain();
    }

    public void setHighspeedSleeperRemain(Integer highspeedSleeperRemain) {
        beforeUpdateRemain(ticketCount.getHighspeedSleeperRemain());
        ticketCount.setHighspeedSleeperRemain(highspeedSleeperRemain);
        ticketRemain += highspeedSleeperRemain;
    }

    public Integer getHardSleeperRemain() {
        return ticketCount.getHardSleeperRemain();
    }

    public void setHardSleeperRemain(Integer hardSleeperRemain) {
        beforeUpdateRemain(ticketCount.getHardSleeperRemain());
        ticketCount.setHardSleeperRemain(hardSleeperRemain);
        ticketRemain += hardSleeperRemain;
    }

    public Integer getSoftRemain() {
        return ticketCount.getSoftRemain();
    }

    public void setSoftRemain(Integer softRemain) {
        beforeUpdateRemain(ticketCount.getSoftRemain());
        ticketCount.setSoftRemain(softRemain);
        ticketRemain += softRemain;
    }

    public Integer getHardRemain() {
        return ticketCount.getHardRemain();
    }

    public void setHardRemain(Integer hardRemain) {
        beforeUpdateRemain(ticketCount.getHardRemain());
        ticketCount.setHardRemain(hardRemain);
        ticketRemain += hardRemain;
    }

    private void beforeUpdateRemain(Integer oneRemain) {
        if(oneRemain != null && oneRemain != 0) {
            ticketRemain -= oneRemain;
        }
    }
}
