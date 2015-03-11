package strategy;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;

import model.Subject;
import model.TwitterDataSubject;

public class ProcessTweetsStrategy implements ProcessStrategy {

	public ProcessTweetsStrategy() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runProcess(TwitterDataSubject subject) {
		// TODO Auto-generated method stub
		ArrayList<String> tweetlist = subject.getTweets();
		Map<String, Integer> tweetMap = subject.getTweetMap();
		ArrayList<JSONObject> mongoDataStore  = subject.getMongoDataStore();
		ArrayList<String> processedTweetlist = new ArrayList<String>();
		
		
		String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		
//		for (Map.Entry<String, Integer> entry : tweetMap.entrySet()) {
//		for(String tweet : tweetlist){
		for(JSONObject fetchedTweet : mongoDataStore){
		Matcher m = p.matcher(fetchedTweet.get("unProcessedTweet").toString());
		int i = 0;
		String tweet = fetchedTweet.get("unProcessedTweet").toString();
		while (m.find()) {	
			tweet = tweet.replaceAll(m.group(i), "").trim();
			i++;
		}
		tweet = tweet.replaceAll("@\\w+|#\\w+|\\bRT\\b", "");
		tweet = tweet.replaceAll("\n", " ");
		tweet = tweet.replaceAll("[^\\p{L}\\p{N} ]+", "");
		tweet = tweet.replaceAll(" +", " ").trim();
		processedTweetlist.add(tweet);
		fetchedTweet.put("processedTweet", tweet);
	//	mongoDataStore
		//System.out.println("Processed Tweet  =  "+ processedTweetlist.get(i));// TEST
		}
		((TwitterDataSubject) subject).setPreProcessedTweetList(processedTweetlist);
		((TwitterDataSubject) subject).setMongoDataStore(mongoDataStore);
		for(JSONObject tweet : mongoDataStore ){
			System.out.println(tweet.toString());
		}
		System.out.println("Size of list in process strategy =  "+ tweetlist.size());// TEST
	}


}
