package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercuryLiveCell;

public interface MercuryLiveCellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(MercuryLiveCell record);

    int insertSelective(MercuryLiveCell record);

    MercuryLiveCell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(MercuryLiveCell record);

    int updateByPrimaryKeyWithBLOBs(MercuryLiveCell record);

    int updateByPrimaryKey(MercuryLiveCell record);
}