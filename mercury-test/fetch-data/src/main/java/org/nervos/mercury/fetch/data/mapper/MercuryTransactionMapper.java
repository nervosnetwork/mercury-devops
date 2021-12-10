package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryTransaction;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryTransactionMapper {

  void emptyTable();

  int deleteByPrimaryKey(Long id);

  int batchInsert(List<MercuryTransaction> transactions);

  int insert(MercuryTransaction record);

  int insertSelective(MercuryTransaction record);

  MercuryTransaction selectByPrimaryKey(Long id);

  List<MercuryTransaction> selectByBlockNumbers(List<Integer> blockNumbers);

  List<MercuryTransaction> selectByTxHashes(List<HexBytes> hashes);

  int updateByPrimaryKeySelective(MercuryTransaction record);

  int updateByPrimaryKeyWithBLOBs(MercuryTransaction record);

  int updateByPrimaryKey(MercuryTransaction record);
}
