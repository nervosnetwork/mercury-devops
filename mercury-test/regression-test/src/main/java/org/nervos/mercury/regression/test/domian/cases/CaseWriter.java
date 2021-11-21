package org.nervos.mercury.regression.test.domian.cases;

import org.nervos.mercury.regression.test.domian.GsonFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CaseWriter {

  private String methodName;
  private String path;

  public CaseWriter(String methodName, String path) {
    this.methodName = methodName;
    this.path = path;
  }

  public <T, R> void write(String name, T t, R r) {
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
      CaseInfo<T, R> caseInfo = new CaseInfo<>(this.methodName, t, r);
      Files.write(path, GsonFactory.newGson().toJson(caseInfo).getBytes());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private static class CaseInfo<T, R> {
    public String methodName;
    public T req;
    public R resp;

    public CaseInfo(String methodName, T req, R resp) {
      this.methodName = methodName;
      this.req = req;
      this.resp = resp;
    }
  }
}
