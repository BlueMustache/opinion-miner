package command;

import javax.swing.JButton;

import model.Subject;
import model.TwitterDataSubject;

public class BtnSetTopicByTrend extends JButton implements Command {

	private Subject subjectRef;
	private BtnFetchTweets fetchTweets;
	
	public BtnSetTopicByTrend(/*String caption,*/Subject subject) {
		// TODO Auto-generated constructor stub
		this.subjectRef = subject;
		fetchTweets = new BtnFetchTweets("Search", subjectRef);
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		((TwitterDataSubject) this.subjectRef).setTopic(this.getText());
		fetchTweets.execute();
		System.out.println("trend btn pressed topic set to : "+ ((TwitterDataSubject) this.subjectRef).getTopic());
	}

}
