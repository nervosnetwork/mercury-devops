package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.TransferPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.From;
import org.nervos.mercury.model.req.Mode;
import org.nervos.mercury.model.req.Source;
import org.nervos.mercury.model.req.To;
import org.nervos.mercury.model.req.ToInfo;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import constant.AddressWithKeyHolder;

@Disabled
public class FeeRateTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.BUILD_TRANSFER_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void defaultFeeRate() {

    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(AddressWithKeyHolder.testAddress1())),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress4(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom)); // unit: CKB, 1 CKB = 10^8 Shannon
    // default 1000 shannons/KB
    //    builder.feeRate(new BigInteger("1000"));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("defaultFeeRate", builder.build(), blockInfo, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void customizedFeeRate() {
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(AddressWithKeyHolder.testAddress1())),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress4(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom)); // unit: CKB, 1 CKB = 10^8 Shannon
    builder.feeRate(new BigInteger("10000"));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("customizedFeeRate", builder.build(), blockInfo, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
