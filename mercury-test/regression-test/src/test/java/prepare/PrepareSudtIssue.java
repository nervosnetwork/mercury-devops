package prepare;

import constant.UdtHolder;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

public class PrepareSudtIssue {

    public static String testAddressesPath =
        "./src/test/java/test-addresses/SudtIssueTestAddresses.json";
    public static ArrayList<AddressTools.AddressGenerateResult> testAddresses =
        BuildUtils.readTestAddresses(PrepareSudtIssue.testAddressesPath);

    private static int sudtChequeCellCapacity = 162;
    private static int secpCellCapacity = 61;
    private static int fee = 1;

    private static int holdByToRequiredCkb = secpCellCapacity + fee;
    private static int holdByFromRequiredCkb = sudtChequeCellCapacity + secpCellCapacity + fee;

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
     * testAddress[0: 1]
     */
    public static void prepareDefault() throws IOException {
        String testAddress = testAddresses.get(0).address;
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(holdByFromRequiredCkb));
    }

    /**
     * Prepare addresses for `testCase0`
     * use testAddress[2: 4]
     */
    public static void prepareTestCase0() throws IOException {
        String ownerAddress = testAddresses.get(2).address;
        BuildUtils.ensureCkbFreeBalance(
                ownerAddress,
                AmountUtils.ckbToShannon(sudtChequeCellCapacity));

        String payFeeAddress = testAddresses.get(4).address;
        BuildUtils.ensureAtLeastCkbFreeBalance(
                payFeeAddress,
                AmountUtils.ckbToShannon(secpCellCapacity + fee));
    }

    /**
     * Prepare addresses for `testPayFee0`
     * use testAddress[5: 7]
     */
    public static void prepareTestPayFee0() throws IOException {
        String ownerAddress = testAddresses.get(5).address;
        BuildUtils.ensureAtLeastCkbFreeBalance(
                ownerAddress,
                AmountUtils.ckbToShannon(holdByFromRequiredCkb));

        String payFeeAddress = testAddresses.get(7).address;
        BuildUtils.ensureAtLeastCkbFreeBalance(
                payFeeAddress,
                AmountUtils.ckbToShannon(secpCellCapacity + fee));
    }

    /**
     * Prepare addresses for `testTo0`
     * use testAddress[8: 9]
     */
    public static void prepareTestTo0() throws IOException {
        prepareForHoldByTo(8, 9);
    }

    /**
     * Prepare addresses for `testTo1`
     * use testAddress[10: 11]
     */
    public static void prepareTestTo1() throws IOException {
        prepareForHoldByTo(10, 11);
    }

    static void prepareForHoldByTo(int owner, int to) throws IOException {
        String ownerAddress = testAddresses.get(owner).address;

        BuildUtils.ensureAtLeastCkbFreeBalance(
                ownerAddress,
                AmountUtils.ckbToShannon(holdByToRequiredCkb + holdByFromRequiredCkb));
        BuildUtils.ensureUdtExisted(ownerAddress);

        BuildUtils.ensureAtLeastCkbFreeBalance(
                ownerAddress,
                AmountUtils.ckbToShannon(holdByToRequiredCkb));

        String toAddress = testAddresses.get(to).address;
        BuildUtils.ensureOneAcp(
                toAddress,
                UdtHolder.getUdtHashFromOwner(ownerAddress));
    }

    /**
     * Prepare addresses for `testChange0`
     * use testAddress[12: 14]
     */
    public static void prepareTestChange0() throws IOException {
        String testAddress = testAddresses.get(12).address;
        BuildUtils.ensureAtLeastCkbFreeBalance(
                testAddress,
                AmountUtils.ckbToShannon(holdByFromRequiredCkb));
    }
}
