package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.DrawingApply;

public interface DrawingApplyMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(DrawingApply record);

    int insertSelective(DrawingApply record);

    DrawingApply selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(DrawingApply record);

    int updateByPrimaryKey(DrawingApply record);
}