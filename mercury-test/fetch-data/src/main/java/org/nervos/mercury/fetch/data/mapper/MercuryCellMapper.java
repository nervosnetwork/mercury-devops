package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryCell;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryCellMapper {

  void emptyTable();

  List<Integer> selectBlockNumberByLockHash(HexBytes lockHash);

  List<MercuryCell> selectByBlockNumbers(List<Integer> blockNumbers);

  List<MercuryCell> selectByConsumedTxHash(List<HexBytes> blockNumbers);

  List<MercuryCell> selectByBlockTxHash(List<HexBytes> blockNumbers);

  int deleteByPrimaryKey(Long id);

  int insert(MercuryCell record);

  void batchInsert(List<MercuryCell> cells);

  int insertSelective(MercuryCell record);

  MercuryCell selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(MercuryCell record);

  int updateByPrimaryKeyWithBLOBs(MercuryCell record);

  int updateByPrimaryKey(MercuryCell record);
}
