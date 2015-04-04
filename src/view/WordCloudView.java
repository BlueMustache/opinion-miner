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
import javax.swing.JInternalFrame;
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

public class WordCloudView extends JInternalFrame implements Observer {

	private JPanel mainPanel;
	private Subject subjectRef;
	private ArrayList<String> tweetList = new ArrayList<String>();
	ArrayList<String> negativeResults;
	String[] words;
	private BufferedReader fileReader = null;
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
		this.add(mainPanel);
		this.setVisible(true);


	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
//		ArrayList<String> tweetTokens = this.negativeResults;
		try {
			getTokens();
//			tweetTokens = removeStopWords(this.negativeResults);
//			removeStopWords(this.negativeResults);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (String token : this.negativeResults) {
			for (int i = random.nextInt(this.negativeResults.size()); i > 0; i--) {
				cloud.addTag(token.toString());
			}
		}
		
		int maxTag=50;
		int minTag=20;
		
		for (Tag tag : cloud.tags()) {
			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
			JLabel label = new JLabel(tag.getName());
			tag.setWeight(randTag);
			label.setOpaque(false);
			label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			label.setFont(label.getFont().deriveFont(
					(float) tag.getWeight()));
			Dimension d = new Dimension(15, 15);
			this.mainPanel.setPreferredSize(d);
			mainPanel.add(label);
		}
		Dimension d = new Dimension(1000, 600);
		this.setPreferredSize(d);//setPreferredSize(d);//
//		this.setViewportView(mainPanel);
		this.revalidate();
		this.repaint();
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
			String sentiment = obj.get("datumResults").toString();
			if (sentiment.equalsIgnoreCase("negative")) {
				negTweet = negTweet +" " + obj.get("processedTweet").toString();
			}
		}
		tokens = tokenizer.tokenize(negTweet);
//		tokens = tokenizer.tokenize("14' - GOAL! Liverpool 0 United 1. Juan Mata finds the net! #mufclive");
		this.negativeResults = new ArrayList<String>(Arrays.asList(tokens));
		int count=0;
		for (String a : negativeResults) {
			System.out.println(a);
			count++;
		}
		System.out.println("Tweet token count = "+count);
		is.close();

	}

	public ArrayList<String> removeStopWords(ArrayList<String> tweetTokens){
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
		
		return tweetTokens;

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
