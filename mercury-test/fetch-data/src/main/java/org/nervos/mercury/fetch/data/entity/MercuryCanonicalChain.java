package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryCanonicalChain {
    private Integer blockNumber;

    private HexBytes blockHash;

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public HexBytes getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(HexBytes blockHash) {
        this.blockHash = blockHash;
    }
}