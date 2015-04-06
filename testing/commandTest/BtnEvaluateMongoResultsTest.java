package commandTest;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import command.BtnAnalyseTweets;
import command.BtnEvaluateResults;
import strategy.DatumBoxAnalysis;
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import controller.SimpleChangeManager;



public class BtnEvaluateMongoResultsTest {
	
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private TwitterDataSubject twitterSubjectRef;
	private  ArrayList<SentimentStrategy> analysisStrategyList;
	private RapidMinerSentimentAnalysis rapidMinerAnalysis;
	private DatumBoxAnalysis datumBoxAnalysis;
	private BtnEvaluateResults btnEval;
	private ArrayList<JSONObject> mongoDataStore;
	private final PrintStream stdout = System.out;
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		changeManager = new SimpleChangeManager();
		subjectRef = new ConcreteSubject();
		twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
		btnEval = new BtnEvaluateResults("Analyze",twitterSubjectRef);
		JSONObject tweet = new JSONObject();
		tweet.put("unProcessedTweet", "test tweet");
		mongoDataStore = twitterSubjectRef.getMongoDataStore();
		mongoDataStore.add(tweet);
		PrintStream ps = new PrintStream(output);
		System.setOut(ps);
	}

	@After
	public void tearDown() throws Exception {
	}



	@Test
	public final void testExecute() {
		btnEval.execute();
		String str = "Update button pressed";
		assertEquals(str, output.toString());


	}
	
}
