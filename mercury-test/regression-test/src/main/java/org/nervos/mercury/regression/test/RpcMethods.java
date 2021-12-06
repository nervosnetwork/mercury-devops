package org.nervos.mercury.regression.test;

import java.util.Arrays;
import java.util.List;

public interface RpcMethods {
  String GET_BALANCE = "get_balance";
  String BUILD_TRANSFER_TRANSACTION = "build_transfer_transaction";
  String BUILD_SIMPLE_TRANSFER_TRANSACTION = "build_simple_transfer_transaction";
  String BUILD_ADJUST_ACCOUNT_TRANSACTION = "build_adjust_account_transaction";
  String BUILD_DAO_DEPOSIT_TRANSACTION = "build_dao_deposit_transaction";
  String BUILD_DAO_WITHDRAW_TRANSACTION = "build_dao_withdraw_transaction";
  String BUILD_DAO_CLAIM_TRANSACTION = "build_dao_claim_transaction";
  String GET_TRANSACTION_INFO = "get_transaction_info";
  String GET_BLOCK_INFO = "get_block_info";
  String REGISTER_ADDRESS = "register_address";
  String QUERY_TRANSACTIONS = "query_transactions";
  String GET_SPENT_TRANSACTION = "get_spent_transaction";
  String GET_DB_INFO = "get_db_info";
  String GET_MERCURY_INFO = "get_mercury_info";
  String INDEXER_GET_TIP = "get_tip";
  String BUILD_SUDT_ISSUE_TRANSACTION = "build_sudt_issue_transaction";
  String INDEXER_GET_CELL_CAPACITY = "get_cells_capacity";

  List<String> RPC_METHODS =
      Arrays.asList(
          //          "build_dao_claim_transaction",
          //          "build_dao_withdraw_transaction",
          //          "register_address"
          "build_dao_deposit_transaction",
          "build_simple_transfer_transaction",
          "build_transfer_transaction",
          "get_balance",
          "get_block_info",
          "build_adjust_account_transaction",
          "get_spent_transaction",
          "query_transactions",
          "get_transaction_info",
          BUILD_SUDT_ISSUE_TRANSACTION);
}
