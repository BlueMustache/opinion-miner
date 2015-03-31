package testRunner;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

//specify a runner class: Suite.class
@RunWith(Suite.class)

//specify an array of test classes
@Suite.SuiteClasses({ commandTest.BtnAnalyzeTweetsTest.class,
		commandTest.BtnEvaluateMongoResultsTest.class, commandTest.BtnFetchTweetsTest.class,
		commandTest.BtnSetTopicByCategoryTest.class, commandTest.BtnUpdateMongoDBTest.class,
		commandTest.ProcessTweetsCommandTest.class, controllerTest.SimpleChangeManagerTest.class,
		modelTest.DatumboxManagerTest.class, modelTest.MongoDBTest.class, modelTest.TwitterDataSubjectTest.class,
		strategyTest.DatumBoxAnalysisTest.class, strategyTest.RapidMinerSentimentAnalysisTest.class,
})
public class TestRunner {


}
