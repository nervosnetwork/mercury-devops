package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.Script;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.QueryTransactionsPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.common.ExtraFilterType;
import org.nervos.mercury.model.common.Range;
import org.nervos.mercury.model.common.ViewType;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;

import constant.AddressWithKeyHolder;
import constant.UdtHolder;

/** @author zjh @Created Date: 2021/7/26 @Description: @Modify by: */
public class QueryTransactionsTest {

  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.QUERY_TRANSACTIONS,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void testQueryTransactionsWithCkb() {

    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithCkb", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithUdt() {
    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.addAssetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithUdt", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithAll() {

    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithAll", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsView() {

    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsView", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsInfo() {

    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.addAssetInfo(AssetInfo.newCkbAsset());
      builder.viewType = ViewType.DoubleEntry;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsInfo", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsCellbase() {

    try {

      String minerAddress = "ckt1qyqd5eyygtdmwdr7ge736zw6z0ju6wsw7rssu8fcve";
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newAddressItem(minerAddress));
      builder.addAssetInfo(AssetInfo.newCkbAsset());
      builder.extraFilter(ExtraFilterType.CellBase);
      builder.limit(BigInteger.valueOf(3));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsCellbase", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsDao() {

    try {

      String daoAddress = AddressWithKeyHolder.testAddress3();
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newAddressItem(daoAddress));
      builder.addAssetInfo(AssetInfo.newCkbAsset());
      builder.extraFilter(ExtraFilterType.Dao);
      builder.limit(BigInteger.valueOf(10));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsDao", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithFromBlockAndToBlock() {
    try {

      Script script = AddressTools.parse(AddressWithKeyHolder.queryTransactionAddress()).script;
      System.out.println(script.computeHash());

      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.range(new Range(new BigInteger("2778110"), new BigInteger("2778201")));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithFromBlockAndToBlock", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithLimit() {
    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.limit(new BigInteger("2"));
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithLimit", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithOrder() {
    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      // default order desc
      builder.order("asc");
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithOrder", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testQueryTransactionsWithTotalCount() {
    try {
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.limit(new BigInteger("1"));
      builder.returnCount(true);
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithTotalCount", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  void testQueryTransactionsWithPage1() {
  //    try {
  //      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
  //
  // builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
  //      builder.limit(new BigInteger("1"));
  //      builder.returnCount(true);
  //
  //      //      System.out.println(g.toJson(builder.build()));
  //
  //      PaginationResponse<TxView<TransactionWithRichStatus>> resp =
  //          ApiFactory.getApi().queryTransactionsWithTransactionView(builder.build());
  //
  //      System.out.println(g.toJson(resp));
  //
  //      if (Objects.isNull(resp.nextCursor)) {
  //        return;
  //      }
  //
  //      while (Objects.nonNull(resp.nextCursor)) {
  //        QueryTransactionsPayloadBuilder builder2 = new QueryTransactionsPayloadBuilder();
  //        builder2.item(
  //            ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
  //        builder2.limit(new BigInteger("1"));
  //        builder2.returnCount(true);
  //        System.out.println(resp.nextCursor);
  //        builder2.cursor(resp.nextCursor);
  //
  //        System.out.println(g.toJson(builder2.build()));
  //        resp = ApiFactory.getApi().queryTransactionsWithTransactionView(builder2.build());
  //
  //        System.out.println(g.toJson(resp));
  //      }
  //
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }

  @Test
  void testQueryTransactionsWithPage2() {
    try {
      // total count = 3
      QueryTransactionsPayloadBuilder builder = new QueryTransactionsPayloadBuilder();
      builder.item(ItemFactory.newIdentityItemByCkb(AddressWithKeyHolder.queryTransactionPubKey()));
      builder.limit(new BigInteger("1"));
      builder.pageNumber(BigInteger.valueOf(3));
      builder.returnCount(true);
      builder.viewType = ViewType.Native;
      builder.viewType = ViewType.Native;
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.QUERY_TRANSACTIONS,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testQueryTransactionsWithPage2", builder.build(), blockInfo);

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
