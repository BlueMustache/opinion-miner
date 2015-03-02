package command;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import model.Subject;
import model.TwitterDataSubject;

public class BtnFetchTweets extends JButton implements Command {
	
	private TwitterDataSubject tweetSubject;
	private Twitter twitterAcc;
	private ProcessStrategy processStrategy;
	private Subject subjectRef;

	public BtnFetchTweets(String caption, Subject subject ) {
		// Constructor for this btn
		super(caption);
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		this.subjectRef = subject;
		this.twitterAcc = ((TwitterDataSubject) subject).getTwitterAcc();
	}

	@Override
	public void execute() {
		// execute method for this btn
		System.out.println("Search btn pressed");// for testing if btn is pressed
		
ArrayList<String> tweetList = new ArrayList<String>();
		
		int tweetCount = 0;
		try {
			Query query = new Query(((TwitterDataSubject) this.subjectRef).getTopic());
			//query.lang("en");
			//query.setCount(10);
			//query.count(20);
			//query.setSince("2015-01-10");
			//query.getGeocode();				All commented lines here may not be required, was trying to limit the amount of tweets fetched
			query.setResultType(Query.POPULAR);
			QueryResult result;
			do {
				result = this.twitterAcc.search(query);
				List<Status> tweets = result.getTweets();
				
				for (Status tweet : tweets) {
					tweetList.add(tweet.getText());
					tweetCount++;
					//System.out.println(tweet.getGeoLocation());	// test to see if geo loaction was possible. it was not as people do not generally provide their location
				}
			} while ((query = result.nextQuery()) != null /*&& tweetCount < 2*/);///MIGHT NEED TO REMOVE THIS LIMITS TWEETS DISPLAYED TO 20 SOMEHOW
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage()); // For testing
		}
		System.out.println("The Tweets Fetched count is : " + tweetCount); //For testing
		
		((TwitterDataSubject) this.subjectRef).setTweetLits(tweetList);	//Set the arraylist of tweets contained in the subject
		//this.tweetSubject.setTweetStore(tweetList); //Set the CSV file with the fetched Tweets
		//this.tweetSubject.setPreProcessedTweetList();
		//processStrategy = new ProcessTweetsStrategy();
		//processStrategy = subjectRef.getProcessStrategy(); // this needsd work
		//processStrategy.runProcess(tweetSubject);
		this.tweetSubject.setTweetStore();
		System.out.println("tweet Count = " + tweetSubject.getTweetCount());
	}

}
