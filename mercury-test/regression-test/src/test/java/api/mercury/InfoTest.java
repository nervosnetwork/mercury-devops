package api.mercury;

import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.model.resp.info.DBInfo;
import org.nervos.mercury.model.resp.info.MercuryInfo;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;

import constant.ApiFactory;

public class InfoTest {
  Gson g = GsonFactory.newGson();
  RpcService rpc = new RpcService("http://127.0.0.1:8116");

  CaseWriter cw =
      new CaseWriter(
          RpcMethods.GET_BLOCK_INFO,
          "/Users/zjh/Documents/cryptape/mercury-devops/mercury-test/regression-test/src/main/resources");

  @Test
  public void testDbInfo() {
    DBInfo dbInfo = null;
    try {
      dbInfo = ApiFactory.getApi().getDbInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(g.toJson(dbInfo));
  }

  @Test
  public void testMercuryInfo() {
    MercuryInfo mercuryInfo = null;
    try {
      mercuryInfo = ApiFactory.getApi().getMercuryInfo();
    } catch (IOException e) {
      e.printStackTrace();
    }
    System.out.println(g.toJson(mercuryInfo));
  }
}
