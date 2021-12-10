package org.nervos.mercury.regression.test.controller;

import org.nervos.mercury.regression.test.db.route.DbContextHolder;
import org.nervos.mercury.regression.test.service.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
public class QueryController {

  @Autowired private QueryService queryService;

  @ApiOperation("tx")
  @GetMapping("/mercury/query")
  public void test() {
    DbContextHolder.checkoutTestDataSource();
    queryService.queryTxs();
  }
}
