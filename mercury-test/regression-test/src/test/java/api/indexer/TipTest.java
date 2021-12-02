package api.indexer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.indexer.model.resp.TipResponse;

import java.io.IOException;

import constant.ApiFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

public class TipTest {
  String method = RpcMethods.INDEXER_GET_TIP;
  RpcService rpc = new RpcService("http://127.0.0.1:8116");
  CaseWriter cw = new CaseWriter(method, "./src/main/resources");

  /**
   * Params Test
   * - Param: null
   * - Value: null
   * - Type: Positive Testing
   */
  @Test
  void testGetTip() {
    try {
      JsonElement response = rpc.post(method, null);
      cw.write("testGetTip", null, response, null);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
