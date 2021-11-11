package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryLiveCell {
    private Long id;

    private Integer outputIndex;

    private Integer txIndex;

    private Integer blockNumber;

    private Integer epochNumber;

    private Integer epochIndex;

    private Integer epochLength;

    private Long capacity;

    private Short lockScriptType;

    private Short typeScriptType;

    private HexBytes txHash;

    private HexBytes blockHash;

    private HexBytes lockHash;

    private HexBytes lockCodeHash;

    private HexBytes lockArgs;

    private HexBytes typeHash;

    private HexBytes typeCodeHash;

    private HexBytes typeArgs;

    private HexBytes data;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getOutputIndex() {
        return outputIndex;
    }

    public void setOutputIndex(Integer outputIndex) {
        this.outputIndex = outputIndex;
    }

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Integer getEpochNumber() {
        return epochNumber;
    }

    public void setEpochNumber(Integer epochNumber) {
        this.epochNumber = epochNumber;
    }

    public Integer getEpochIndex() {
        return epochIndex;
    }

    public void setEpochIndex(Integer epochIndex) {
        this.epochIndex = epochIndex;
    }

    public Integer getEpochLength() {
        return epochLength;
    }

    public void setEpochLength(Integer epochLength) {
        this.epochLength = epochLength;
    }

    public Long getCapacity() {
        return capacity;
    }

    public void setCapacity(Long capacity) {
        this.capacity = capacity;
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

    public HexBytes getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(HexBytes blockHash) {
        this.blockHash = blockHash;
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

    public HexBytes getData() {
        return data;
    }

    public void setData(HexBytes data) {
        this.data = data;
    }
}