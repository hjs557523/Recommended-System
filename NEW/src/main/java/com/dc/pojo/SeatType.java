package com.dc.pojo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 15:24
 * @Modified By:
 */
public class SeatType implements Serializable {
    private String seattype;

    private BigDecimal percost;

    public String getSeattype() {
        return seattype;
    }

    public void setSeattype(String seattype) {
        this.seattype = seattype;
    }

    public BigDecimal getPercost() {
        return percost;
    }

    public void setPercost(BigDecimal percost) {
        this.percost = percost;
    }

    @Override
    public String toString() {
        return "SeatType{" +
                "seattype='" + seattype + '\'' +
                ", percost=" + percost +
                '}';
    }
}
