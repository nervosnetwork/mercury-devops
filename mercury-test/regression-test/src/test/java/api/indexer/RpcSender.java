package api.indexer;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.nervos.mercury.GsonFactory;
import org.nervos.mercury.regression.test.domian.cases.CaseWriter;
import org.nervos.mercury.regression.test.domian.rpc.RpcService;

import java.io.IOException;

public class RpcSender {
    static Gson g = GsonFactory.newGson();
    static RpcService rpc = new RpcService("http://127.0.0.1:8116");

    private RpcSender() {}

    public static JsonElement sendRequest(String method, Object payload) {
        try {
            System.out.println("request: " + g.toJson(payload));
            JsonElement response = rpc.post(method, g.fromJson(g.toJson(payload), JsonObject.class));
            System.out.println("response: " + g.toJson(response));
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void sendRequestAndWriteCase(String method, Object payload) {
        JsonElement response = sendRequest(method, payload);
        if (response != null) {
            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
            StackTraceElement e = stacktrace[2];//maybe this number needs to be corrected
            String caseName = e.getMethodName();
            CaseWriter cw = new CaseWriter(method, "./src/main/resources");
            cw.write(caseName, null, response, null);
        } else {
          throw new IllegalStateException("fail to send request or write case");
        }
    }

}
