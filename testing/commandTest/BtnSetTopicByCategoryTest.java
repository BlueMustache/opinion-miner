package commandTest;

import static org.junit.Assert.assertEquals;

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

import command.BtnSetTopicByCategory;
import controller.SimpleChangeManager;

public class BtnSetTopicByCategoryTest {
	
	private Subject subjectRef;
	private SimpleChangeManager changeManager;
	private TwitterDataSubject twitterSubjectRef;
	private BtnSetTopicByCategory btnTrendSearch;
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
		btnTrendSearch = new BtnSetTopicByCategory(twitterSubjectRef);
		PrintStream ps = new PrintStream(output);
		System.setOut(ps);

	}

	@After
	public void tearDown() throws Exception {
	}
	



	@Test
	public final void testExecute() throws AWTException, InterruptedException {
		
//		Robot rob = new Robot();
//		rob.keyPress(KeyEvent.VK_ENTER);
//		rob.setAutoWaitForIdle(true);
		twitterSubjectRef.setTopic("twitter");
		btnTrendSearch.setText("twitter");
		btnTrendSearch.execute();
		String str = "Search btn pressed";
		assertEquals(15, twitterSubjectRef.getTweetCount());


	}
}
