package org.nervos.mercury.regression.test.controller;

import org.nervos.mercury.regression.test.db.route.DynamicDataSource;
import org.nervos.mercury.regression.test.domian.cases.CaseFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import org.nervos.mercury.regression.test.domian.test.TestNgRunner;
import org.nervos.mercury.regression.test.domian.test.TestRunner;
import org.nervos.mercury.regression.test.domian.test.TestSuiteInfo;
import org.nervos.mercury.regression.test.service.FetchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import io.swagger.annotations.ApiOperation;

@RestController
public class TestController {
  @Autowired private FetchDataService fetchDataService;

  @Autowired private DynamicDataSource dataSource;

  @ApiOperation("回归测试")
  @GetMapping("/mercury/tests")
  public void test(
      @RequestParam(value = "dbUrl", defaultValue = "127.0.0.1:8432") String dbUrl,
      @RequestParam(value = "username", defaultValue = "postgres") String username,
      @RequestParam(value = "password", defaultValue = "123456") String password,
      @RequestParam(value = "mercuryUrl", defaultValue = "127.0.0.1:8116") String mercuryUrl,
      @RequestParam(value = "blockHeight", defaultValue = "3480565") Integer blockHeight) {

    dataSource.putDataSource(
        "jdbc:postgresql://".concat(dbUrl).concat("/mercury"), username, password);

    fetchDataService.fetchData(blockHeight);

    RpcService rpc = new RpcService("http://".concat(mercuryUrl));
    RpcFactory.init(rpc);

    CaseFactory factory = new CaseFactory();
    List<TestSuiteInfo> testSuiteInfos = factory.get();
    TestRunner runner = new TestNgRunner();
    testSuiteInfos.forEach(x -> runner.addSuite(x));
    runner.run();

    dataSource.removeDataSource();
  }
}
