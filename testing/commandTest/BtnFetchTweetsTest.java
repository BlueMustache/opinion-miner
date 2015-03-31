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
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import command.BtnAnalyseTweets;
import command.BtnFetchTweets;
import controller.SimpleChangeManager;

public class BtnFetchTweetsTest {

	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private TwitterDataSubject twitterSubjectRef;
	private BtnFetchTweets btnSearch;

	@Before
	public void setUp() throws Exception {
		this.changeManager = new SimpleChangeManager();
		this.subjectRef = new ConcreteSubject();
		this.twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
		this.twitterSubjectRef.setTopic("twitter");
		Thread.sleep(2000);
		this.btnSearch = new BtnFetchTweets("Search",twitterSubjectRef);
		Thread.sleep(2000);
		System.out.println(this.twitterSubjectRef.getTopic());
	}


	@Test
	public final void testExecute() throws InterruptedException {
//		this.twitterSubjectRef.setTopic("twitter");
		Thread.sleep(2000);
		this.btnSearch.execute();

		assertEquals(15, twitterSubjectRef.getTweetCount());
	}
	
}
