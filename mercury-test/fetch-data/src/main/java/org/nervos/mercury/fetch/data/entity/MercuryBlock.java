package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryBlock {
    private HexBytes blockHash;

    private Integer blockNumber;

    private Short version;

    private Integer compactTarget;

    private Long blockTimestamp;

    private Integer epochNumber;

    private Integer epochIndex;

    private Integer epochLength;

    private Integer unclesCount;

    private HexBytes parentHash;

    private HexBytes transactionsRoot;

    private HexBytes proposalsHash;

    private HexBytes unclesHash;

    private HexBytes uncles;

    private HexBytes dao;

    private HexBytes nonce;

    private HexBytes proposals;

    public HexBytes getBlockHash() {
        return blockHash;
    }

    public void setBlockHash(HexBytes blockHash) {
        this.blockHash = blockHash;
    }

    public Integer getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(Integer blockNumber) {
        this.blockNumber = blockNumber;
    }

    public Short getVersion() {
        return version;
    }

    public void setVersion(Short version) {
        this.version = version;
    }

    public Integer getCompactTarget() {
        return compactTarget;
    }

    public void setCompactTarget(Integer compactTarget) {
        this.compactTarget = compactTarget;
    }

    public Long getBlockTimestamp() {
        return blockTimestamp;
    }

    public void setBlockTimestamp(Long blockTimestamp) {
        this.blockTimestamp = blockTimestamp;
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

    public Integer getUnclesCount() {
        return unclesCount;
    }

    public void setUnclesCount(Integer unclesCount) {
        this.unclesCount = unclesCount;
    }

    public HexBytes getParentHash() {
        return parentHash;
    }

    public void setParentHash(HexBytes parentHash) {
        this.parentHash = parentHash;
    }

    public HexBytes getTransactionsRoot() {
        return transactionsRoot;
    }

    public void setTransactionsRoot(HexBytes transactionsRoot) {
        this.transactionsRoot = transactionsRoot;
    }

    public HexBytes getProposalsHash() {
        return proposalsHash;
    }

    public void setProposalsHash(HexBytes proposalsHash) {
        this.proposalsHash = proposalsHash;
    }

    public HexBytes getUnclesHash() {
        return unclesHash;
    }

    public void setUnclesHash(HexBytes unclesHash) {
        this.unclesHash = unclesHash;
    }

    public HexBytes getUncles() {
        return uncles;
    }

    public void setUncles(HexBytes uncles) {
        this.uncles = uncles;
    }

    public HexBytes getDao() {
        return dao;
    }

    public void setDao(HexBytes dao) {
        this.dao = dao;
    }

    public HexBytes getNonce() {
        return nonce;
    }

    public void setNonce(HexBytes nonce) {
        this.nonce = nonce;
    }

    public HexBytes getProposals() {
        return proposals;
    }

    public void setProposals(HexBytes proposals) {
        this.proposals = proposals;
    }
}