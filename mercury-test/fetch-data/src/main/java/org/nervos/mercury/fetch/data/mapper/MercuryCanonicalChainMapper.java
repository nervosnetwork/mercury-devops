package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryCanonicalChain;

public interface MercuryCanonicalChainMapper {
    int deleteByPrimaryKey(Integer blockNumber);

    int insert(MercuryCanonicalChain record);

    int insertSelective(MercuryCanonicalChain record);

    MercuryCanonicalChain selectByPrimaryKey(Integer blockNumber);

    int updateByPrimaryKeySelective(MercuryCanonicalChain record);

    int updateByPrimaryKeyWithBLOBs(MercuryCanonicalChain record);
}