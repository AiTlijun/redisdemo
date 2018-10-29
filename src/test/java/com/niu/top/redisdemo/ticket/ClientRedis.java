package com.niu.top.redisdemo.ticket;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hongwei
 * @date 2018/10/24 10:10
 */
public class ClientRedis {

    public static void main(String[] args) throws InterruptedException {
        SaleTicket3 saleTicket = new SaleTicket3();
        Thread threadA = new Thread(saleTicket, "窗口A");
        Thread threadB = new Thread(saleTicket, "窗口B");
        Thread threadC = new Thread(saleTicket, "窗口C");
        Thread threadD = new Thread(saleTicket, "窗口D");
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        // Thread.currentThread().join();
    }
}

class SaleTicket3 implements Runnable {
    //public int ticketCount = 200;
    AtomicInteger ticketCount = new AtomicInteger(200);// 第一次方案
    AtomicInteger sellCount = new AtomicInteger(0);
    Lock lock = new ReentrantLock();

    @Override
    public void run() {
            while (ticketCount.get() > 0) {
                if (ticketCount.get() > 0) {
                   // lock.lock();
                    // System.out.println("before ticketCount" + ticketCount + "   sellCount:" + sellCount);
                    sellCount.getAndIncrement();
                    //System.out.println("after ticketCount" + ticketCount + "   sellCount:" + sellCount);
                    System.out.println(Thread.currentThread().getName() + "卖票窗口卖出一张票,剩余票数：" + ticketCount.getAndDecrement()
                            + " 卖出票数：" + sellCount);
                    //lock.unlock();
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
    }
}

