import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import controller.Controller;
import view.MainUI;
import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

public class Main {

	public static void main(String[] args) throws ClassNotFoundException,InstantiationException, IllegalAccessException,UnsupportedLookAndFeelException, IOException {

		System.out.println("Driver running");


		// Select the Look and Feel need a backup to go to the default look and feel maybe a try catch
		UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");

		Subject twitterSubject = new ConcreteSubject();
		twitterSubject = new TwitterDataSubject(twitterSubject);
		//((TwitterDataSubject) twitterSubject).setTopic("#ISIS exclude:retweets"); //FOR TESTING TO CHECK TWEETS HAVE A QUERY
		((TwitterDataSubject) twitterSubject).setProcessStrategy(); //THis needs WORK
		
		MainUI app = new MainUI((TwitterDataSubject) twitterSubject);
		Controller controller = new Controller((TwitterDataSubject)twitterSubject,app);
		
		System.out.println("tweet Count = " + ((TwitterDataSubject) twitterSubject).getTweetCount());



	}

}
