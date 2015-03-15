package command;

import java.awt.Font;

import javax.swing.JButton;

import strategy.ProcessStrategy;
import strategy.ProcessTweetsStrategy;
import strategy.UpdateMongoDBStrategy;
import twitter4j.Twitter;
import model.Subject;
import model.TwitterDataSubject;

public class BtnUpdateMongoDB extends JButton implements Command {
	
	private ProcessStrategy mongoDBStrategy;
	private Subject subjectRef;
	
	public BtnUpdateMongoDB(String caption, Subject subject) {
		// Constructor for this btn
		super(caption);
		this.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		this.subjectRef = subject;
		this.mongoDBStrategy = new UpdateMongoDBStrategy();
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		System.out.println("MongoStratgey Execute");
		mongoDBStrategy.runProcess((TwitterDataSubject) this.subjectRef);
	}

}
