package org.nervos.mercury.regression.test.domian.test;

public interface TestRunner {
  void run();
  //    void addSuite(String name, List<Class> classes);
  void addSuite(TestSuiteInfo info);

  Boolean status();
}
