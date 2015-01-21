package model;

import view.Observer;

public class SubjectDecorator implements Subject {
	
	private Subject subjectReference = null;
	
	public SubjectDecorator(Subject subject){
		this.subjectReference = subject;
	}
	@Override
	public void registerObserver(Observer observer) {
	}

	@Override
	public void removeObserver(Observer observer) {
	}

	@Override
	public void notifyObservers() {
	}
	//@Override
//	public Subject getSubject();

}
