package model;

import controller.Controller.CommandListner;
import strategy.SentimentStrategy;
import view.Observer;

public interface Subject {
	
	public void registerObserver(Observer observer);

	public void removeObserver(Observer observer);

	public void notifyObservers();
	
	public String description();//this may be deleted at some stage only here for proff of concept


	public void addCommandListner(CommandListner commandListner);

	public void addDatumObservers(Observer o, String ObserverRef);
}
