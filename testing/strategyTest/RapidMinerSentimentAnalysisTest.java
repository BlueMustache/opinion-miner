package strategyTest;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.csvreader.CsvReader;
import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;

import strategy.DatumBoxAnalysis;
import strategy.RapidMinerSentimentAnalysis;
import controller.SimpleChangeManager;

public class RapidMinerSentimentAnalysisTest {

	private RapidMinerSentimentAnalysis rapidMinerAnalysis;
	private TwitterDataSubject twitterSubjectRef;
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private ArrayList<JSONObject> mongoDataStore;
	private BufferedReader fileReader = null;
	
	@Before
	public void setUp() throws Exception {
		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();
		changeManager = new SimpleChangeManager();
		subjectRef = new ConcreteSubject();
		this.twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
		rapidMinerAnalysis = new RapidMinerSentimentAnalysis();
		this.twitterSubjectRef.reSetProgressCount();
		JSONObject tweet = new JSONObject();
		tweet.put("processedTweet", "test tweet");
		this.mongoDataStore = new ArrayList<JSONObject>();
		this.mongoDataStore.add(tweet);
		this.twitterSubjectRef.setMongoDataStore(mongoDataStore);
		this.twitterSubjectRef.setTweetStore();
		this.rapidMinerAnalysis.runSentimentAnalysis(this.twitterSubjectRef);		
	}
	@Test
	public final void testAnalysisCount() throws InterruptedException {	
		Thread.sleep(2000);
		int count = twitterSubjectRef.getRapidminerProgressCount();
		assertEquals(count, 1);
	}
	
	@Test
	public final void testvaladateresults() {

		try {
			CsvReader fileReader = new CsvReader(
					"D:/Workspace/Opinion Miner/output.csv");
			fileReader.readHeaders();
			for (int i = 0; i < mongoDataStore.size()
					&& fileReader.readRecord(); i++) {
				String result = fileReader.get("prediction(Sentiment)");
				mongoDataStore.get(i).put("RapidResult", result);
				assertEquals(twitterSubjectRef.getMongoDataStore().get(i).get("RapidResult"), result);	
			}
		} catch (Exception e) {
			System.out
					.println("Error in Rapidminer CsvFileWriter Rapidminer results not written !!!");
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
