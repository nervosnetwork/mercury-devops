package org.nervos.mercury.regression.test.domian.test;

import org.nervos.mercury.regression.test.domian.test.listenter.MyExtentTestNgFormatter;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestNgRunner implements TestRunner {
  private TestNG ng = new TestNG();
  private List<XmlSuite> xmlSuites = new ArrayList<>(1);

  @Override
  public void run() {
    this.ng.setThreadCount(20);
    this.ng.setListenerClasses(Arrays.asList(MyExtentTestNgFormatter.class));
    this.ng.setXmlSuites(this.xmlSuites);
    this.ng.run();
  }

  @Override
  public void addSuite(TestSuiteInfo info) {
    XmlSuite suite = new XmlSuite();
    suite.setName(info.getName());
    suite.setThreadCount(10);
    suite.setParallel(XmlSuite.ParallelMode.TESTS);

    for (TestCaseInfo testCase : info.getTestCases()) {
      String caseId = suite.getName().concat(":").concat(testCase.getName());

      List<XmlClass> xmlClasses = new ArrayList<>(1);
      XmlClass xmlClass = new XmlClass(BaseCase.class);

      Map<String, String> parameters = new HashMap<>(2, 1);
      parameters.put("caseId", caseId);
      xmlClass.setParameters(parameters);
      xmlClasses.add(xmlClass);

      XmlTest test = new XmlTest(suite);
      test.setName(testCase.getName());
      test.setXmlClasses(xmlClasses);
    }

    this.xmlSuites.add(suite);
  }

  @Override
  public void exit() {}
}
