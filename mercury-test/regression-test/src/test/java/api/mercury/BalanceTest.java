package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.OutPoint;
import org.nervos.ckb.type.Script;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.indexer.model.ScriptType;
import org.nervos.indexer.model.SearchKeyBuilder;
import org.nervos.indexer.model.resp.CellResponse;
import org.nervos.indexer.model.resp.CellsResponse;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.GetBalancePayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;

import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.UdtHolder;

@Disabled
public class BalanceTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.GET_BALANCE,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void getCkbBalance() {
    try {

      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());

      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getCkbBalance", builder.build(), balance, null);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getSudtBalance() {
    GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));
    builder.addAssetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

    try {
      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getSudtBalance", builder.build(), balance, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getAllBalance() {

    GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
    builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getAllBalance", builder.build(), balance, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getBalanceByAddress() {

    try {
      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
      builder.item(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress4()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());

      System.out.println(g.toJson(builder.build()));

      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getBalanceByAddress", builder.build(), balance, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void getBalanceByIdentity() {

    try {
      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.testPubKey4()));
      builder.addAssetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));

      System.out.println(g.toJson(builder.build()));

      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getBalanceByIdentity", builder.build(), balance, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  void getBalanceByRecordByScriptByChequeCellSender() {
  //
  //    try {
  //
  //      Script script = AddressTools.parse(AddressWithKeyHolder.testAddress1()).script;
  //
  //      OutPointResponse outPoint = new OutPointResponse();
  //      outPoint.txHash = "0xecfea4bdf6bf8290d8f8186ed9f4da9b0f8fbba217600b47632f5a72ff677d4d";
  //      outPoint.index = "0x0";
  //
  //      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
  //      builder.item(
  //          ItemFactory.newRecordItemByScript(new OutPoint(outPoint.txHash, outPoint.index),
  // script));
  //      builder.addAssetInfo(AssetInfo.newCkbAsset());
  //
  //      GetBalanceResponse balance = ApiFactory.getApi().getBalance(builder.build());
  //      cw.write("getBalanceByRecordByScriptByChequeCellSender", builder.build(), balance);
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //  @Test
  //  void getBalanceByRecordByScriptChequeCellReceiver() {
  //
  //    try {
  //
  //      Script script = AddressTools.parse(AddressWithKeyHolder.testAddress2()).script;
  //
  //      OutPointResponse outPoint = new OutPointResponse();
  //      outPoint.txHash = "0xecfea4bdf6bf8290d8f8186ed9f4da9b0f8fbba217600b47632f5a72ff677d4d";
  //      outPoint.index = "0x0";
  //
  //      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
  //      builder.item(
  //          ItemFactory.newRecordItemByScript(new OutPoint(outPoint.txHash, outPoint.index),
  // script));
  //      builder.addAssetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
  //
  //      System.out.println(g.toJson(builder.build()));
  //
  //      GetBalanceResponse balance = ApiFactory.getApi().getBalance(builder.build());
  //      cw.write("getBalanceByRecordByScriptChequeCellReceiver", builder.build(), balance);
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }

  @Test
  void getBalanceByRecordByAddress() {

    try {

      Script script = AddressTools.parse(AddressWithKeyHolder.testAddress4()).script;
      CellResponse cells = getCells(script);

      GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
      builder.item(
          ItemFactory.newRecordItemByAddress(
              new OutPoint(cells.outPoint.txHash, cells.outPoint.index),
              AddressWithKeyHolder.testAddress4()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());

      System.out.println(g.toJson(builder.build()));

      JsonElement balance =
          rpc.post(RpcMethods.GET_BALANCE, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("getBalanceByRecordByAddress", builder.build(), balance, null);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private CellResponse getCells(Script script) throws IOException {

    SearchKeyBuilder key = new SearchKeyBuilder();
    key.script(script);
    key.scriptType(ScriptType.lock);

    System.out.println(g.toJson(key.build()));

    CellsResponse cells =
        ApiFactory.getApi()
            .getCells(key.build(), "asc", "0x" + new BigInteger("10").toString(16), null);

    if (cells.objects.size() > 0) {
      return cells.objects.get(0);
    }
    return null;
  }
}
