package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.OutPoint;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.GetSpentTransactionPayloadBuilder;
import org.nervos.mercury.model.common.ViewType;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;

public class GetSpentTransactionTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.GET_SPENT_TRANSACTION,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  public void testGetSpentTransactionView() {
    GetSpentTransactionPayloadBuilder builder = new GetSpentTransactionPayloadBuilder();
    builder.outpoint(
        new OutPoint("0xb2e952a30656b68044e1d5eed69f1967347248967785449260e3942443cbeece", "0x1"));
    builder.structureType = ViewType.Native;

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.GET_SPENT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testGetSpentTransactionView", builder.build(), blockInfo, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testGetSpentTransactionInfo() {
    GetSpentTransactionPayloadBuilder builder = new GetSpentTransactionPayloadBuilder();
    builder.outpoint(
        new OutPoint("0xb2e952a30656b68044e1d5eed69f1967347248967785449260e3942443cbeece", "0x1"));
    builder.structureType = ViewType.DoubleEntry;

    System.out.println(g.toJson(builder.build()));

    try {
      JsonElement blockInfo =
          rpc.post(
              RpcMethods.GET_SPENT_TRANSACTION,
              g.fromJson(g.toJson(builder.build()), JsonObject.class));

      cw.write("testGetSpentTransactionInfo", builder.build(), blockInfo, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
