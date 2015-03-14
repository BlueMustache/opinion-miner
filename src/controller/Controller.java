package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import command.Command;
import factory.IObserverFactory;
import factory.ObserverFactory;
import view.ControlPanelView;
import view.EvaluationView;
import view.MainUI;
import view.Observer;
import model.Subject;
import model.TwitterDataSubject;

public class Controller {

	public Subject subject;
	private Observer view;
	private Command action;
	private ArrayList<Observer> viewList;
	private Map<String, Observer> viewListMap;
	private MainUI mainUI;
	private Observer evalView;
	private IObserverFactory observerFactory;

	// private ProgressMonitor monitor;

	public Controller(Subject subject, MainUI mainUI) {
		// Constructor for the controller, take the control view and the twitter data subject as params
		this.subject = subject;
		this.mainUI = mainUI;
		// this.viewListMap = viewListMap;
		this.subject.addCommandListner(new CommandListner());
		observerFactory = new ObserverFactory();
		evalView = observerFactory.createObserver("evalView", subject);
	}

	public class CommandListner implements ActionListener {
		// Action all button commands
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
			action.execute();
			String command = e.getActionCommand();
			if (e.getActionCommand().equalsIgnoreCase("search")|| !e.getActionCommand().equalsIgnoreCase("Analyze")&& !e.getActionCommand().equalsIgnoreCase("Update DB")) {
				mainUI.getTabbedPane().setSelectedIndex(1);
			}
			if (e.getActionCommand().equalsIgnoreCase("Analyze")) {
				final ProgressMonitor monitor = new ProgressMonitor(mainUI,"analysis", "Iteration", 0,((TwitterDataSubject) subject).getMongoDataStore().size());

				final Runnable runnable = new Runnable() {
					public void run() {
						int sleepTime = 500;
						while (((TwitterDataSubject) subject).getDatumBoxProgressCount() < ((TwitterDataSubject) subject).getMongoDataStore().size()) {
							monitor.setNote("Iteration "+ ((TwitterDataSubject) subject).getDatumBoxProgressCount());
							monitor.setProgress(((TwitterDataSubject) subject).getDatumBoxProgressCount());
							if (monitor.isCanceled()) {
								monitor.setProgress(((TwitterDataSubject) subject).getTweetCount());
								break;
							}
						}
						monitor.close();
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
				mainUI.getTabbedPane().setSelectedIndex(2);
			}
			if (e.getActionCommand().equalsIgnoreCase("Evaluate")) {
				//evalView = new EvaluationView(subject,"EvalView");
				System.out.println("Eval view created");
				mainUI.getTabbedPane().addTab("evalView", null, (Component) evalView,null);
				System.out.println("Eval view created");
			}
			// switch(command)
			// {
			// case "Search" :
			// mainUI.getTabbedPane().setSelectedIndex(1);
			// break;
			// case "Analyze" :
			// break;
			// // case "Update DB" :
			// // break;
			// default :
			// System.out.println("Invalid Button");
			// }
		}
	}

}
