package model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;

import view.Observer;

public class DatumBoxSubject extends SubjectDecorator {

	private Subject subject;
	//private TweetManager tweetManager;
	private ArrayList<Observer> observers;
	private final String fileName = "DatumAnalysis.csv";
	private IDatumBoxManager datumBoxManager;
	private FileWriter fileWriter = null;
	private String tweetDataStore = "D:/Workspace/Opinion Miner/fetchedTweets.csv";
	
	
	

	public DatumBoxSubject(Subject subjectReference) {
		super(subjectReference);
		observers = new ArrayList();
		this.datumBoxManager = new DatumboxManager("2ddfadb2561f2a6273b801dc35d4ab09");
		// TODO Auto-generated constructor stub
	}
	
	public IDatumBoxManager getDatumBoxManager() {
		return datumBoxManager;
	}

	public void setDatumBoxManager(IDatumBoxManager datumBoxManager) {
		this.datumBoxManager = datumBoxManager;
	}
	
	public void setDatumAnalysisFile(){
//		ArrayList<String> tweets = getFetchedTweets();
//		JSONObject sentimentPrediction = new JSONObject();
//		
//		try {
//			fileWriter = new FileWriter(fileName);
//			// Write the CSV file header
//			//fileWriter.append(FILE_HEADER.toString());
//			// Add a new line separator after the header
//			//fileWriter.append("\n");
//			// Write a new tweet list to the CSV file
//			for (String tweet : tweets) {
//				sentimentPrediction = this.datumBoxManager.TwitterSentimentAnalysis(tweet);
//				tweet = tweet.replace(","," ");
//				fileWriter.append(String.valueOf(tweet));
//				fileWriter.append(",");
//				fileWriter.append(sentimentPrediction.get("result").toString());
//				fileWriter.append("\n");
//			}
//			System.out.println("DatumBox results CSV file was created successfully !!!");
//		} catch (Exception e) {
//			System.out.println("Error in CsvFileWriter DatumBox results not written !!!");
//			e.printStackTrace();
//		} finally {
//			try {
//				fileWriter.flush();
//				fileWriter.close();
//			} catch (IOException e) {
//				System.out.println("Error while flushing/closing fileWriter !!!");
//				e.printStackTrace();
//			}
//		}
		notifyObservers();
	}
	
	public ArrayList<String> getFetchedTweets(){
		String csvFile = "D:/Workspace/Opinion Miner/fetchedTweets.csv";
		// "/Users/mkyong/Downloads/GeoIPCountryWhois.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";
		ArrayList<String> tweets = new ArrayList<String>();

		try {

			br = new BufferedReader(new FileReader(csvFile));
			while ((line = br.readLine()) != null) {
				tweets.add(line);
				// use comma as separator
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println("Done");

		return tweets;
	}
	
//	public void registerObserver(Observer observer) {
//		// TODO Auto-generated method stub
//		
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
//		// TODO Auto-generated method stub
//		
//	}

}
