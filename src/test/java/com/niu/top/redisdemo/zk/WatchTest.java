package com.niu.top.redisdemo.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;

/**
 * @author hongwei
 * @date 2018/10/29 13:58
 */
public class WatchTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("localhost", 2181);
        String path = "/locks/app1";
        String currentPath = "0000000016";
        String beforePath = "";
        // zkClient.createPersistent(path+"/","lock");
        for (int i = 0; i < 10; i++) {
            zkClient.createEphemeralSequential(path + "/", "lock");
        }
        List<String> childrens = zkClient.getChildren(path);
        Collections.sort(childrens);
        childrens.forEach(System.out::println);
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String path, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String path) throws Exception {
                System.out.println("监听到节点删除！删除节点路径：" + path);
            }
        };
        if (currentPath != null && !currentPath.isEmpty()) {
            List<String> childrens2 = zkClient.getChildren(path);
            Collections.sort(childrens2);
            childrens2.forEach(System.out::println);
            if (currentPath.equals(childrens2.get(0).toString())) {
                System.out.println("获取到事务节点路径：" + currentPath);
            } else {
                int currentPathIndex = childrens2.indexOf(currentPath);
                System.out.println("监听上个节点位置，当前节点位置：" + currentPathIndex + "  上个节点路径：" + childrens2.get(currentPathIndex - 1));
                zkClient.subscribeDataChanges(path + "/" + childrens2.get(currentPathIndex - 1), iZkDataListener);
            }
        }
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
