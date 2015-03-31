package modelTest;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import model.ConcreteSubject;
import model.MongoDB;
import model.Subject;
import model.TwitterDataSubject;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import command.BtnAnalyseTweets;
import controller.SimpleChangeManager;

public class MongoDBTest {
	
	private MongoDB mongoDB;
	private DB database;
	
	@Before
	public void setUp() throws Exception {
		this.mongoDB = new MongoDB();
	}
	
	@Test
	public final void testgetDatabase() {
		assertEquals(mongoDB.getDatabase(), mongoDB.getDatabase());
	}
}
