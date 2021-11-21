package org.nervos.mercury.regression.test;

import org.mybatis.spring.annotation.MapperScan;
import org.nervos.mercury.regression.test.domian.cases.CaseFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcFactory;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import org.nervos.mercury.regression.test.domian.test.TestNgRunner;
import org.nervos.mercury.regression.test.domian.test.TestRunner;
import org.nervos.mercury.regression.test.domian.test.TestSuiteInfo;
import org.nervos.mercury.regression.test.service.FetchDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
@MapperScan(basePackages = "org.nervos.mercury.fetch.data.mapper")
public class Application {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    FetchDataService fetchDataService = context.getBean(FetchDataService.class);
    fetchDataService.fetchData();

    RpcService rpc = new RpcService("http://127.0.0.1:8116");
    RpcFactory.init(rpc);

    CaseFactory factory = new CaseFactory();
    List<TestSuiteInfo> testSuiteInfos = factory.get();
    TestRunner runner = new TestNgRunner();
    testSuiteInfos.forEach(x -> runner.addSuite(x));
    runner.run();
  }
}
