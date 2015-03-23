package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ProgressMonitor;
import javax.swing.Timer;
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

	// private ProgressMonitor monitor;

	public Controller(Subject subject, MainUI mainUI) {
		// Constructor for the controller, take the control view and the twitter data subject as params
		this.subject = subject;
		this.mainUI = mainUI;
		// this.viewListMap = viewListMap;
		this.subject.addCommandListner(new CommandListner());
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
				mainUI.getTabbedPane().revalidate();
				mainUI.getTabbedPane().repaint();
				int index = mainUI.getTabbedPane().getSelectedIndex();
				if(((TwitterDataSubject) subject).getMongoDataStore().size()==0){
					mainUI.getTabbedPane().setSelectedIndex(0);
				}
			}
			if (e.getActionCommand().equalsIgnoreCase("Analyze")) {
				final ProgressMonitor monitor = new ProgressMonitor(mainUI,"analysis", "Iteration", 0,((TwitterDataSubject) subject).getMongoDataStore().size()*2);
				
				final Timer timer = new Timer(2, new ActionListener() {
		            public void actionPerformed(ActionEvent e) {
		            	JOptionPane.showMessageDialog(null, "Analysis complete");
		            }
		        });
				
				final Runnable runnable = new Runnable() {
					public void run() {
						int sleepTime = 500;
						int progressCount=0;
						while (progressCount < (((TwitterDataSubject) subject).getTweetCount()*2)-1) {
							progressCount = ((TwitterDataSubject) subject).getDatumBoxProgressCount()+((TwitterDataSubject) subject).getRapidminerProgressCount();
							monitor.setNote("Analyzing "/*+ progressNoteCount+"%"*/);
							monitor.setProgress(progressCount);
							if (monitor.isCanceled()) {
								monitor.setProgress(((TwitterDataSubject) subject).getTweetCount());
								break;
							}
						}
						monitor.close();
						((TwitterDataSubject) subject).reSetDatumBoxProgressCount();
						timer.setRepeats(false);
						timer.start();
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
				mainUI.getTabbedPane().setSelectedIndex(4);
				
			}
			
//			if (e.getActionCommand().equalsIgnoreCase("Evaluate") && ((TwitterDataSubject) subject).getMongoDataStore().size()==0) {
//				JOptionPane.showMessageDialog(null, "No Twitterdata to evaluate.");
//			}
			if (e.getActionCommand().equalsIgnoreCase("Evaluate")) {
				
				System.out.println("Eval view created");
				mainUI.getTabbedPane().setSelectedIndex(6);
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
