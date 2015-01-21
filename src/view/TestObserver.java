package view;

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

	

}
