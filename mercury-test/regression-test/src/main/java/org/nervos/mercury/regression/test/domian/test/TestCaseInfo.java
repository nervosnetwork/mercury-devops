package org.nervos.mercury.regression.test.domian.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import lombok.Getter;

@Getter
public class TestCaseInfo {

  private String name;
  private JsonElement reqParams;
  private JsonElement resp;
  private String methodName;
  private String msg;

  public TestCaseInfo(String name, JsonObject caseInfo, String methodName) {
    this.name = name;
    this.reqParams = caseInfo.get("req");
    this.resp = caseInfo.get("resp").isJsonNull() ? null : caseInfo.getAsJsonObject("resp");
    this.methodName = methodName;
    this.msg =
        caseInfo.get("msg").isJsonNull() ? null : caseInfo.getAsJsonPrimitive("msg").getAsString();
  }
}
