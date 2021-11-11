package org.nervos.mercury.fetch.data.entity;

import org.nervos.mercury.fetch.data.entity.type.HexBytes;

public class MercuryRegisteredAddress {
    private HexBytes lockHash;

    private String address;

    public HexBytes getLockHash() {
        return lockHash;
    }

    public void setLockHash(HexBytes lockHash) {
        this.lockHash = lockHash;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
}