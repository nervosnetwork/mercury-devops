package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryScript;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public interface MercuryScriptMapper {
    int deleteByPrimaryKey(HexBytes scriptHash);

    int insert(MercuryScript record);

    int insertSelective(MercuryScript record);

    MercuryScript selectByPrimaryKey(HexBytes scriptHash);

    int updateByPrimaryKeySelective(MercuryScript record);

    int updateByPrimaryKeyWithBLOBs(MercuryScript record);

    int updateByPrimaryKey(MercuryScript record);
}
