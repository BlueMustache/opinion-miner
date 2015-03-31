package modelTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import model.DatumboxManager;
import model.IDatumBoxManager;

import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class DatumboxManagerTest {
	
	private IDatumBoxManager datumBoxManager;
	
	@Before
	public void setUp() throws Exception {
		this.datumBoxManager = new DatumboxManager("2ddfadb2561f2a6273b801dc35d4ab09");
		
	}
	@Test
	public final void testTwitterSentimentAnalysis() {
		JSONObject sentimentPrediction = new JSONObject();	
		sentimentPrediction = datumBoxManager.TwitterSentimentAnalysis("test");
		assertNotNull(sentimentPrediction);
	}
	@Test
	public final void teststringToJSONObject() {
		
		JSONObject sentimentPrediction_2 = new JSONObject();
		sentimentPrediction_2 = this.datumBoxManager.stringToJSONObject("{\"output\": {\"status\": 1,\"result\": \"neutral\"}}");
		assertNotNull(sentimentPrediction_2);
	}
}
