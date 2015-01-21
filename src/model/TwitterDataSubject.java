package model;

import view.Observer;

public class TwitterDataSubject extends SubjectDecorator {

	private Subject subject;
	
	public TwitterDataSubject(Subject subjectReference) {
		super(subjectReference);
		// TODO Auto-generated constructor stub
	}

	public void registerObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeObserver(Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public Subject getSubject() {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
