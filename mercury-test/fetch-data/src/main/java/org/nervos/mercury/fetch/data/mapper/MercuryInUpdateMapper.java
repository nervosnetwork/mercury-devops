package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryInUpdate;

public interface MercuryInUpdateMapper {
    int deleteByPrimaryKey(Boolean isIn);

    int insert(MercuryInUpdate record);

    int insertSelective(MercuryInUpdate record);
}