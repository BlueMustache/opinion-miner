package command;

import java.awt.Font;

import javax.swing.JButton;

import strategy.DatumBoxAnalysis;
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import model.DatumBoxSubject;
import model.Subject;
import model.TwitterDataSubject;

public class BtnAnalyseTweets extends JButton implements Command {
	
	private DatumBoxSubject datumSubject;
	private TwitterDataSubject tweetSubject;
	private SentimentStrategy analysis;
	private Subject subjectRef;
	
	public BtnAnalyseTweets(String caption, Subject subject) {
		// TODO Auto-generated constructor stub
		super(caption);
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		this.subjectRef = subject;
		
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		//try {
			/*for(SentimentStrategy analysis : */subjectRef.getAnalysisStrategys();/*){*/
			try {
				analysis.runSentimentAnalysis(subjectRef);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//}
		//} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		//}
		System.out.println("Analyse btn Pressed");
	}

}
