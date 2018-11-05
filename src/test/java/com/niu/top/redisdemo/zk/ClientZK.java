package com.niu.top.redisdemo.zk;

import com.niu.top.redisdemo.redis.ResidLock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * @author hongwei
 * @date 2018/10/24 10:10
 */
public class ClientZK {

    public static void main(String[] args) throws InterruptedException {
        SaleTicket saleTicket = new SaleTicket();
        Thread threadA = new Thread(saleTicket, "窗口A");
        Thread threadB = new Thread(saleTicket, "窗口B");
        Thread threadC = new Thread(saleTicket, "窗口C");
        Thread threadD = new Thread(saleTicket, "窗口D");
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        Thread.currentThread().join();
    }
}

class SaleTicket implements Runnable {
    private int ticketCount = 200;
    //AtomicInteger ticketCount = new AtomicInteger(200);// 第一次方案
    AtomicInteger sellCount = new AtomicInteger(0);
    Lock lock = new ZKLock();

    @Override
    public void run() {
        while (ticketCount > 0) {
            lock.lock();
            if (ticketCount > 0) {
                try {
                    try {
                        Thread.sleep(300);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // System.out.println("before ticketCount" + ticketCount + "   sellCount:" + sellCount);
                    sellCount.getAndIncrement();
                    //System.out.println("after ticketCount" + ticketCount + "   sellCount:" + sellCount);
                    System.out.println(Thread.currentThread().getName() + "卖票窗口卖出一张票,剩余票数：" + ticketCount--
                            + " 卖出票数：" + sellCount);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                } finally {
                    lock.unlock();
                }
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


