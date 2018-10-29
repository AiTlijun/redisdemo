package com.niu.top.redisdemo.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @author hongwei
 * @date 2018/10/25 14:18
 */
public class ResidLock implements Lock {

    private static final String lockLuaScriptPATH = "D://IdeaProjects//redisdemo//src//test//java//com//niu//top//redisdemo//ticket//lua//lock.lua";
    private static final String unLockLuaScriptPATH = "D://IdeaProjects//redisdemo//src//test//java//com//niu//top//redisdemo//ticket//lua//unlock.lua";
    // private Jedis jedis = null;
    private static final String lockKEY = "lockKEY";
    private static final ThreadLocal<String> threadLocalId = new ThreadLocal<String>();

    private static final ThreadLocal<Integer> tryCount = new ThreadLocal<Integer>() {
        protected Integer initialValue() {
            return 3;
        }

        ;
    };

    public ResidLock() {
    }

    @Override
    public void lock() {
        if (tryLock()) {
            return;
        }
        double random = Math.round(Math.random() * 50);
       // System.out.println("random:" + random);
        try {
            Thread.sleep(new Double(random).longValue());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock();
    }

    @Override
    public boolean tryLock() {
        //tryCount.set(tryCount.get() - 1);
        Jedis jedis = RedisUtil.getJedis();
        String randomUUID = UUID.randomUUID().toString();
        threadLocalId.set(randomUUID);
        String lockLuaScript = RedisUtil.getScript(lockLuaScriptPATH);
        List<String> args = new ArrayList<String>();
        args.add(randomUUID);
        args.add("1000");
        if ("1".equals(jedis.eval(lockLuaScript, Collections.singletonList(lockKEY), args).toString())) {
            System.out.println(Thread.currentThread().getName() + "--->success --->value:" + jedis.get(lockKEY));
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void unlock() {
        Jedis jedis = RedisUtil.getJedis();
        String unLockLuaScript = RedisUtil.getScript(unLockLuaScriptPATH);
        Object result = jedis.eval(unLockLuaScript, Collections.singletonList(lockKEY), Collections.singletonList(threadLocalId.get()));
        if ("1".equals(result.toString())) {
            System.out.println("释放锁成功！");
        } else {
            System.out.println("释放锁失败！");
        }
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
