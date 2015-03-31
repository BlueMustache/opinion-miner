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
	
	public SimpleChangeManager() {
		// TODO Auto-generated constructor stub
		
	}

	public Map<String, Observer> getObserverMap() {
		return observerMap;
	}

	public void setObserverMap(Map<String, Observer> observerMap) {
		this.observerMap = observerMap;
	}

	public TwitterDataSubject getSubjectRef() {
		return subjectRef;
	}

	public void setSubjectRef(TwitterDataSubject subjectRef) {
		this.subjectRef = subjectRef;
	}

	public void register(Subject subject) {
		// TODO Auto-generated method stub
		this.subjectRef = (TwitterDataSubject) subject;
		this.observerMap = subjectRef.getObservers();
	}

	public void unRegister(Subject subject, Observer observer) {
		// TODO Auto-generated method stub
		
	}

	public void notifyChange(String observerRef) {
		// TODO Auto-generated method stub
//		System.out.println("Notify Change Called in changemanager ObserverRef = " +observerRef);
		for (Entry<String, Observer> entry : observerMap.entrySet()) {
			if(entry.getKey().equalsIgnoreCase(observerRef)){
				entry.getValue().update(this.subjectRef);
			}
//			if(entry.getKey().equalsIgnoreCase("elavView")){
//				entry.getValue().update(this.subjectRef);
//				entry.getKey().
//			}
			
		}
	}

}
