package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryCell;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryCellMapper {

  List<Integer> selectBlockNumberByLockHash(HexBytes lockHash);

  int deleteByPrimaryKey(Long id);

  int insert(MercuryCell record);

  int insertSelective(MercuryCell record);

  MercuryCell selectByPrimaryKey(Long id);

  int updateByPrimaryKeySelective(MercuryCell record);

  int updateByPrimaryKeyWithBLOBs(MercuryCell record);

  int updateByPrimaryKey(MercuryCell record);
}
