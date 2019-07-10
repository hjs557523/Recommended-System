package com.dc.mapper;

import com.dc.pojo.TrainType;
import org.springframework.stereotype.Repository;

/**
 * @author 黄继升 16041321
 * @Description:
 * @date Created in 2019/7/7 17:31
 * @Modified By:
 */

@Repository
public interface TrainTypeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TrainType record);

    int insertSelective(TrainType record);

    TrainType selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(TrainType record);

    int updateByPrimaryKey(TrainType record);
}
