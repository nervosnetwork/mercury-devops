package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryScript {
    private HexBytes scriptHash;

    private Short scriptType;

    private Integer scriptArgsLen;

    private HexBytes scriptHash160;

    private HexBytes scriptCodeHash;

    private HexBytes scriptArgs;

    public HexBytes getScriptHash() {
        return scriptHash;
    }

    public void setScriptHash(HexBytes scriptHash) {
        this.scriptHash = scriptHash;
    }

    public Short getScriptType() {
        return scriptType;
    }

    public void setScriptType(Short scriptType) {
        this.scriptType = scriptType;
    }

    public Integer getScriptArgsLen() {
        return scriptArgsLen;
    }

    public void setScriptArgsLen(Integer scriptArgsLen) {
        this.scriptArgsLen = scriptArgsLen;
    }

    public HexBytes getScriptHash160() {
        return scriptHash160;
    }

    public void setScriptHash160(HexBytes scriptHash160) {
        this.scriptHash160 = scriptHash160;
    }

    public HexBytes getScriptCodeHash() {
        return scriptCodeHash;
    }

    public void setScriptCodeHash(HexBytes scriptCodeHash) {
        this.scriptCodeHash = scriptCodeHash;
    }

    public HexBytes getScriptArgs() {
        return scriptArgs;
    }

    public void setScriptArgs(HexBytes scriptArgs) {
        this.scriptArgs = scriptArgs;
    }
}