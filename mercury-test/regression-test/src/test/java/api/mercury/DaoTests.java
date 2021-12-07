package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constant.ApiFactory;
import constant.Config;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.RpcMethods;
import org.nervos.mercury.model.DaoDepositPayloadBuilder;
import org.nervos.mercury.model.DaoWithdrawPayloadBuilder;
import org.nervos.mercury.model.req.From;
import org.nervos.mercury.model.req.Source;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import prepare.PrepareDao;
import utils.BuildUtils;
import utils.SignUtils;

import java.io.IOException;
import java.util.Arrays;

public class DaoTests {
    Gson g = GsonFactory.newGson();
    RpcService rpc = new RpcService("http://127.0.0.1:8116");
    CaseWriter cw1 = new CaseWriter(RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION, "./src/main/resources");
    CaseWriter cw2 = new CaseWriter(RpcMethods.BUILD_DAO_WITHDRAW_TRANSACTION, "./src/main/resources");

    @Test
    /**
     * Params Test
     * - Param: `deposit from`
     * - Value: address + free（CKB）
     * - Type: Positive Testing
     */
    void testDepositFrom() throws IOException {
        PrepareDao.prepareDepositFrom();
        String testAddress = PrepareDao.testAddresses.get(0).address;
        DaoDepositPayloadBuilder builder = new DaoDepositPayloadBuilder();
        builder.from(
                From.newFrom(
                        Arrays.asList(ItemFactory.newAddressItem(testAddress)),
                        Source.Free));
        builder.amount(AmountUtils.ckbToShannon(200));

        System.out.println(g.toJson(builder));

        JsonElement response =
                rpc.post(
                        RpcMethods.BUILD_DAO_DEPOSIT_TRANSACTION,
                        g.fromJson(g.toJson(builder.build()), JsonObject.class));

        cw1.write("testDepositFrom", builder.build(), response, null);

        if(Config.isSendTransactionWhenTest()) {
            TransactionCompletionResponse s = ApiFactory.getApi().buildDaoDepositTransaction(builder.build());
            Transaction tx = SignUtils.sign(s);
            String txHash = ApiFactory.getApi().sendTransaction(tx);
            BuildUtils.ensureBeOnChain(txHash);
        }
    }

    @Test
    /**
     * Params Test
     * - Param: `withdraw from`
     * - Value: address + free（CKB）
     * - Type: Positive Testing
     */
    void testWithdrawFrom() throws IOException {
        String testAddress = PrepareDao.testAddresses.get(0).address;
        DaoWithdrawPayloadBuilder builder = new DaoWithdrawPayloadBuilder();
        builder.from(ItemFactory.newAddressItem(testAddress));

        System.out.println(g.toJson(builder));

        JsonElement response =
                rpc.post(
                        RpcMethods.BUILD_DAO_WITHDRAW_TRANSACTION,
                        g.fromJson(g.toJson(builder.build()), JsonObject.class));

        cw2.write("testWithdrawFrom", builder.build(), response, null);

        if(Config.isSendTransactionWhenTest()) {
            TransactionCompletionResponse s = ApiFactory.getApi().buildDaoWithdrawTransaction(builder.build());
            Transaction tx = SignUtils.sign(s);
            String txHash = ApiFactory.getApi().sendTransaction(tx);
            BuildUtils.ensureBeOnChain(txHash);
        }
    }
}
