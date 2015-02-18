package view;

import controller.Controller.CommandListner;

import model.Subject;

public class TestObserver implements Observer {

	private Subject subject;
	
	public TestObserver(Subject subject) {
		super();
		
		subject.registerObserver(this);
		this.subject = subject;
		System.out.println("Subject registered success");//TEST
	}
	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		
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
