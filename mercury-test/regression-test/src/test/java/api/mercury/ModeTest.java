package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.TransferPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.From;
import org.nervos.mercury.model.req.Mode;
import org.nervos.mercury.model.req.Source;
import org.nervos.mercury.model.req.To;
import org.nervos.mercury.model.req.ToInfo;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import constant.AddressWithKeyHolder;
import constant.UdtHolder;
import utils.SignUtils;

@Disabled
public class ModeTest {

  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.BUILD_TRANSFER_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void transferCompletionCkbWithFree() {
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress0())),
            Source.Free));

    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress4(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom)); // unit: CKB, 1 CKB = 10^8 Shannon

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("transferCompletionCkbWithFree", builder.build(), blockInfo, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void transferCompletionSudtWithFree() {
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(AddressWithKeyHolder.testAddress1())),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress2(), new BigInteger("100"))),
            Mode.HoldByFrom));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("transferCompletionSudtWithFree", builder.build(), blockInfo, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  void transferCompletionCkbWithClaimable() {
  //    TransferPayloadBuilder builder = new TransferPayloadBuilder();
  //    builder.assetInfo(AssetInfo.newCkbAsset());
  //    builder.from(
  //        From.newFrom(
  //            Arrays.asList(
  //                ItemFactory.newIdentityItemByAddress(AddressWithKeyHolder.testAddress1())),
  //            Source.Claimable));
  //
  //    builder.to(
  //        To.newTo(
  //            Arrays.asList(
  //                new ToInfo(AddressWithKeyHolder.testAddress2(), AmountUtils.ckbToShannon(100))),
  //            Mode.HoldByFrom)); // unit: CKB, 1 CKB = 10^8 Shannon
  //
  //    try {
  //      TransactionCompletionResponse s =
  //          ApiFactory.getApi().buildTransferTransaction(builder.build());
  //    } catch (Exception e) {
  //      assertEquals("The transaction does not support ckb", e.getMessage());
  //    }
  //  }

  @Test
  void transferCompletionSudtWithClaimable() {
    // link SourceTest
  }

  @Test
  void transferCompletionSudtWithHoldByTo() {
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(AddressWithKeyHolder.testAddress1())),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(
                    AddressTools.generateAcpAddress(AddressWithKeyHolder.testAddress4()),
                    new BigInteger("100"))),
            Mode.HoldByTo));

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_TRANSFER_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("transferCompletionSudtWithHoldByTo", builder.build(), blockInfo, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Transaction sign(TransactionCompletionResponse s) throws IOException {
    Transaction tx = SignUtils.sign(s);
    return tx;
  }
}
