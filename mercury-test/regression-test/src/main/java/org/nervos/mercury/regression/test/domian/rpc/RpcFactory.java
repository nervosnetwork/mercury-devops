package org.nervos.mercury.regression.test.domian.rpc;

public class RpcFactory {

  private static RpcService rpcService;

  public static RpcService getRpc() {
    return rpcService;
  }

  public static void init(RpcService rpcService) {
    RpcFactory.rpcService = rpcService;
  }
}
