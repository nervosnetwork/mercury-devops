package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryRegisteredAddress;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public interface MercuryRegisteredAddressMapper {
    int deleteByPrimaryKey(HexBytes lockHash);

    int insert(MercuryRegisteredAddress record);

    int insertSelective(MercuryRegisteredAddress record);

    MercuryRegisteredAddress selectByPrimaryKey(HexBytes lockHash);

    int updateByPrimaryKeySelective(MercuryRegisteredAddress record);

    int updateByPrimaryKey(MercuryRegisteredAddress record);
}