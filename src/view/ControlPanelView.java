package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import command.BtnAnalyseTweets;
import command.BtnFetchTweets;
import command.BtnUpdateMongoResults;
import controller.Controller.CommandListner;
//import controller.Controller.IDocumentListener;
import model.Subject;
import model.TwitterDataSubject;

import java.awt.Font;

public class ControlPanelView extends JPanel implements Observer {
	
	private TwitterDataSubject subject;
	private BtnFetchTweets btnFetchTweets;
	private BtnAnalyseTweets btnAnalyze;
	private BtnUpdateMongoResults btnUpdateDB;
	private JTextField textField = new JTextField();
	private JSplitPane splitPane = new JSplitPane();
	private Subject subjectRef; 
	private String viewRef;
	
	public ControlPanelView(final TwitterDataSubject subjectRef, String viewRef) {
		// Constructor
		subjectRef.registerObserver(this);
		this.subject = subjectRef;
		this.viewRef = viewRef;
		
		btnFetchTweets = new BtnFetchTweets("Search", subjectRef);	
		btnAnalyze = new BtnAnalyseTweets("Analyze", subjectRef);
		btnUpdateDB = new BtnUpdateMongoResults("Update DB", subjectRef);
		setBtnLayout(); //Call set layout method to layout the buttons
		textFieldListner();// Need to try put this in the controller
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub	
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
	
	public void setBtnLayout(){	
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
	         				.addComponent(btnUpdateDB, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
	         				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
	         			.addContainerGap())
	         );
	         //btnAnalyze.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
	         ctrlLayout.setVerticalGroup(
	         	ctrlLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(ctrlLayout.createSequentialGroup()
	         			.addGap(5)
	         			.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	         			.addPreferredGap(ComponentPlacement.RELATED)
	         			.addComponent(btnAnalyze).addGap(8)
	         			.addComponent(btnUpdateDB)
	         			.addContainerGap(164, Short.MAX_VALUE))
	         );
	         this.setLayout(ctrlLayout);
	}
	@Override
	public Observer getView(/*String viewRef*/) {
		//if(viewRef.equalsIgnoreCase(this.viewRef)){
			return this;
		//}
		//return null;
	}

	public void setViewRef(String viewRef) {
		this.viewRef = viewRef;
	}
	
	public void setVisibility(boolean bool){
		this.setVisible(bool);
	}

	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		btnFetchTweets.addActionListener(commandListner);
		KeyStroke keystroke = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0, false);
		btnFetchTweets.registerKeyboardAction(commandListner, keystroke, JComponent.WHEN_FOCUSED);
		btnAnalyze.addActionListener(commandListner);
		btnUpdateDB.addActionListener(commandListner);
	}

	@Override
	public String getViewRef() {
		// TODO Auto-generated method stub
		return this.viewRef;
	}

	@Override
	public void repaintParent() {
		// TODO Auto-generated method stub
		
	}

}
