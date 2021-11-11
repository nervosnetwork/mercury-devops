package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.mercuryIndexerCell;

public interface mercuryIndexerCellMapper {
    int deleteByPrimaryKey(Long id);

    int insert(mercuryIndexerCell record);

    int insertSelective(mercuryIndexerCell record);

    mercuryIndexerCell selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(mercuryIndexerCell record);

    int updateByPrimaryKeyWithBLOBs(mercuryIndexerCell record);

    int updateByPrimaryKey(mercuryIndexerCell record);
}