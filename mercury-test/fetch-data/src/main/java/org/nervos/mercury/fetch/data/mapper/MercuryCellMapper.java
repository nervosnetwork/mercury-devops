package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryCell;

public interface MercuryCellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MercuryCell record);

    int insertSelective(MercuryCell record);

    MercuryCell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MercuryCell record);

    int updateByPrimaryKeyWithBLOBs(MercuryCell record);

    int updateByPrimaryKey(MercuryCell record);
}