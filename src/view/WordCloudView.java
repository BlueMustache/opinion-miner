package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

public class WordCloudView extends JPanel implements Observer {
	
	private JPanel mainPanel; 
	private Subject subjectRef;
	private ArrayList<String> tweetList = new ArrayList<String>();
	String[] words;
	private String viewRef;
	private static final String[] WORDS = { "art", "australia", "baby", "beach", "birthday", "blue", "bw", "california", "canada", "canon",
        "cat", "chicago", "china", "christmas", "city", "dog", "england", "europe", "family", "festival", "flower", "flowers", "food",
        "france", "friends", "fun", "germany", "holiday", "india", "italy", "japan", "london", "me", "mexico", "music", "nature",
        "new", "newyork", "night", "nikon", "nyc", "paris", "park", "party", "people", "portrait", "sanfrancisco", "sky", "snow",
        "spain", "summer", "sunset", "taiwan", "tokyo", "travel", "trip", "uk", "usa", "vacation", "water", "wedding" };
	Random random ;
	Cloud cloud;
	
	public WordCloudView(Subject subjectRef, String viewRef) {
		// TODO Auto-generated constructor stub
		//setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;
		this.viewRef = viewRef;
		subjectRef.registerObserver(this);
		this.mainPanel = new JPanel();
	
		this.random = new Random();
		this.cloud = new Cloud();
		this.setVisible(true);	
	
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
	//	this.tweetList = ((TwitterDataSubject) subjectRef).getTweets();
		//String str = 
//		for(int i=0; i<WORDS.length;i++){
//		words = this.tweetList.get(i).split(" ");
//		}
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

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Observer getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisibility(boolean bool) {
		// TODO Auto-generated method stub
		this.setVisible(bool);
	}

	@Override
	public String getViewRef() {
		// TODO Auto-generated method stub
		return this.viewRef;
	}

	@Override
	public void repaintParent() {
		// TODO Auto-generated method stub
		
	}

}
