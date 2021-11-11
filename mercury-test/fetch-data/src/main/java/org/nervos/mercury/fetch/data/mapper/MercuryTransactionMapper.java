package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryTransaction;

public interface MercuryTransactionMapper {

  int deleteByPrimaryKey(Long id);

  int insert(MercuryTransaction record);

  int insertSelective(MercuryTransaction record);

  MercuryTransaction selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(MercuryTransaction record);

  int updateByPrimaryKeyWithBLOBs(MercuryTransaction record);

  int updateByPrimaryKey(MercuryTransaction record);
}
