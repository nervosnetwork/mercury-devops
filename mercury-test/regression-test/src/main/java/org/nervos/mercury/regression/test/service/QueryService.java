package org.nervos.mercury.regression.test.service;

import org.apache.commons.lang3.time.StopWatch;
import org.nervos.ckb.type.Script;
import org.nervos.mercury.fetch.data.entity.MercuryCell;
import org.nervos.mercury.fetch.data.entity.MercuryScript;
import org.nervos.mercury.fetch.data.entity.MercuryTransaction;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;
import org.nervos.mercury.fetch.data.mapper.MercuryCellMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryIndexerCellMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryScriptMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryTransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
public class QueryService {

  @Autowired private MercuryIndexerCellMapper indexerCellMapper;

  @Autowired private MercuryScriptMapper scriptMapper;

  @Autowired private MercuryTransactionMapper transactionMapper;

  @Autowired private MercuryCellMapper mercuryCellMapper;

  public void queryTxs() {
    StopWatch sw = new StopWatch();
    sw.start();

    List<MercuryScript> mercuryScripts =
        scriptMapper.selectByScriptArgs(
            HexBytes.newHexBytes("0x1a4ff63598e43af9cd42324abb7657fa849c5bc3"));

    List<HexBytes> txHashes =
        indexerCellMapper.selectByLockHashes(
            mercuryScripts.stream()
                .map(
                    x ->
                        HexBytes.newHexBytes(
                            new Script(
                                    x.getScriptCodeHash().toHex(),
                                    x.getScriptArgs().toHex(),
                                    Script.TYPE)
                                .computeHash()))
                .collect(toList()));

    List<MercuryTransaction> mercuryTransactions = transactionMapper.selectByTxHashes(txHashes);

    List<MercuryCell> mercuryCells = mercuryCellMapper.selectByBlockTxHash(txHashes);
    List<MercuryCell> mercuryCells1 = mercuryCellMapper.selectByConsumedTxHash(txHashes);

    sw.split();
    System.out.println(sw.getSplitTime());
    sw.stop();
  }
}
