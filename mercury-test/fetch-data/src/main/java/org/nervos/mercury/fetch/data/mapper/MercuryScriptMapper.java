package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryScript;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

import java.util.List;

public interface MercuryScriptMapper {
  int deleteByPrimaryKey(HexBytes scriptHash);

  int insert(MercuryScript record);

  void batchInsert(List<MercuryScript> scripts);

  int insertSelective(MercuryScript record);

  MercuryScript selectByPrimaryKey(HexBytes scriptHash);

  List<MercuryScript> selectByScriptHashes(List<HexBytes> scriptHashes);

  int updateByPrimaryKeySelective(MercuryScript record);

  int updateByPrimaryKeyWithBLOBs(MercuryScript record);

  int updateByPrimaryKey(MercuryScript record);
}
