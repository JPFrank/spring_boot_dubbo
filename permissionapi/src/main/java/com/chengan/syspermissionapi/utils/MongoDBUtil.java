//package com.chengan.syspermissionapi.utils;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.MongoDatabase;
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//public class MongoDBUtil {
//    static final String DBName = "test";
//    static final String ServerAddress = "127.0.0.1";
//    static final int PORT = 29017;
//
//    public MongoDBUtil(){}
//
//    // 连接到MongDB,返回连接实例对象
//    public MongoClient getMongoClient(){
//        MongoClient mongoClient = null;
//        try {
//            mongoClient = new MongoClient(ServerAddress, PORT);
//            log.debug("Connect To MongoDB Successfully ~");
//        } catch (Exception e){
//            System.err.println(e.getClass().getName() + ":" + e.getMessage());
//        }
//        return mongoClient;
//    }
//
//    // 获取数据库
//    public MongoDatabase getMongoDB(MongoClient mongoClient){
//        MongoDatabase mongoDatabase = null;
//        try {
//            if (mongoClient != null) {
//                mongoDatabase = mongoClient.getDatabase(DBName);
//                log.debug("Connected To DB ~");
//            } else {
//                throw new RuntimeException("mongoClient Can Not Be Null ~")
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return mongoDatabase；
//    }
//
//    //
//}