package view;

import java.awt.BorderLayout;
import java.awt.Color;
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
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

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

public class WordCloudView extends JSplitPane implements Observer {

	private JPanel negPanel;
	private JPanel posPanel;
	private Subject subjectRef;
	private ArrayList<String> tweetList = new ArrayList<String>();
	List<String> negativeResults;
	List<String> positiveResults;
	String[] words;
	private BufferedReader fileReader = null;
	private Random random;
	private Cloud negCloud;
	private Cloud posCloud;
	private Tokenizer tokenizer;
	private TokenizerModel model;
	private InputStream is;
	private final String[] stopWords = {
			"of,in,see,\\(\\d+\\)$,this,its,so,null,me,the,they,in,is,to,able",
			"about", "across", "after", "all", "almost", "also", "among",
			"and", "any", "are", "because", "been", "but", "can", "cannot",
			"could", "dear", "did", "does", "either", "else", "ever",
			"every", "for", "from", "get", "got", "had", "has", "have",
			"her", "hers", "him", "his", "how", "however", "into", "its",
			"just", "least", "let", "like", "likely", "may", "might",
			"most", "must", "neither", "nor", "not", "off", "often",
			"only", "other", "our", "own", "rather", "said", "say", "says",
			"she", "should", "since", "some", "than", "that", "the",
			"their", "them", "then", "there", "these", "they", "this",
			"tis", "too", "twas", "wants", "was", "were", "what", "when",
			"where", "which", "while", "who", "whom", "why", "will",
			"with", "would", "yet", "you", "your" };

	public WordCloudView(Subject subjectRef, String viewRef) {
		// TODO Auto-generated constructor stub
		// setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;
		this.subjectRef.registerObserver(this, viewRef);	
		this.random = new Random();
		this.setVisible(true);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.negCloud = new Cloud();
		this.posCloud = new Cloud();
		negativeResults = new ArrayList<String>();
		positiveResults = new ArrayList<String>();
		this.negPanel = new JPanel();
		this.posPanel = new JPanel();
		// try {
		// getTokens();
		// tweetTokens = removeStopWords(negativeResults);
		// } catch (InvalidFormatException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		/////////////////////////////////////////////////////////////////////////////////////////
		try {
			is = new FileInputStream("en-token.bin");
			model = new TokenizerModel(is);
			tokenizer = new TokenizerME(model);
			
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef)
				.getMongoDataStore();

		String negativeTokens[] = null;// = new ArrayList<String>();// =
		// tokenizer.tokenize(txtData.getData());
		String postiveTokens[] = null;
		String negTweet = null;
		String posTweet = null;
		for (JSONObject obj : mongoDataStore) {
			String sentiment = obj.get("RapidResult").toString();
			if (sentiment.equalsIgnoreCase("negative")) {
				negTweet = negTweet + " "
						+ obj.get("processedTweet").toString();
			} else if (sentiment.equalsIgnoreCase("positive")) {
				posTweet = posTweet + " "
						+ obj.get("processedTweet").toString();
			} else {
				negTweet = negTweet + "";
				posTweet = posTweet + "";
			}
		}
		negativeTokens = tokenizer.tokenize(negTweet);
		postiveTokens = tokenizer.tokenize(posTweet);
		negativeResults = Arrays.asList(negativeTokens);
		positiveResults = Arrays.asList(postiveTokens);

		/////////////////////////////////////////////////////////////////////////////////////
		
		for (int x = 0; x < negativeResults.size(); x++) {
			for (int i = 0; i < stopWords.length; i++) {
				if (negativeResults.get(x).equalsIgnoreCase(stopWords[i])) {
					negativeResults.get(x).replace(negativeResults.get(x), "");
				}
			}
		}
		
		for (int x = 0; x < positiveResults.size(); x++) {
			for (int i = 0; i < stopWords.length; i++) {
				if (positiveResults.get(x).equalsIgnoreCase(stopWords[i])) {
					positiveResults.get(x).replace(positiveResults.get(x), "");
				}
			}
		}
		/////////////////////////////////////////////////////////////////////////////////////
		for (String token : negativeResults) {
			for (int i = random.nextInt(negativeResults.size()); i > 0; i--) {
				negCloud.addTag(token.toString());
			}
		}
		for (String token : positiveResults) {
			for (int i = random.nextInt(positiveResults.size()); i > 0; i--) {
				posCloud.addTag(token.toString());
			}
		}
		

		int maxTag = 50;
		int minTag = 20;

		for (Tag tag : negCloud.tags()) {
			if (tag.getName().contains("null")) {
				tag.setName("");
			}
			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
			JLabel label = new JLabel(tag.getName());
			tag.setWeight(randTag);
			label.setOpaque(false);
			label.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			label.setFont(label.getFont().deriveFont((float) tag.getWeight()));
			label.setForeground(new Color(0, 132, 180));
			label.revalidate();
			label.repaint();
			// Dimension d = new Dimension(15, 15);
			// this.mainPanel.setPreferredSize(d);
			negPanel.add(label);
			negPanel.revalidate();
			negPanel.repaint();
		}
		
		for (Tag tag : posCloud.tags()) {
			if (tag.getName().contains("null")) {
				tag.setName("");
			}
			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
			JLabel label = new JLabel(tag.getName());
			tag.setWeight(randTag);
			label.setOpaque(false);
			label.setFont(new Font("Gotham Medium", Font.PLAIN, 14));
			label.setFont(label.getFont().deriveFont((float) tag.getWeight()));
			label.setForeground(new Color(0, 132, 180));
			label.revalidate();
			label.repaint();
			// Dimension d = new Dimension(15, 15);
			// this.mainPanel.setPreferredSize(d);
			posPanel.add(label);
			posPanel.revalidate();
			posPanel.repaint();
		}
		posPanel.setBorder(new TitledBorder(new LineBorder(new Color(
				0, 132, 180), 4, true), "Positive", TitledBorder.LEFT,
				TitledBorder.CENTER, new Font("Gotham Medium", Font.PLAIN,20)));
		negPanel.setBorder(new TitledBorder(new LineBorder(new Color(
				0, 132, 180), 4, true), "Negative", TitledBorder.LEFT,
				TitledBorder.CENTER, new Font("Gotham Medium", Font.PLAIN,20)));
		Dimension d = new Dimension(500, 600);
		this.setPreferredSize(d);// setPreferredSize(d);//
		this.setDividerLocation(getWidth()/2);
//		negPanel.setPreferredSize(d);
//		posPanel.setPreferredSize(d);
		this.setLeftComponent(negPanel);
		negPanel.revalidate();
		negPanel.repaint();
		this.setRightComponent(posPanel);
		posPanel.revalidate();
		posPanel.repaint();
		this.revalidate();
		this.repaint();
	}

