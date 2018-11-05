package com.niu.top.redisdemo.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hongwei
 * @date 2018/10/29 16:18
 */
public class Test {
    public static void main(String[] args) {
        ZkClient zkClient = ZKUtils.getZkClient();
        String path = "/locks/app";
        String currentPath = "0000000016";
        String beforePath = "";
        zkClient.createPersistent(path , "app_locks");
    }
}
