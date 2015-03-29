package strategy;

import model.Subject;
import model.TwitterDataSubject;

public interface ProcessStrategy {
	
	public void runProcess(Subject subject);
}
