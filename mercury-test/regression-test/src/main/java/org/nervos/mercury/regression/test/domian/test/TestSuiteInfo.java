package org.nervos.mercury.regression.test.domian.test;

import java.util.List;

import lombok.Getter;

@Getter
public class TestSuiteInfo {
  public String name;
  private List<TestCaseInfo> testCases;

  public TestSuiteInfo(String name, List<TestCaseInfo> testCases) {
    this.name = name;
    this.testCases = testCases;
  }
}
