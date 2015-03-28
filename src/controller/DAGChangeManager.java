//package controller;
//
//import java.util.Map;
//
//import model.Subject;
//import view.Observer;
//
//public class DAGChangeManager  implements ChangeManager{
//	
//	private Map<Subject, Observer> SubjectObservermapping;
//	
//	public DAGChangeManager() {
//		// TODO Auto-generated constructor stub
//	}
//
//	@Override
//	public void register(Subject subject, Observer observer) {
//		// TODO Auto-generated method stub
////		subject.registerObserver(observer);
//		SubjectObservermapping.put(subject, observer);
//		
//	}
//
//	@Override
//	public void unRegister(Subject subject, Observer observer) {
//		// TODO Auto-generated method stub
//		SubjectObservermapping.remove(subject);
//	}
//
//	@Override
//	public void notifyChange(Subject subject) {
//		// TODO Auto-generated method stub
//		SubjectObservermapping.get(subject).
//	}
//
//}
