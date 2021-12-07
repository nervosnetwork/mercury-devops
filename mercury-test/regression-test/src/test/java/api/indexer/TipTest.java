package api.indexer;

import api.RpcSender;
import org.junit.jupiter.api.Test;
import org.nervos.mercury.regression.test.RpcMethods;

public class TipTest {
  String method = RpcMethods.INDEXER_GET_TIP;

  /**
   * Params Test
   * - Param: null
   * - Value: null
   * - Type: Positive Testing
   */
  @Test
  void testGetTip() {
    RpcSender.sendRequestAndWriteCase(method, null);
  }
}
