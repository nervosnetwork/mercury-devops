package prepare;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

import java.io.IOException;
import java.util.ArrayList;

public class PrepareDao {
    public static String testAddressesPath =
            "./src/test/java/test-addresses/DaoTestAddresses.json";
    public static ArrayList<AddressTools.AddressGenerateResult> testAddresses =
            BuildUtils.readTestAddresses(PrepareDao.testAddressesPath);

    private static int chequeCellCapacity = 162;
    private static int secpCkbCellCapacity = 61;
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
     * Prepare addresses for `testFrom`
     * use testAddress[0]
     */
    public static void prepareDepositFrom() throws IOException {
        String testAddress = testAddresses.get(0).address;
        BuildUtils.ensureCkbFreeBalance(testAddress, AmountUtils.ckbToShannon(300));
    }
}
