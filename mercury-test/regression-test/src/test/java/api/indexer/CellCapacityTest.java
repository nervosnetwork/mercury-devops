package api.indexer;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.Script;
import org.nervos.indexer.model.ScriptType;
import org.nervos.indexer.model.SearchKeyBuilder;
import org.nervos.mercury.regression.test.RpcMethods;

public class CellCapacityTest {
  String method = RpcMethods.INDEXER_GET_CELL_CAPACITY;
  // You can find the following lock script and type script in the output#1
  // of tx 0xeb7ebb26f7c0f7822925001bf2eaea0b00b8f742bde38c30dc3e43efb6947b51
  Script lockScript = new Script(
      "0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8",
      "0x0c24d18f16e3c43272695e5db006a22cb9ddde51",
      Script.TYPE);

  Script typeScript = new Script(
      "0xc5e5dcf215925f7ef4dfaf5f4b4f105bc321c02776d6e7d52a1db3fcd9d011a4",
      "0x7c7f0ee1d582c385342367792946cff3767fe02f26fd7f07dba23ae3c65b28bc",
      Script.TYPE);

  /**
   * Params Test
   * - Param: `script`
   * - Value: lock script
   * - Type: Positive Testing
   */
  @Test
  void testLockScriptCapacity() {
    SearchKeyBuilder builder = new SearchKeyBuilder();
    builder.script(lockScript);
    builder.scriptType(ScriptType.lock);

    RpcSender.sendRequestAndWriteCase(method, builder.build());
  }

  /**
   * Params Test
   * - Param: `script`
   * - Value: type script
   * - Type: Positive Testing
   */
  @Test
  void testTypeScriptCapacity() {
    SearchKeyBuilder builder = new SearchKeyBuilder();
    builder.script(typeScript);
    builder.scriptType(ScriptType.type);

    RpcSender.sendRequestAndWriteCase(method, builder.build());
  }

  /**
   * Params Test
   * - Param: `script`, `filter.script
   * - Value: lock script, type script
   * - Type: Positive Testing
   */
  @Test
  void testLockScriptAndTypeScriptCapacity() {
    SearchKeyBuilder builder = new SearchKeyBuilder();
    builder.script(lockScript);
    builder.scriptType(ScriptType.lock);
    builder.filterScript(typeScript);

    RpcSender.sendRequestAndWriteCase(method, builder.build());
  }

  /**
   * Params Test
   * - Param: `script`, `filter.script
   * - Value: type script, lock script
   * - Type: Positive Testing
   */
  @Test
  void testTypeScriptAndLockScriptCapacity() {
    SearchKeyBuilder builder = new SearchKeyBuilder();
    builder.script(typeScript);
    builder.scriptType(ScriptType.type);
    builder.filterScript(lockScript);

    RpcSender.sendRequestAndWriteCase(method, builder.build());
  }



}
