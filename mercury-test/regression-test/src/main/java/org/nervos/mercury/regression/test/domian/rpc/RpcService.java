package org.nervos.mercury.regression.test.domian.rpc;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.nervos.ckb.service.RpcResponse;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/** Copyright © 2019 Nervos Foundation. All rights reserved. */
public class RpcService {

  private static final MediaType JSON_MEDIA_TYPE = MediaType.get("application/json; charset=utf-8");
  private static AtomicLong nextId = new AtomicLong(0);

  private OkHttpClient client;
  private String url;
  private Gson gson = new Gson();

  public RpcService(String rpcUrl) {
    url = rpcUrl;
    client =
        new OkHttpClient.Builder()
            .connectTimeout(180, TimeUnit.SECONDS)
            .readTimeout(180, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .build();
  }

  public JsonElement post(String method, JsonElement params) throws IOException {
    RequestBody body =
        RequestBody.create(JSON_MEDIA_TYPE, this.createRequestParam(method, params).toString());
    Request request = new Request.Builder().url(url).post(body).build();
    Response response = client.newCall(request).execute();
    if (response.isSuccessful()) {
      String responseBody = Objects.requireNonNull(response.body()).string();
      RpcResponse rpcResponse =
          gson.fromJson(responseBody, new TypeToken<RpcResponse>() {}.getType());

      if (rpcResponse.error != null) {
        throw new IOException(
            "RpcService method " + method + " error " + gson.toJson(rpcResponse.error));
      }

      JsonElement jsonElement =
          new JsonParser().parse(responseBody).getAsJsonObject().get("result");

      return jsonElement;
    } else {
      throw new IOException("RpcService method " + method + " error code " + response.code());
    }
  }

  private JsonObject createRequestParam(String method, JsonElement params) {
    JsonObject req = new JsonObject();
    req.addProperty("jsonrpc", "2.0");
    req.addProperty("method", method);
    req.addProperty("id", nextId.getAndIncrement());

    JsonArray arr = new JsonArray();
    arr.add(params);
    req.add("params", arr);

    return req;
  }

  static class RequestParams {
    String jsonrpc = "2.0";
    String method;
    List params;
    long id;

    public RequestParams(String method, List params) {
      this.method = method;
      this.params = params;
      this.id = nextId.getAndIncrement();
    }
  }
}
