package model;
//This whole class may not be required
import java.util.ArrayList;
import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TweetManager implements ITweetManager {

	Twitter twitter;
	
	public TweetManager() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Twitter buildConfiguration() {
		// TODO Auto-generated method stub
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setIncludeMyRetweetEnabled(false);
		cb.setOAuthConsumerKey("lAUjsLcVsEYDfyoyVz8ZLGJEn");
		cb.setOAuthConsumerSecret("1yMPEgEduQnTOR9Vhic8K4DDIr0e4jGDAgHV1vfRNZrVy7wuOJ");
		cb.setOAuthAccessToken("1132014068-3por1LAq9kljhAgotaxwEpPJu6xRYaRRFfKXD3O");
		cb.setOAuthAccessTokenSecret("bohSoky6eklbPsdjpYMDotqYSI5oZB4bpoHeeYpUf8jmM");
		
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
		
		return twitter;
	}

	@Override
	public void setTopic(String topic) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public ArrayList<String> getTweets(String topic) {
		// TODO Auto-generated method stub
		ArrayList<String> tweetList = new ArrayList<String>();
		
		int tweetCount = 0;
		try {
			Query query = new Query(topic);
			//query.lang("en");
			//query.setCount(10);
			//query.count(20);
			//query.setSince("2015-01-10");
			//query.getGeocode();
			query.setResultType(Query.POPULAR);
			QueryResult result;
			do {
				result = this.twitter.search(query);
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
		
		return tweetList;		
	
	}

}
