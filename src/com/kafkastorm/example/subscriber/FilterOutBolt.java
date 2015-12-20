package com.kafkastorm.example.subscriber;

import backtype.storm.task.OutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichBolt;
import backtype.storm.tuple.Tuple;

import java.util.Map;

public class FilterOutBolt extends BaseRichBolt {

	private static final long serialVersionUID = 1L;
	OutputCollector outputCollector;

	public void execute(Tuple tuple) {


		System.out.println("message " + tuple.getValues());
		outputCollector.ack(tuple);

	}

	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {
		this.outputCollector = arg2;
	}

	public void declareOutputFields(OutputFieldsDeclarer arg0) {
	}

}
