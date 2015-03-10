import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.IOException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import view.MainUI;
import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,InstantiationException, IllegalAccessException,UnsupportedLookAndFeelException, IOException {

		System.out.println("Driver running");

//		Subject datumSubject = new ConcreteSubject();
//		datumSubject = new DatumBoxSubject(datumSubject);

		// Select the Look and Feel need a backup to go to the default look and feel maybe a try catch
		UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		Subject twitterSubject = new ConcreteSubject();
		twitterSubject = new TwitterDataSubject(twitterSubject);
		//((TwitterDataSubject) twitterSubject).setTopic("#ISIS exclude:retweets"); //FOR TESTING TO CHECK TWEETS HAVE A QUERY
		((TwitterDataSubject) twitterSubject).setProcessStrategy(); //THis needs WORK
		MainUI app = new MainUI((TwitterDataSubject) twitterSubject);
		
		System.out.println("tweet Count = " + ((TwitterDataSubject) twitterSubject).getTweetCount());



	}

}
