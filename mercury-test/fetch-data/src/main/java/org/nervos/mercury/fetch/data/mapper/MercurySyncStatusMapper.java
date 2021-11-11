package org.nervos.mercury.fetch.data.mapper;

import org.nervos.mercury.fetch.data.entity.MercurySyncStatus;

public interface MercurySyncStatusMapper {
    int deleteByPrimaryKey(Integer blockNumber);

    int insert(MercurySyncStatus record);

    int insertSelective(MercurySyncStatus record);
}