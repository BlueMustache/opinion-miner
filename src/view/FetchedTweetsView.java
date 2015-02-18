package view;

import java.util.ArrayList;

import javax.swing.*;

import controller.Controller.CommandListner;

import model.Subject;
import model.TwitterDataSubject;

public class FetchedTweetsView extends JPanel implements Observer  {

	private TwitterDataSubject subject;
	private JTextArea txtArea = new JTextArea("");
	
	public FetchedTweetsView(TwitterDataSubject subjectReference)  {
		// TODO Auto-generated constructor stub
		this.subject = subjectReference;
		subjectReference.registerObserver(this);
		this.add(txtArea);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		//this.subject = (TwitterDataSubject) subject;
		String tweetStr = "";
		ArrayList<String> tweetList = this.subject.getTweets();
		for(int i=0;i<tweetList.size();i++){
			tweetStr = tweetStr + "\n " + tweetList.get(i).toString();
//			this.txtArea.setText(tweetList.get(i).toString() + "\n");
		}
		this.txtArea.setText(tweetStr);
		System.out.println("tweets added to view");
		
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void addActionListener(SearchListner listner) {
//		// TODO Auto-generated method stub
//		
//	}


}
