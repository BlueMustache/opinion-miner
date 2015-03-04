package changeManager;

import view.Observer;
import model.Subject;

public interface IChangeManager {
	public void register(Subject subject,Observer observer);
	public void unRegister(Subject subject,Observer observer);
	public void notifyObservers();
}
