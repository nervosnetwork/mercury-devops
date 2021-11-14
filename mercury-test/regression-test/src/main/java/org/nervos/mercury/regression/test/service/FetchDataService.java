package org.nervos.mercury.regression.test.service;

import com.google.common.collect.Lists;
import org.nervos.ckb.utils.address.AddressParser;
import org.nervos.mercury.fetch.data.entity.*;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;
import org.nervos.mercury.fetch.data.mapper.*;
import org.nervos.mercury.regression.test.db.route.DbContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

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

  @Autowired private MercuryBlockMapper blockMapper;
  @Autowired private MercuryCanonicalChainMapper canonicalChainMapper;
  //  @Autowired private MercuryUncleRelationshipMapper uncleRelationshipMapper;
  @Autowired private MercuryTransactionMapper transactionMapper;
  @Autowired private MercuryCellMapper cellMapper;
  @Autowired private MercuryLiveCellMapper liveCellMapper;
  @Autowired private MercuryScriptMapper scriptMapper;

  public void fetchData() {
    DbContextHolder.checkoutRawDataSource();

    List<Integer> blockNumbers = this.getBlockNumbersByAddresses();
    this.saveBlocks(blockNumbers);
    this.saveTransactions(blockNumbers);
    this.saveCells(blockNumbers);
  }

  private void saveBlocks(List<Integer> blockNumbers) {
    DbContextHolder.checkoutRawDataSource();
    List<MercuryBlock> mercuryBlocks = this.blockMapper.selectByBlockNumbers(blockNumbers);
    DbContextHolder.checkoutTestDataSource();
    this.blockMapper.batchInsert(mercuryBlocks);

    DbContextHolder.checkoutRawDataSource();
    List<MercuryCanonicalChain> mercuryCanonicalChains =
        this.canonicalChainMapper.selectByBlockNumbers(blockNumbers);
    DbContextHolder.checkoutTestDataSource();
    this.canonicalChainMapper.batchInsert(mercuryCanonicalChains);

    //    DbContextHolder.checkoutRawDataSource();
    //    List<MercuryUncleRelationship> mercuryUncleRelationships
    //            =
    // this.uncleRelationshipMapper.selectByBlockHashes(mercuryBlocks.stream().map(MercuryBlock::getBlockHash).collect(toList()));
    //    DbContextHolder.checkoutTestDataSource();
    //    this.uncleRelationshipMapper.batchInsert(mercuryUncleRelationships);

  }

  private void saveTransactions(List<Integer> blockNumbers) {
    DbContextHolder.checkoutRawDataSource();
    List<MercuryTransaction> mercuryTransactions =
        this.transactionMapper.selectByBlockNumbers(blockNumbers);

    DbContextHolder.checkoutTestDataSource();
    List<List<MercuryTransaction>> partitions = Lists.partition(mercuryTransactions, 500);
    partitions.forEach(x -> this.transactionMapper.batchInsert(x));
  }

  private void saveCells(List<Integer> blockNumbers) {
    DbContextHolder.checkoutRawDataSource();
    List<MercuryCell> mercuryCells = this.cellMapper.selectByBlockNumbers(blockNumbers);
    DbContextHolder.checkoutTestDataSource();
    List<List<MercuryCell>> cells = Lists.partition(mercuryCells, 500);
    cells.forEach(x -> this.cellMapper.batchInsert(x));

    DbContextHolder.checkoutRawDataSource();
    List<MercuryLiveCell> mercuryLiveCells = this.liveCellMapper.selectByBlockNumbers(blockNumbers);
    DbContextHolder.checkoutTestDataSource();
    List<List<MercuryLiveCell>> liveCells = Lists.partition(mercuryLiveCells, 500);
    liveCells.forEach(x -> this.liveCellMapper.batchInsert(x));

    List<HexBytes> scriptHashes =
        Stream.concat(
                mercuryCells.stream()
                    .filter(x -> x.getTypeHash() != null)
                    .map(MercuryCell::getTypeHash)
                    .distinct(),
                mercuryCells.stream().map(MercuryCell::getLockHash).distinct())
            .distinct()
            .collect(toList());
    DbContextHolder.checkoutRawDataSource();
    List<MercuryScript> mercuryScripts = this.scriptMapper.selectByScriptHashes(scriptHashes);
    DbContextHolder.checkoutTestDataSource();
    List<List<MercuryScript>> scripts = Lists.partition(mercuryScripts, 500);
    scripts.forEach(x -> this.scriptMapper.batchInsert(x));
  }

  private List<Integer> getBlockNumbersByAddresses() {
    return ADDRESSES.stream()
        .flatMap(
            x ->
                cellMapper
                    .selectBlockNumberByLockHash(
                        HexBytes.newHexBytes(AddressParser.parse(x).script.computeHash()))
                    .stream())
        .distinct()
        .collect(toList());
  }
}
