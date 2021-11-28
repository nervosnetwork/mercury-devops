package prepare;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class PrepareTransfer {

    static public String testAddressesPath = "./src/test/java/test-addresses/PrepareTransferTestAddresses.json";
    static public ArrayList<AddressTools.AddressGenerateResult> testAddresses = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);

    @Test
    void generateTestAddresses() {
        try {
            BuildUtils.generateTestAddresses(testAddressesPath, 500);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Prepare addresses for `testAssetInfo0`
     * use testAddress[0]
     */
    public static void prepareAssetInfo0() throws IOException {
        String testAddress = testAddresses.get(0).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(200));
    }

    /**
     * Prepare addresses for `testAssetInfo1`
     * use testAddress[1]
     */
    public static void prepareAssetInfo1() throws IOException {
        String testAddress = testAddresses.get(1).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(200));
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
    }
}
