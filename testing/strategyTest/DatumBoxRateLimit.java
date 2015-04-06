package strategyTest;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

import strategy.DatumBoxAnalysis;
import controller.SimpleChangeManager;

public class DatumBoxRateLimit {
	
	private DatumBoxAnalysis datumBoxAnalysis;
	private TwitterDataSubject twitterSubjectRef;
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private ArrayList<JSONObject> mongoDataStore;
	
	@Before
	public void setUp() throws Exception {
		changeManager = new SimpleChangeManager();
		subjectRef = new ConcreteSubject();
		twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
		datumBoxAnalysis = new DatumBoxAnalysis();
		JSONObject tweet = new JSONObject();
		tweet.put("processedTweet", "test tweet");
		this.mongoDataStore = new ArrayList<JSONObject>();
		this.mongoDataStore.add(tweet);
		this.twitterSubjectRef.setMongoDataStore(mongoDataStore);
//		this.datumBoxAnalysis.run();
		
		
	}
	@Test
	public final void testAnalysisCount() throws InterruptedException {
		
		for(int i=0;i<1000;i++){
		datumBoxAnalysis.runSentimentAnalysis(twitterSubjectRef);
		Thread.sleep(1500);
		}
		assertEquals(twitterSubjectRef.getDatumBoxProgressCount(), 1000);
	}

	

}
