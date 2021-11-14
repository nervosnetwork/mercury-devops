package org.nervos.mercury.regression.test.db.route;

public class DbContextHolder {
  private static final ThreadLocal<String> CONTEXT_HOLDER = new ThreadLocal<String>();

  public static void checkoutRawDataSource() {
    CONTEXT_HOLDER.set(DataSourceType.RAW_DATA_SOURCE.name());
  }

  public static void checkoutTestDataSource() {
    CONTEXT_HOLDER.set(DataSourceType.TEST_DATA_SOURCE.name());
  }

  public static String getDBType() {
    return CONTEXT_HOLDER.get();
  }
}
