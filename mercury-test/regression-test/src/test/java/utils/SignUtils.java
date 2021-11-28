package utils;

import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.mercury.model.resp.MercuryScriptGroup;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.signature.Secp256k1SighashBuilder;

import java.util.List;

import constant.AddressWithKeyHolder;

/** @author zjh @Created Date: 2021/7/23 @Description: @Modify by: */
public class SignUtils {
  public static Transaction sign(TransactionCompletionResponse s) {
    List<MercuryScriptGroup> scriptGroups = s.getScriptGroup();
    Secp256k1SighashBuilder signBuilder = new Secp256k1SighashBuilder(s.txView);

    for (MercuryScriptGroup sg : scriptGroups) {
      System.out.println(sg.getAddress() + " signing transaction");
      signBuilder.sign(sg, AddressWithKeyHolder.getKey(sg.getAddress()));
    }

    Transaction tx = signBuilder.buildTx();
    return tx;
  }

  public static Transaction signByKey(TransactionCompletionResponse s, String key) {
    List<MercuryScriptGroup> scriptGroups = s.getScriptGroup();
    Secp256k1SighashBuilder signBuilder = new Secp256k1SighashBuilder(s.txView);

    for (MercuryScriptGroup sg : scriptGroups) {
      signBuilder.sign(sg, key);
    }

    Transaction tx = signBuilder.buildTx();
    return tx;
  }
}
