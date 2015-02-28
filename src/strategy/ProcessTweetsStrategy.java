package strategy;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		ArrayList<String> processedTweetlist = new ArrayList<String>();
		
		
		String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern p = Pattern.compile(urlPattern, Pattern.CASE_INSENSITIVE);
		
		for(String tweet : tweetlist){
		Matcher m = p.matcher(tweet);
		int i = 0;
		while (m.find()) {
			tweet = tweet.replaceAll(m.group(i), "").trim();
			i++;
		}
		tweet = tweet.replaceAll("@\\w+|#\\w+|\\bRT\\b", "");
		tweet = tweet.replaceAll("\n", " ");
		tweet = tweet.replaceAll("[^\\p{L}\\p{N} ]+", "");
		tweet = tweet.replaceAll(" +", " ").trim();
		processedTweetlist.add(tweet);
		}
		((TwitterDataSubject) subject).setPreProcessedTweetList(processedTweetlist);
		System.out.println("Size of list in process strategy =  "+ tweetlist.size());// TEST
	}


}
