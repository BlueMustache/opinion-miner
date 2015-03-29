package command;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.json.simple.JSONObject;

import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import view.Observer;
import model.Subject;
import model.TwitterDataSubject;

public class BtnFetchTweets extends JButton implements Command {

	private TwitterDataSubject tweetSubject;
	private Twitter twitterAcc;
	private ProcessTweetsCommand processStrategyCmd;
	private Subject subjectRef;

	public BtnFetchTweets(String caption, Subject subject) {
		// Constructor for this btn
		super(caption);
		this.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
		this.setForeground(new Color(0, 132, 180));
		this.subjectRef = subject;
		this.twitterAcc = ((TwitterDataSubject) subject).getTwitterAcc();
		this.processStrategyCmd = new ProcessTweetsCommand(subject);
	}

	@Override
	public void execute() {
		// execute method for this btn
		System.out.println("Search btn pressed");// for testing if btn is
		System.out.println("Search topic = "+((TwitterDataSubject) this.subjectRef).getTopic());											// pressed

			try {
				if((!((TwitterDataSubject) this.subjectRef).getTopic().isEmpty())){
				ArrayList<String> tweetList = new ArrayList<String>();
				((TwitterDataSubject) this.subjectRef).resetMongoDataStore();
				ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef).getMongoDataStore();

				int tweetCount = 0;
				try {
					Query query = new Query(((TwitterDataSubject) this.subjectRef).getTopic());;
					query.lang("en");
					query.setResultType(Query.MIXED);
					QueryResult result;
					do {
						result = this.twitterAcc.search(query);
						List<Status> tweets = result.getTweets();

						for (Status tweet : tweets) {
							JSONObject fetchedTweet = new JSONObject();
							fetchedTweet.put("unProcessedTweet",tweet.getText());
							fetchedTweet.put("retweetCount",tweet.getRetweetCount());
							tweetList.add(tweet.getText());
							tweetCount++;
							mongoDataStore.add(fetchedTweet);
							// System.out.println("RetweetCount = "
							// +tweet.getRetweetCount()); // test
						}
					} while ((query = result.nextQuery()) != null  && tweetCount < 10);// /MIGHT NEED TO REMOVE THIS LIMITS TWEETS DISPLAYED TO 20 SOMEHOW
				} catch (TwitterException te) {
					te.printStackTrace();
					System.out.println("Failed to search tweets: "+ te.getMessage()); // For// testing
				}
				if(tweetCount==0){
					JOptionPane.showMessageDialog(null,"No matching Tweets found \n Please search a different topic.", "Error",JOptionPane.ERROR_MESSAGE);
				}
				System.out.println("The Tweets Fetched count is : "+ tweetCount); // For// testing

				((TwitterDataSubject) this.subjectRef).reSetProgressCount();
				((TwitterDataSubject) this.subjectRef).hasChanged("tweetView");
				((TwitterDataSubject) this.subjectRef).setMongoDataStore(mongoDataStore);
				System.out.println("The Mongo data count is : "+ mongoDataStore.size());

				this.processStrategyCmd.execute();
				((TwitterDataSubject) this.subjectRef).setTweetStore();
				((TwitterDataSubject) this.subjectRef).reSetTopic();
				System.out.println("tweet Count = "+ ((TwitterDataSubject) subjectRef).getTweetCount());
				
				// ///////////TEST/////////
//				for (JSONObject tweet : mongoDataStore) {
//					System.out.println(tweet.toString());
//				}
				}
				// ////////////////////////
			} catch (NullPointerException e) {
				JOptionPane.showMessageDialog(null,"Search topic not provided.", "Error",JOptionPane.ERROR_MESSAGE);
			}
			
		
			
	}

}
