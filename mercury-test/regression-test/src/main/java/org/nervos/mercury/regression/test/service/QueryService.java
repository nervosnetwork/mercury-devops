package org.nervos.mercury.regression.test.service;

import org.apache.commons.lang3.time.StopWatch;
import org.nervos.ckb.type.OutPoint;
import org.nervos.ckb.type.Script;
import org.nervos.ckb.type.cell.CellInput;
import org.nervos.ckb.type.cell.CellOutput;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.mercury.fetch.data.entity.MercuryCell;
import org.nervos.mercury.fetch.data.entity.MercuryScript;
import org.nervos.mercury.fetch.data.entity.MercuryTransaction;
import org.nervos.mercury.fetch.data.entity.type.HexBytes;
import org.nervos.mercury.fetch.data.mapper.MercuryCellMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryIndexerCellMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryScriptMapper;
import org.nervos.mercury.fetch.data.mapper.MercuryTransactionMapper;
import org.nervos.mercury.model.resp.TransactionView;
import org.nervos.mercury.model.resp.TransactionWithRichStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

@Service
public class QueryService {

  @Autowired private MercuryIndexerCellMapper indexerCellMapper;

  @Autowired private MercuryScriptMapper scriptMapper;

  @Autowired private MercuryTransactionMapper transactionMapper;

  @Autowired private MercuryCellMapper mercuryCellMapper;

  public List<TransactionView> queryTxs() {
    StopWatch sw = new StopWatch();
    sw.start();

    List<MercuryScript> mercuryScripts =
        scriptMapper.selectByScriptArgs(
            HexBytes.newHexBytes("0xa3b8598e1d53e6c5e89e8acb6b4c34d3adb13f2b"));

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
                .collect(toList()),
            HexBytes.newHexBytes(
                "0xf21e7350fa9518ed3cbb008e0e8c941d7e01a12181931d5608aa366ee22228bd"));

    List<MercuryTransaction> mercuryTransactions = transactionMapper.selectByTxHashes(txHashes);

    sw.split();
    System.out.println("query tx: ".concat(String.valueOf(sw.getSplitTime())));
    List<MercuryCell> mercuryCells = mercuryCellMapper.selectByTxHash(txHashes);
    sw.split();
    System.out.println("query outputs: ".concat(String.valueOf(sw.getSplitTime())));

    List<MercuryCell> mercuryCells1 = mercuryCellMapper.selectByConsumedTxHash(txHashes);
    sw.split();
    System.out.println("query inputs: ".concat(String.valueOf(sw.getSplitTime())));

    Map<String, List<MercuryCell>> outputGroup =
        mercuryCells.stream().collect(groupingBy(x -> x.getTxHash().toHex()));
    Map<String, List<MercuryCell>> inputGroup =
        mercuryCells1.stream().collect(groupingBy(x -> x.getConsumedTxHash().toHex()));
    List<TransactionView> collect =
        mercuryTransactions.stream()
            .map(
                x -> {
                  TransactionView view = new TransactionView();
                  view.transactionView = new TransactionWithRichStatus();
                  view.transactionView.transaction = new Transaction();
                  view.transactionView.transaction.hash = x.getTxHash().toHex();
                  view.transactionView.transaction.witnesses =
                      Arrays.asList(x.getWitnesses().toHex().split(",").clone());
                  view.transactionView.transaction.version = x.getVersion().toString();
                  view.transactionView.transaction.inputs =
                      inputGroup.get(x.getTxHash().toHex()).stream()
                          .map(
                              y -> {
                                CellInput input = new CellInput();
                                input.previousOutput =
                                    new OutPoint(
                                        y.getTxHash().toHex(), y.getInputIndex().toString());
                                input.since = y.getSince().toHex();
                                return input;
                              })
                          .collect(toList());

                  view.transactionView.transaction.outputs =
                      outputGroup.get(x.getTxHash().toHex()).stream()
                          .map(
                              y -> {
                                CellOutput output = new CellOutput();
                                output.lock =
                                    new Script(
                                        y.getLockCodeHash().toHex(),
                                        y.getLockArgs().toHex(),
                                        y.getLockScriptType().toString());
                                output.type =
                                    new Script(
                                        y.getTypeCodeHash().toHex(),
                                        y.getTypeArgs().toHex(),
                                        y.getTypeScriptType().toString());
                                output.capacity = y.getCapacity().toString();

                                return output;
                              })
                          .collect(toList());

                  view.transactionView.transaction.outputsData =
                      outputGroup.get(x.getTxHash().toHex()).stream()
                          .map(y -> y.getData().toHex())
                          .collect(toList());

                  return view;
                })
            .collect(toList());

    sw.split();
    System.out.println(sw.getSplitTime());
    sw.stop();

    return collect;
  }
}
