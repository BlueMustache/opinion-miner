package command;

import strategy.ProcessStrategy;
import model.Subject;
import model.TwitterDataSubject;

public class ProcessTweetsCommand implements Command {
	
	private Subject subjectRef;
	
	public ProcessTweetsCommand(Subject subject) {
		// TODO Auto-generated constructor stub
		this.subjectRef = subject;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
		ProcessStrategy processStrategy = ((TwitterDataSubject) subjectRef).getProcessStrategy();
		processStrategy.runProcess(this.subjectRef);
		
	}

}
