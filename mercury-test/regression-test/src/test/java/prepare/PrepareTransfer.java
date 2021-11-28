package prepare;

import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.address.AddressTools;
import utils.BuildUtils;

import java.util.ArrayList;

public class PrepareTransfer {

    static public String testAddressesPath = "./src/test/java/test-addresses/PrepareTransferTestAddresses.json";

    @Test
    void generateTestAddresses() {
        try {
            BuildUtils.generateTestAddresses(testAddressesPath, 500);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    void prepareAssetInfo0() {

    }
}
