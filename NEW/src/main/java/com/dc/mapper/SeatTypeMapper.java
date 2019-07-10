package com.dc.mapper;

import com.dc.pojo.SeatType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 18:01
 * @Modified By:
 */

@Repository
public interface SeatTypeMapper {
    int deleteByPrimaryKey(String seattype);

    int insert(SeatType record);

    int insertSelective(SeatType record);

    SeatType selectByPrimaryKey(String seattype);

    int updateByPrimaryKeySelective(SeatType record);

    int updateByPrimaryKey(SeatType record);

    BigDecimal getPercostBySeatType(String seattype);

    List<SeatType> selectAllSeatType();
}
