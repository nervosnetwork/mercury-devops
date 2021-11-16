package org.nervos.mercury.regression.test;

import org.mybatis.spring.annotation.MapperScan;
import org.nervos.mercury.regression.test.service.FetchDataService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@MapperScan(basePackages = "org.nervos.mercury.fetch.data.mapper")
public class Application {
  public static void main(String[] args) {
    ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
    FetchDataService fetchDataService = context.getBean(FetchDataService.class);
    fetchDataService.fetchData();
  }
}
