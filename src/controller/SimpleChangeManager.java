package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Subject;
import view.Observer;

public class SimpleChangeManager implements ChangeManager{
	
	private Map<Subject, Observer> subjectObservermapping;
	
	public SimpleChangeManager() {
		// TODO Auto-generated constructor stub
		this.subjectObservermapping = new HashMap<Subject, Observer>();
	}

	@Override
	public void register(Subject subject, Observer observer) {
		// TODO Auto-generated method stub
		subjectObservermapping.put(subject, observer);
	}

	@Override
	public void unRegister(Subject subject, Observer observer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyChange(Subject subject) {
		// TODO Auto-generated method stub
		for (Entry<Subject, Observer> entry : subjectObservermapping.entrySet()) {
			if (entry.getKey().equals(subject)) {	
				entry.getValue().update(subject);
			}
		}
	}

}
