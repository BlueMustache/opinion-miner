import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import strategy.RapidMinerSentimentAnalysis;
import view.MainUI;
import view.TestObserver;
import model.ConcreteSubject;
import model.DatumBoxSubject;
import model.Subject;
import model.TwitterDataSubject;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Driver running");

		

		
		Subject datumSubject = new ConcreteSubject();
		
		
		datumSubject = new DatumBoxSubject(datumSubject);

		try {
			// Select the Look and Feel
			UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					// Start the application
					Subject twitterSubject = new ConcreteSubject();
					twitterSubject = new TwitterDataSubject(twitterSubject);
					((TwitterDataSubject) twitterSubject).setTweetStore("#ISIS exclude:retweets");

					//((DatumBoxSubject) datumSubject).setDatumAnalysisFile();
					RapidMinerSentimentAnalysis analysis = new RapidMinerSentimentAnalysis();
					try {
						analysis.runSentimentAnalysis(twitterSubject);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					MainUI app = new MainUI((TwitterDataSubject) twitterSubject);
					Image img;
					try {
						img = ImageIO.read(new File("D:\\Workspace\\TattoDemo\\pics\\Twitter_logo_blue.png"));
						app.setIconImage(img);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					app.setSize(1000, 600);
					app.setLocationRelativeTo(null);
					app.setVisible(true);
				}
			});
		} catch (Exception ex) {ex.printStackTrace();		
		}
		// System.out.println("Concrete subject created");
		//System.out.println(twitterSubject.description());
		

		// /System.out.println("Concrete Subject decorated to TwitterData Subject");
		//System.out.println(twitterSubject.description());
		//TestObserver temp = new TestObserver(twitterSubject);
		

	}

}
