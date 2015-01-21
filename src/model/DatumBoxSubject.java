package model;

import view.Observer;

public class DatumBoxSubject extends SubjectDecorator {

	Subject subject;
	
	public DatumBoxSubject(Subject subjectReference) {
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

}
