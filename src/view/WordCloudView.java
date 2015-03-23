package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.InvalidFormatException;

import org.json.simple.JSONObject;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import com.csvreader.CsvReader;
import com.mongodb.util.JSON;

import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

public class WordCloudView extends JPanel implements Observer {

	private JPanel mainPanel;
	private Subject subjectRef;
	private ArrayList<String> tweetList = new ArrayList<String>();
	ArrayList<String> negativeResults;
	String[] words;
	private BufferedReader fileReader = null;
	private static final String[] WORDS = { "art", "australia", "baby",
			"beach", "birthday", "blue", "bw", "california", "canada", "canon",
			"cat", "chicago", "china", "christmas", "city", "dog", "england",
			"europe", "family", "festival", "flower", "flowers", "food",
			"france", "friends", "fun", "germany", "holiday", "india", "italy",
			"japan", "london", "me", "mexico", "music", "nature", "new",
			"newyork", "night", "nikon", "nyc", "paris", "park", "party",
			"people", "portrait", "sanfrancisco", "sky", "snow", "spain",
			"summer", "sunset", "taiwan", "tokyo", "travel", "trip", "uk",
			"usa", "vacation", "water", "wedding" };
	private Random random;
	private Cloud cloud;

	public WordCloudView(Subject subjectRef, String viewRef) {
		// TODO Auto-generated constructor stub
		// setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;

		this.subjectRef.registerObserver(this, viewRef);
		this.mainPanel = new JPanel();

		this.random = new Random();
		this.cloud = new Cloud();
		this.setVisible(true);


	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		try {
			getTokens();
			removeStopWords(this.negativeResults);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String token : this.negativeResults) {
//			for (int i = random.nextInt(50); i > 0; i--) {
				cloud.addTag(token.toString());
//			}
		}
		for (Tag tag : cloud.tags()) {
			final JLabel label = new JLabel(tag.getName());
			label.setOpaque(false);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setFont(label.getFont().deriveFont(
					(float) tag.getWeight() * 20));
			Dimension d = new Dimension(800, 475);
			this.mainPanel.setPreferredSize(d);
			mainPanel.add(label);
		}
		this.add(mainPanel);

	}

	public void getTokens() throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream(
				"D:/Workspace/Opinion Miner/en-token.bin");
		TokenizerModel model = new TokenizerModel(is);
		Tokenizer tokenizer = new TokenizerME(model);
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef)
				.getMongoDataStore();

		String tokens[] = null;// = new ArrayList<String>();// =
								// tokenizer.tokenize(txtData.getData());
		String negTweet = null;
		for (JSONObject obj : mongoDataStore) {
			String sentiment = obj.get("rapidMinerResults").toString();
			if (sentiment.equalsIgnoreCase("negative")) {
				negTweet = negTweet + obj.get("processedTweet").toString();
			}
		}
		tokens = tokenizer.tokenize(negTweet);
		this.negativeResults = new ArrayList<String>(Arrays.asList(tokens));
		int count=0;
		for (String a : negativeResults) {
			System.out.println(a);
			count++;
		}
		System.out.println("Tweet token count = "+count);
		is.close();

	}

	public void removeStopWords(ArrayList<String> tweetTokens){
		ArrayList<String> tweetToken = tweetTokens;
		String[] stopWords = {"able","about","across","after","all","almost","also","among","and","any","are","because","been","but","can","cannot","could","dear","did","does","either","else","ever","every","for","from","get","got","had","has","have","her","hers","him","his","how","however","into","its","just","least","let","like","likely","may","might","most","must","neither","nor","not","off","often","only","other","our","own","rather","said","say","says","she","should","since","some","than","that","the","their","them","then","there","these","they","this","tis","too","twas","wants","was","were","what","when","where","which","while","who","whom","why","will","with","would","yet","you","your"};
		
		for(int x=0;x<tweetToken.size();x++){
			for(int i=0;i<stopWords.length;i++){
				if(tweetToken.get(x).equalsIgnoreCase(stopWords[i])){
					tweetTokens.remove(x);
				}
			}
		}
		
		
		int count = 0;
		for(String str : tweetTokens){
//			System.out.println("With Stopwords removeds = ");
			System.out.println(str);
			count++;
		}
		System.out.println("Tweet token count with stop words removed = "+count);

	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getObserverRef() {
		// TODO Auto-generated method stub
		return null;
	}

}
