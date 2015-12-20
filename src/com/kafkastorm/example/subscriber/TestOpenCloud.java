package com.kafkastorm.example.subscriber;

import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.SqrtFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.palette.ColorPalette;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class TestOpenCloud {

	public static final List<WordFrequency> WORD_FREQUENCIES = Arrays
			.asList(new WordFrequency("apple", 22),
					new WordFrequency("Yuri", 3),
					new WordFrequency("Shaul", 15),
					new WordFrequency("Anton", 50),
					new WordFrequency("Roni", 5),
					new WordFrequency("Adish", 12),
					new WordFrequency("Uriel", 10),
					new WordFrequency("Ido", 7),
					new WordFrequency("Dekel", 6),
					new WordFrequency("Ofir", 2),
					new WordFrequency("Adish", 12),
					new WordFrequency("Ben", 29),
					new WordFrequency("Chen", 22),
					new WordFrequency("Ortal", 35),
					new WordFrequency("Mishka", 1),
					new WordFrequency("Moshe", 34),
					new WordFrequency("Naor", 38),
					new WordFrequency("Mor", 42),
					new WordFrequency("Michal", 27),
					new WordFrequency("Brachi", 20));

	protected void testWriteToStreamAsPNG(List<WordFrequency> wordFrequencies) throws Exception {
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
	}

	public static void main(String[] args) {
//		SwingUtilities.invokeLater(new Runnable() {
//			@Override
//			public void run() {
//				// new TestOpenCloud().initUI();
//				try {
//					new TestOpenCloud().testWriteToStreamAsPNG(WORD_FREQUENCIES);
//				} catch (Exception e) {
//					throw new RuntimeException(e);
//				}
//			}
//		});
	}

}