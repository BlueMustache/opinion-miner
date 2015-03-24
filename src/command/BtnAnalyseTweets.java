package command;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;

import strategy.SentimentStrategy;
import model.Subject;
import model.TwitterDataSubject;

public class BtnAnalyseTweets extends JButton implements Command {

	private Subject subjectRef;

	public BtnAnalyseTweets(String caption, Subject subject) {
		// TODO Auto-generated constructor stub
		super(caption);
		this.setFont(new Font("Gotham Medium", Font.PLAIN, 20));
		this.subjectRef = subject;

	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub

		if (!(((TwitterDataSubject) this.subjectRef).getMongoDataStore().isEmpty())) {
			for (SentimentStrategy analysis : ((TwitterDataSubject) subjectRef).getAnalysisStrategys()) {
				analysis.runSentimentAnalysis(subjectRef);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Error", "No data to analyze.",JOptionPane.ERROR_MESSAGE);
		}
		System.out.println("Analyse btn Pressed");
	}

}
