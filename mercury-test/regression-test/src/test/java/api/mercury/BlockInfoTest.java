package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.GetBlockInfoPayloadBuilder;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;
import java.math.BigInteger;

/** @author zjh @Created Date: 2021/7/20 @Description: @Modify by: */
public class BlockInfoTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.GET_BLOCK_INFO,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void testGetBlockInfoWithBlockNumber() {
    try {

      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
      builder.blockNumber(new BigInteger("3480565"));

      System.out.println(g.toJson(builder.build()));

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.GET_BLOCK_INFO, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testGetBlockInfoWithBlockNumber", builder.build(), blockInfo, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetBlockInfoWithBlockHash() {
    try {

      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
      builder.blockHash("0x85189bea8b85f63f6e3f1c836645f413f9af35d4913b91daaf04ee169721095f");

      System.out.println(g.toJson(builder.build()));

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.GET_BLOCK_INFO, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testGetBlockInfoWithBlockHash", builder.build(), blockInfo, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  void testGetBlockInfoWithBlockHashAndBlockNumber() {
    try {

      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
      builder.blockNumber(new BigInteger("3480565"));
      builder.blockHash("0x85189bea8b85f63f6e3f1c836645f413f9af35d4913b91daaf04ee169721095f");

      System.out.println(g.toJson(builder.build()));

      JsonElement blockInfo =
          rpc.post(
              RpcMethods.GET_BLOCK_INFO, g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testGetBlockInfoWithBlockHashAndBlockNumber", builder.build(), blockInfo, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  //  @Test
  //  void testTipBlockInfo() {
  //    try {
  //
  //      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
  //
  //      // Request parameter is empty
  //      System.out.println(g.toJson(builder.build()));
  //
  //      BlockInfoResponse blockInfo = ApiFactory.getApi().getBlockInfo(builder.build());
  //
  //      System.out.println(g.toJson(blockInfo));
  //    } catch (IOException e) {
  //      e.printStackTrace();
  //    }
  //  }
  //
  //  @Test
  //  void testBlockHashAndBlockNumberDoNotMatch() {
  //    try {
  //
  //      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
  //      builder.blockNumber(new BigInteger("2172092"));
  //      builder.blockHash("0xee8adba356105149cb9dc1cb0d09430a6bd01182868787ace587961c0d64e742");
  //
  //      System.out.println(g.toJson(builder.build()));
  //
  //      ApiFactory.getApi().getBlockInfo(builder.build());
  //
  //    } catch (IOException e) {
  //      //      assertEquals("block number and hash mismatch", e.getMessage());
  //    }
  //  }
  //
  //  @Test
  //  void testCannotFind() {
  //    try {
  //
  //      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
  //      builder.blockHash("0xee8adba356105149cb9dc1cb0d09430a6bd01182868787ace587961c0d64e741");
  //
  //      System.out.println(g.toJson(builder.build()));
  //
  //      ApiFactory.getApi().getBlockInfo(builder.build());
  //      // TODO: 2021/7/22 error handle
  //    } catch (IOException e) {
  //      //      Assert.assertTrue(
  //      //          e.getMessage()
  //      //              .startsWith(
  //      //                  "RpcService method get_generic_block error
  //      // {\"code\":-32602,\"message\":\"Cannot get block by hash H256"));
  //    }
  //  }
  //
  //  @Test
  //  void testWrongHeight() {
  //    try {
  //
  //      GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
  //      builder.blockNumber(new BigInteger("217209233"));
  //
  //      System.out.println(g.toJson(builder.build()));
  //
  //      // error: invalid block number
  //      ApiFactory.getApi().getBlockInfo(builder.build());
  //
  //    } catch (IOException e) {
  //      //      assertEquals(
  //      //          "RpcService method get_generic_block error
  // {\"code\":-32602,\"message\":\"invalid
  //      // block number\"}",
  //      //          e.getMessage());
  //    }
  //  }
}
