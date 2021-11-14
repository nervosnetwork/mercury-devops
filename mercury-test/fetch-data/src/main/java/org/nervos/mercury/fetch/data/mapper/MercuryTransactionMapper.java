package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryBlock;
import org.nervos.mercury.fetch.data.entity.MercuryTransaction;

import java.util.List;

public interface MercuryTransactionMapper {

  int deleteByPrimaryKey(Long id);

  int batchInsert(List<MercuryTransaction> transactions);

  int insert(MercuryTransaction record);

  int insertSelective(MercuryTransaction record);

  MercuryTransaction selectByPrimaryKey(Long id);

  List<MercuryTransaction> selectByBlockNumbers(List<Integer> blockNumbers);

  int updateByPrimaryKeySelective(MercuryTransaction record);

  int updateByPrimaryKeyWithBLOBs(MercuryTransaction record);

  int updateByPrimaryKey(MercuryTransaction record);
}
