package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryCanonicalChain;

import java.util.List;

public interface MercuryCanonicalChainMapper {
  int deleteByPrimaryKey(Integer blockNumber);

  int insert(MercuryCanonicalChain record);

  int batchInsert(List<MercuryCanonicalChain> mercuryCanonicalChains);

  int insertSelective(MercuryCanonicalChain record);

  MercuryCanonicalChain selectByPrimaryKey(Integer blockNumber);

  List<MercuryCanonicalChain> selectByBlockNumbers(List<Integer> blockNumbers);

  int updateByPrimaryKeySelective(MercuryCanonicalChain record);

  int updateByPrimaryKeyWithBLOBs(MercuryCanonicalChain record);
}
