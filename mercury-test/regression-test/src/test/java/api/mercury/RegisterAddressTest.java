package api.mercury;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.utils.address.AddressTools;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import constant.AddressWithKeyHolder;
import constant.ApiFactory;

/** @author zjh @Created Date: 2021/7/24 @Description: @Modify by: */
@Disabled
public class RegisterAddressTest {

  @Test
  void testRegisterAddress() {
    try {
      List<String> scriptHashes =
          ApiFactory.getApi()
              .registerAddresses(
                  Arrays.asList(
                      AddressTools.generateAcpAddress(AddressWithKeyHolder.testAddress0())));
      System.out.println(scriptHashes);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
