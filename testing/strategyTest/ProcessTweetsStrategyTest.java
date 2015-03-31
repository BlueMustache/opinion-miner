//package strategyTest;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//import java.util.ArrayList;
//
//import model.ConcreteSubject;
//import model.Subject;
//import model.TwitterDataSubject;
//
//import org.json.simple.JSONObject;
//import org.junit.Before;
//import org.junit.Test;
//
//import controller.SimpleChangeManager;
//import strategy.ProcessStrategy;
//import strategy.ProcessTweetsStrategy;
//
//public class ProcessTweetsStrategyTest {
//	
//	private Subject subjectRef;
//	private SimpleChangeManager changeManager;
//	private TwitterDataSubject twitterSubjectRef;
//	private ProcessStrategy processStrategy;
//	private ArrayList<JSONObject> mongoDataStore;
//	
//	@Before
//	public void setUp() throws Exception {
//		changeManager = new SimpleChangeManager();
//		subjectRef = new ConcreteSubject();
////		twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
//		processStrategy = new ProcessTweetsStrategy();
//		twitterSubjectRef.setProcessStrategy(processStrategy);
//		JSONObject tweet = new JSONObject();
//		tweet.put("unProcessedTweet", "https http ftp gopher telnet file Unsure http  % ; $ ( ) ~ _ ?  + - = \\ \\\\ .& ] * )  \"@  # \\w+| \\bRT\\ b");
//		mongoDataStore = twitterSubjectRef.getMongoDataStore();
//		mongoDataStore.add(tweet);
//		
//	}
//	@Test
//	public final void testIfProcessed() throws InterruptedException {
//
//		assertEquals(mongoDataStore.get(0).get("processedTweet").toString().length(),0);
////		}
//	}
//
//}
