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
	private FileWriter fileWriter;
	private String topic;
	private final String fetchedTweetsCSV = "D:/Workspace/Opinion Miner/fetchedTweets.csv";
	private Twitter twitterAcc;
	private ArrayList<String> preProcessedTweetList;
	private  ArrayList<SentimentStrategy> analysisStrategyList;
	private ArrayList<JSONObject> datumBoxResults;
	private ArrayList<JSONObject> rapidMinerResults;
	private ProcessStrategy processStrategy;
	private ArrayList<JSONObject> mongoDataStore;
	private int datumBoxProgressCount;

	public TwitterDataSubject(Subject subjectReference) {
		// Constructor
		super(subjectReference);
		this.observers = new ArrayList<Observer>();
		this.mongoDataStore = new ArrayList<JSONObject>();
		this.datumBoxProgressCount=0;
		this.fileWriter = null;
		this.preProcessedTweetList = new ArrayList<String>();
		this.analysisStrategyList = new ArrayList<SentimentStrategy>();
		this.datumBoxResults = new ArrayList<JSONObject>();
		this.rapidMinerResults = new ArrayList<JSONObject>();
		
		buildConfiguration(); // Create build to create a twitter access account
		setAnalysisStrategys();

	}

	
	public int getDatumBoxProgressCount() {
		return datumBoxProgressCount;
	}


	public void setDatumBoxProgressCount() {
		this.datumBoxProgressCount++;
	}
	
	public void reSetDatumBoxProgressCount() {
		this.datumBoxProgressCount=0;
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

	
	public int getTweetCount(){
		return this.mongoDataStore.size();
	}

	public void setTweetStore() {

		try {
			fileWriter = new FileWriter(fetchedTweetsCSV);
			for(JSONObject obj : this.mongoDataStore){
				String tweet = obj.get("processedTweet").toString();
				tweet = tweet.replace(",", " ");
				fileWriter.append(tweet);
				fileWriter.append(",");
				fileWriter.append("\n");
			}
			System.out.println("Fetched Tweet CSV file was created successfully !!!");
		} catch (Exception e) {
			System.out.println("Error in Fetched Tweet CsvFileWriter !!!");
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
	}
	
	public ArrayList<String> getPreProcessedTweetList() {
		return preProcessedTweetList;
	}
	//might remove this
	public void setPreProcessedTweetList(ArrayList<String> processedTweetList) {
		this.preProcessedTweetList = processedTweetList;
		
		System.out.println("setPreProcessedTweetList activated");// TEST
		System.out.println(processedTweetList.size());// TEST
		for(int i = 0; i<this.preProcessedTweetList.size();i++){
			System.out.println(preProcessedTweetList.get(i).toString());
		}
	}
	

	public ArrayList<SentimentStrategy> getAnalysisStrategys() {
		return this.analysisStrategyList;
	}

	public void setAnalysisStrategys(/*ArrayList<SentimentStrategy> analysisStrategys*/) {
		
		RapidMinerSentimentAnalysis analysis = new RapidMinerSentimentAnalysis();		//move this to main
		DatumBoxAnalysis datumAnalysis = new DatumBoxAnalysis();
		analysisStrategyList.add(analysis);
		analysisStrategyList.add(datumAnalysis);
	}
	
	public ArrayList<JSONObject> getDatumResultsJSON() {
		return datumBoxResults;
	}

	public void setDatumResultsJSON(ArrayList<JSONObject> datumResultsJSON) {
		this.datumBoxResults = datumResultsJSON;
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

	public String getFetchedTweetsCSV() {
		return this.fetchedTweetsCSV;
	}
	

	public ProcessStrategy getProcessStrategy() {
		return processStrategy;
	}

	public void setProcessStrategy(/*ProcessStrategy processStrategy*/) {
		this.processStrategy = new ProcessTweetsStrategy();//move this to main
	}


	public ArrayList<JSONObject> getRapidResultsJSON() {
		return rapidMinerResults;
	}

	public void setRapidResultsJSON(ArrayList<JSONObject> rapidResultsJSON) {
		this.rapidMinerResults = rapidResultsJSON;
		notifyObservers();
	}
	
	

	public ArrayList<JSONObject> getMongoDataStore() {
		return mongoDataStore;
	}

	public void setMongoDataStore(ArrayList<JSONObject> mongoDataStore) {
		this.mongoDataStore = mongoDataStore;
		notifyObservers();
		System.out.println("Set mongo DB activiated");
	}
	
	public void resetMongoDataStore(){
		this.mongoDataStore.clear();
	}
	
	@Override
	public void addCommandListner(CommandListner commandListner) {
		// TODO Auto-generated method stub
		for (Observer o : observers) {
			o.addActionListener(commandListner);
		}
	}

}
