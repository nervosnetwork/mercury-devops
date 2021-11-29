package constant;

public class ScriptMeta {
    // mainnet/testnet: 0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8
    private static String SECP_CODE_HASH = "0x9bd7e06f3ecf4be0f2fcd2188b23f1b9fcc88e5d4b65a8637b17723bbda3cce8";

    // mainnet: 0xd369597ff47f29fbc0d47d2e3775370d1250b85140c670e4718af712983a2354
    // testnet: 0x3419a1c09eb2567f6552ee7a8ecffd64155cffe0f1796e6e61ec088d740c1356
    private static String ACP_CODE_HASH = "0x3419a1c09eb2567f6552ee7a8ecffd64155cffe0f1796e6e61ec088d740c1356";

    // mainnet: 0xe4d4ecc6e5f9a059bf2f7a82cca292083aebc0c421566a52484fe2ec51a9fb0c
    // testnet: 0x60d5f39efce409c587cb9ea359cefdead650ca128f0bd9cb3855348f98c70d5b
    private static String CHEQUE_CODE_HASH = "0x60d5f39efce409c587cb9ea359cefdead650ca128f0bd9cb3855348f98c70d5b";

    // mainnet/testnet: 0x82d76d1b75fe2fd9a27dfbaa65a039221a380d76c926f378d3f81cf3e7e13f2e
    private static String DAO_CODE_HASH = "0x82d76d1b75fe2fd9a27dfbaa65a039221a380d76c926f378d3f81cf3e7e13f2e";

    // mainnet: 0x5e7a36a77e68eecc013dfa2fe6a23f3b6c344b04005808694ae6dd45eea4cfd5
    // testnet: 0xc5e5dcf215925f7ef4dfaf5f4b4f105bc321c02776d6e7d52a1db3fcd9d011a4
    private static String SUDT_CODE_HASH = "0xc5e5dcf215925f7ef4dfaf5f4b4f105bc321c02776d6e7d52a1db3fcd9d011a4";

    public static String getSecpCodeHash() {
        return SECP_CODE_HASH;
    }

    public static String getAcpCodeHash() {
        return ACP_CODE_HASH;
    }

    public static String getChequeCodeHash() {
        return CHEQUE_CODE_HASH;
    }

    public static String getDaoCodeHash() {
        return DAO_CODE_HASH;
    }

    public static String getSudtCodeHash() {
        return SUDT_CODE_HASH;
    }
}
