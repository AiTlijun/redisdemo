package com.niu.top.redisdemo.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author hongwei
 * @date 2018/10/25 9:54
 */
public class RedisConfig {

    public static void main(String[] args) {
       /* DefaultRedisScript<Long> lockScript = new DefaultRedisScript<Long>();
        lockScript.setResultType(Long.class);
        //D:\IdeaProjects\redisdemo\src\test\java\com\niu\top\redisdemo\ticket\lua
        lockScript.setScriptSource( new ResourceScriptSource(new FileSystemResource("D://IdeaProjects//redisdemo//src//test//java//com//niu//top//redisdemo//ticket//lua//lock.lua")));
        System.out.println(lockScript.getScriptAsString());
        Jedis jedis = RedisUtil.getJedis();
        List<String> list = new ArrayList<String>();
        list.add("test5");
        list.add("10000");
        Object result = jedis.eval(lockScript.getScriptAsString(),Collections.singletonList("lock1"), list);
        System.out.println("-------------"+result.toString() + "           "+jedis.get("lock1"));*/

        ResidLock residLock = new ResidLock();
        //residLock.tryLock();
        Jedis jedis = RedisUtil.getJedis();
       /* System.out.println("------------- " + jedis.get("lockKEY")+ "     ResidLock");
        residLock.unlock();
        System.out.println("------------- " + jedis.get("lockKEY")+ "     ResidLock");*/
        String lockLuaScriptPATH = "D://IdeaProjects//redisdemo//src//test//java//com//niu//top//redisdemo//ticket//lua//lock.lua";
        String lockLuaScript = RedisUtil.getScript(lockLuaScriptPATH);
        List<String> argsList = new ArrayList<String>();
        String randomUUID = UUID.randomUUID().toString();
        argsList.add(randomUUID);
        argsList.add("10000");
        Object result = jedis.eval(lockLuaScript, Collections.singletonList("lockTest"), argsList);
        System.out.println("tryLock resulet:" + result.toString());
        System.out.println(jedis.get("lockTest"));

    }
}
