package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import controller.ChangeManager;
import view.Observer;

public class DatumBoxSubject extends SubjectDecorator {

	private ArrayList<JSONObject> datumBoxResults;
	private int datumBoxProgressCount;
	private Map<String, Observer> observerMap;
	private ArrayList<Observer> observers;
	private ChangeManager changeManager;

	public DatumBoxSubject(Subject subjectReference) {
		// TODO Auto-generated constructor stub
		super(subjectReference);
		this.observerMap = new HashMap<String, Observer>();
		this.observers = new ArrayList<Observer>();
		this.datumBoxResults = new ArrayList<JSONObject>();
	}

	public ArrayList<JSONObject> getDatumBoxResults() {
		return datumBoxResults;
	}

	public void setDatumBoxResults(ArrayList<JSONObject> datumBoxResults) {
		this.datumBoxResults = datumBoxResults;
	}

	public int getDatumBoxProgressCount() {
		return datumBoxProgressCount;
	}

	public void setDatumBoxProgressCount(int datumBoxProgressCount) {
		this.datumBoxProgressCount = datumBoxProgressCount;
	}

	public void registerObserver(Observer observer) {
		// Add observers to subject
		observers.add(observer);
		changeManager.register(this, observer);
	}

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
		for (Observer o : observers) {
			o.update(this);
		}
	}

	public void notifyObserver(String observerRef) {
		// notify specific observers on update
		System.out.println("Notify observer Called");
		for (Entry<String, Observer> entry : observerMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(observerRef)) {
				entry.getValue().update(this);
			}

		}
	}
	public void notifyChange(){
		changeManager.notifyChange(this);
	}
}
