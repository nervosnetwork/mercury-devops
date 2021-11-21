package org.nervos.mercury.regression.test.domian.cases;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.nervos.mercury.regression.test.domian.test.TestCaseInfo;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CaseReader {

  private String methodName;

  public CaseReader(String methodName) {
    this.methodName = methodName;
  }

  public Map<String, TestCaseInfo> read() throws IOException {
    String s = "classpath*:case/";
    String e = "/*.json";

    ResourcePatternResolver loader = new PathMatchingResourcePatternResolver();
    List<Resource> resources = Arrays.asList(loader.getResources(s.concat(methodName).concat(e)));

    Gson gson = new Gson();
    Map<String, TestCaseInfo> testCaseInfos = new HashMap<>(resources.size());
    for (Resource re : resources) {
      InputStream in = re.getInputStream();
      byte[] datas = new byte[in.available()];
      in.read(datas);
      in.close();
      JsonObject jobj = gson.fromJson(new String(datas), JsonObject.class);

      TestCaseInfo testCaseInfo = new TestCaseInfo(re.getFilename(), jobj, this.methodName);
      testCaseInfos.put(this.methodName.concat(":").concat(re.getFilename()), testCaseInfo);
    }

    return testCaseInfos;
  }
}
