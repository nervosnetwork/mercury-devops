package constant;

public class Config {
    private static boolean PREPARE_WHEN_TEST = true;
    private static boolean SEND_TRANSACTION_WHEN_TEST = true;

    public static boolean isPrepareWhenTest() {
        return PREPARE_WHEN_TEST;
    }
    public static boolean isSendTransactionWhenTest() {
        return SEND_TRANSACTION_WHEN_TEST;
    }
}
