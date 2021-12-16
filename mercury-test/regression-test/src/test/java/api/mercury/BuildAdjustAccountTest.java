package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.AdjustAccountPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.Config;
import constant.UdtHolder;
import prepare.PrepareAdjustAccount;
import utils.BuildUtils;
import utils.SignUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildAdjustAccountTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");
  CaseWriter cw = new CaseWriter(RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION, "./src/main/resources");

  private Transaction trySendTransaction(
      AdjustAccountPayloadBuilder builder,
      String filename
      ) throws IOException
  {
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
      rpc.post(
          RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
          g.fromJson(g.toJson(builder.build()), JsonObject.class));

    cw.write(filename, builder.build(), response, null);

    TransactionCompletionResponse s =
      ApiFactory.getApi().buildAdjustAccountTransaction(builder.build());
    Transaction tx = SignUtils.sign(s);

    if (!Config.isSendTransactionWhenTest()) {
      return tx;
    }

    String txHash = ApiFactory.getApi().sendTransaction(tx);
    BuildUtils.ensureBeOnChain(txHash);

    return tx;
  }

  private void assertInvalidTransaction(AdjustAccountPayloadBuilder builder) {
    System.out.println(g.toJson(builder.build()));

    assertThrows(IOException.class, () -> {
      rpc.post(
          RpcMethods.BUILD_ADJUST_ACCOUNT_TRANSACTION,
          g.fromJson(g.toJson(builder.build()), JsonObject.class));
    });
  }

  private static AdjustAccountPayloadBuilder getDefaultBuilder(int item) {
    String itemAddress = PrepareAdjustAccount.testAddresses.get(item).address;

    AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByAddress(itemAddress));
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.accountNumber(BigInteger.ONE);

    return builder;
  }

  @Test
  /**
   * Params Test
   * - Param: `item`
   * - Value: `Secp` `Identity`
   * - Param: `from`
   * - Value: Empty
   * - Param: `asset_info`
   * - Value: UDT
   * - Param: `account_number`
   * - Value: 1
   * - Param: `extra_ckb`
   * - Value: `null`
   * - Param: `fee_rate`
   * - Value: `null`
   * - Type: Positive Testing
   */
  void testDefault() throws IOException {
    PrepareAdjustAccount.prepareDefault();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(0);

    trySendTransaction(builder, "default");
  }

  @Test
  /**
   * Case Test
   * - Consume part of ACP cells
   */
  void testCase0() throws IOException {
    PrepareAdjustAccount.prepareTestCase0();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(1);

    trySendTransaction(builder, "testCase0");
  }

  @Test
  /**
   * Case Test
   * - Create ACP cells when there are already some ACP cells
   */
  void testCase1() throws IOException {
    PrepareAdjustAccount.prepareTestCase1();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(2);
    builder.accountNumber(BigInteger.TWO);

    trySendTransaction(builder, "testCase1");
  }

  @Test
  /**
   * Params Test
   * - Param: `account_number`
   * - Value: 0
   * - Type: Positive Testing
   */
  void testAccountNumber0() throws IOException {
    PrepareAdjustAccount.prepareTestAccountNumber0();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(3);
    builder.accountNumber(BigInteger.ZERO);

    trySendTransaction(builder, "testAccountNumber0");
  }

  @Test
  /**
   * Params Test
   * - Param: `account_number`
   * - Value: 2
   * - Type: Positive Testing
   */
  void testAccountNumber1() throws IOException {
    PrepareAdjustAccount.prepareTestAccountNumber1();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(4);
    builder.accountNumber(BigInteger.TWO);

    trySendTransaction(builder, "testAccountNumber1");
  }

  @Test
  /**
   * Params Test
   * - Param: `extra_ckb`
   * - Value: 2
   * - Type: Positive Testing
   */
  void testExtraCkb0() throws IOException {
    PrepareAdjustAccount.prepareTestExtraCkb0();

    AdjustAccountPayloadBuilder builder = getDefaultBuilder(5);
    builder.extraCkb(BigInteger.TWO);
    builder.accountNumber(BigInteger.TWO);

    builder.assetInfo(AssetInfo.newCkbAsset());

    trySendTransaction(builder, "testExtraCkb0");
  }

  @Test
  /**
   * Params Test
   * - Param: `asset_info`
   * - Value: `CKB`
   * - Type: Negative Testing
   */
  void testAssetInfo0() throws IOException {
    AdjustAccountPayloadBuilder builder = getDefaultBuilder(0);
    builder.assetInfo(AssetInfo.newCkbAsset());

    assertInvalidTransaction(builder);
  }
}
