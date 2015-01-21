package model;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import view.Observer;

public class TwitterDataSubject extends SubjectDecorator {

	private Subject subject;
	private ArrayList<Observer> observers;
	private FileWriter fileWriter = null;
	private final String fileName = "fetchedTweets.csv";
	private String topic;
	
	public TwitterDataSubject(Subject subjectReference) {
		super(subjectReference);
		observers = new ArrayList();
		// TODO Auto-generated constructor stub
	}
	
	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public ArrayList<String> getTweets(String topic) {
		this.topic = topic;
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setIncludeMyRetweetEnabled(false);
		cb.setOAuthConsumerKey("lAUjsLcVsEYDfyoyVz8ZLGJEn");
		cb.setOAuthConsumerSecret("1yMPEgEduQnTOR9Vhic8K4DDIr0e4jGDAgHV1vfRNZrVy7wuOJ");
		cb.setOAuthAccessToken("1132014068-3por1LAq9kljhAgotaxwEpPJu6xRYaRRFfKXD3O");
		cb.setOAuthAccessTokenSecret("bohSoky6eklbPsdjpYMDotqYSI5oZB4bpoHeeYpUf8jmM");
		System.out.println("OAuth Success");//TEST
		Twitter twitter = new TwitterFactory(cb.build()).getInstance();
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
				result = twitter.search(query);
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
	

	public void setTweetStore(String topic){
		ArrayList<String> tweets = getTweets(topic);
		try {
			fileWriter = new FileWriter(fileName);
			// Write the CSV file header
			//fileWriter.append(FILE_HEADER.toString());
			// Add a new line separator after the header
			fileWriter.append("\n");
			// Write a new tweet list to the CSV file
			for (String tweet : tweets) {
				tweet = tweet.replace(","," ");
				fileWriter.append(String.valueOf(removeUrl(tweet)));
				fileWriter.append(",");
				fileWriter.append("\n");
			}
			System.out.println("CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in CsvFileWriter !!!");
			e.printStackTrace();
		} finally {
			try {
				fileWriter.flush();
				fileWriter.close();
			} catch (IOException e) {
				System.out.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
		notifyObservers();
		System.out.println("Observers notified");//TEST
	}
		//CAN BE REMOVED
//	@Override
//	public void registerObserver(Observer observer) {
//		// Add observers to subject
//				observers.add(observer);
//	}
//
//	@Override
//	public void removeObserver(Observer observer) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void notifyObservers() {
//		// notify all observers on update
//				for(Observer o: observers){
//					o.update(this);
//				}	
//	}
	
	public String description(){
		// For testing to check if subjects are been decorated
		return "twitter data state added to " + super.description();
		
	}
	
	//Temp in subject should be located in subject package
	private static String removeUrl(String commentstr)
    {
        String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
        Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(commentstr);
        int i = 0;
        while (m.find()) {
            commentstr = commentstr.replaceAll(m.group(i),"").trim();
            i++;
        }
        commentstr = commentstr.replaceAll("@\\w+|#\\w+|\\bRT\\b", "");
        commentstr = commentstr.replaceAll("\n", " ");
        commentstr = commentstr.replaceAll("[^\\p{L}\\p{N} ]+", "");
        commentstr = commentstr.replaceAll(" +", " ").trim();
        
        return commentstr;
    }
}
