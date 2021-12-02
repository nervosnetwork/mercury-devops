package api.indexer;

import org.nervos.ckb.type.Script;

import org.nervos.mercury.regression.test.RpcMethods;

public class CellCapacityTest {
  String method = RpcMethods.INDEXER_GET_CELL_CAPACITY;
  Script lockScript = new Script(
      "0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8",
      "0x0c24d18f16e3c43272695e5db006a22cb9ddde51",
      Script.TYPE);

}
