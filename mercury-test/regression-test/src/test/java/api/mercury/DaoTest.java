package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.DaoClaimPayloadBuilder;
import org.nervos.mercury.model.DaoDepositPayloadBuilder;
import org.nervos.mercury.model.DaoWithdrawPayloadBuilder;
import org.nervos.mercury.model.req.From;
import org.nervos.mercury.model.req.Source;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.util.Arrays;

import constant.AddressWithKeyHolder;

public class DaoTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw1 =
      new CaseWriter(
          RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  CaseWriter cw2 =
      new CaseWriter(
          RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  CaseWriter cw3 =
      new CaseWriter(
          RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  public void testDepositWithAddress() {
    DaoDepositPayloadBuilder builder = new DaoDepositPayloadBuilder();
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress3())),
            Source.Free));
    builder.amount(AmountUtils.ckbToShannon(300));

    System.out.println(g.toJson(builder));

    try {

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw1.write("testDepositWithAddress", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testDepositWithIdentity() {
    DaoDepositPayloadBuilder builder = new DaoDepositPayloadBuilder();
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey3())),
            Source.Free));
    builder.amount(AmountUtils.ckbToShannon(300));

    System.out.println(g.toJson(builder));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw1.write("testDepositWithIdentity", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testWithdraw() {
    DaoWithdrawPayloadBuilder builder = new DaoWithdrawPayloadBuilder();
    builder.from(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress3()));
    builder.payFee(AddressWithKeyHolder.testAddress1());

    System.out.println(g.toJson(builder));

    TransactionCompletionResponse transactionCompletionResponse = null;
    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw2.write("testWithdraw", builder.build(), blockInfo);
    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(g.toJson(transactionCompletionResponse));
  }

  @Test
  public void testClaim() {
    DaoClaimPayloadBuilder builder = new DaoClaimPayloadBuilder();
    builder.from(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress3()));

    System.out.println(g.toJson(builder));

    TransactionCompletionResponse transactionCompletionResponse = null;
    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw3.write("testClaim", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }

    System.out.println(g.toJson(transactionCompletionResponse));
  }
}
