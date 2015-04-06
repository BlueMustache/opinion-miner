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
		// Constructor for the controller, take the control view and the twitter
		// data subject as params
		this.subject = subject;
		this.mainUI = mainUI;
		// this.viewListMap = viewListMap;
		((TwitterDataSubject) this.subject)
				.addCommandListner(new CommandListner());
	}

	public class CommandListner implements ActionListener {
		// Action all button commands
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
			action.execute();
			String command = e.getActionCommand();
			if (e.getActionCommand().equalsIgnoreCase("search") && ((TwitterDataSubject) subject).getMongoDataStore().size()==0
					|| !e.getActionCommand().equalsIgnoreCase("Analyze")
					&& !e.getActionCommand().equalsIgnoreCase("Update Data Set")) {
				mainUI.getTabbedPane().setSelectedIndex(1);
				mainUI.getTabbedPane().setEnabledAt(1, true);
				mainUI.getTabbedPane().setEnabledAt(3, false);
				mainUI.getTabbedPane().setEnabledAt(2, false);
				mainUI.getTabbedPane().setEnabledAt(4, false);
				mainUI.getTabbedPane().setEnabledAt(5, false);
				mainUI.getTabbedPane().setEnabledAt(6, false);	
				mainUI.getCTRLView().enableEval(false);
				mainUI.getCTRLView().enableUpdate(false);
				mainUI.getTabbedPane().revalidate();
				mainUI.getTabbedPane().repaint();
				int index = mainUI.getTabbedPane().getSelectedIndex();
				if (((TwitterDataSubject) subject).getMongoDataStore().size() == 0) {
					mainUI.getTabbedPane().setSelectedIndex(0);
					mainUI.getTabbedPane().setEnabledAt(1, false);
					mainUI.getTabbedPane().setEnabledAt(3, false);
					mainUI.getTabbedPane().setEnabledAt(2, false);
					mainUI.getTabbedPane().setEnabledAt(4, false);
					mainUI.getTabbedPane().setEnabledAt(5, false);
					mainUI.getTabbedPane().setEnabledAt(6, false);
				}
				try {
					if (((TwitterDataSubject) subject).getMongoDataStore()
							.get(0).containsKey("RapidResult")){
						mainUI.getTabbedPane().setSelectedIndex(1);
						mainUI.getTabbedPane().setEnabledAt(1, true);
						mainUI.getTabbedPane().setEnabledAt(3, true);
						mainUI.getTabbedPane().setEnabledAt(2, true);
						mainUI.getTabbedPane().setEnabledAt(4, true);
						mainUI.getTabbedPane().setEnabledAt(5, true);
						mainUI.getTabbedPane().setEnabledAt(6, true);
					}
				} catch (IndexOutOfBoundsException ex) {
					System.out.println("results not set");
				}
			}
//			if (e.getActionCommand().equalsIgnoreCase("search")&& ((TwitterDataSubject) subject).getMongoDataStore()
//					.get(0).containsKey("RapidResult")){
//				mainUI.getTabbedPane().setSelectedIndex(1);
//				mainUI.getTabbedPane().setEnabledAt(1, true);
//				mainUI.getTabbedPane().setEnabledAt(3, true);
//				mainUI.getTabbedPane().setEnabledAt(2, true);
//				mainUI.getTabbedPane().setEnabledAt(4, true);
//				mainUI.getTabbedPane().setEnabledAt(5, true);
//				mainUI.getTabbedPane().setEnabledAt(6, true);
//			}
			if (e.getActionCommand().equalsIgnoreCase("Analyze")
					&& ((TwitterDataSubject) subject).getMongoDataStore()
							.size() > 0) {
				final ProgressMonitor monitor = new ProgressMonitor(mainUI,
						"Analyzing", "Iteration", 0,
						((TwitterDataSubject) subject).getMongoDataStore()
								.size() * 2);
			
				final Timer timer = new Timer(2, new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						JOptionPane
								.showMessageDialog(null, "Analysis complete");
					}
				});

				final Runnable runnable = new Runnable() {
					public void run() {
						int sleepTime = 500;
						int progressCount = 0;
						while (progressCount < (((TwitterDataSubject) subject)
								.getTweetCount() * 2)) {
							progressCount = ((TwitterDataSubject) subject)
									.getDatumBoxProgressCount()
									+ ((TwitterDataSubject) subject)
											.getRapidminerProgressCount();
							// monitor.setNote("Analyzing "/*+
							// progressNoteCount+"%"*/);
							monitor.setProgress(progressCount);
							if (monitor.isCanceled()) {
								monitor.setProgress(((TwitterDataSubject) subject)
										.getTweetCount());
								break;
							}
						}
						monitor.close();
						mainUI.getCTRLView().enableEval(true);
						((TwitterDataSubject) subject).clearProgressCount();
						timer.setRepeats(false);
						timer.start();
					}
				};
				Thread thread = new Thread(runnable);
				thread.start();
				mainUI.getTabbedPane().setSelectedIndex(3);
				mainUI.getTabbedPane().setEnabledAt(3, true);
				mainUI.getTabbedPane().setEnabledAt(2, true);

			}
			if (e.getActionCommand().equalsIgnoreCase("Analyze")
					&& ((TwitterDataSubject) subject).getMongoDataStore()
							.size() == 0) {
				mainUI.getTabbedPane().setSelectedIndex(0);
				mainUI.getTabbedPane().setEnabledAt(1, false);
				mainUI.getTabbedPane().setEnabledAt(3, false);
				mainUI.getTabbedPane().setEnabledAt(2, false);
				mainUI.getTabbedPane().setEnabledAt(4, false);
				mainUI.getTabbedPane().setEnabledAt(5, false);
				mainUI.getTabbedPane().setEnabledAt(6, false);
			}
			if (e.getActionCommand().equalsIgnoreCase("Evaluate")) {
				System.out.println("Eval view created");
				mainUI.getTabbedPane().setSelectedIndex(4);
				mainUI.getTabbedPane().setEnabledAt(3, true);
				mainUI.getTabbedPane().setEnabledAt(2, true);
				mainUI.getTabbedPane().setEnabledAt(4, true);
				mainUI.getTabbedPane().setEnabledAt(5, true);
				mainUI.getTabbedPane().setEnabledAt(6, true);
				mainUI.getCTRLView().enableUpdate(true);

				System.out.println("Eval view created");
				if (((TwitterDataSubject) subject).getMongoDataStore().size() == 0) {
					mainUI.getTabbedPane().setSelectedIndex(0);
					mainUI.getTabbedPane().setEnabledAt(1, false);
					mainUI.getTabbedPane().setEnabledAt(3, false);
					mainUI.getTabbedPane().setEnabledAt(2, false);
					mainUI.getTabbedPane().setEnabledAt(4, false);
					mainUI.getTabbedPane().setEnabledAt(5, false);
					mainUI.getTabbedPane().setEnabledAt(6, false);
				}
//				try {
//					if (!((TwitterDataSubject) subject).getMongoDataStore()
//							.get(0).containsKey("RapidResult")) {
//						mainUI.getTabbedPane().setSelectedIndex(1);
//						mainUI.getTabbedPane().setEnabledAt(1, true);
//						mainUI.getTabbedPane().setEnabledAt(3, false);
//						mainUI.getTabbedPane().setEnabledAt(2, false);
//						mainUI.getTabbedPane().setEnabledAt(4, false);
//						mainUI.getTabbedPane().setEnabledAt(5, false);
//						mainUI.getTabbedPane().setEnabledAt(6, false);
//					}
//				} catch (IndexOutOfBoundsException ex) {
//					// JOptionPane.showMessageDialog(null,"Error",
//					// "Evaluation.",JOptionPane.ERROR_MESSAGE);
//				}

//				if (e.getActionCommand().equalsIgnoreCase("Update Data Set")&&mainUI.getTabbedPane().getSelectedIndex()) {
//					mainUI.getTabbedPane().setSelectedIndex(1);
//					mainUI.getTabbedPane().setEnabledAt(1, true);
//					mainUI.getTabbedPane().setEnabledAt(3, true);
//					mainUI.getTabbedPane().setEnabledAt(2, true);
//					mainUI.getTabbedPane().setEnabledAt(4, true);
//					mainUI.getTabbedPane().setEnabledAt(5, true);
//					mainUI.getTabbedPane().setEnabledAt(6, true);
//					mainUI.getCTRLView().activateBtns(false);
//				}
				

			}
		}
	}

}
