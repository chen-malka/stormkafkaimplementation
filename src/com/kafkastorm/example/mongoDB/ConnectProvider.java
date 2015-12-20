package com.kafkastorm.example.mongoDB;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

public class ConnectProvider {
    static DB db;

    public static DB getConnect() {
        if (db == null) {
            try {
                MongoClient mongo = new MongoClient("HLLT-ANTONL2", 27017);
                db = mongo.getDB("newDatabase");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }
        return db;
    }
}
