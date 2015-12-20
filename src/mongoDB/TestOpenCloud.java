package mongoDB;

import com.google.common.collect.Lists;
import com.kafkastorm.example.mongoDB.ConnectProvider;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import wordcloud.CollisionMode;
import wordcloud.WordCloud;
import wordcloud.WordFrequency;
import wordcloud.bg.CircleBackground;
import wordcloud.font.scale.SqrtFontScalar;
import wordcloud.nlp.FrequencyAnalyzer;
import wordcloud.palette.ColorPalette;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TestOpenCloud {


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

	protected static void writeToStreamAsPNG(List<WordFrequency> wordFrequencies) throws Exception {
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
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				// new TestOpenCloud().initUI();
				TestOpenCloud.getWordsAndWriteToFile();
			}
		});
	}

}