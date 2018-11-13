package com.niu.top.redisdemo.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hongwei
 * @date 2018/11/12 15:47
 */
public class mongodbTest {

    public static String mongodbTest(String ip, int port, String user, String password, String dbname){
        MongoDatabase db;
        db = null;
        MongoClient client=null;
        try{
            MongoClientOptions.Builder build=new MongoClientOptions.Builder();
            //与数据最大连接数50
            build.connectionsPerHost(50);
            //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
            build.threadsAllowedToBlockForConnectionMultiplier(50);
            build.connectTimeout(1*60*1000);
            build.maxWaitTime(2*60*1000);
            MongoClientOptions options=build.build();
            //设置服务器信息
            ServerAddress serverAddress=new ServerAddress(ip,port);
            List<ServerAddress> seeds=new ArrayList<ServerAddress>();
            seeds.add(serverAddress);
            //设置验证信息
            MongoCredential credentials=MongoCredential.createScramSha1Credential(user,dbname,password.toCharArray());
            List<MongoCredential> credentialsList=new ArrayList<MongoCredential>();
            credentialsList.add(credentials);
            client=new MongoClient(seeds,credentialsList,options);
            //获取数据库mydb,不存在的话，会自动建立该数据库
            db=client.getDatabase("test");

        }catch(Exception e){
            return e.getClass().getName()+": "+e.getMessage();

        }
        if(db!=null&&client!=null){
            //获取集合
            MongoCollection<Document> documents=db.getCollection("mydb");
            Document document=documents.find().first();
            System.out.println(document.toJson());

        }
        client.close();
        return ip;
    }


    public static void main(String[] args) {
        String ip = "132.232.182.241";
        int port = 27017;
        String user = "test_usr";
        String password = "test_pwd";
        String dbname = "test";
        mongodbTest(ip,port,user,password,dbname);
    }
}
