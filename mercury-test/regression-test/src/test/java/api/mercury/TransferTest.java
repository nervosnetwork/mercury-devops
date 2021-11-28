package api.mercury;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import constant.AddressWithKeyHolder;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.mercury.model.TransferPayloadBuilder;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.req.*;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.regression.test.RpcMethods;
import org.nervos.mercury.regression.test.domian.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;
import utils.BuildUtils;
import prepare.PrepareTransfer;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class TransferTest {
    Gson g = GsonFactory.newGson();
    RpcService rpc = new RpcService("http://47.243.115.142:8116");

    CaseWriter cw = new CaseWriter(RpcMethods.BUILD_TRANSFER_TRANSACTION, "./src/main/resources");
    ArrayList<AddressTools.AddressGenerateResult> a = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);

    @Test
    /**
     * Params Test
     * - Param: `asset_info`
     * - Value: CKB
     * - Type: Positive Testing
     */
    void testAssetInfo0() {
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        try {
            // 1. create new address
            AddressTools.AddressGenerateResult testAddress = AddressTools.generateFullAddress(Network.TESTNET);
            BuildUtils.getFreeCkb(testAddress.address, BigInteger.ONE);

            // 2. test
            builder.assetInfo(AssetInfo.newCkbAsset());
            builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(testAddress.address)), Source.Free));
            builder.to(To.newTo(Arrays.asList(new ToInfo(AddressWithKeyHolder.testAddress1(), AmountUtils.ckbToShannon(100))), Mode.HoldByFrom));

            System.out.println(g.toJson(builder.build()));

            JsonElement response =
                    rpc.post(
                            RpcMethods.BUILD_TRANSFER_TRANSACTION,
                            g.fromJson(g.toJson(builder.build()), JsonObject.class));

            cw.write("testAssetInfo0", builder.build(), response, null);

        } catch (Exception e) {
            cw.write("testAssetInfo0", builder.build(), null, e.getMessage());
        }
    }


    /**
     * Negative Testing
     */
}
