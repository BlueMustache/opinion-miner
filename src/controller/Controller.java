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
	private JTextField textField = new JTextField();

	public Controller(TwitterDataSubject subject, ControlPanelView ctrlView) {
		// TODO Auto-generated constructor stub
		this.subject = subject;
		this.ctrlView = ctrlView;
		this.subject.addCommandListner(new CommandListner());
		this.ctrlView.addDocumentListener(new IDocumentListener());

		// this.textField.setText(this.ctrlView.getTextFieldStr());
		// this.textField.getDocument().addDocumentListener(new
		// IDocumentListener());

	}

	public class CommandListner implements ActionListener {
		// Action for when update is pushed
		@Override
		public void actionPerformed(ActionEvent e) {
			action = (Command) e.getSource();
			action.execute();
		}
	}

	public class IDocumentListener implements DocumentListener {

		@Override
		public void changedUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub
			subject.setTopic(e.toString());


			StringBuilder sb = new StringBuilder();
			String inputStr = "";
			for(int i=0; i < 50 ;i++ ){
			try {	
				sb.append((e.getDocument().getText(i,e.getLength())));
				
			} catch (BadLocationException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			System.out.println("new topic string = " + sb.toString());
			subject.setTopic(sb.toString());
			System.out.println("model topic value = " + subject.getTopic());
			}
			
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			// TODO Auto-generated method stub

		}

	}

}
