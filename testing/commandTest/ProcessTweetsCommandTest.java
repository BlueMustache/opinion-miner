package commandTest;

import static org.junit.Assert.assertEquals;

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

import strategy.DatumBoxAnalysis;
import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import command.BtnAnalyseTweets;
import command.BtnUpdateMongoDB;
import command.ProcessTweetsCommand;
import controller.SimpleChangeManager;



public class ProcessTweetsCommandTest {
	
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private TwitterDataSubject twitterSubjectRef;
	private ProcessTweetsCommand processCmd;
	private ProcessStrategy processStrategy;
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
		processCmd = new ProcessTweetsCommand(twitterSubjectRef);
		processStrategy = new ProcessTweetsStrategy();
		twitterSubjectRef.setProcessStrategy(processStrategy);
		JSONObject tweet = new JSONObject();
		tweet.put("unProcessedTweet", "test tweet");
		mongoDataStore = twitterSubjectRef.getMongoDataStore();
		mongoDataStore.add(tweet);
		PrintStream ps = new PrintStream(output);
		System.setOut(ps);
	}


	@Test
	public final void testExecute() {
		processCmd.execute();
		String str = "Processed tweet cmd execute";
		assertEquals(str, output.toString());


	}
	
}
