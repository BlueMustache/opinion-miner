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
import model.Subject;
import model.TwitterDataSubject;

public class Controller {

	public Subject subject;
	private ControlPanelView ctrlView;
	private Command action;

	public Controller(Subject subject, ControlPanelView ctrlView) {
		// Constructor for the controller, take the control view and the twitter data subject as params
		this.subject = subject;
		this.ctrlView = ctrlView;
		this.subject.addCommandListner(new CommandListner());


	}

	public class CommandListner implements ActionListener {
		// Action all button commands
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
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
	
}
