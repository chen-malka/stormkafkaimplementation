package com.kafkastorm.example.subscriber;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import backtype.storm.tuple.Values;

import java.util.List;
import java.util.Map;

public class ParseOutBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	OutputCollector outputCollector;

	public void execute(Tuple tuple) {
		List<Object> values = tuple.getValues();
		String message = (String) values.get(0);
		String user = message.substring(6, message.indexOf("ran service"));
		String serviceName = message.substring(message.indexOf("ran service") + 13, message.indexOf("with parameters"));
		outputCollector.emit(new Values(user, serviceName));
		outputCollector.ack(tuple);
		System.out.println("parse msg");

	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.outputCollector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
		arg0.declare(new Fields("user","serviceName"));

	}

}
