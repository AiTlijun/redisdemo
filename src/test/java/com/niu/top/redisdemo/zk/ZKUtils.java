package com.niu.top.redisdemo.zk;

import org.I0Itec.zkclient.ZkClient;

/**
 * @author hongwei
 * @date 2018/11/2 9:53
 */
public class ZKUtils {

    private static ZkClient zkClient = null;

    public static  ZkClient getZkClient() {
        synchronized (new Object()) {
            if (zkClient == null) {
                zkClient = new ZkClient("localhost", 2181);
            }
        }
        return zkClient;
    }



}
