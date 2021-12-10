package org.nervos.mercury.fetch.data.entity.type;

import org.nervos.ckb.utils.Numeric;

public class HexBytes {
  private byte[] hexBytes;

  public HexBytes(byte[] hexBytes) {
    this.hexBytes = hexBytes;
  }

  public byte[] getHexBytes() {
    return hexBytes;
  }

  public String toHex() {
    return Numeric.toHexString(this.hexBytes);
  }

  public static HexBytes newHexBytes(String hex) {
    return new HexBytes(Numeric.hexStringToByteArray(hex));
  }
}
