import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.rapidminer.RapidMiner;
import com.rapidminer.RapidMiner.ExecutionMode;

import controller.Controller;
import controller.SimpleChangeManager;
import strategy.DatumBoxAnalysis;
import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import strategy.RapidMinerSentimentAnalysis;
import view.MainUI;
import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,InstantiationException, IllegalAccessException,UnsupportedLookAndFeelException, IOException {

		System.out.println("Driver running");


		// Select the Look and Feel need a backup to go to the default look and feel maybe a try catch
		UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
		
		RapidMiner.setExecutionMode(ExecutionMode.COMMAND_LINE);
		RapidMiner.init();
		
		SimpleChangeManager changeManager = new SimpleChangeManager();
		
		Subject twitterSubject = new ConcreteSubject();
		twitterSubject = new TwitterDataSubject(twitterSubject,changeManager);
		//((TwitterDataSubject) twitterSubject).setTopic("#ISIS exclude:retweets"); //FOR TESTING TO CHECK TWEETS HAVE A QUERY
		
		RapidMinerSentimentAnalysis rapidMinerAnalysis = new RapidMinerSentimentAnalysis();		//move this to main
		DatumBoxAnalysis datumBoxAnalysis = new DatumBoxAnalysis();
		ProcessStrategy processStrategy = new ProcessTweetsStrategy();
		
		((TwitterDataSubject) twitterSubject).setProcessStrategy(processStrategy); //THis needs WORK
		((TwitterDataSubject) twitterSubject).addSentimentAnalysisStrategy(rapidMinerAnalysis);
		((TwitterDataSubject) twitterSubject).addSentimentAnalysisStrategy(datumBoxAnalysis);
		
		MainUI app = new MainUI((TwitterDataSubject) twitterSubject);
		Controller controller = new Controller((TwitterDataSubject)twitterSubject,app);
		
		System.out.println("tweet Count = " + ((TwitterDataSubject) twitterSubject).getTweetCount());



	}

}
