package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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

import com.mongodb.util.JSON;

import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

public class WordCloudView extends JPanel implements Observer {
	
	private JPanel mainPanel; 
	private Subject subjectRef;
	private ArrayList<String> tweetList = new ArrayList<String>();
	String[] words;
	private static final String[] WORDS = { "art", "australia", "baby", "beach", "birthday", "blue", "bw", "california", "canada", "canon",
        "cat", "chicago", "china", "christmas", "city", "dog", "england", "europe", "family", "festival", "flower", "flowers", "food",
        "france", "friends", "fun", "germany", "holiday", "india", "italy", "japan", "london", "me", "mexico", "music", "nature",
        "new", "newyork", "night", "nikon", "nyc", "paris", "park", "party", "people", "portrait", "sanfrancisco", "sky", "snow",
        "spain", "summer", "sunset", "taiwan", "tokyo", "travel", "trip", "uk", "usa", "vacation", "water", "wedding" };
	private Random random ;
	private Cloud cloud;
	
	public WordCloudView(Subject subjectRef, String viewRef){
		// TODO Auto-generated constructor stub
		//setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;

		this.subjectRef.registerObserver(this,viewRef);
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
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 for (String s : WORDS) {
	            for (int i = random.nextInt(50); i > 0; i--) {
	                cloud.addTag(s);
	            }
	        }
	        for (Tag tag : cloud.tags()) {
	            final JLabel label = new JLabel(tag.getName());
	            label.setOpaque(false);
	            label.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
	            label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
	            Dimension d = new Dimension(800, 475);
	    		this.mainPanel.setPreferredSize(d);
	            mainPanel.add(label);          
	        }
	        this.add(mainPanel);
	}
	
public void getTokens() throws InvalidFormatException, IOException {
		InputStream is = new FileInputStream("D:/Workspace/Opinion Miner/en-token.bin"); 
		TokenizerModel model = new TokenizerModel(is);
		Tokenizer tokenizer = new TokenizerME(model);
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef).getMongoDataStore();
		ArrayList<JSONObject> negativeResults = new ArrayList<JSONObject>();
		
		String tokens[] = null;// = new ArrayList<String>();// = tokenizer.tokenize(txtData.getData());
		String negTweet = null;
		for(JSONObject obj :mongoDataStore ){
			String sentiment = obj.get("rapidMinerResults").toString();
			if(sentiment.equalsIgnoreCase("negative")){
				negTweet = negTweet + obj.get("processedTweet").toString();
			}
		}
		tokens = tokenizer.tokenize(negTweet);
		for (String a : tokens){
			System.out.println(a);
		}
		
		
		is.close();
		
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
