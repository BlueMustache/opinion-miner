package command;

import java.awt.Color;
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
		this.setForeground(new Color(0, 132, 180));
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
			System.out.println("Analyse button Error");
			JOptionPane.showMessageDialog(null, "No Data To Analyze. First Search For tweets & Analyze", "No Data",JOptionPane.INFORMATION_MESSAGE);
		}
		System.out.print("Analyse btn Pressed");
	}

}
