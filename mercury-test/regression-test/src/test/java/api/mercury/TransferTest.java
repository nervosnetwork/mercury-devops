package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.Config;
import constant.UdtHolder;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.mercury.model.TransferPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.*;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.TransactionCompletionResponse;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import utils.BuildUtils;
import prepare.PrepareTransfer;
import utils.SignUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class TransferTest {
    Gson g = GsonFactory.newGson();
    RpcService rpc = new RpcService("http://127.0.0.1:8116");
    CaseWriter cw = new CaseWriter(RpcMethods.BUILD_TRANSFER_TRANSACTION, "./src/main/resources");

    void trySendTransaction(TransferPayloadBuilder builder) throws IOException{
        if(Config.isSendTransactionWhenTest()) {
            TransactionCompletionResponse s = ApiFactory.getApi().buildTransferTransaction(builder.build());
            Transaction tx = SignUtils.sign(s);
            String txHash = ApiFactory.getApi().sendTransaction(tx);
            BuildUtils.ensureBeOnChain(txHash);
        }
    }

    @Test
    /**
     * Params Test
     * - Param: `asset_info`
     * - Value: CKB
     * - Type: Positive Testing
     */
    void testAssetInfo0() throws IOException {
        if(Config.isPrepareWhenTest()) {
            PrepareTransfer.prepareAssetInfo0();
        }

        String testAddress = PrepareTransfer.testAddresses.get(0).address;
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newCkbAsset());
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
        builder.to(To.newTo(Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress1(), AmountUtils.ckbToShannon(100))), Mode.HoldByFrom));
        System.out.println(g.toJson(builder.build()));

        JsonElement response =
                rpc.post(
                        RpcMethods.BUILD_TRANSFER_TRANSACTION,
                        g.fromJson(g.toJson(builder.build()), JsonObject.class));
        cw.write("testAssetInfo0", builder.build(), response, null);

        trySendTransaction(builder);
    }


    @Test
    /**
     * Params Test
     * - Param: `asset_info`
     * - Value: UDT
     * - Type: Positive Testing
     *
     * Bug
     */
    void testAssetInfo1() throws IOException {
        if(Config.isPrepareWhenTest()) {
            PrepareTransfer.prepareAssetInfo1();
        }

        String testAddress = PrepareTransfer.testAddresses.get(0).address;
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress)), Source.Free));
        builder.to(To.newTo(Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress0(), BigInteger.valueOf(180))), Mode.HoldByTo));
        System.out.println(g.toJson(builder.build()));

        JsonElement response =
                rpc.post(
                        RpcMethods.BUILD_TRANSFER_TRANSACTION,
                        g.fromJson(g.toJson(builder.build()), JsonObject.class));
        cw.write("testAssetInfo1", builder.build(), response, null);

        trySendTransaction(builder);
    }
}
