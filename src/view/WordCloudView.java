package view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
	private static final String[] WORDS = { "art", "australia", "baby", "beach", "birthday", "blue", "bw", "california", "canada", "canon",
        "cat", "chicago", "china", "christmas", "city", "dog", "england", "europe", "family", "festival", "flower", "flowers", "food",
        "france", "friends", "fun", "germany", "holiday", "india", "italy", "japan", "london", "me", "mexico", "music", "nature",
        "new", "newyork", "night", "nikon", "nyc", "paris", "park", "party", "people", "portrait", "sanfrancisco", "sky", "snow",
        "spain", "summer", "sunset", "taiwan", "tokyo", "travel", "trip", "uk", "usa", "vacation", "water", "wedding" };
	Random random ;
	Cloud cloud;
	
	public WordCloudView(Subject subjectRef) {
		// TODO Auto-generated constructor stub
		//setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;
		subjectRef.registerObserver(this);
		//this.setLayout(new ScrollPaneLayout());
		
		this.mainPanel = new JPanel();
		//this.mainPanel.setLayout(new FlowLayout());
		this.random = new Random();
		this.cloud = new Cloud();
		//this.mainPanel.setSize(40, 40);
		//this.setSize(400, 400);
		Dimension d = new Dimension(600,600);
		//this.setLayout(new ScrollPaneLayout());
		this.mainPanel.setPreferredSize(d);
		this.setVisible(true);	
	//	setViewportView(mainPanel);
//		GridBagLayout gbl_panel = new GridBagLayout();
//		gbl_panel.columnWidths = new int[] { 0, 0 };
//		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
//		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
//		mainPanel.setLayout(gbl_panel);
		
	//	this.setViewportView(mainPanel);
//		this.tweetList = ((TwitterDataSubject) subjectRef).getTweets();
//		//String str = 
//		//words = this.tweetList.get(1).split(" ");
//		 for (String s : words) {
//	            for (int i = random.nextInt(50); i > 0; i--) {
//	                cloud.addTag(s);
//	            }
//	        }
//	        for (Tag tag : cloud.tags()) {
//	            final JLabel label = new JLabel(tag.getName());
//	            label.setOpaque(false);
//	            label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
//	            mainPanel.add(label);
//	        }
//	        this.add(mainPanel);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.tweetList = ((TwitterDataSubject) subjectRef).getTweets();
		//String str = 
		for(int i=0; i<this.tweetList.size();i++){
		words = this.tweetList.get(i).split(" ");
		}
		 for (String s : words) {
	            for (int i = random.nextInt(50); i > 0; i--) {
	                cloud.addTag(s);
	            }
	        }
	        for (Tag tag : cloud.tags()) {
	            final JLabel label = new JLabel(tag.getName());
	            label.setOpaque(false);
	            label.setFont(label.getFont().deriveFont((float) tag.getWeight() * 10));
	            mainPanel.add(label);
	        }
	        this.add(mainPanel);
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		
	}

}
