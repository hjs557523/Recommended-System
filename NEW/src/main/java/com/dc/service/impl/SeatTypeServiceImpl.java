package com.dc.service.impl;

import com.dc.mapper.SeatTypeMapper;
import com.dc.pojo.SeatType;
import com.dc.service.SeatTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 黄继升 16041321
 * @Description: 座位类型表业务层实现类
 * @date Created in 2019/7/7 18:12
 * @Modified By:
 */

@Service
public class SeatTypeServiceImpl implements SeatTypeService {

    @Autowired
    private SeatTypeMapper seatTypeMapper;

    public List<SeatType> getAllSeatType() {
        return seatTypeMapper.selectAllSeatType();
    }
}
