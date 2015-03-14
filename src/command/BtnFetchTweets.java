package command;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
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
	private ProcessStrategy processStrategy;
	private Subject subjectRef;

	public BtnFetchTweets(String caption, Subject subject) {
		// Constructor for this btn
		super(caption);
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		this.subjectRef = subject;
		this.twitterAcc = ((TwitterDataSubject) subject).getTwitterAcc();
	}

	@Override
	public void execute() {
		// execute method for this btn
		System.out.println("Search btn pressed");// for testing if btn is
													// pressed

		ArrayList<String> tweetList = new ArrayList<String>();

		((TwitterDataSubject) this.subjectRef).resetMongoDataStore();
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef)
				.getMongoDataStore();

		int tweetCount = 0;
		try {
			Query query = new Query(
					((TwitterDataSubject) this.subjectRef).getTopic());
			// query.lang("en");
			// query.setCount(10);
			// query.count(20);
			// query.setSince("2015-01-10");
			// query.getGeocode(); All commented lines here may not be required,
			// was trying to limit the amount of tweets fetched
			query.setResultType(Query.POPULAR);
			QueryResult result;
			do {
				result = this.twitterAcc.search(query);
				List<Status> tweets = result.getTweets();

				for (Status tweet : tweets) {
					JSONObject fetchedTweet = new JSONObject();
					fetchedTweet.put("unProcessedTweet", tweet.getText());
					fetchedTweet.put("retweetCount", tweet.getRetweetCount());
					tweetList.add(tweet.getText());
					tweetCount++;
					mongoDataStore.add(fetchedTweet);
					// System.out.println("RetweetCount = " +tweet.getRetweetCount()); // test
				}
			} while ((query = result.nextQuery()) != null /* && tweetCount < 2 */);// /MIGHT
																					// NEED
																					// TO
																					// REMOVE
																					// THIS
																					// LIMITS
																					// TWEETS
																					// DISPLAYED
																					// TO
																					// 20
																					// SOMEHOW
		} catch (TwitterException te) {
			te.printStackTrace();
			System.out.println("Failed to search tweets: " + te.getMessage()); // For
																				// testing
		}
		System.out.println("The Tweets Fetched count is : " + tweetCount); // For
																			// testing

		((TwitterDataSubject) this.subjectRef).reSetDatumBoxProgressCount();
		((TwitterDataSubject) this.subjectRef)
				.setMongoDataStore(mongoDataStore);
		System.out
				.println("The Mongo data count is : " + mongoDataStore.size());

		processStrategy = new ProcessTweetsStrategy();
		processStrategy = ((TwitterDataSubject) subjectRef).getProcessStrategy(); // this needsd work
		processStrategy.runProcess((TwitterDataSubject) subjectRef);
		((TwitterDataSubject) this.subjectRef).setTweetStore();
		System.out.println("tweet Count = "+ ((TwitterDataSubject) subjectRef).getTweetCount());

		// ///////////TEST/////////
		for (JSONObject tweet : mongoDataStore) {
			System.out.println(tweet.toString());
		}
		// ////////////////////////
	}

}
