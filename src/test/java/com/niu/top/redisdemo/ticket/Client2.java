package com.niu.top.redisdemo.ticket;

import net.bytebuddy.description.modifier.SynchronizationState;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author hongwei
 * @date 2018/10/24 10:10
 */
public class Client2 {

    public static void main(String[] args) throws InterruptedException {
        SaleTicket2 saleTicket = new SaleTicket2();
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

class SaleTicket2 implements Runnable {
    private int ticketCount = 200;
    AtomicInteger sellCount = new AtomicInteger(0);
    Lock lock = new ReentrantLock();
    Object object = new Object();

    @Override
    public void run() {
            while (ticketCount > 0) {
                if (ticketCount > 0) {
                    synchronized (object){
                        sellCount.getAndIncrement();
                        //System.out.println("after ticketCount" + ticketCount + "   sellCount:" + sellCount);
                        System.out.println(Thread.currentThread().getName() + "卖票窗口卖出一张票,剩余票数：" + ticketCount--
                                + " 卖出票数：" + sellCount);

                    }
                    // lock.lock();
                    // System.out.println("before ticketCount" + ticketCount + "   sellCount:" + sellCount);
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

