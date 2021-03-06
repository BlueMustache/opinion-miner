package strategy;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
	public void runSentimentAnalysis(Subject subject) {
		// TODO Auto-generated method stub
		this.subject = subject;
		datumThread = new Thread(this);
		datumThread.start();
//		System.out.println("Datum miner thread started");
	
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		this.datumBoxManager = new DatumboxManager("2ddfadb2561f2a6273b801dc35d4ab09");
		
		ArrayList<JSONObject> datumResults = new ArrayList<JSONObject>();
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) subject).getMongoDataStore();
		System.out.println("Datum miner thread started");
		boolean runDatumAnalysisSuccess = true;
		
		try {	
			int i =1;		//temp while datum rate limit is in place so i can test		
			int testCount =0;
			for(JSONObject obj : mongoDataStore){
				JSONObject sentimentPrediction = new JSONObject();	
					try{			
//						sentimentPrediction = this.datumBoxManager.TwitterSentimentAnalysis(obj.get("processedTweet").toString());	//temp while datum rate limit is in place so i can test
						sentimentPrediction.put("tweet", obj.get("processedTweet").toString());
						obj.put("datumResults", sentimentPrediction.get("result").toString());//temp while datum rate limit is in place so i can test
						((TwitterDataSubject) subject).setProgressCount(true);
						testCount++;//test
					} catch (NullPointerException e) {
						runDatumAnalysisSuccess = false;
						obj.put("datumResults", "No Result!");//temp while datum rate limit is in place so i can test
						((TwitterDataSubject) subject).setProgressCount(true);	
						i++;//temp while datum rate limit is in place so i can test
						
					}

					System.out.println("DatumBox results count !!!" +((TwitterDataSubject) subject).getDatumBoxProgressCount());
			}
			
			
			System.out.println("DatumBox results created successfully !!!");
			
		} catch (NullPointerException e) {
			System.out.println("Error DatumBox results not written !!!");
			e.printStackTrace();
			
		}
		for (JSONObject tweet : mongoDataStore) {
			System.out.println("datum results");
			System.out.println(tweet.toString());
		}
		((TwitterDataSubject) subject).hasChanged("datumView");
		((TwitterDataSubject) subject).setMongoDataStore(mongoDataStore);
		if(!runDatumAnalysisSuccess){
			JOptionPane.showMessageDialog(null,"Datum Box Api Call Rate Limit Reached\n No Results Returned", "DatumBox Error",JOptionPane.ERROR_MESSAGE);
		}
		}
	}
	


