package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;

import org.junit.jupiter.api.Test;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;

public class TransactionInfoTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.GET_TRANSACTION_INFO,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  void testGetTransactionInfo() {
    try {
      JsonElement transactionInfo =
          rpc.post(
              RpcMethods.GET_TRANSACTION_INFO,
              new JsonPrimitive(
                  "0x4329e4c751c95384a51072d4cbc9911a101fd08fc32c687353d016bf38b8b22c"));

      cw.write(
          "testGetSpentTransactionInfo",
          "0x4329e4c751c95384a51072d4cbc9911a101fd08fc32c687353d016bf38b8b22c",
          transactionInfo);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
