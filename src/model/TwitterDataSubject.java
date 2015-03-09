package model;


import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import controller.Controller.CommandListner;
import strategy.DatumBoxAnalysis;
import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import view.Observer;

public class TwitterDataSubject extends SubjectDecorator {

	private ArrayList<Observer> observers;
	private FileWriter fileWriter = null;
	private final String fileName = "fetchedTweets.csv";
	private String topic;
	private String tweetDataStore = "D:/Workspace/Opinion Miner/fetchedTweets.csv";
	private String datumBoxCSV = "D:/Workspace/Opinion Miner/DatumAnalysis.csv";
	private Twitter twitterAcc;
	private ArrayList<String> tweetList = new ArrayList<String>();
	private ArrayList<String> preProcessedTweetList = new ArrayList<String>();
	private int tweetCount;
	private  ArrayList<SentimentStrategy> AnalysisStrategys = new ArrayList<SentimentStrategy>();
	private ArrayList<JSONObject> datumResultsJSON = new ArrayList<JSONObject>();
	private ArrayList<JSONObject> rapidResultsJSON = new ArrayList<JSONObject>();
	private ProcessStrategy processStrategy;
	private ArrayList<JSONObject> mongoDataStore;


	public TwitterDataSubject(Subject subjectReference) {
		// Constructor
		super(subjectReference);
		observers = new ArrayList<Observer>();
		this.mongoDataStore = new ArrayList<JSONObject>();
		buildConfiguration(); // Create build to create a twitter access account
		setAnalysisStrategys();

	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void buildConfiguration() {
		// Process for OAuth configuration
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setIncludeMyRetweetEnabled(false);
		cb.setOAuthConsumerKey("lAUjsLcVsEYDfyoyVz8ZLGJEn");
		cb.setOAuthConsumerSecret("1yMPEgEduQnTOR9Vhic8K4DDIr0e4jGDAgHV1vfRNZrVy7wuOJ");
		cb.setOAuthAccessToken("1132014068-3por1LAq9kljhAgotaxwEpPJu6xRYaRRFfKXD3O");
		cb.setOAuthAccessTokenSecret("bohSoky6eklbPsdjpYMDotqYSI5oZB4bpoHeeYpUf8jmM");

		this.twitterAcc = new TwitterFactory(cb.build()).getInstance();

	}

	// This returns the twitter account created might not be needed
	public Twitter getTwitterAcc() {
		return this.twitterAcc;
	}

	public ArrayList<String> getTweets(/* String topic */) {
		// this functionality was moved to a command
		return this.tweetList;
	}
	
	public int getTweetCount(){
		return this.tweetList.size();
	}

	public void setTweetLits(ArrayList<String> tweets) {
		this.tweetList = tweets;
		notifyObservers();
	}

	public void setTweetStore(/*ArrayList<String> tweets*/) {

		try {
			fileWriter = new FileWriter(fileName);
			for (String tweet : this.preProcessedTweetList) {
				tweet = tweet.replace(",", " ");
				fileWriter.append(tweet);
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
				System.out
						.println("Error while flushing/closing fileWriter !!!");
				e.printStackTrace();
			}
		}
		System.out.println("Observers notified");// TEST
	}
	
	public ArrayList<String> getPreProcessedTweetList() {
		return preProcessedTweetList;
	}

	public void setPreProcessedTweetList(ArrayList<String> processedTweetList) {
		this.preProcessedTweetList = processedTweetList;
		
		System.out.println("setPreProcessedTweetList activated");// TEST
		System.out.println(processedTweetList.size());// TEST
		for(int i = 0; i<this.preProcessedTweetList.size();i++){
			System.out.println(preProcessedTweetList.get(i).toString());
		}
	}
	

	public ArrayList<SentimentStrategy> getAnalysisStrategys() {
		return this.AnalysisStrategys;
	}

	public void setAnalysisStrategys(/*ArrayList<SentimentStrategy> analysisStrategys*/) {
		
		RapidMinerSentimentAnalysis analysis = new RapidMinerSentimentAnalysis();		//move this to main
		DatumBoxAnalysis datumAnalysis = new DatumBoxAnalysis();
		AnalysisStrategys.add(analysis);
		AnalysisStrategys.add(datumAnalysis);
	}
	
	public String getDatumBoxCSV() {
		return datumBoxCSV;
	}

	public void setDatumBoxCSV(String datumBoxCSV) {
		this.datumBoxCSV = datumBoxCSV;
	}
	
	public ArrayList<JSONObject> getDatumResultsJSON() {
		return datumResultsJSON;
	}

	public void setDatumResultsJSON(ArrayList<JSONObject> datumResultsJSON) {
		this.datumResultsJSON = datumResultsJSON;
		notifyObservers();
	}
	

	// CAN BE REMOVED
	@Override
	public void registerObserver(Observer observer) {
		// Add observers to subject
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// remove observer
		int i = observers.indexOf(observer);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		// notify all observers on update
		for (Observer o : observers) {
			o.update(this);
		}
	}

	public String description() {
		// For testing to check if subjects are been decorated
		return "twitter data state added to " + super.description();

	}

	public String getTweetDataStore() {
		return this.tweetDataStore;
	}
	

	public ProcessStrategy getProcessStrategy() {
		return processStrategy;
	}

	public void setProcessStrategy(/*ProcessStrategy processStrategy*/) {
		this.processStrategy = new ProcessTweetsStrategy();//move this to main
	}


	public ArrayList<JSONObject> getRapidResultsJSON() {
		return rapidResultsJSON;
	}

	public void setRapidResultsJSON(ArrayList<JSONObject> rapidResultsJSON) {
		this.rapidResultsJSON = rapidResultsJSON;
		notifyObservers();
	}
	
	

	public ArrayList<JSONObject> getMongoDataStore() {
		return mongoDataStore;
	}

	public void setMongoDataStore(ArrayList<JSONObject> mongoDataStore) {
		this.mongoDataStore = mongoDataStore;
	}

	@Override
	public void addCommandListner(CommandListner commandListner) {
		// TODO Auto-generated method stub
		for (Observer o : observers) {
			o.addActionListener(commandListner);
		}
	}

}
