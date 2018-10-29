package com.niu.top.redisdemo.redis;

import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;
import redis.clients.jedis.Jedis;

/**
 * @author hongwei
 * @date 2018/10/25 10:03
 */
public class RedisUtil {

    public static RedisConnection getRedisConn() {
        {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setDatabase(0);
            redisStandaloneConfiguration.setPort(6379);
            redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
            redisStandaloneConfiguration.setHostName("132.232.182.241");
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
            RedisConnection conn = jedisConnectionFactory.getConnection();
            //conn.set("hello".getBytes(), "world".getBytes());
            //System.out.println(new String(conn.get("hello".getBytes())));
            return conn;
        }
    }

    public static Jedis getJedis() {
        {
            RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
            redisStandaloneConfiguration.setDatabase(0);
            redisStandaloneConfiguration.setPort(6379);
            redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
            redisStandaloneConfiguration.setHostName("132.232.182.241");
            JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory(redisStandaloneConfiguration);
            RedisConnection conn = jedisConnectionFactory.getConnection();
            //jedisConnectionFactory.setShardInfo();
            //conn.set("hello".getBytes(), "world".getBytes());
            //System.out.println(new String(conn.get("hello".getBytes())));
            return (Jedis) conn.getNativeConnection();
        }
    }

    public static String getScript(String luaPath){
        DefaultRedisScript<Integer> lockScript = new DefaultRedisScript<Integer>();
        lockScript.setResultType(Integer.class);
        //D:\IdeaProjects\redisdemo\src\test\java\com\niu\top\redisdemo\ticket\lua
        lockScript.setScriptSource( new ResourceScriptSource(new FileSystemResource(luaPath)));
        //System.out.println(lockScript.getScriptAsString());
        return lockScript.getScriptAsString();
    }

}
