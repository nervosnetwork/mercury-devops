package constant;

import org.nervos.api.CkbApi;
import org.nervos.api.DefaultCkbApi;

public class ApiFactory {

  private static String MERCURY_URL = "http://47.243.115.142:8116";

  private static String CKB_URL = "https://mercury-testnet.ckbapp.dev/rpc";

  private static String Indexer_URL = "https://mercury-testnet.ckbapp.dev/indexer";

  private static CkbApi API = new DefaultCkbApi(CKB_URL, MERCURY_URL, Indexer_URL, false);

  public static CkbApi getApi() {
    return API;
  }
}
