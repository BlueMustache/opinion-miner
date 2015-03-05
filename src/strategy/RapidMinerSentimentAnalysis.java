package strategy;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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
	public void runSentimentAnalysis(Subject subject) throws Exception {
		// TODO Auto-generated method stub
		this.subject = subject;
		rapidThread = new Thread(this);
		rapidThread.start();
		System.out.println("Rapid miner thread started");
//		((TwitterDataSubject) subject).getTweetDataStore();
//		this.csvFilsName = ((TwitterDataSubject) subject).getTweetDataStore();
//
//		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
//		RapidMiner.init();
//
//		RepositoryLocation pLoc = new RepositoryLocation(
//				"//FYP_Model/ClassificationModelProcess");
//		ProcessEntry pEntry = (ProcessEntry) pLoc.locateEntry();
//		String processXML = pEntry.retrieveXML();
//		Process myProcess = new Process(processXML);
//		myProcess.setProcessLocation(new RepositoryProcessLocation(pLoc));
//		// myProcess.run();
//
//		Operator op = myProcess.getOperator("Read CSV");
//		op.setParameter(com.rapidminer.operator.nio.CSVExampleSource.PARAMETER_CSV_FILE,((TwitterDataSubject) subject).getTweetDataStore());
//		// C:\Users\Admin\Documents\College_Year_4\FYP\Project\Normalised_Data
//
//		IOContainer container = myProcess.run();
//		for (int i = 0; i < container.size(); i++) {
//			IOObject ioObject = container.getElementAt(i);
//			// do something
//			System.out.println("the element @ i = " + ioObject.toString()
//					+ "\n");
//		}
		
//		ArrayList<String> tweets = ((TwitterDataSubject) subject).getPreProcessedTweetList();
//		String csvFile = ((TwitterDataSubject) subject).getDatumBoxCSV();
//		//JSONObject sentimentPrediction = new JSONObject();
//		ArrayList<JSONObject> rapidResults = new ArrayList<JSONObject>();
//		String row = "";
//		
//		try {
//			CsvReader fileReader = new CsvReader("D:/Workspace/Opinion Miner/output.csv");	
//			fileReader.readHeaders();
//
//			//fileReader = new BufferedReader(new FileReader("D:/Workspace/Opinion Miner/output.csv"));
//			// Write the CSV file header
//			// fileWriter.append(FILE_HEADER.toString());
//			// Add a new line separator after the header
//			// fileWriter.append("\n");
//			// Write a new tweet list to the CSV file
//			int count =0;
//			while (fileReader.readRecord()) {
////				String [] output = row.split(",");
////				System.out.println(output[1]+"LOOOK !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//				String result = fileReader.get("prediction(Sentiment)");
//				String tweet = fileReader.get("text");
//				JSONObject sentimentPrediction = new JSONObject();
//				sentimentPrediction.put("result", result);
//				sentimentPrediction.put("tweet", tweet);
//				rapidResults.add(sentimentPrediction);
//				count++;
//			}
//			System.out.println("Rapidminer results CSV file was created successfully !!!");
//			
//		} catch (Exception e) {
//			System.out
//					.println("Error in CsvFileWriter DatumBox results not written !!!");
//			e.printStackTrace();
//		} finally {
//			if (fileReader != null) {
//				try {
//					fileReader.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		((TwitterDataSubject) subject).setRapidResultsJSON(rapidResults);
//
//		}
	}

	@Override
	public void run()  {
		// TODO Auto-generated method stub
		
		((TwitterDataSubject) subject).getTweetDataStore();

		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();

		RepositoryLocation pLoc;
		try {
			pLoc = new RepositoryLocation(
					"//FYP_Model/ClassificationModelProcess");
		
		ProcessEntry pEntry = (ProcessEntry) pLoc.locateEntry();
		String processXML = pEntry.retrieveXML();
		Process myProcess = new Process(processXML);
		myProcess.setProcessLocation(new RepositoryProcessLocation(pLoc));
		// myProcess.run();

		Operator op = myProcess.getOperator("Read CSV");
		op.setParameter(com.rapidminer.operator.nio.CSVExampleSource.PARAMETER_CSV_FILE,((TwitterDataSubject) subject).getTweetDataStore());
		// C:\Users\Admin\Documents\College_Year_4\FYP\Project\Normalised_Data
		Operator csvOutput = myProcess.getOperator("Write CSV");
		IOContainer container = myProcess.run();
		for (int i = 0; i < container.size(); i++) {
			IOObject ioObject = container.getElementAt(i);
			// do something
			System.out.println("the element @ i = " + ioObject.toString()
					+ "\n");		
		}
		} catch (RepositoryException | IOException | XMLException | OperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Running rapid miner thread");
		
		ArrayList<String> tweets = ((TwitterDataSubject) subject).getPreProcessedTweetList();
		String csvFile = ((TwitterDataSubject) subject).getDatumBoxCSV();
		//JSONObject sentimentPrediction = new JSONObject();
		ArrayList<JSONObject> rapidResults = new ArrayList<JSONObject>();
		String row = "";
		
		try {
			CsvReader fileReader = new CsvReader("D:/Workspace/Opinion Miner/output.csv");	
			fileReader.readHeaders();

			//fileReader = new BufferedReader(new FileReader("D:/Workspace/Opinion Miner/output.csv"));
			// Write the CSV file header
			// fileWriter.append(FILE_HEADER.toString());
			// Add a new line separator after the header
			// fileWriter.append("\n");
			// Write a new tweet list to the CSV file
			int count =0;
			while (fileReader.readRecord()) {
//				String [] output = row.split(",");
//				System.out.println(output[1]+"LOOOK !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
				String result = fileReader.get("prediction(Sentiment)");
				String tweet = fileReader.get("text");
				JSONObject sentimentPrediction = new JSONObject();
				sentimentPrediction.put("result", result);
				sentimentPrediction.put("tweet", tweet);
				rapidResults.add(sentimentPrediction);
				count++;
			}
			System.out.println("Rapidminer results CSV file was created successfully !!!");
			
		} catch (Exception e) {
			System.out
					.println("Error in CsvFileWriter DatumBox results not written !!!");
			e.printStackTrace();
		} finally {
			if (fileReader != null) {
				try {
					fileReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		((TwitterDataSubject) subject).setRapidResultsJSON(rapidResults);

		}
	}
	

}
