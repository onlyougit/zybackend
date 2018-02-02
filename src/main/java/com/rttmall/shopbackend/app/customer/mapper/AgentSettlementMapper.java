package com.rttmall.shopbackend.app.customer.mapper;

import com.rttmall.shopbackend.app.customer.pojo.AgentSettlement;

public interface AgentSettlementMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AgentSettlement record);

    int insertSelective(AgentSettlement record);

    AgentSettlement selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AgentSettlement record);

    int updateByPrimaryKey(AgentSettlement record);
}