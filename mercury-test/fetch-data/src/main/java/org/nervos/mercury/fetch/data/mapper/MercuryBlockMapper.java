package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryBlock;
import org.nervos.mercury.fetch.data.entity.MercuryCell;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryBlockMapper {

  int deleteByPrimaryKey(HexBytes blockHash);

  void batchInsert(List<MercuryBlock> blocks);

  int insert(MercuryBlock record);

  int insertSelective(MercuryBlock record);

  List<MercuryBlock> selectByBlockNumbers(List<Integer> blockNumbers);

  MercuryBlock selectByPrimaryKey(HexBytes blockHash);

  int updateByPrimaryKeySelective(MercuryBlock record);

  int updateByPrimaryKeyWithBLOBs(MercuryBlock record);

  int updateByPrimaryKey(MercuryBlock record);
}
