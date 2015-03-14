package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.ProgressMonitor;

import org.json.simple.JSONObject;

import model.DatumboxManager;
import model.IDatumBoxManager;
import model.Subject;
import model.TwitterDataSubject;

public class DatumBoxAnalysis implements SentimentStrategy, Runnable {

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
		System.out.println("Datum miner thread started");
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.datumBoxManager = new DatumboxManager("2ddfadb2561f2a6273b801dc35d4ab09");
		ArrayList<String> tweets = ((TwitterDataSubject) subject).getPreProcessedTweetList();
		JSONObject sentimentPrediction = new JSONObject();	
		ArrayList<JSONObject> datumResults = new ArrayList<JSONObject>();
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) subject).getMongoDataStore();
		
		
		try {	
			//for (String tweet : tweets) {
			for(JSONObject obj : mongoDataStore){
				sentimentPrediction = this.datumBoxManager.TwitterSentimentAnalysis(obj.get("processedTweet").toString());
				sentimentPrediction.put("tweet", obj.get("processedTweet").toString());
				obj.put("datumResults", sentimentPrediction.get("result").toString());
				datumResults.add(sentimentPrediction);	
				((TwitterDataSubject) subject).setDatumBoxProgressCount();
				System.out.println("DatumBox results count !!!" +((TwitterDataSubject) subject).getDatumBoxProgressCount());
			}
			System.out.println("DatumBox results created successfully !!!");
			
		} catch (Exception e) {
			System.out
					.println("Error DatumBox results not written !!!");
			e.printStackTrace();
		}
		
		((TwitterDataSubject) subject).setDatumResultsJSON(datumResults);
		}
	}
	


