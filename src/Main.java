import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import strategy.RapidMinerSentimentAnalysis;
import view.MainUI;
import view.TestObserver;
import model.ConcreteSubject;
import model.DatumBoxSubject;
import model.Subject;
import model.TwitterDataSubject;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			UnsupportedLookAndFeelException {
		// TODO Auto-generated method stub
		System.out.println("Driver running");

		Subject datumSubject = new ConcreteSubject();

		datumSubject = new DatumBoxSubject(datumSubject);

		// Select the Look and Feel
		UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		Subject twitterSubject = new ConcreteSubject();
		twitterSubject = new TwitterDataSubject(twitterSubject);
		((TwitterDataSubject) twitterSubject).setTweetStore("#ISIS exclude:retweets");
		MainUI app = new MainUI((TwitterDataSubject) twitterSubject);
		// ((DatumBoxSubject) datumSubject).setDatumAnalysisFile();
		
		RapidMinerSentimentAnalysis analysis = new RapidMinerSentimentAnalysis();
		try {
			analysis.runSentimentAnalysis(twitterSubject);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		// System.out.println("Concrete subject created");
		// System.out.println(twitterSubject.description());

		// /System.out.println("Concrete Subject decorated to TwitterData Subject");
		// System.out.println(twitterSubject.description());
		// TestObserver temp = new TestObserver(twitterSubject);

	}

}
