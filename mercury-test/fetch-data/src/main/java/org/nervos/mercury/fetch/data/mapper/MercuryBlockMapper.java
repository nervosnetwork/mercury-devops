package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryBlock;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public interface MercuryBlockMapper {
    int deleteByPrimaryKey(HexBytes blockHash);

    int insert(MercuryBlock record);

    int insertSelective(MercuryBlock record);

    MercuryBlock selectByPrimaryKey(HexBytes blockHash);

    int updateByPrimaryKeySelective(MercuryBlock record);

    int updateByPrimaryKeyWithBLOBs(MercuryBlock record);

    int updateByPrimaryKey(MercuryBlock record);
}