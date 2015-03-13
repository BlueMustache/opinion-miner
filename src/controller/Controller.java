package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import command.Command;
import view.ControlPanelView;
import view.Observer;
import model.Subject;
import model.TwitterDataSubject;

public class Controller {

	public Subject subject;
	private Observer view;
	private Command action;
	private ArrayList<Observer> viewList;
	private Map<String,Observer> viewListMap; 
	
	public Controller(Subject subject, Map<String,Observer> viewListMap) {
		// Constructor for the controller, take the control view and the twitter data subject as params
		this.subject = subject;
		this.viewListMap = viewListMap;
		this.subject.addCommandListner(new CommandListner());
		
	
//		for(Map.Entry<String,Observer> entry  : viewListMap.entrySet()){
//			if(entry.getKey().equals("ctrlView")){
//				entry.getValue().setVisibility(true);
//			}else{
//				entry.getValue().setVisibility(false);
//			}
//		}
		System.out.println("Viewlist size = "+ this.viewListMap.size());


	}

	public class CommandListner implements ActionListener {
		// Action all button commands
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
			action.execute();
			String command = e.getActionCommand();
			
			switch(command)
			{
			case "Search" :
				viewListMap.get("tweetView").setVisibility(true);
				viewListMap.get("cloudView").setVisibility(true);
				break;
			case "Analyze" :
				viewListMap.get("datumView").setVisibility(true);
				viewListMap.get("rapidView").setVisibility(true);
				
				break;
//			case "Update DB" :
//				viewListMap.get("tweetView").setVisibility(true);
//				break;
			default :
	            System.out.println("Invalid Button");
			}
			
			System.out.println("action from cmd llistner = "+ e.getActionCommand()+"ID = "+ e.getID()+"ParamString = "+e.paramString()+"hashCode = "+e.hashCode());
		}
	}
	
//	public class TextListner implements ActionListener {
//		@Override
//		public void actionPerformed(ActionEvent e) {
//			// TODO Auto-generated method stub
//			String str =  e.getSource().toString();
//			System.out.println("The value in the text field is = "+ str);
//		}
//		
//	}
	
}
