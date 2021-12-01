package prepare;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;

public class PrepareTransfer {

    static public String testAddressesPath = "./src/test/java/test-addresses/TransferTestAddresses.json";
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
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(300));
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
    }

    /**
     * Prepare addresses for `testFrom0`
     * use testAddress[2]
     */
    public static void prepareFrom0() throws IOException {
        String testAddress = testAddresses.get(2).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(150));
    }

    /**
     * Prepare addresses for `testFrom1`
     * use testAddress[3]
     */
    public static void prepareFrom1() throws IOException {
        String testAddress = testAddresses.get(3).address;
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(10));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(180));
    }

    /**
     * Prepare addresses for `testFrom2`
     * use testAddress[4]
     */
    public static void prepareFrom2() throws IOException {
        String testAddress = testAddresses.get(4).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(199));
    }

    /**
     * Prepare addresses for `testFrom3`
     * use testAddress[5]
     */
    public static void prepareFrom3() throws IOException {
        String testAddress = testAddresses.get(5).address;
        BuildUtils.ensureUdtClaimableBalance(testAddress, BigInteger.valueOf(88));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(450));
    }

    /**
     * Prepare addresses for `testFrom4`
     * use testAddress[6]
     */
    public static void prepareFrom4() throws IOException {
        String testAddress = testAddresses.get(6).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtClaimableBalance(testAddress, BigInteger.valueOf(88));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(450));
    }

    /**
     * Prepare addresses for `testFrom5`
     * use testAddress[7], testAddress[8]
     */
    public static void prepareFrom5() throws IOException {
        String testAddress1 = testAddresses.get(7).address;
        String testAddress2 = testAddresses.get(8).address;
        BuildUtils.ensureCkbFreeBalance(testAddress1, AmountUtils.ckbToShannon(150));
        BuildUtils.ensureCkbFreeBalance(testAddress2, AmountUtils.ckbToShannon(150));
    }

    /**
     * Prepare addresses for `testFrom6`
     * use testAddress[9], testAddress[10]
     */
    public static void prepareFrom6() throws IOException {
        String testAddress1 = testAddresses.get(9).address;
        String testAddress2 = testAddresses.get(10).address;
        BuildUtils.ensureCkbFreeBalance(testAddress1, AmountUtils.ckbToShannon(150));
        BuildUtils.ensureCkbFreeBalance(testAddress2, AmountUtils.ckbToShannon(150));
    }


    /**
     * Prepare addresses for `testFrom7`
     * use testAddress[11]
     */
    public static void prepareFrom7() throws IOException {
        String testAddress = testAddresses.get(11).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(170));
    }

    /**
     * Prepare addresses for `testTo0`
     * use testAddress[12]
     */
    public static void prepareTo0() throws IOException {
        String testAddress = testAddresses.get(12).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(190));
    }

    /**
     * Prepare addresses for `testTo1`
     * use testAddress[13]
     */
    public static void prepareTo1() throws IOException {
        String testAddress = testAddresses.get(13).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(250));
    }

    /**
     * Prepare addresses for `testTo2`
     * use testAddress[14]
     */
    public static void prepareTo2() throws IOException {
        String testAddress = testAddresses.get(14).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
    }

    /**
     * Prepare addresses for `testTo3`
     * use testAddress[15]
     */
    public static void prepareTo3() throws IOException {
        String testAddress = testAddresses.get(15).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
    }

    /**
     * Prepare addresses for `testPayFee0`
     * use testAddress[16], testAddress[17]
     */
    public static void preparePayFee0() throws IOException {
        String testAddress = testAddresses.get(16).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(190));
        String payFeeAddress = testAddresses.get(17).address;
        BuildUtils.ensureCkbFreeBalance(payFeeAddress, AmountUtils.ckbToShannon(63));
    }

    /**
     * Prepare addresses for `testPayFee1`
     * use testAddress[18], testAddress[19]
     */
    public static void preparePayFee1() throws IOException {
        String testAddress = testAddresses.get(18).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(250));
        String payFeeAddress = testAddresses.get(19).address;
        BuildUtils.ensureCkbFreeBalance(payFeeAddress, AmountUtils.ckbToShannon(63));
    }

    /**
     * Prepare addresses for `testChange0`
     * use testAddress[20]
     */
    public static void prepareChange0() throws IOException {
        String testAddress = testAddresses.get(20).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(190));
    }

    /**
     * Prepare addresses for `testChange1`
     * use testAddress[21]
     */
    public static void prepareChange1() throws IOException {
        String testAddress = testAddresses.get(21).address;
        BuildUtils.ensureOneAcp(testAddress);
        BuildUtils.ensureUdtFreeBalance(testAddress, BigInteger.valueOf(200));
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(250));
    }

    /**
     * Prepare addresses for `testFeeRate0` and `testFeeRate1`
     * use testAddress[22]
     */
    public static void prepareFeeRate0() throws IOException {
        String testAddress = testAddresses.get(22).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(190));
    }

    /**
     * Prepare addresses for `testCase0`
     * use testAddress[23]
     */
    public static void prepareCase0() throws IOException {
        String testAddress = testAddresses.get(23).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(200));
    }

    /**
     * Prepare addresses for `testCase0`
     * use testAddress[24]
     */
    public static void prepareCase1() throws IOException {
        String testAddress = testAddresses.get(24).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(200));
    }
}
