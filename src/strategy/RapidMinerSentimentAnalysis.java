package strategy;

import java.io.IOException;

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
	}
	

}
