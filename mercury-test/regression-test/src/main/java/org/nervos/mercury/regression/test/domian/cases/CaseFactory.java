package org.nervos.mercury.regression.test.domian.cases;

import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.test.TestCaseInfo;
import org.nervos.mercury.regression.test.domian.test.TestSuiteInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseFactory {

  private static final Map<String, TestCaseInfo> TEST_CASES = new HashMap<>();

  public static List<TestSuiteInfo> get() {

    List<TestSuiteInfo> infos = new ArrayList<>(RpcMethods.RPC_METHODS.size());
    for (String methodName : RpcMethods.RPC_METHODS) {
      CaseReader reader = new CaseReader(methodName);
      try {

        Map<String, TestCaseInfo> cases = reader.read();
        TestSuiteInfo info = new TestSuiteInfo(methodName, new ArrayList(cases.values()));
        infos.add(info);
        TEST_CASES.putAll(cases);

      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return infos;
  }

  public static TestCaseInfo getCase(String caseId) {
    return TEST_CASES.get(caseId);
  }
}
