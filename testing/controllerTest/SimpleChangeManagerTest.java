package controllerTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import controller.SimpleChangeManager;
import view.ControlPanelView;
import view.FetchedTweetsView;

public class SimpleChangeManagerTest {
	
	private SimpleChangeManager changeManager;
	private ControlPanelView view;
	private Subject subjectRef;
	private TwitterDataSubject twitterSubjectRef;
	private final PrintStream stdout = System.out;
	private final ByteArrayOutputStream output = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		changeManager = new SimpleChangeManager();
		subjectRef = new ConcreteSubject();
		twitterSubjectRef = new TwitterDataSubject(subjectRef,changeManager);
		view = new ControlPanelView(twitterSubjectRef,"view");
		twitterSubjectRef.registerObserver(view,"view");
		changeManager.register(twitterSubjectRef);
		PrintStream ps = new PrintStream(output);
		System.setOut(ps);
	}


	@Test
	public final void testRegister() {
		assertEquals(changeManager.getObserverMap().size(), 1);
	}
	
	@Test
	public final void testNotify() {
		view.update(twitterSubjectRef);
		String str = "ctrl view updated";
		assertEquals(str, output.toString());
	}
	
	
}
