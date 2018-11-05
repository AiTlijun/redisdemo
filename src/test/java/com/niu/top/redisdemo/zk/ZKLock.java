package com.niu.top.redisdemo.zk;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author hongwei
 * @date 2018/10/29 15:13
 */
public class ZKLock implements Lock {

    private ZkClient zkClient = ZKUtils.getZkClient();
    private final String path = "/locks/app";
    private ThreadLocal<String> currentPath = new ThreadLocal<String>();
    private String beforePath;

    @Override
    public void lock() {
        tryLock();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        //System.out.println("当前线程：" + Thread.currentThread().getName());
        String newCurrentPath = zkClient.createEphemeralSequential(path + "/", "lock");
        newCurrentPath = newCurrentPath.substring(newCurrentPath.lastIndexOf("/") + 1);
        List<String> lockList = zkClient.getChildren(path);
        Collections.sort(lockList);
        //lockList.forEach(System.out::println);
        if (!lockList.isEmpty()) {
            if (newCurrentPath != null && newCurrentPath.length() > 0) {
                if (newCurrentPath.equals(lockList.get(0).toString())) {
                    System.out.println("当前节点排在最前面，可获取锁：" + newCurrentPath);
                    currentPath.set(newCurrentPath);
                    return true;
                } else {
                    String newbeforePath = lockList.get(lockList.indexOf(newCurrentPath) - 1);
                    // String prevNode = currentPath.substring(currentPath.lastIndexOf("/") + 1);
                    //beforePath = lockList.get(Collections.binarySearch(lockList, prevNode) - 1);
                    CountDownLatch countDownLatch = new CountDownLatch(1);

                    IZkDataListener iZkDataListener = new IZkDataListener() {
                        @Override
                        public void handleDataChange(String s, Object o) throws Exception {
                        }

                        @Override
                        public void handleDataDeleted(String s) throws Exception {
                            System.out.println("---" + s + "-------countDownLatch.countDown  可获取锁了--------");
                            countDownLatch.countDown();
                        }
                    };
                    System.out.println("当前节点：" + newCurrentPath + "   监听节点：" + path + "/" + newbeforePath);
                    zkClient.subscribeDataChanges(path + "/" + newbeforePath, iZkDataListener);
                    currentPath.set(newCurrentPath);
                    try {
                        //zkClient.exists(path);
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                System.out.println("创建节点失败！");
            }
        }

        return true;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        System.out.println("解锁，删除节点：path:" + path + "     currentPath:" + currentPath.get());
        zkClient.delete(path + "/" + currentPath.get());
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
