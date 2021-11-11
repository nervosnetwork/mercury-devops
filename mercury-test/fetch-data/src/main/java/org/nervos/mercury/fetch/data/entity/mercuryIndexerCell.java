package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class mercuryIndexerCell {
    private Long id;

    private Integer blockNumber;

    private Short ioType;

    private Integer ioIndex;

    private Integer txIndex;

    private Short lockScriptType;

    private Short typeScriptType;

    private HexBytes txHash;

    private HexBytes lockHash;

    private HexBytes lockCodeHash;

    private HexBytes lockArgs;

    private HexBytes typeHash;

    private HexBytes typeCodeHash;

    private HexBytes typeArgs;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Short getIoType() {
        return ioType;
    }

    public void setIoType(Short ioType) {
        this.ioType = ioType;
    }

    public Integer getIoIndex() {
        return ioIndex;
    }

    public void setIoIndex(Integer ioIndex) {
        this.ioIndex = ioIndex;
    }

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Short getLockScriptType() {
        return lockScriptType;
    }

    public void setLockScriptType(Short lockScriptType) {
        this.lockScriptType = lockScriptType;
    }

    public Short getTypeScriptType() {
        return typeScriptType;
    }

    public void setTypeScriptType(Short typeScriptType) {
        this.typeScriptType = typeScriptType;
    }

    public HexBytes getTxHash() {
        return txHash;
    }

    public void setTxHash(HexBytes txHash) {
        this.txHash = txHash;
    }

    public HexBytes getLockHash() {
        return lockHash;
    }

    public void setLockHash(HexBytes lockHash) {
        this.lockHash = lockHash;
    }

    public HexBytes getLockCodeHash() {
        return lockCodeHash;
    }

    public void setLockCodeHash(HexBytes lockCodeHash) {
        this.lockCodeHash = lockCodeHash;
    }

    public HexBytes getLockArgs() {
        return lockArgs;
    }

    public void setLockArgs(HexBytes lockArgs) {
        this.lockArgs = lockArgs;
    }

    public HexBytes getTypeHash() {
        return typeHash;
    }

    public void setTypeHash(HexBytes typeHash) {
        this.typeHash = typeHash;
    }

    public HexBytes getTypeCodeHash() {
        return typeCodeHash;
    }

    public void setTypeCodeHash(HexBytes typeCodeHash) {
        this.typeCodeHash = typeCodeHash;
    }

    public HexBytes getTypeArgs() {
        return typeArgs;
    }

    public void setTypeArgs(HexBytes typeArgs) {
        this.typeArgs = typeArgs;
    }
}