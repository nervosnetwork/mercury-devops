package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.Config;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.ckb.utils.address.AddressGenerator;
import org.nervos.mercury.model.SudtIssuePayloadBuilder;
import org.nervos.mercury.model.req.*;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import prepare.PrepareSudtIssue;
import utils.BuildUtils;
import utils.SignUtils;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuildSudtIssueTransactionTest {
    Gson g = GsonFactory.newGson();
    RpcService rpc = new RpcService("http://127.0.0.1:8116");
    CaseWriter cw = new CaseWriter(RpcMethods.BUILD_SUDT_ISSUE_TRANSACTION, "./src/main/resources");

    private static String illegalAddress = "not ckb address";

    private Transaction trySendTransaction(
            SudtIssuePayloadBuilder builder,
            String filename
            ) throws IOException
    {
        System.out.println(g.toJson(builder.build()));

        JsonElement response =
            rpc.post(
                    RpcMethods.BUILD_SUDT_ISSUE_TRANSACTION,
                    g.fromJson(g.toJson(builder.build()), JsonObject.class));

        cw.write(filename, builder.build(), response, null);

        TransactionCompletionResponse s =
            ApiFactory.getApi().buildSudtIssueTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);

        if (!Config.isSendTransactionWhenTest()) {
            return tx;
        }

        String txHash = ApiFactory.getApi().sendTransaction(tx);
        BuildUtils.ensureBeOnChain(txHash);

        return tx;

    }

    private void assertInvalidTransaction(SudtIssuePayloadBuilder builder) {
        System.out.println(g.toJson(builder.build()));

        assertThrows(IOException.class, () -> {
            rpc.post(
                    RpcMethods.BUILD_SUDT_ISSUE_TRANSACTION,
                    g.fromJson(g.toJson(builder.build()), JsonObject.class));
        });
    }

    private static SudtIssuePayloadBuilder getDefaultBuilder(int owner, int to) {
        String ownerAddress = PrepareSudtIssue.testAddresses.get(owner).address;

        SudtIssuePayloadBuilder builder = new SudtIssuePayloadBuilder();
        builder.owner(ownerAddress);

        String toAddress = PrepareSudtIssue.testAddresses.get(to).address;
        builder.to(newDefaultTo(toAddress, Mode.HoldByFrom));

        return builder;
    }

    private static To newDefaultTo(String address, Mode mode) {
        return newDefaultTo(address, mode, 1);
    }

    private static To newDefaultTo(String address, Mode mode, int amount) {
        return To.newTo(
                Arrays.asList(
                    new ToInfo(address, AmountUtils.ckbToShannon(amount))), mode);
    }

    @Test
    /**
     * Params Test
     * - Param: `owner`
     * - Value: `Secp` address
     * - Param: `to`
     * - Value: `Secp` address + `HoldByFrom` + `amount` is 1
     * - Param: `pay_fee`
     * - Value: `null`
     * - Param: `change`
     * - Value: `null`
     * - Param: `fee_rate`
     * - Value: `null`
     * - Param: `since`
     * - Value: `null`
     * - Type: Positive Testing
     */
    void testDefault() throws IOException {
        PrepareSudtIssue.prepareDefault();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(0, 1);

        trySendTransaction(builder, "default");
    }

    @Test
    /**
     * Params Test
     * - Param: `owner`
     * - Value: Illegal address
     * - Type: Negative Testing
     */
    void testFailedOwner0() throws IOException {
        SudtIssuePayloadBuilder builder = getDefaultBuilder(0, 1);
        builder.owner(illegalAddress);

        assertInvalidTransaction(builder);
    }

    @Test
    /**
     * Case Test
     * - While owner's CKB is only enough for issuing sUDT, we need another
     *   address to pay fee.
     */
    void testCase0() throws IOException {
        PrepareSudtIssue.prepareTestCase0();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(2, 3);

        String payFeeAddress = PrepareSudtIssue.testAddresses.get(4).address;
        builder.payFee(ItemFactory.newIdentityItemByAddress(payFeeAddress));

        trySendTransaction(builder, "testCase0");
    }

    @Test
    /**
     * Params Test
     * - Param: `pay_fee`
     * - Value: `Secp` address
     * - Type: Positive Testing
     */
    void testPayFee0() throws IOException {
        PrepareSudtIssue.prepareTestPayFee0();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(5, 6);

        String payFeeAddress = PrepareSudtIssue.testAddresses.get(7).address;
        builder.payFee(ItemFactory.newIdentityItemByAddress(payFeeAddress));

        trySendTransaction(builder, "testPayFee0");
    }

    @Test
    /**
     * Params Test
     * - Param: `pay_fee`
     * - Value: Illegal address
     * - Type: Negative Testing
     */
    void testFailedPayFee0() throws IOException {
        PrepareSudtIssue.prepareDefault();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(0, 1);

        builder.payFee(ItemFactory.newAddressItem(illegalAddress));

        assertInvalidTransaction(builder);
    }

    @Test
    /**
     * Params Test
     * - Param: `to`
     * - Value: `Secp` address + `HoldByTo`
     * - Type: Positive Testing
     */
    void testTo0() throws IOException {
        PrepareSudtIssue.prepareTestTo0();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(8, 9);

        String toAddress = PrepareSudtIssue.testAddresses.get(9).address;
        builder.to(newDefaultTo(toAddress, Mode.HoldByTo));

        trySendTransaction(builder, "testTo0");
    }

    @Test
    /**
     * Params Test
     * - Param: `to`
     * - Value: `ACP` address + `HoldByTo`
     * - Type: Positive Testing
     */
    void testTo1() throws IOException {
        PrepareSudtIssue.prepareTestTo1();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(10, 11);

        String toSecpAddress = PrepareSudtIssue.testAddresses.get(11).address;
        String toAcpAddress = AddressTools.generateAcpAddress(toSecpAddress);
        builder.to(newDefaultTo(toAcpAddress, Mode.HoldByTo));

        trySendTransaction(builder, "testTo1");
    }

    @Test
    /**
     * Params Test
     * - Param: `to`
     * - Value: `amount` is 0
     * - Type: Negative Testing
     */
    void testFailedTo0() throws IOException {
        PrepareSudtIssue.prepareDefault();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(0, 1);

        String toAddress = PrepareSudtIssue.testAddresses.get(1).address;
        builder.to(newDefaultTo(toAddress, Mode.HoldByFrom, 0));

        assertInvalidTransaction(builder);
    }

    @Test
    /**
     * Params Test
     * - Param: `to`
     * - Value: Illegal address
     * - Type: Negative Testing
     */
    void testFailedTo1() throws IOException {
        PrepareSudtIssue.prepareDefault();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(0, 1);
        builder.to(newDefaultTo(illegalAddress, Mode.HoldByFrom));

        assertInvalidTransaction(builder);
    }

    @Test
    /**
     * Params Test
     * - Param: `change`
     * - Value: `Secp` address
     * - Type: Positive Testing
     */
    void testChange0() throws IOException {
        PrepareSudtIssue.prepareTestChange0();

        SudtIssuePayloadBuilder builder = getDefaultBuilder(12, 13);

        String changeAddress = PrepareSudtIssue.testAddresses.get(14).address;
        builder.change(changeAddress);

        trySendTransaction(builder, "testChange0");
    }
}
