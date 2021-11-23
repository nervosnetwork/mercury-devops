package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.SimpleTransferPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.ToInfo;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.math.BigInteger;

import constant.AddressWithKeyHolder;
import constant.UdtHolder;

public class BuildSimpleTransferTransactionTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void testCkbInsufficientBalanceToPayTheFee1() {
    SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
    try {
      AddressTools.AddressGenerateResult from = AddressTools.generateShortAddress(Network.TESTNET);
      AddressTools.AddressGenerateResult to = AddressTools.generateShortAddress(Network.TESTNET);

      builder.addFrom(from.address);
      builder.addTo(new ToInfo(to.address, AmountUtils.ckbToShannon(100)));
      builder.assetInfo(AssetInfo.newCkbAsset());

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

    } catch (Exception e) {
      cw.write("testCkbInsufficientBalanceToPayTheFee1", builder.build(), null, e.getMessage());
      //      assertEquals(true, e.getMessage().contains("token is not enough"));
    }
  }

  @Test
  void testCkbInsufficientBalanceToPayTheFee2() {
    SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
    try {
      AddressTools.AddressGenerateResult from = AddressTools.generateShortAddress(Network.TESTNET);

      builder.addFrom(from.address);
      builder.addTo(new ToInfo(AddressWithKeyHolder.testAddress4(), AmountUtils.ckbToShannon(100)));
      builder.assetInfo(AssetInfo.newCkbAsset());

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

    } catch (Exception e) {
      cw.write("testCkbInsufficientBalanceToPayTheFee2", builder.build(), null, e.getMessage());
      //      assertEquals(true, e.getMessage().contains("token is not enough"));
    }
  }

  @Test
  void testSourceByClaimable() {

    SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
    builder.addFrom(AddressWithKeyHolder.testAddress2());
    builder.addTo(new ToInfo(AddressWithKeyHolder.testAddress4(), new BigInteger("20")));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

    System.out.println(g.toJson(builder.build()));

    try {

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testSourceByClaimable", builder.build(), blockInfo, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSourceByChequeCell() {

    try {
      AddressTools.AddressGenerateResult to = AddressTools.generateShortAddress(Network.TESTNET);

      SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
      builder.addFrom(AddressWithKeyHolder.testAddress2());
      builder.addTo(new ToInfo(to.address, new BigInteger("20")));
      builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

      System.out.println(g.toJson(builder.build()));

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testSourceByChequeCell", builder.build(), blockInfo, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testSourceByFree() {

    SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
    builder.addFrom(AddressWithKeyHolder.testAddress4());
    builder.addTo(new ToInfo(AddressWithKeyHolder.testAddress1(), new BigInteger("20")));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testSourceByFree", builder.build(), blockInfo, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Test
  void testModeByHoldyTo() {

    SimpleTransferPayloadBuilder builder = new SimpleTransferPayloadBuilder();
    builder.addFrom(AddressWithKeyHolder.testAddress1());
    builder.addTo(
        new ToInfo(
            AddressTools.generateAcpAddress(AddressWithKeyHolder.testAddress4()),
            new BigInteger("20")));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_SIMPLE_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testModeByHoldyTo", builder.build(), blockInfo, null);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
