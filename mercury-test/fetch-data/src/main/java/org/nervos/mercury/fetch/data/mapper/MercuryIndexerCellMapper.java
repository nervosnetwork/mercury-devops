package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryIndexerCell;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryIndexerCellMapper {
  int deleteByPrimaryKey(Long id);

  int insert(MercuryIndexerCell record);

  int insertSelective(MercuryIndexerCell record);

  List<HexBytes> selectByLockHashes(List<HexBytes> hashes);

  MercuryIndexerCell selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(MercuryIndexerCell record);

  int updateByPrimaryKeyWithBLOBs(MercuryIndexerCell record);

  int updateByPrimaryKey(MercuryIndexerCell record);
}
