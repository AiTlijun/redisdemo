package com.niu.top.redisdemo.mysql;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author hongwei
 * @date 2018/11/5 12:19
 */
public class mysqlLock implements Lock {
    @Override
    public void lock() {
        if(tryLock()){
            return;
        }else{
            lock();
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        int insertLock = DBUtils.insertLock();
        System.out.println("获得锁："+insertLock);
        if(insertLock > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        int deleteLock = DBUtils.deleteLock();
        System.out.println("释放锁："+deleteLock);
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
