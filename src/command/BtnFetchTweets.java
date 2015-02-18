package command;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import model.TwitterDataSubject;

public class BtnFetchTweets extends JButton implements Command {
	
	private TwitterDataSubject tweetSubject;
	private Twitter twitterAcc;

	public BtnFetchTweets(String caption, TwitterDataSubject subject ) {
		// TODO Auto-generated constructor stub
		super(caption);
		this.tweetSubject = subject;
		this.twitterAcc = subject.getTwitterAcc();
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//this.tweetSubject.setTopic("test");
		System.out.println("Search btn pressed");
		
ArrayList<String> tweetList = new ArrayList<String>();
		
		int tweetCount = 0;
		try {
			Query query = new Query(this.tweetSubject.getTopic());
			//query.lang("en");
			//query.setCount(10);
			//query.count(20);
			//query.setSince("2015-01-10");
			//query.getGeocode();
			query.setResultType(Query.POPULAR);
			QueryResult result;
			do {
				result = this.twitterAcc.search(query);
				List<Status> tweets = result.getTweets();
				
				for (Status tweet : tweets) {
					tweetList.add(tweet.getText());
					tweetCount++;
					//System.out.println(tweet.getGeoLocation());
				}
			} while ((query = result.nextQuery()) != null /*&& tweetCount < 2*/);///MIGHT NEED TO REMOVE THIS LIMITS TWEETS DISPLAYED TO 20 SOMEHOW
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage());
		}
		System.out.println("The Tweets Fetched count is : " + tweetCount);
		
		this.tweetSubject.setTweetLits(tweetList);
		this.tweetSubject.setTweetStore(tweetList);
	}

}
