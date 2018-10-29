package com.niu.top.redisdemo.redis;

import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author hongwei
 * @date 2018/10/25 8:54
 */
public class RedisClient {
    public static void main(String[] args) {
        RedisConnection redisConnection = RedisUtil.getRedisConn();
        redisConnection.set("name".getBytes(), "mark".getBytes());
        System.out.println(new String(redisConnection.get("name".getBytes())));
        boolean result = redisConnection.setNX("name".getBytes(), "mary".getBytes());
        //redisConnection.e
        //redisConnection.set("name".getBytes(),"mary".getBytes());
        System.out.println(result);
        System.out.println(new String(redisConnection.get("name".getBytes())));

        Jedis jedis = RedisUtil.getJedis();


        String keyVlue = UUID.randomUUID().toString();

        jedis.set("lock", keyVlue);
        System.out.println(jedis.get("lock"));
       keyVlue = UUID.randomUUID().toString();
        System.out.println("new value:" + keyVlue);

        String result2 = jedis.set("lock", keyVlue, "NX", "PX", 1000);
        System.out.println(jedis.get("lock") + "  result:" + result2);





        String COMPARE_AND_DELETE = RedisUtil.getScript("D://IdeaProjects//redisdemo//src//test//java//com//niu//top//redisdemo//ticket//lua//unlock.lua");
               /* "if redis.call('get',KEYS[1]) == ARGV[1]\n" +
                        "then\n" +
                        "    return redis.call('del',KEYS[1])\n" +
                        "else\n" +
                        "    return 0\n" +
                        "end";*/
        System.out.println("COMPARE_AND_DELETE:"+COMPARE_AND_DELETE);
        Object result3 = jedis.eval(COMPARE_AND_DELETE,Collections.singletonList("lock"), Collections.singletonList(keyVlue));
        System.out.println("-------------"+result3.toString() + "           "+jedis.get("lock"));




    }
}
