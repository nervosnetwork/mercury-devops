package constant;

import org.nervos.ckb.type.Script;
import org.nervos.ckb.utils.address.AddressTools;

/** @author zjh @Created Date: 2021/7/23 @Description: @Modify by: */
public class UdtHolder {
  public static final String UDT_HASH =
      "0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd";

  public static String getUdtHashFromOwner(String address) {
    Script ownerScript = AddressTools.parse(address).script;

    Script udtScript =
      new Script(ScriptMeta.getSudtCodeHash(), ownerScript.computeHash(), Script.TYPE);

    return udtScript.computeHash();
  }
}