	public void getTokens() throws InvalidFormatException, IOException {
		// InputStream is = new FileInputStream(
		// "en-token.bin");
		// TokenizerModel model = new TokenizerModel(is);
		// Tokenizer tokenizer = new TokenizerME(model);
		// ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject)
		// this.subjectRef)
		// .getMongoDataStore();
		//
		// String negativeTokens[] = null;// = new ArrayList<String>();// =
		// // tokenizer.tokenize(txtData.getData());
		// String postiveTokens[] = null;
		// String negTweet = null;
		// String posTweet = null;
		// for (JSONObject obj : mongoDataStore) {
		// String sentiment = obj.get("RapidResult").toString();
		// if (sentiment.equalsIgnoreCase("negative")) {
		// negTweet = negTweet +" " + obj.get("processedTweet").toString();
		// }else if(sentiment.equalsIgnoreCase("positive")){
		// posTweet = posTweet +" " + obj.get("processedTweet").toString();
		// }else{
		// negTweet = negTweet +"";
		// posTweet = posTweet +"";
		// }
		// }
		// negativeTokens = tokenizer.tokenize(negTweet);
		// postiveTokens = tokenizer.tokenize(posTweet);
		// negativeResults = new
		// ArrayList<String>(Arrays.asList(negativeTokens));
		// positiveResults = new
		// ArrayList<String>(Arrays.asList(postiveTokens));
		int count = 0;
		for (String a : negativeResults) {
			System.out.println(a);
			count++;
		}
		System.out.println("Tweet token count = " + count);
		is.close();

	}

	public ArrayList<String> removeStopWords(ArrayList<String> tweetTokens) {
		return tweetTokens;
//		ArrayList<String> tweetToken = tweetTokens;
//		
//
//		for (int x = 0; x < tweetToken.size(); x++) {
//			for (int i = 0; i < stopWords.length; i++) {
//				if (tweetToken.get(x).equalsIgnoreCase(stopWords[i])) {
//					tweetTokens.remove(x);
//				}
//			}
//		}
//
//		int count = 0;
//		for (String str : tweetTokens) {
//			// System.out.println("With Stopwords removeds = ");
//			System.out.println(str);
//			count++;
//		}
//		System.out.println("Tweet token count with stop words removed = "
//				+ count);
//
//		return tweetTokens;

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
