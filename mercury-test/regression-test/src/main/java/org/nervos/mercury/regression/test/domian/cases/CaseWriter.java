package org.nervos.mercury.regression.test.domian.cases;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.nervos.mercury.model.common.ExtraFilter;
import org.nervos.mercury.model.req.item.Item;
import org.nervos.mercury.model.resp.Ownership;
import org.nervos.mercury.model.resp.RecordResponse;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaseWriter {

  private String methodName;
  private String path;
  Gson g =
      new GsonBuilder()
          .serializeNulls()
          .setPrettyPrinting()
          .registerTypeAdapter(Ownership.class, new Ownership.Deserializer())
          .registerTypeAdapter(RecordResponse.class, new RecordResponse())
          .registerTypeAdapter(ExtraFilter.class, new ExtraFilter())
          .registerTypeAdapter(Item.class, new Item.Serializer())
          .create();

  public CaseWriter(String methodName, String path) {
    this.methodName = methodName;
    this.path = path;
  }

  public <T, R> void write(String name, T t, R r, String msg) {
    Path path =
        Paths.get(
            this.path
                .concat("/case")
                .concat("/")
                .concat(this.methodName)
                .concat("/")
                .concat(name)
                .concat(".json"));

    try {
      CaseInfo<T, R> caseInfo = new CaseInfo<>(this.methodName, t, r, msg);
      Files.write(path, this.g.toJson(caseInfo).getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static class CaseInfo<T, R> {
    public String methodName;
    public T req;
    public R resp;
    public String msg;

    public CaseInfo(String methodName, T req, R resp, String msg) {
      this.methodName = methodName;
      this.req = req;
      this.resp = resp;
      this.msg = msg;
    }
  }
}
