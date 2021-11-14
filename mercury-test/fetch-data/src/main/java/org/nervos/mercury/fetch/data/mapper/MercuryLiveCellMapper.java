package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryLiveCell;

import java.util.List;

public interface MercuryLiveCellMapper {
  int deleteByPrimaryKey(Long id);

  int insert(MercuryLiveCell record);

  int insertSelective(MercuryLiveCell record);

  void batchInsert(List<MercuryLiveCell> cells);

  List<MercuryLiveCell> selectByBlockNumbers(List<Integer> blockNumbers);

  MercuryLiveCell selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(MercuryLiveCell record);

  int updateByPrimaryKeyWithBLOBs(MercuryLiveCell record);

  int updateByPrimaryKey(MercuryLiveCell record);
}
