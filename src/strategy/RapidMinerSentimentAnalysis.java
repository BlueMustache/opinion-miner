package strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.csvreader.CsvReader;

import org.json.simple.JSONObject;

import model.Subject;
import model.TwitterDataSubject;

import com.rapidminer.*;
import com.rapidminer.Process;
import com.rapidminer.RapidMiner.ExecutionMode;
import com.rapidminer.operator.IOContainer;
import com.rapidminer.operator.IOObject;
import com.rapidminer.operator.Operator;
import com.rapidminer.operator.OperatorException;
import com.rapidminer.repository.MalformedRepositoryLocationException;
import com.rapidminer.repository.ProcessEntry;
import com.rapidminer.repository.RepositoryException;
import com.rapidminer.repository.RepositoryLocation;
import com.rapidminer.tools.XMLException;

public class RapidMinerSentimentAnalysis implements SentimentStrategy, Runnable {
	
	private Subject subject;
	String csvFilsName;
	private Thread rapidThread;
	private BufferedReader fileReader = null;
	
	public RapidMinerSentimentAnalysis() {
		// TODO Auto-generated constructor stub
		System.out.println("Rapidminer analysis running");
	}

	@Override
	public void runSentimentAnalysis(Subject subject) {
		// TODO Auto-generated method stub
		this.subject = subject;
		rapidThread = new Thread(this);
		rapidThread.start();
		System.out.println("Rapid miner thread started");
	}

	@Override
	public void run()  {
		// TODO Auto-generated method stub
		
		((TwitterDataSubject) subject).getFetchedTweetsCSV();

		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();

		RepositoryLocation pLoc;
		try {
			pLoc = new RepositoryLocation("//FYP_Model/ClassificationModelProcessDemo_2");
		
			ProcessEntry pEntry = (ProcessEntry) pLoc.locateEntry();
			String processXML = pEntry.retrieveXML();
			Process myProcess = new Process(processXML);
			myProcess.setProcessLocation(new RepositoryProcessLocation(pLoc));

			Operator op = myProcess.getOperator("Read CSV");
			op.setParameter(com.rapidminer.operator.nio.CSVExampleSource.PARAMETER_CSV_FILE,((TwitterDataSubject) subject).getFetchedTweetsCSV());
			Operator csvOutput = myProcess.getOperator("Write CSV");
			IOContainer container = myProcess.run();

		} catch (RepositoryException | IOException | XMLException | OperatorException e) {
			JOptionPane.showMessageDialog(null,"Fatal error in RapidMiner Model", "DatumBox Error.",JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
		System.out.println("Running rapid miner thread");
		updateTwitterData();
	}
	
	public void updateTwitterData(){
		ArrayList<JSONObject> rapidResults = new ArrayList<JSONObject>();
		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) subject).getMongoDataStore();
		
		try {
			CsvReader fileReader = new CsvReader("D:/Workspace/Opinion Miner/output.csv");	
			fileReader.readHeaders();
			while (fileReader.readRecord()) {
				String result = fileReader.get("prediction(Sentiment)");
				String tweet = fileReader.get("text");
				for(JSONObject obj : mongoDataStore){
					obj.put("RapidResult", result);
				}
//				JSONObject sentimentPrediction = new JSONObject();
//				sentimentPrediction.put("RapidResult", result);
//				sentimentPrediction.put("tweet", tweet);
//				rapidResults.add(sentimentPrediction);
				((TwitterDataSubject) subject).setRapidMinerProgressCount();
				System.out.println("Rapid results count !!!" +((TwitterDataSubject) subject).getRapidminerProgressCount());
			}
			System.out.println("Rapidminer results CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out
					.println("Error in Rapidminer CsvFileWriter Rapidminer results not written !!!");
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		for (JSONObject tweet : mongoDataStore) {
			System.out.println("rapid results");
			System.out.println(tweet.toString());
		}
		((TwitterDataSubject) subject).hasChanged("rapidView");
		((TwitterDataSubject) subject).setMongoDataStore(mongoDataStore);
//		((TwitterDataSubject) subject).setRapidResultsJSON(rapidResults);
	}
	

}
