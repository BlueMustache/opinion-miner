package factory;

import model.Subject;
import view.Observer;

public interface IObserverFactory {
	
	public Observer createObserver(String type, Subject subject);
}
