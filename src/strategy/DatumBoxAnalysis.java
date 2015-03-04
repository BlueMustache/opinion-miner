package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import model.DatumboxManager;
import model.IDatumBoxManager;
import model.Subject;
import model.TwitterDataSubject;

public class DatumBoxAnalysis implements SentimentStrategy, Runnable {

	private FileWriter fileWriter = null;
	private IDatumBoxManager datumBoxManager;
	private Thread datumThread;
	private Subject subject;

	public DatumBoxAnalysis() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void runSentimentAnalysis(Subject subject) throws Exception {
		// TODO Auto-generated method stub
		this.subject = subject;
		datumThread = new Thread(this);
		datumThread.start();
		System.out.println("Rapid miner thread started");
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
	this.datumBoxManager = new DatumboxManager("2ddfadb2561f2a6273b801dc35d4ab09");
		
		ArrayList<String> tweets = ((TwitterDataSubject) subject)
				.getPreProcessedTweetList();
		String csvFile = ((TwitterDataSubject) subject).getDatumBoxCSV();
		JSONObject sentimentPrediction = new JSONObject();
		ArrayList<JSONObject> datumResults = new ArrayList<JSONObject>();
		
		
		try {
			fileWriter = new FileWriter(csvFile);
			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());
			// Add a new line separator after the header
			// fileWriter.append("\n");
			// Write a new tweet list to the CSV file
			for (String tweet : tweets) {
				sentimentPrediction = this.datumBoxManager.TwitterSentimentAnalysis(tweet);
				tweet = tweet.replace(",", " ");
				fileWriter.append(String.valueOf(tweet));
				fileWriter.append(",");
				fileWriter.append(sentimentPrediction.get("result").toString());
				fileWriter.append("\n");
				sentimentPrediction.put("tweet", tweet);
				datumResults.add(sentimentPrediction);
		
			}
			System.out.println("DatumBox results CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out
					.println("Error in CsvFileWriter DatumBox results not written !!!");
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
		((TwitterDataSubject) subject).setDatumResultsJSON(datumResults);

		}
	}
	


