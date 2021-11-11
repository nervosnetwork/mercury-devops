package org.nervos.mercury.regression.test.service;

import org.nervos.ckb.utils.address.AddressParser;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;
import org.nervos.mercury.fetch.data.mapper.MercuryCellMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class FetchDataService {

  private static final List<String> ADDRESSES =
      Arrays.asList(
          "ckt1qyq28wze3cw48ek9az0g4jmtfs6d8td38u4s6hp2s0",
          "ckt1qyq27z6pccncqlaamnh8ttapwn260egnt67ss2cwvz",
          "ckt1qyqqtg06h75ymw098r3w0l3u4xklsj04tnsqctqrmc",
          "ckt1qyqzqfj8lmx9h8vvhk62uut8us844v0yh2hsnqvvgc",
          "ckt1qyqg88ccqm59ksxp85788pnqg4rkejdgcg2qxcu2qf",
          "ckt1qyqg03ul48cpvd3wzlqu2t5qpe80hdv3nqpq4hswge",
          "ckt1qyqg6y6utyjqhc3znvv7as97tgqxkd9nkr9ssuxft0");

  @Autowired private MercuryCellMapper cellMapper;

  public void fetchData() {
    List<Integer> blockNumbers = this.getBlockNumbersByAddresses();
    System.out.println(blockNumbers);
  }

  private List<Integer> getBlockNumbersByAddresses() {
    return ADDRESSES.stream()
        .flatMap(
            x ->
                cellMapper
                    .selectBlockNumberByLockHash(
                        HexBytes.newHexBytes(AddressParser.parse(x).script.computeHash()))
                    .stream())
        .collect(toList());
  }
}
