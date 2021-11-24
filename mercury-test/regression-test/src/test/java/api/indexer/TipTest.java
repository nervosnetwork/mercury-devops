package api.indexer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.nervos.indexer.model.resp.TipResponse;

import java.io.IOException;

import constant.ApiFactory;

@Disabled
public class TipTest {
  @Test
  void getTip() {
    try {
      TipResponse tip = ApiFactory.getApi().getTip();
      System.out.println(tip.blockHash);
      System.out.println(tip.blockNumber);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
