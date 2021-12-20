package prepare;

import constant.UdtHolder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

public class PrepareAdjustAccount {

    public static String testAddressesPath =
        "./src/test/java/test-addresses/AdjustAccountTestAddresses.json";
    public static ArrayList<AddressTools.AddressGenerateResult> testAddresses =
        BuildUtils.readTestAddresses(PrepareAdjustAccount.testAddressesPath);

    private static int defaultACPCellCapacity = 143;
    private static int fee = 1;

    @Test
    void generateTestAddresses() {
        try {
            BuildUtils.generateTestAddresses(testAddressesPath, 500);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Prepare addresses for `testDefault`
     * testAddress[0]
     */
    public static void prepareDefault() throws IOException {
        String testAddress = testAddresses.get(0).address;
        BuildUtils.ensureAcpCount(testAddress, 0);
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(defaultACPCellCapacity + fee));
    }

    /**
     * Prepare addresses for `testCase0`
     * testAddress[1]
     */
    public static void prepareTestCase0() throws IOException {
        String testAddress = testAddresses.get(1).address;
        BuildUtils.ensureAcpCount(testAddress, 2);
    }

    /**
     * Prepare addresses for `testCase1`
     * testAddress[2]
     */
    public static void prepareTestCase1() throws IOException {
        String testAddress = testAddresses.get(2).address;
        BuildUtils.ensureAcpCount(testAddress, 1);
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(defaultACPCellCapacity + fee));
    }

    /**
     * Prepare addresses for `testAccountNumber0`
     * testAddress[3]
     */
    public static void prepareTestAccountNumber0() throws IOException {
        String testAddress = testAddresses.get(3).address;
        BuildUtils.ensureAcpCount(testAddress, 1);
    }

    /**
     * Prepare addresses for `testAccountNumber1`
     * testAddress[4]
     */
    public static void prepareTestAccountNumber1() throws IOException {
        String testAddress = testAddresses.get(4).address;
        BuildUtils.ensureAcpCount(testAddress, 0);
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(2 * defaultACPCellCapacity + fee));
    }

    /**
     * Prepare addresses for `testExtraCkb0`
     * testAddress[5]
     */
    public static void prepareTestExtraCkb0() throws IOException {
        String testAddress = testAddresses.get(5).address;
        BuildUtils.ensureAcpCount(testAddress, 0);
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(2 * (defaultACPCellCapacity + 2) + fee));
    }
}
