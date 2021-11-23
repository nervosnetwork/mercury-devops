package org.nervos.mercury.regression.test.domian.test;

import com.google.gson.JsonElement;

import net.javacrumbs.jsonunit.assertj.JsonAssertions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcFactory;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import java.util.Objects;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class BaseCase {

  @Test
  @Parameters({"caseId"})
  public void test(String caseId) throws Exception {
    TestCaseInfo aCase = CaseFactory.getCase(caseId);
    try {

      Reporter.log("req: ".concat(aCase.getReqParams().toString()));

      JsonElement resp = RpcFactory.getRpc().post(aCase.getMethodName(), aCase.getReqParams());
      JsonAssertions.assertThatJson(resp)
          .when(IGNORING_ARRAY_ORDER)
          .whenIgnoringPaths("tx_view.hash")
          .isEqualTo(aCase.getResp());

      Reporter.log("resp: ".concat(GsonFactory.newGson().toJson(resp)));

    } catch (Exception e) {
      if (!Objects.equals(aCase.getMsg(), e.getMessage())) {
        Reporter.log("error: ".concat(ExceptionUtils.getStackTrace(e)));
        throw e;
      } else {
        Reporter.log("msg: ".concat(e.getMessage()));
      }
    }
  }
}
