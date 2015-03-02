package model;

import java.util.ArrayList;

import controller.Controller.CommandListner;
import strategy.SentimentStrategy;
import view.Observer;

public class ConcreteSubject implements Subject {
	
	private ArrayList<Observer> observers;
	
	@Override
	public void registerObserver(Observer observer) {
		// Add observers to subject
				observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		// remove observer
		int i = observers.indexOf(observer);
		if (i >= 0) {
		observers.remove(i);
		}
	}

	@Override
	public void notifyObservers() {
		// notify all observers on update
				for(Observer o: observers){
					o.update(this);
				}	
	}

	@Override
	public String description() {
		// TODO Auto-generated method stub
		return "concrete subject created";
	}

	@Override
	public SentimentStrategy getAnalysisStrategys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCommandListner(CommandListner commandListner) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public Subject getSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
