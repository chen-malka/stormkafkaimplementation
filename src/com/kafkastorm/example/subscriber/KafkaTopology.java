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

		getWordsAndWriteToFile();

		try {
			Thread.sleep(100000000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void getWordsAndWriteToFile() {
		DBCollection users =  ConnectProvider.getConnect().getCollection("user");
		List<WordFrequency> wordFrequencies = Lists.newArrayList();
		for (DBObject dbObject : users.find()) {
			 wordFrequencies.add(new WordFrequency((String) dbObject.get("name"),(Integer)dbObject.get("count")));
		}

		try {
			writeToStreamAsPNG(wordFrequencies);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	protected static byte[] writeToStreamAsPNG(List<WordFrequency> wordFrequencies) throws Exception {
		final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
		frequencyAnalyzer.setWordFrequencesToReturn(400);
		frequencyAnalyzer.setMinWordLength(3);

		final WordCloud wordCloud = new WordCloud(500, 500, CollisionMode.PIXEL_PERFECT);
		wordCloud.setPadding(2);
		wordCloud.setBackground(new CircleBackground(250));
		wordCloud.setColorPalette(new ColorPalette(	new Color(0x4055F1),
				new Color(0x408DF1),
				new Color(0x40AAF1),
				new Color(0x40C5F1),
				new Color(0x40D3F1),
				new Color(0xFFFFFF)));
		wordCloud.setFontScalar(new SqrtFontScalar(15, 60));
		wordCloud.build(wordFrequencies);
		wordCloud.writeToFile("output/wordcloud_circle.png");


		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		wordCloud.writeToStreamAsPNG(byteArrayOutputStream);

		return  byteArrayOutputStream.toByteArray();
	}




}
