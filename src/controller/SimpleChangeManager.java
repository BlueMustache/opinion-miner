package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import model.Subject;
import model.TwitterDataSubject;
import view.Observer;

public class SimpleChangeManager implements ChangeManager{
	
	private Map<String, Observer> observerMap;
	private TwitterDataSubject subjectRef;
	
	public SimpleChangeManager(TwitterDataSubject subjectRef) {
		// TODO Auto-generated constructor stub
		this.subjectRef = subjectRef; 
		this.observerMap = subjectRef.getObservers();
	}

//	public void register(Subject subject, Observer observer) {
//		// TODO Auto-generated method stub
//		subjectObservermapping.put(subject, observer);
//	}

	public void unRegister(Subject subject, Observer observer) {
		// TODO Auto-generated method stub
		
	}

	public void notifyChange(String observerRef) {
		// TODO Auto-generated method stub
		System.out.println("Notify observer Called");
		for (Entry<String, Observer> entry : observerMap.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(observerRef)){
				entry.getValue().update(this.subjectRef);
			}
			
		}
	}

}
