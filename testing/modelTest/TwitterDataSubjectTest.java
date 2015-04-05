package modelTest;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
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
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import view.ControlPanelView;
import command.BtnSetTopicByCategory;
import controller.SimpleChangeManager;

public class TwitterDataSubjectTest {
	
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private TwitterDataSubject twitterSubjectRef;
	private ProcessStrategy processStrategy;
	private ArrayList<JSONObject> mongoDataStore;
	private RapidMinerSentimentAnalysis rapidMinerAnalysis;	
	private DatumBoxAnalysis datumBoxAnalysis; 
	private Twitter twitterAcc;
	private final PrintStream stdout = System.out;
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	private ControlPanelView view;
	
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
		view = new ControlPanelView(twitterSubjectRef,"view");
		twitterSubjectRef.registerObserver(view,"view");
		changeManager.register(twitterSubjectRef);
		processStrategy = new ProcessTweetsStrategy();
		twitterSubjectRef.setProcessStrategy(processStrategy);
		
		rapidMinerAnalysis = new RapidMinerSentimentAnalysis();	
		datumBoxAnalysis = new DatumBoxAnalysis();
		twitterSubjectRef.addSentimentAnalysisStrategy(rapidMinerAnalysis);
		twitterSubjectRef.addSentimentAnalysisStrategy(datumBoxAnalysis);
	
		twitterSubjectRef.clearProgressCount();
		twitterSubjectRef.setTopic("test");
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setIncludeMyRetweetEnabled(false);
		cb.setOAuthConsumerKey("lAUjsLcVsEYDfyoyVz8ZLGJEn");
		cb.setOAuthConsumerSecret("1yMPEgEduQnTOR9Vhic8K4DDIr0e4jGDAgHV1vfRNZrVy7wuOJ");
		cb.setOAuthAccessToken("1132014068-3por1LAq9kljhAgotaxwEpPJu6xRYaRRFfKXD3O");
		cb.setOAuthAccessTokenSecret("bohSoky6eklbPsdjpYMDotqYSI5oZB4bpoHeeYpUf8jmM");
		twitterAcc = new TwitterFactory(cb.build()).getInstance();

	}


	@Test
	public final void testGetDatumCount() throws AWTException, InterruptedException {
		twitterSubjectRef.setProgressCount(true);
		assertEquals(this.twitterSubjectRef.getDatumBoxProgressCount(), 1);
	}
	@Test
	public final void testGetRapidCount() throws AWTException, InterruptedException {
		twitterSubjectRef.setProgressCount(false);
		assertEquals(this.twitterSubjectRef.getRapidminerProgressCount(), 1);
	}
	@Test
	public final void testreSetProgressCount() throws AWTException, InterruptedException {
		
		assertEquals(this.twitterSubjectRef.getRapidminerProgressCount(), 0);
		assertEquals(this.twitterSubjectRef.getDatumBoxProgressCount(), 0);
	}
	@Test
	public final void testGetTopic() throws AWTException, InterruptedException {
		
		assertEquals(this.twitterSubjectRef.getTopic(), "test exclude:retweets");
	}
	@Test
	public final void testReSetTopic() throws AWTException, InterruptedException {
		twitterSubjectRef.clearTopic();
		assertEquals(this.twitterSubjectRef.getTopic(), null);
	}
	@Test
	public final void testTwitterAcc() throws AWTException, InterruptedException {
		assertEquals(this.twitterSubjectRef.getTwitterAcc(), this.twitterAcc);
	}
	
	@Test
	public final void testGetAnalysisStrategys() throws AWTException, InterruptedException {
		assertEquals(this.twitterSubjectRef.getAnalysisStrategys().size(), 2);
	}
	@Test
	public final void testGetObservers() throws AWTException, InterruptedException {
		assertEquals(this.twitterSubjectRef.getObservers().size(), 1);
	}
	@Test
	public final void testGetdescription() throws AWTException, InterruptedException {
		assertEquals(this.twitterSubjectRef.description(), "twitter data state added to concrete subject");
	}
	@Test
	public final void testGetProcessStrategy() throws AWTException, InterruptedException {
		assertEquals(this.twitterSubjectRef.getProcessStrategy(), processStrategy);
	}
	@Test
	public final void testReSetMongoDataStore() throws AWTException, InterruptedException {
		twitterSubjectRef.clearSubjectDataStore();
		assertEquals(this.twitterSubjectRef.getMongoDataStore().size(), 0);
	}

}
