package view;

import javax.swing.*;

import model.Subject;
import model.TwitterDataSubject;

public class RapidMinerView extends JPanel implements Observer  {

	private TwitterDataSubject subject;
	private JTextArea txtArea = new JTextArea("Open File to process");
	
	public RapidMinerView(TwitterDataSubject subjectReference)  {
		// TODO Auto-generated constructor stub
		this.subject = subjectReference;
		subjectReference.registerObserver(this);
		this.add(txtArea);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		//this.subject = (TwitterDataSubject) subject;
		this.txtArea.setText(this.subject.getTweetDataStore());
		
	}

}
