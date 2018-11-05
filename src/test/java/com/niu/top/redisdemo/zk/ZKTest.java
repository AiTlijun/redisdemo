package com.niu.top.redisdemo.zk;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.ZooKeeper;

import java.util.UUID;

/**
 * @author hongwei
 * @date 2018/10/29 11:24
 */
public class ZKTest {

    public static void main(String[] args) {
        ZkClient zkClient = new ZkClient("localhost", 2181);
        zkClient.createPersistent("/locks");
        String uuid = UUID.randomUUID().toString();
        String newLock = "lock_" + uuid;
        String newPath = "/locks/" + newLock;
        zkClient.createEphemeralSequential(newPath, "aaa");
        //zkClient.delete("/lock");
        ZooKeeper zooKeeper = null;
        /*try {
            zooKeeper = new ZooKeeper("localhost:2181",100,null);
           //zooKeeper.create("/root", "root data".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            //zooKeeper.setData("/root", "hello".getBytes(), -1);
            //zooKeeper.delete("/locks",-1);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }*/




    }
}
