package org.nervos.mercury.fetch.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.nervos.mercury.fetch.data.entity.MercuryUncleRelationship;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public interface MercuryUncleRelationshipMapper {
    int deleteByPrimaryKey(@Param("blockHash") HexBytes blockHash, @Param("uncleHashes") HexBytes uncleHashes);

    int insert(MercuryUncleRelationship record);

    int insertSelective(MercuryUncleRelationship record);
}