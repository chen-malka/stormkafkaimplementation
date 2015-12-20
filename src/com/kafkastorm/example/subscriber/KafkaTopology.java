package com.kafkastorm.example.subscriber;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import com.google.common.collect.Lists;
import com.kafkastorm.example.mongoDB.ConnectProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import storm.kafka.*;
import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.SqrtFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.palette.ColorPalette;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.util.List;

public class KafkaTopology {

	public static void main(String args[]) {
		BrokerHosts zk = new ZkHosts("bdesdev:2181");
		SpoutConfig spoutConf = new SpoutConfig(zk, "test-topic", "/kafkastorm", "discovery");
		spoutConf.scheme = new SchemeAsMultiScheme(new StringScheme());
		KafkaSpout spout = new KafkaSpout(spoutConf);
		TopologyBuilder builder = new TopologyBuilder();
		String spoutName = "spoutName";
		builder.setSpout(spoutName, spout, 1);
		String parserBoltName = "1";
		builder.setBolt(parserBoltName, new ParseOutBolt()).shuffleGrouping(spoutName);

		String activeUserBoltName = "2";
		builder.setBolt(activeUserBoltName, new ActiveUserSaverBolt()).shuffleGrouping(parserBoltName);
		Config config = new Config();
		config.setDebug(true);
		LocalCluster cluster = new LocalCluster();
		cluster.submitTopology("kafka", config, builder.createTopology());

		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}



}
