package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.json.simple.JSONObject;

import controller.SimpleChangeManager;
import strategy.ProcessStrategy;
import strategy.RapidMinerSentimentAnalysis;
import strategy.SentimentStrategy;
import view.Observer;

public class RapidMinerSubject extends SubjectDecorator {

	private ArrayList<JSONObject> rapidMinerResults;
	private int rapidminerProgressCount;
	private Map<String, Observer> observerMap;
	private ArrayList<Observer> observers;
	private SentimentStrategy sentimentStrategy;
	private SimpleChangeManager changeManager;

	public RapidMinerSubject(Subject subjectReference) {
		// TODO Auto-generated constructor stub
		super(subjectReference);
		this.observerMap = new HashMap<String, Observer>();
		this.observers = new ArrayList<Observer>();
		this.rapidMinerResults = new ArrayList<JSONObject>();
		this.changeManager = new SimpleChangeManager();
	}

	public ArrayList<JSONObject> getRapidMinerResults() {
		return rapidMinerResults;
	}

	public void setRapidMinerResults(ArrayList<JSONObject> rapidMinerResults) {
		this.rapidMinerResults = rapidMinerResults;
		notifyChange();
	}

	public int getRapidminerProgressCount() {
		return rapidminerProgressCount;
	}

	public void setRapidminerProgressCount() {
		this.rapidminerProgressCount++;
	}

	public SentimentStrategy getSentimentStrategy() {
		return sentimentStrategy;
	}

	public void setSentimentStrategy(/*SentimentStrategy sentimentStrategy*/) {
		RapidMinerSentimentAnalysis analysis = new RapidMinerSentimentAnalysis();
		this.sentimentStrategy = analysis;
	}

	public void registerObserver(Observer observer) {
		// Add observers to subject
		observers.add(observer);
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

	public void notifyChange() {
		System.out.println("Changemanager called");
		changeManager.notifyChange(this);
	}
}
