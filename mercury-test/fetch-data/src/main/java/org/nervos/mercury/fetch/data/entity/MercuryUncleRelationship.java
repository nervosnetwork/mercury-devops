package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryUncleRelationship {
    private HexBytes blockHash;

    private HexBytes uncleHashes;

    public HexBytes getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(HexBytes blockHash) {
        this.blockHash = blockHash;
    }

    public HexBytes getUncleHashes() {
        return uncleHashes;
    }

    public void setUncleHashes(HexBytes uncleHashes) {
        this.uncleHashes = uncleHashes;
    }
}