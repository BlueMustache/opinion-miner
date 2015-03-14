package factory;

import model.Subject;
import view.EvaluationView;
import view.Observer;


public class ObserverFactory implements IObserverFactory{

	public ObserverFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Observer createObserver(String type, Subject subject) {
		// TODO Auto-generated method stub
		final Observer evalView = new EvaluationView(subject);
		
		if (type.equalsIgnoreCase("evalView")) {
			return evalView;
		}
		return null;
	}

}
