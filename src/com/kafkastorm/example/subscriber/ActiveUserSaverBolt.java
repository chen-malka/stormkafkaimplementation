package com.kafkastorm.example.subscriber;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;
import com.kafkastorm.example.mongoDB.ConnectProvider;
import com.mongodb.*;

import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

public class ActiveUserSaverBolt extends BaseRichBolt {



	private static final long serialVersionUID = 1L;
	OutputCollector outputCollector;

	public void execute(Tuple tuple) {
		System.out.println("message " + tuple.getValues());
		DBCollection users = ConnectProvider.getConnect().getCollection("user");
		String userName = (String) tuple.getValueByField("user");
		BasicDBObject searchQuery = new BasicDBObject();
		searchQuery.put("name", userName);
		DBCursor cursor = users.find(searchQuery);
		Iterator<DBObject> i = cursor.iterator();
		if (i.hasNext()) {
			searchQuery.put("name", "anton");
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("count", (((Integer)users.find(searchQuery).iterator().next().get("count")))+1);
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
			users.update(searchQuery, updateObj);
		}


	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.outputCollector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
	}

}
