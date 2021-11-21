package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.AdjustAccountPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import constant.AddressWithKeyHolder;
import constant.UdtHolder;

public class BuildAdjustAccountTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void testCreateAsset()
      throws InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {

    AddressTools.AddressGenerateResult newAddress =
        AddressTools.generateShortAddress(Network.TESTNET);

    AddressWithKeyHolder.put(newAddress.address, newAddress.privateKey);

    AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(newAddress.lockArgs));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.addFrom(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey3()));
    builder.accountNumber(BigInteger.ONE);

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testCreateAsset", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testAdjustAssetAccountWithUdt() {
    AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.accountNumber(BigInteger.TEN);

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testAdjustAssetAccountWithUdt", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testAdjustAssetPayFrom() {
    AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));
    builder.addFrom(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress3()));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.accountNumber(BigInteger.TEN);

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testAdjustAssetPayFrom", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testAdjustAssetExtraCkb() {
    AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey2()));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.extraCkb(AmountUtils.ckbToShannon(200));
    builder.accountNumber(BigInteger.TEN);

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testAdjustAssetExtraCkb", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
