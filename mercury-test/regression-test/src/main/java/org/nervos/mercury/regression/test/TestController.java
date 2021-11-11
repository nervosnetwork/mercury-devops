package org.nervos.mercury.regression.test;

import org.nervos.mercury.fetch.data.mapper.MercuryBlockMapper;
import org.nervos.mercury.regression.test.service.FetchDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @Autowired private MercuryBlockMapper mercuryBlockMapper;
  @Autowired private FetchDataService fetchDataService;

  @GetMapping("/tests")
  public void tests() {
    //    HexBytes hexBytes =
    //
    // HexBytes.newHexBytes("0xee8adba356105149cb9dc1cb0d09430a6bd01182868787ace587961c0d64e742");
    //    MercuryBlock mercuryBlock = mercuryBlockMapper.selectByPrimaryKey(hexBytes);
    //
    //    System.out.println(mercuryBlock);

    fetchDataService.fetchData();
  }
}
