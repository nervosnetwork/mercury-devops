package org.nervos.mercury.regression.test.domian.test;

import com.google.gson.JsonElement;

import net.javacrumbs.jsonunit.assertj.JsonAssertions;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.nervos.mercury.regression.test.domian.cases.CaseFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcFactory;
import org.testng.Reporter;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import static net.javacrumbs.jsonunit.core.Option.IGNORING_ARRAY_ORDER;

public class BaseCase {

  @Test
  @Parameters({"caseId"})
  public void test(String caseId) throws Exception {

    try {
      TestCaseInfo aCase = CaseFactory.getCase(caseId);
      Reporter.log("req: ".concat(aCase.getReqParams().toString()));

      JsonElement resp = RpcFactory.getRpc().post(aCase.getMethodName(), aCase.getReqParams());
      JsonAssertions.assertThatJson(resp)
          .when(IGNORING_ARRAY_ORDER)
          .whenIgnoringPaths("tx_view.hash")
          .isEqualTo(aCase.getResp());
    } catch (Exception e) {
      Reporter.log("error: ".concat(ExceptionUtils.getStackTrace(e)));
      throw e;
    }
  }
}
