package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;

import command.Command;
import view.ControlPanelView;
import model.TwitterDataSubject;

public class Controller {

	public TwitterDataSubject subject;
	private ControlPanelView ctrlView;
	private Command action;

	public Controller(TwitterDataSubject subject, ControlPanelView ctrlView) {
		// Constructor for the controller, take the control view and the twitter data subject as params
		this.subject = subject;
		this.ctrlView = ctrlView;
		this.subject.addCommandListner(new CommandListner());
		//this.ctrlView.addDocumentListener(new IDocumentListener());
		//this.subject.addCommandListner(new TextListner());

	}

	public class CommandListner implements ActionListener {
		// Action all button commands
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
			String str =  e.getSource().toString();
			action.execute();
		}
	}
	
	public class TextListner implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			String str =  e.getSource().toString();
			System.out.println("The value in the text field is = "+ str);
		}
		
	}
	

//	public class IDocumentListener implements DocumentListener {	
//		@Override
//		public void changedUpdate(DocumentEvent e) {
//			// implemented automatically, but not required
//		}
//		@Override
//		public void insertUpdate(DocumentEvent e) {
//			// TODO Auto-generated method stub
//			//subject.setTopic(e.getDocument().toString());
//
//			StringBuilder sb = new StringBuilder();
//			String inputStr = "";
//			//for(int i=0; i < 50 ;i++ ){
//			try {	
//				sb.append((e.getDocument().getText(i,e.getLength())));
//				
//			} catch (BadLocationException e2) {
//				// TODO Auto-generated catch block
//				e2.printStackTrace();
//			}
//			System.out.println("new topic string = " + sb.toString());
//			subject.setTopic(sb.toString());
//			System.out.println("model topic value = " + subject.getTopic());
//			//}		
//		}
	//	@Override
//		public void removeUpdate(DocumentEvent e) {
//			//implemented automatically, but not required
//		}
	//}

}
