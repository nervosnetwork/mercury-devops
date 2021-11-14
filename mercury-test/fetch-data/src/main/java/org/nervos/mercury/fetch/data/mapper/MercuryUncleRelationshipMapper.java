package org.nervos.mercury.fetch.data.mapper;

import org.apache.ibatis.annotations.Param;
import org.nervos.mercury.fetch.data.entity.MercuryBlock;
import org.nervos.mercury.fetch.data.entity.MercuryUncleRelationship;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryUncleRelationshipMapper {
  int deleteByPrimaryKey(
      @Param("blockHash") HexBytes blockHash, @Param("uncleHashes") HexBytes uncleHashes);

  int insert(MercuryUncleRelationship record);

  int batchInsert(List<MercuryUncleRelationship> uncleRelationships);

  int insertSelective(MercuryUncleRelationship record);

  List<MercuryUncleRelationship> selectByBlockHashes(List<HexBytes> blockHashes);
}
