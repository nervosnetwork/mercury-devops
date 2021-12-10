package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.OutPoint;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
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
import org.nervos.mercury.regression.test.domian.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;

import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.Config;
import constant.UdtHolder;
import prepare.PrepareTransfer;
import utils.BuildUtils;
import utils.SignUtils;

@Disabled
public class TransferTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");
  CaseWriter cw = new CaseWriter(RpcMethods.BUILD_TRANSFER_TRANSACTION, "./src/main/resources");

  void trySendTransaction(TransferPayloadBuilder builder) throws IOException {
    if (Config.isSendTransactionWhenTest()) {
      TransactionCompletionResponse s =
          ApiFactory.getApi().buildTransferTransaction(builder.build());
      Transaction tx = SignUtils.sign(s);
      String txHash = ApiFactory.getApi().sendTransaction(tx);
      BuildUtils.ensureBeOnChain(txHash);
    }
  }

  @Test
  /** Params Test - Param: `asset_info` - Value: CKB - Type: Positive Testing */
  void testAssetInfo0() throws IOException {
    PrepareTransfer.prepareAssetInfo0();

    String testAddress = PrepareTransfer.testAddresses.get(0).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress1(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testAssetInfo0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `asset_info` - Value: UDT - Type: Positive Testing */
  void testAssetInfo1() throws IOException {
    PrepareTransfer.prepareAssetInfo1();

    String testAddress = PrepareTransfer.testAddresses.get(1).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(180))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testAssetInfo1", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `asset_info` - Value: Arbitary `asset_type` - Type: Negative Testing */
  void testAssetInfo2() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"HSJU\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `asset_info` - Value: Arbitary `udt_hash` - Type: Negative Testing */
  void testAssetInfo3() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"dhhf3454554\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `from` - Value: identity + free（CKB） - Type: Positive Testing */
  void testFrom0() throws IOException {
    PrepareTransfer.prepareFrom0();

    String testAddress = PrepareTransfer.testAddresses.get(2).address;
    System.out.println(testAddress);
    System.out.println(AddressTools.generateAcpAddress(testAddress));
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(70))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `from` - Value: address + free（CKB） - Type: Positive Testing */
  void testFrom1() throws IOException {
    PrepareTransfer.prepareFrom1();

    String testAddress = PrepareTransfer.testAddresses.get(3).address;
    System.out.println(testAddress);
    System.out.println(AddressTools.generateAcpAddress(testAddress));
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(From.newFrom(Arrays.asList(ItemFactory.newAddressItem(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(70))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom1", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `from` - Value: record_id + free（CKB） - Type: Positive Testing */
  void testFrom2() throws IOException {
    PrepareTransfer.prepareFrom2();

    String testAddress = PrepareTransfer.testAddresses.get(4).address;
    System.out.println(testAddress);
    System.out.println(AddressTools.generateAcpAddress(testAddress));
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newRecordItemByAddress(
                    new OutPoint(
                        "0x52ae55cde686eddd26a62c0316038614adbec88cf0c1929b89b07a0c344c9f95",
                        "0x0"),
                    testAddress),
                ItemFactory.newRecordItemByAddress(
                    new OutPoint(
                        "0xd4a2b3ecab39cab5db015144b84a73c93f75db1bdaed4e42d099edd8de46c074",
                        "0x1"),
                    testAddress)),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(70))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom2", builder.build(), response, null);
  }

  @Test
  /**
   * Params Test - Param: `from` - Value: identity + claimable（UDT）, testAddress has no acp cell. -
   * Expectation: Udt change will create secp udt cell. Pay attention to the lock_args, it can not
   * be receiver part of cheque args. - Type: Positive Testing
   */
  void testFrom3() throws IOException {
    PrepareTransfer.prepareFrom3();

    String testAddress = PrepareTransfer.testAddresses.get(5).address;
    System.out.println(testAddress);
    System.out.println(AddressTools.generateAcpAddress(testAddress));
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Claimable));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(80))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom3", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /**
   * Params Test - Param: `from` - Value: identity + claimable（UDT）, testAddress has an acp cell. -
   * Expectation: Udt change should be sent to acp cell, should better not generate secp udt cell. -
   * Type: Positive Testing
   */
  void testFrom4() throws IOException {
    PrepareTransfer.prepareFrom4();

    String testAddress = PrepareTransfer.testAddresses.get(6).address;
    System.out.println(testAddress);
    System.out.println(AddressTools.generateAcpAddress(testAddress));
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Claimable));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(80))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom4", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `from` - Value: identity array + free（CKB） - Type: Positive Testing */
  void testFrom5() throws IOException {
    PrepareTransfer.prepareFrom5();

    String testAddress1 = PrepareTransfer.testAddresses.get(7).address;
    String testAddress2 = PrepareTransfer.testAddresses.get(8).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(testAddress1),
                ItemFactory.newIdentityItemByAddress(testAddress2)),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(200))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom5", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `from` - Value: address array + free（CKB） - Type: Positive Testing */
  void testFrom6() throws IOException {
    PrepareTransfer.prepareFrom6();

    String testAddress1 = PrepareTransfer.testAddresses.get(9).address;
    String testAddress2 = PrepareTransfer.testAddresses.get(10).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newAddressItem(testAddress1), ItemFactory.newAddressItem(testAddress2)),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(200))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFrom6", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /**
   * Params Test - Param: `from` - Value: address + free（CKB）, secp address can afford while acp
   * address can not afford, use acp address here - Type: Negative Testing
   */
  void testFrom7() throws IOException {
    PrepareTransfer.prepareFrom7();

    String testAddress = PrepareTransfer.testAddresses.get(3).address;
    String testAcpAddress = AddressTools.generateAcpAddress(testAddress);
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(Arrays.asList(ItemFactory.newAddressItem(testAcpAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(70))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
  }

  @Test
  /** Params Test - Param: `from` - Value: Arbitary item type - Type: Negative Testing */
  void testFrom8() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"hhgd\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `from` - Value: Arbitary item value - Type: Negative Testing */
  void testFrom9() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"fererggtr\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `from` - Value: Arbitary source type - Type: Negative Testing */
  void testFrom10() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Hgg3\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `from` - Value: different item type - Type: Negative Testing */
  void testFrom11() throws IOException {
    String testAddress1 = PrepareTransfer.testAddresses.get(9).address;
    String testAddress2 = PrepareTransfer.testAddresses.get(10).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(
                ItemFactory.newIdentityItemByAddress(testAddress1),
                ItemFactory.newAddressItem(testAddress2)),
            Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(200))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
  }

  @Test
  /** Params Test - Param: `to` - Value: secp address + HoldByFrom（CKB） - Type: Positive Testing */
  void testTo0() throws IOException {
    PrepareTransfer.prepareTo0();

    String testAddress = PrepareTransfer.testAddresses.get(12).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testTo0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `to` - Value: secp address + HoldByFrom（UDT） - Type: Positive Testing */
  void testTo1() throws IOException {
    PrepareTransfer.prepareTo1();

    String testAddress = PrepareTransfer.testAddresses.get(13).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByFrom));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testTo1", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /**
   * Params Test - Param: `to` - Value: secp address + HoldByTo（UDT） - Expectation: Acp cell should
   * be enough to afford fee - Type: Positive Testing
   */
  void testTo2() throws IOException {
    PrepareTransfer.prepareTo2();

    String testAddress = PrepareTransfer.testAddresses.get(14).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByTo));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testTo2", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `to` - Value: acp address + HoldByTo（UDT） - Type: Positive Testing */
  /**
   * Case Test - One address send all its CKB to another address, by using a third address to pay
   * fee.
   */
  void testTo3() throws IOException {
    PrepareTransfer.prepareTo3();

    String testAddress = PrepareTransfer.testAddresses.get(15).address;
    String testAcpAddress = AddressTools.generateAcpAddress(testAddress);
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAcpAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByTo));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testTo3", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `to` - Value: invalid amount format - Type: Negative Testing */
  void testTo4() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"erfrfer33\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /** Params Test - Param: `to` - Value: negative transfer amount - Type: Negative Testing */
  void testTo5() throws IOException {
    String request =
        "{\"asset_info\":{\"asset_type\":\"CKB\",\"udt_hash\":\"0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd\"},\"from\":{\"items\":[{\"type\":\"Identity\",\"value\":\"0x009b9b8e4d3739f80ea5f209226bf545b5c130438c\"}],\"source\":\"Free\"},\"to\":{\"to_infos\":[{\"address\":\"ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0\",\"amount\":\"-180\"}],\"mode\":\"HoldByTo\"},\"pay_fee\":null,\"change\":null,\"fee_rate\":1000,\"since\":null}";
    rpc.post(RpcMethods.BUILD_TRANSFER_TRANSACTION, g.fromJson(request, JsonObject.class));
  }

  @Test
  /**
   * Params Test - Param: `to` - Value: transfer amount larger than free balance - Type: Negative
   * Testing
   */
  void testTo6() throws IOException {
    String testAddress = PrepareTransfer.testAddresses.get(15).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100000))),
            Mode.HoldByTo));
    System.out.println(g.toJson(builder.build()));

    rpc.post(
        RpcMethods.BUILD_TRANSFER_TRANSACTION,
        g.fromJson(g.toJson(builder.build()), JsonObject.class));
  }

  @Test
  /**
   * Params Test - Param: `to` - Value: cheque address + HoldByFrom（CKB） - Type: Negative Testing
   */
  void testTo7() throws IOException {
    // cheque address
    String testAddress =
        "ckt1qpsdtuu7lnjqn3v8ew02xkwwlh4dv5x2z28shkwt8p2nfruccux4kqftchvd55ayfedxykhh4z0rlkrmhqlpu378wt6d3pw2v2zaslvzhrkuzepal8euucck2akla";
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByTo));
    System.out.println(g.toJson(builder.build()));

    rpc.post(
        RpcMethods.BUILD_TRANSFER_TRANSACTION,
        g.fromJson(g.toJson(builder.build()), JsonObject.class));
  }

  @Test
  /** Params Test - Param: `pay_fee` - Value: secp address（CKB） - Type: Positive Testing */
  void testPayFee0() throws IOException {
    PrepareTransfer.preparePayFee0();

    String testAddress = PrepareTransfer.testAddresses.get(16).address;
    String payFeeAddress = PrepareTransfer.testAddresses.get(17).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    builder.payFee(payFeeAddress);
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testPayFee0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `pay_fee` - Value: secp address（UDT） - Type: Positive Testing */
  void testPayFee1() throws IOException {
    PrepareTransfer.preparePayFee1();

    String testAddress = PrepareTransfer.testAddresses.get(18).address;
    String payFeeAddress = PrepareTransfer.testAddresses.get(19).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByFrom));
    builder.payFee(payFeeAddress);
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testPayFee1", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `change` - Value: secp address（CKB） - Type: Positive Testing */
  void testChange0() throws IOException {
    PrepareTransfer.prepareChange0();

    String testAddress = PrepareTransfer.testAddresses.get(20).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    builder.change(AddressWithKeyHolder.testAddress1());
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testChange0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `change` - Value: secp address（UDT） - Type: Positive Testing */
  void testChange1() throws IOException {
    PrepareTransfer.prepareChange1();

    String testAddress = PrepareTransfer.testAddresses.get(21).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(100))),
            Mode.HoldByFrom));
    builder.change(AddressWithKeyHolder.testAddress0());
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testChange1", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `fee_rate` - Value: > 1000 - Type: Positive Testing */
  void testFeeRate0() throws IOException {
    PrepareTransfer.prepareFeeRate0();

    String testAddress = PrepareTransfer.testAddresses.get(22).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    builder.feeRate(BigInteger.valueOf(2000));
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testFeeRate0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /** Params Test - Param: `fee_rate` - Value: < 1000 - Type: Positive Testing */
  void testFeeRate1() throws IOException {
    PrepareTransfer.prepareFeeRate0();

    String testAddress = PrepareTransfer.testAddresses.get(22).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(100))),
            Mode.HoldByFrom));
    builder.feeRate(BigInteger.valueOf(900));
    System.out.println(g.toJson(builder.build()));

    trySendTransaction(builder);
  }

  @Test
  /**
   * Case Test - One address send all its CKB to another address, by using a third address to pay
   * fee.
   */
  void testCase0() throws IOException {
    PrepareTransfer.prepareCase0();

    String testAddress = PrepareTransfer.testAddresses.get(23).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(200))),
            Mode.HoldByFrom));
    builder.payFee(AddressWithKeyHolder.testAddress1());
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testCase0", builder.build(), response, null);

    trySendTransaction(builder);
  }

  @Test
  /**
   * Case Test - One address only have one acp cell, and extra 1 free CKB on it. It can pay fee to
   * support UDT transfer.
   */
  void testCase1() throws IOException {
    PrepareTransfer.prepareCase1();

    String testAddress = PrepareTransfer.testAddresses.get(24).address;
    TransferPayloadBuilder builder = new TransferPayloadBuilder();
    builder.assetInfo(AssetInfo.newCkbAsset());
    builder.from(
        From.newFrom(
            Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
    builder.to(
        To.newTo(
            Arrays.asList(
                new ToInfo(AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(199))),
            Mode.HoldByFrom));
    builder.change(AddressWithKeyHolder.testAddress0());
    System.out.println(g.toJson(builder.build()));

    JsonElement response =
        rpc.post(
            RpcMethods.BUILD_TRANSFER_TRANSACTION,
            g.fromJson(g.toJson(builder.build()), JsonObject.class));
    cw.write("testCase1", builder.build(), response, null);

    trySendTransaction(builder);
  }
}
