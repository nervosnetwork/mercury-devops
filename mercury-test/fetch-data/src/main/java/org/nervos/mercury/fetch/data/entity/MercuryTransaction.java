package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryTransaction {
    private Long id;

    private Integer txIndex;

    private Integer inputCount;

    private Integer outputCount;

    private Integer blockNumber;

    private Long txTimestamp;

    private Short version;

    private HexBytes txHash;

    private HexBytes blockHash;

    private HexBytes cellDeps;

    private HexBytes headerDeps;

    private HexBytes witnesses;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTxIndex() {
        return txIndex;
    }

    public void setTxIndex(Integer txIndex) {
        this.txIndex = txIndex;
    }

    public Integer getInputCount() {
        return inputCount;
    }

    public void setInputCount(Integer inputCount) {
        this.inputCount = inputCount;
    }

    public Integer getOutputCount() {
        return outputCount;
    }

    public void setOutputCount(Integer outputCount) {
        this.outputCount = outputCount;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Long getTxTimestamp() {
        return txTimestamp;
    }

    public void setTxTimestamp(Long txTimestamp) {
        this.txTimestamp = txTimestamp;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
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

    public HexBytes getCellDeps() {
        return cellDeps;
    }

    public void setCellDeps(HexBytes cellDeps) {
        this.cellDeps = cellDeps;
    }

    public HexBytes getHeaderDeps() {
        return headerDeps;
    }

    public void setHeaderDeps(HexBytes headerDeps) {
        this.headerDeps = headerDeps;
    }

    public HexBytes getWitnesses() {
        return witnesses;
    }

    public void setWitnesses(HexBytes witnesses) {
        this.witnesses = witnesses;
    }
}