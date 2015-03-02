package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import command.BtnAnalyseTweets;
import command.BtnFetchTweets;
import controller.Controller.CommandListner;
//import controller.Controller.IDocumentListener;
import controller.Controller.TextListner;
import model.Subject;
import model.TwitterDataSubject;

import java.awt.Font;

public class ControlPanelView extends JPanel implements Observer {
	
	private TwitterDataSubject subject;
	private BtnFetchTweets btnFetchTweets;
	private BtnAnalyseTweets btnAnalyze;
	//private JButton btnAnalyze = new JButton("Analyze");
	private JTextField textField = new JTextField();
	private JSplitPane splitPane = new JSplitPane();
	private Subject subjectRef; 
	
	public ControlPanelView(final Subject subjectRef) {
		// Constructor
		subjectRef.registerObserver(this);
		this.subject = subject;
		
		btnFetchTweets = new BtnFetchTweets("Search", subjectRef);
		btnAnalyze = new BtnAnalyseTweets("Analyze", subjectRef);
		setBtnLayout(); //Call set layout method to layout the buttons
		textFieldListner();// Need to try put this in the controller
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub	
	}
	
	public String getTextFieldStr() {
		return this.textField.toString();
	}
	
	//This is not good practice but works much better
	public void textFieldListner(){
		textField.getDocument().addDocumentListener(new DocumentListener(){

			@Override
			public void changedUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Topic set. Topic = "+ textField.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				((TwitterDataSubject)subject).setTopic(textField.getText());
				System.out.println("Topic set. Topic = "+ textField.getText());
			}

			@Override
			public void removeUpdate(DocumentEvent arg0) {
				// TODO Auto-generated method stub
				//System.out.println("Topic set. Topic = "+ textField.getText());
			}
			
		});
	}
	
	public void setBtnLayout(/*JSplitPane pane,JButton btnAnalyze*/){	
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setLeftComponent(textField);
        textField.setColumns(10);
        splitPane.setRightComponent(btnFetchTweets);
		
		this.setBorder((Border) new LineBorder(new Color(0, 0, 0)));
		GroupLayout ctrlLayout = new GroupLayout(this);
		
		ctrlLayout.setHorizontalGroup(
	         	ctrlLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(ctrlLayout.createSequentialGroup()
	         			.addContainerGap()
	         			.addGroup(ctrlLayout.createParallelGroup(Alignment.LEADING)
	         				.addComponent(btnAnalyze, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
	         				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
	         			.addContainerGap())
	         );
	         btnAnalyze.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
	         ctrlLayout.setVerticalGroup(
	         	ctrlLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(ctrlLayout.createSequentialGroup()
	         			.addGap(5)
	         			.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	         			.addPreferredGap(ComponentPlacement.RELATED)
	         			.addComponent(btnAnalyze)
	         			.addContainerGap(164, Short.MAX_VALUE))
	         );
	         this.setLayout(ctrlLayout);
	}
	
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		btnFetchTweets.addActionListener(commandListner);
		btnAnalyze.addActionListener(commandListner);
	}

}
