package command;

import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JOptionPane;

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
		if (!(((TwitterDataSubject) this.subjectRef).getMongoDataStore().isEmpty())) {
			System.out.println("MongoStratgey Execute");
			JOptionPane.showMessageDialog(null, "Database Update Complete");
			mongoDBStrategy.runProcess((TwitterDataSubject) this.subjectRef);
		} else {
			JOptionPane.showMessageDialog(null, "Error", "No data to update.",JOptionPane.ERROR_MESSAGE);
		}
	}

}
