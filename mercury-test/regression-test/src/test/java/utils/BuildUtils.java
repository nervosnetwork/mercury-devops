package utils;

import com.google.gson.JsonArray;
import constant.AddressWithKeyHolder;
import constant.ApiFactory;
import constant.ScriptMeta;
import constant.UdtHolder;
import org.junit.jupiter.api.Test;
import org.nervos.ckb.address.Network;
import org.nervos.ckb.crypto.secp256k1.ECKeyPair;
import org.nervos.ckb.type.Header;
import org.nervos.ckb.type.Script;
import org.nervos.ckb.type.transaction.Transaction;
import org.nervos.ckb.type.transaction.TransactionWithStatus;
import org.nervos.ckb.utils.AmountUtils;
import org.nervos.ckb.utils.Numeric;
import org.nervos.ckb.utils.address.AddressTools;
import org.nervos.indexer.model.ScriptType;
import org.nervos.indexer.model.SearchKeyBuilder;
import org.nervos.indexer.model.resp.CellResponse;
import org.nervos.indexer.model.resp.CellsResponse;
import org.nervos.indexer.model.resp.TipResponse;
import org.nervos.mercury.model.*;
import org.nervos.mercury.model.common.AssetInfo;
import org.nervos.mercury.model.common.AssetType;
import org.nervos.mercury.model.req.*;
import org.nervos.mercury.model.req.item.ItemFactory;
import org.nervos.mercury.model.resp.*;
import org.nervos.mercury.regression.test.domian.GsonFactory;
import prepare.PrepareTransfer;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class BuildUtils {
    public static int newSudtRequiredCkb = 224;

    public static void transferFreeCkb(String fromAddress, String toAddress, BigInteger amount) throws IOException{
        System.out.println("from: " + fromAddress + ", to: " + toAddress + ", amount: " + amount + " free CKB");
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newCkbAsset());
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(fromAddress)), Source.Free));
        builder.to(To.newTo(Arrays.asList(new ToInfo(toAddress,amount)),Mode.HoldByFrom));
        // testAddress1 is used to pay fee if necessary.
        builder.payFee(AddressWithKeyHolder.testAddress1());
        TransactionCompletionResponse s = ApiFactory.getApi().buildTransferTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void getFreeCkb(String address, BigInteger amount) throws IOException {
        BuildUtils.transferFreeCkb(AddressWithKeyHolder.testAddress0(), address, amount);
    }

    public static void transferFreeUdt(String fromAddress, String toAddress, BigInteger amount) throws IOException {
        System.out.println("from: " + fromAddress + ", to: " + toAddress + ", amount: " + amount + " free UDT");
        BuildUtils.ensureOneAcp(toAddress);
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(fromAddress)), Source.Free));
        builder.to(To.newTo(Arrays.asList(new ToInfo(toAddress,amount)),Mode.HoldByTo));
        // testAddress1 is used to pay fee if necessary.
        builder.payFee(AddressWithKeyHolder.testAddress1());
        TransactionCompletionResponse s = ApiFactory.getApi().buildTransferTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void getFreeUdt(String address, BigInteger amount) throws IOException {
        BuildUtils.transferFreeUdt(AddressWithKeyHolder.testAddress0(), address, amount);
    }

    public static void generateClaimableUdt(String fromAddress, String toAddress, BigInteger amount) throws IOException {
        System.out.println("from: " + fromAddress + ", to: " + toAddress + ", amount: " + amount + " generate claimable UDT");
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(fromAddress)), Source.Free));
        builder.to(To.newTo(Arrays.asList(new ToInfo(toAddress,amount)),Mode.HoldByFrom));
        // testAddress1 is used to pay fee if necessary.
        builder.payFee(AddressWithKeyHolder.testAddress1());
        TransactionCompletionResponse s = ApiFactory.getApi().buildTransferTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void getClaimableUdt(String address, BigInteger amount) throws IOException {
        BuildUtils.generateClaimableUdt(AddressWithKeyHolder.testAddress0(), address, amount);
    }

    public static void consumeClaimableUdt(String fromAddress, String toAddress, BigInteger amount) throws IOException {
        BuildUtils.ensureOneAcp(toAddress);
        System.out.println("from: " + fromAddress + ", to: " + toAddress + ", amount: " + amount + " consumed claimable UDT");
        TransferPayloadBuilder builder = new TransferPayloadBuilder();
        builder.assetInfo(AssetInfo.newUdtAsset(UdtHolder.UDT_HASH));
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newIdentityItemByAddress(fromAddress)), Source.Claimable));
        builder.to(To.newTo(Arrays.asList(new ToInfo(toAddress,amount)),Mode.HoldByTo));
        // testAddress1 is used to pay fee if necessary.
        builder.payFee(AddressWithKeyHolder.testAddress1());
        TransactionCompletionResponse s = ApiFactory.getApi().buildTransferTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void consumeClaimableUdt(String address, BigInteger amount) throws IOException {
        BuildUtils.consumeClaimableUdt(address, AddressWithKeyHolder.testAddress0(), amount);
    }

    public static void adjustAcpNumber(String fromAddress, String target_address, int amount, String udtHash) throws IOException {
        System.out.println("address " + target_address + " create " + amount + " acp cell, acp address: " + AddressTools.generateAcpAddress(target_address));
        AdjustAccountPayloadBuilder builder = new AdjustAccountPayloadBuilder();
        builder.item(ItemFactory.newIdentityItemByAddress(target_address));
        builder.assetInfo(AssetInfo.newUdtAsset(udtHash));
        builder.addFrom(ItemFactory.newIdentityItemByAddress(fromAddress));
        builder.accountNumber(BigInteger.valueOf(amount));
        TransactionCompletionResponse s = ApiFactory.getApi().buildAdjustAccountTransaction(builder.build());
        if (Objects.isNull(s)) {
            System.out.println("address " + target_address + " already has " + amount + " acp cell");
            return;
        }
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void ensureOneAcp(String address) throws IOException {
        BuildUtils.adjustAcpNumber(AddressWithKeyHolder.testAddress0(), address, 1, UdtHolder.UDT_HASH);
    }

    public static void ensureOneAcp(String address, String udtHash) throws IOException {
        BuildUtils.adjustAcpNumber(AddressWithKeyHolder.testAddress0(), address, 1, udtHash);
    }

    public static void ensureAcpCount(String address, int count) throws IOException {
        BuildUtils.adjustAcpNumber(AddressWithKeyHolder.testAddress0(), address, count, UdtHolder.UDT_HASH);
    }

    public static void ensureBeOnChain(String txHash) throws IOException {
        BigInteger committedBlockNumber = null;
        int index = 0;
        System.out.println("try to ensure transaction " + txHash + " to be on chain");
        while (true) {
            if(index >= 900) {
                throw new IOException("30 minutes failed to be on chain, " + txHash + " may lost");
            }
            try {
                String status = "committed";

                index++;
                BigInteger rpcTipBlockNumber = BuildUtils.getRpcTipBlockNumber();
                BigInteger mercuryTipBlockNumber = BuildUtils.getMercuryTipBlockNumber();
                if (committedBlockNumber == null) {
                    TransactionWithStatus txWithStatus = ApiFactory.getApi().getTransaction(txHash);
                    status = txWithStatus.txStatus.status;
                    if (status.equals("committed")) {
                        committedBlockNumber = rpcTipBlockNumber;
                    }
                } else if (mercuryTipBlockNumber.compareTo(committedBlockNumber) != -1) {
                    break;
                }
                System.out.println(txHash + " rpc status: " + status + ", tip block number: rpc-" + rpcTipBlockNumber + ", mercury-" + mercuryTipBlockNumber);
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    public static void generateTestAddresses(String testAddressesPath, int number) throws Exception {
        Path path = Paths.get(testAddressesPath);
        ArrayList<AddressTools.AddressGenerateResult> addresses = new ArrayList();
        for(int i = 0; i < number; i++) {
            AddressTools.AddressGenerateResult testAddress = AddressTools.generateAddress(Network.TESTNET);
            addresses.add(testAddress);
        }
        Files.write(path, GsonFactory.newGson().toJson(addresses).getBytes());
    }

    public static ArrayList<AddressTools.AddressGenerateResult> readTestAddresses(String testAddressesPath) {
        Path path = Paths.get(testAddressesPath);
        ArrayList<AddressTools.AddressGenerateResult> testAddresses = new ArrayList();
        try {
            String json_data = new String(Files.readAllBytes(path));

            JsonArray array = GsonFactory.newGson().fromJson(json_data, JsonArray.class);
            for (int i = 0; i < array.size(); i++) {
                AddressTools.AddressGenerateResult testAddress = new AddressTools.AddressGenerateResult();
                testAddress.address = array.get(i).getAsJsonObject().get("address").toString().replaceAll("\"", "");
                testAddress.lockArgs = array.get(i).getAsJsonObject().get("lockArgs").toString().replaceAll("\"", "");
                testAddress.privateKey = array.get(i).getAsJsonObject().get("privateKey").toString().replaceAll("\"", "");

                AddressWithKeyHolder.put(testAddress.address, testAddress.privateKey);
                AddressWithKeyHolder.putPubKey(testAddress.address, "0x" + testAddress.lockArgs);
                testAddresses.add(testAddress);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

        return testAddresses;
    }

    public static List<BalanceResponse> getBalance(String address) throws IOException {
        GetBalanceResponse balance;
        GetBalancePayloadBuilder builder = new GetBalancePayloadBuilder();
        builder.item(ItemFactory.newIdentityItemByAddress(address));
        balance = ApiFactory.getApi().getBalance(builder.build());
        return balance.balances;
    }

    public static void ensureAtLeastCkbFreeBalance(String address, BigInteger freeAmount) throws IOException {
        BigInteger freeBalance = getCkbFreeBalance(address);
        System.out.println("Address " + address + " has " + freeBalance + " free CKB");

        freeBalance = legalizeCkbTweaking(address, freeAmount, freeBalance);

        supplyCkbFreeBalance(address, freeAmount, freeBalance);
    }

    public static void ensureCkbFreeBalance(String address, BigInteger freeAmount) throws IOException {
        BigInteger freeBalance = getCkbFreeBalance(address);
        System.out.println("Address " + address + " has " + freeBalance + " free CKB");

        freeBalance = legalizeCkbTweaking(address, freeAmount, freeBalance);

        supplyCkbFreeBalance(address, freeAmount, freeBalance);
        reduceCkbFreeBalance(address, freeAmount, freeBalance);
    }

    public static BigInteger legalizeCkbTweaking(String address, BigInteger freeAmount, BigInteger freeBalance) throws IOException {
        BigInteger transferAmount = freeBalance.subtract(freeAmount).abs();
        // if transfer amount less than minimum CKB, then clear CKB first, then send freeAmount again
        if(transferAmount.compareTo(BigInteger.ZERO) != 0 && transferAmount.compareTo(AmountUtils.ckbToShannon(61))  == -1) {
            System.out.println("Address " + address + " clear " + freeBalance + " CKB, because transfer amount " + transferAmount + " less than minimum transferable CKB");
            BuildUtils.transferFreeCkb(address, AddressWithKeyHolder.testAddress0(), freeBalance);
            return BigInteger.ZERO;
        }
        return freeBalance;
    }

    public static void supplyCkbFreeBalance(String address, BigInteger freeAmount, BigInteger freeBalance) throws IOException {
        BigInteger transferAmount = freeBalance.subtract(freeAmount).abs();

        if(freeBalance.compareTo(freeAmount) == -1) {
            System.out.println("Address " + address + " add " + transferAmount + " CKB");
            BuildUtils.transferFreeCkb(AddressWithKeyHolder.testAddress0(), address, transferAmount);
        }
    }

    public static void reduceCkbFreeBalance(String address, BigInteger freeAmount, BigInteger freeBalance) throws IOException {
        BigInteger transferAmount = freeBalance.subtract(freeAmount).abs();

        if(freeBalance.compareTo(freeAmount) == 1) {
            System.out.println("Address " + address + " reduce " + transferAmount + " CKB");
            BuildUtils.transferFreeCkb(address, AddressWithKeyHolder.testAddress0(), transferAmount);
        }
    }

    public static BigInteger getCkbFreeBalance(String address) throws IOException {
        List<BalanceResponse> balances = BuildUtils.getBalance(address);
        BigInteger freeBalance = BigInteger.ZERO;
        for (BalanceResponse balance: balances) {
            if(balance.assetInfo.assetType.equals(AssetType.CKB)) {
                freeBalance = freeBalance.add(balance.free);
            }
        }

        return freeBalance;
    }

    @Test
    void testEnsureCkbFreeBalance() {
        try {
            ArrayList<AddressTools.AddressGenerateResult> as = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);
            BuildUtils.ensureCkbFreeBalance(as.get(0).address, AmountUtils.ckbToShannon(200));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void ensureUdtFreeBalance(String address, BigInteger freeAmount) throws IOException {
        List<BalanceResponse> balances = BuildUtils.getBalance(address);
        BigInteger freeBalance = BigInteger.ZERO;
        for (BalanceResponse balance: balances) {
            if(balance.assetInfo.assetType.equals(AssetType.UDT) && balance.assetInfo.udtHash.equals(UdtHolder.UDT_HASH)) {
                freeBalance = freeBalance.add(balance.free);
            }
        }
        System.out.println("Address " + address + " has " + freeBalance + " free UDT");
        if(freeBalance.compareTo(freeAmount) == 1) {
            BigInteger transferAmount = freeBalance.subtract(freeAmount);
            System.out.println("Address " + address + " reduce " + transferAmount + " UDT");
            BuildUtils.transferFreeUdt(address, AddressWithKeyHolder.testAddress0(), transferAmount);
        } else if(freeBalance.compareTo(freeAmount) == -1) {
            BigInteger transferAmount = freeAmount.subtract(freeBalance);
            System.out.println("Address " + address + " add " + transferAmount + " UDT");
            BuildUtils.transferFreeUdt(AddressWithKeyHolder.testAddress0(), address, transferAmount);
        }
    }

    @Test
    void testEnsureUdtFreeBalance() {
        try {
            ArrayList<AddressTools.AddressGenerateResult> as = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);
            BuildUtils.ensureUdtFreeBalance(as.get(1).address, BigInteger.valueOf(200));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static void ensureUdtClaimableBalance(String address, BigInteger claimableAmount) throws IOException {
        List<BalanceResponse> balances = BuildUtils.getBalance(address);
        BigInteger claimableBalance = BigInteger.ZERO;
        for (BalanceResponse balance: balances) {
            if(balance.assetInfo.assetType.equals(AssetType.UDT) && balance.assetInfo.udtHash.equals(UdtHolder.UDT_HASH)) {
                claimableBalance = claimableBalance.add(balance.claimable);
            }
        }
        if(claimableBalance.compareTo(claimableAmount) != 0) {
            if(claimableBalance.compareTo(BigInteger.ZERO) != 0) {
                BuildUtils.consumeClaimableUdt(address, claimableBalance);
            }
            BuildUtils.getClaimableUdt(address, claimableAmount);
        }
    }

    @Test
    void testEnsureUdtClaimableBalance() {
        try {
            ArrayList<AddressTools.AddressGenerateResult> as = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);
            BuildUtils.ensureUdtClaimableBalance(as.get(3).address, BigInteger.valueOf(200));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void testGetMercuryTip() throws IOException {
        GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
        BlockInfoResponse blockInfo = ApiFactory.getApi().getBlockInfo(builder.build());
        System.out.println(blockInfo.blockNumber);
    }

    public static List<CellResponse> getDaoCells(String lockArgs) throws IOException {
        List<CellResponse> cells = new ArrayList();
        SearchKeyBuilder builder = new SearchKeyBuilder();
        builder.script(new Script(ScriptMeta.getSecpCodeHash(), lockArgs, Script.TYPE));
        builder.scriptType(ScriptType.lock);
        builder.filterScript(new Script(ScriptMeta.getDaoCodeHash(),"0x", Script.TYPE));
        String limit = "0x" + new BigInteger("10").toString(16);
        CellsResponse response = ApiFactory.getApi().getCells(builder.build(), "asc", limit, null);
        if (!Objects.isNull(response)) {
            cells = response.objects;
        }
        return cells;
    }

    public static void depositDao(String address, BigInteger amount) throws IOException {
        DaoDepositPayloadBuilder builder = new DaoDepositPayloadBuilder();
        builder.from(From.newFrom(Arrays.asList(ItemFactory.newAddressItem(AddressWithKeyHolder.testAddress0())), Source.Free));
        builder.to(address);
        builder.amount(amount);
        TransactionCompletionResponse s = ApiFactory.getApi().buildDaoDepositTransaction(builder.build());
        Transaction signTx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(signTx);
        System.out.println("tx_hash: " + txHash + ", deposit " + amount + " CKB to " + address);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static boolean ensureDaoDeposit(String address, BigInteger minDepositAmount) throws IOException {
        String lockArgs = AddressWithKeyHolder.getPubKeyByAddress(address);
        List<CellResponse> cells = BuildUtils.getDaoCells(lockArgs);
        BigInteger daoDepositBalance = BigInteger.ZERO;

        BigInteger tipEpochNumber = BuildUtils.getTipEpochNumber();
        boolean hasMaturityDaoDeposit = false;

        for(CellResponse cell: cells) {
            System.out.println(cell.outputData);
            if(cell.outputData.equals("0x0000000000000000")) {
                BigInteger capacity = new BigInteger(cell.output.capacity.substring(2), 16);
                daoDepositBalance = daoDepositBalance.add(capacity);

                BigInteger daoDepositEpochNumber = BuildUtils.getEpochNumber(cell.blockNumber);
                if(tipEpochNumber.subtract(daoDepositEpochNumber).compareTo(BigInteger.valueOf(5)) == 1) {
                    hasMaturityDaoDeposit = true;
                }
            }
        }
        if(minDepositAmount.compareTo(daoDepositBalance) == 1) {
            BigInteger depositAmount = minDepositAmount.subtract(daoDepositBalance);
            BuildUtils.depositDao(address, depositAmount);
        }

        return hasMaturityDaoDeposit;
    }

    @Test
    void testEnsureDaoDeposit() {
        try {
            ArrayList<AddressTools.AddressGenerateResult> as = BuildUtils.readTestAddresses(PrepareTransfer.testAddressesPath);
            BuildUtils.ensureDaoDeposit(as.get(4).address, AmountUtils.ckbToShannon(200));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static void withdrawDao(String address) throws IOException {
        DaoWithdrawPayloadBuilder builder = new DaoWithdrawPayloadBuilder();
        builder.from(ItemFactory.newIdentityItemByAddress(address));
        builder.payFee(AddressWithKeyHolder.testAddress1());
        TransactionCompletionResponse s = ApiFactory.getApi().buildDaoWithdrawTransaction(builder.build());
        Transaction signTx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(signTx);
        System.out.println("tx_hash: " + txHash + ", " + address + " withdraw CKB");
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static boolean ensureDaoWithdraw(String address, BigInteger depositAmount) throws IOException {
        String lockArgs = AddressWithKeyHolder.getPubKeyByAddress(address);
        List<CellResponse> cells = BuildUtils.getDaoCells(lockArgs);

        BigInteger tipEpochNumber = BuildUtils.getTipEpochNumber();
        boolean hasMaturityDaoWithdraw = false;

        if(cells.isEmpty()) {
            BuildUtils.depositDao(address, depositAmount);
            System.out.println("No deposit cells, deposit " + depositAmount + " CKB");
        }
        for(CellResponse cell: cells) {
            // Exist withdraw cell.
            if(!cell.outputData.equals("0x0000000000000000")) {
                hasMaturityDaoWithdraw = true;
            }
        }

        // If do not have withdraw cell, try to withdraw maturity deposit cell.
        if(!hasMaturityDaoWithdraw) {
            for(CellResponse cell: cells) {
                System.out.println(cell.outputData);
                if(cell.outputData.equals("0x0000000000000000")) {
                    BigInteger daoDepositEpochNumber = BuildUtils.getEpochNumber(cell.blockNumber);
                    // if has maturity deposit cells, withdraw them
                    if(tipEpochNumber.subtract(daoDepositEpochNumber).compareTo(BigInteger.valueOf(5)) == 1) {
                        BuildUtils.withdrawDao(address);
                        break;
                    }
                }
            }
        }

        return hasMaturityDaoWithdraw;
    }

    @Test
    void testEnsureDaoWithdraw() {
        try {
            System.out.println(BuildUtils.ensureDaoWithdraw(AddressWithKeyHolder.testAddress2(), AmountUtils.ckbToShannon(200)));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public static BigInteger getRpcTipBlockNumber() throws IOException {
        Header header = ApiFactory.getApi().getTipHeader();
        return new BigInteger(header.number.substring(2), 16);
    }

    public static BigInteger getMercuryTipBlockNumber() throws IOException {
        GetBlockInfoPayloadBuilder builder = new GetBlockInfoPayloadBuilder();
        TipResponse tip = ApiFactory.getApi().getTip();
        return new BigInteger(tip.blockNumber.substring(2), 16);
    }

    public static BigInteger getTipEpochNumber() throws IOException {
        Header header = ApiFactory.getApi().getTipHeader();
        System.out.println(new BigInteger(header.number.substring(2), 16));
        String epoch = header.epoch;
        return new BigInteger(epoch.substring(epoch.length() - 6), 16);
    }

    @Test
    void testGetTipEpochNumber() {
        try {
            System.out.println(BuildUtils.getTipEpochNumber());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static BigInteger getEpochNumber(String blockNumber) throws IOException {
        String epoch = ApiFactory.getApi().getHeaderByNumber(blockNumber).epoch;
        return new BigInteger(epoch.substring(epoch.length() - 6), 16);
    }

    @Test
    void testGetEpochNumber() {
        try {
            System.out.println(BuildUtils.getEpochNumber("0x2b4f51"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void supplyCkb() throws IOException{
        BuildUtils.transferFreeCkb(AddressWithKeyHolder.testAddress2(), AddressWithKeyHolder.testAddress0(), AmountUtils.ckbToShannon(1000));
    }

    public static void issueSudt(String ownerAddress, String targetAddress, int amount) throws IOException {
        System.out.println("address " + ownerAddress + " issues " + amount + " sUdt to " + targetAddress);

        SudtIssuePayloadBuilder builder = new SudtIssuePayloadBuilder();
        builder.owner(ownerAddress);
        builder.to(
                To.newTo(
                    Arrays.asList(
                        new ToInfo(targetAddress, AmountUtils.ckbToShannon(amount))),
                    Mode.HoldByFrom));

        TransactionCompletionResponse s = ApiFactory.getApi().buildSudtIssueTransaction(builder.build());
        Transaction tx = SignUtils.sign(s);
        String txHash = ApiFactory.getApi().sendTransaction(tx);
        System.out.println("tx_hash: " + txHash);
        BuildUtils.ensureBeOnChain(txHash);
    }

    public static void ensureUdtExisted(String ownerAddress) throws IOException {
        System.out.println("try to ensure udt " + UdtHolder.getUdtHashFromOwner(ownerAddress) + ", owned by address " + ownerAddress + " to be on chain");

        BuildUtils.ensureAtLeastCkbFreeBalance(ownerAddress, AmountUtils.ckbToShannon(newSudtRequiredCkb));
        BuildUtils.issueSudt(ownerAddress, AddressWithKeyHolder.testAddress0(), 1);
    }
}
