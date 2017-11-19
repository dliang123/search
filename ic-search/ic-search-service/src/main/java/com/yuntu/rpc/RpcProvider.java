package com.yuntu.rpc;

/**
 * Created by Administrator on 2017/11/8.
 */
public class RpcProvider {
    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, 1234);
    }
}
