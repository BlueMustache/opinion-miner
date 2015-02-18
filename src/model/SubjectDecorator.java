package model;

import java.util.ArrayList;

import controller.Controller.CommandListner;
import view.Observer;

public class SubjectDecorator implements Subject {
	
	private Subject subjectReference = null;
	private ArrayList<Observer> observers;
	
	public SubjectDecorator(Subject subject){
		this.subjectReference = subject;
		observers = new ArrayList<Observer>();
	}
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
		// For testing to check if subjects are been decorated
		return subjectReference.description();
	}

	public void addCommandListner(CommandListner commandListner) {
		// TODO Auto-generated method stub
		for(Observer o: observers){
			o.addActionListener(commandListner);
		}
	}
//	public void addSearchListner(SearchListner listner) {
//		// TODO Auto-generated method stub
//		
//	}


}
