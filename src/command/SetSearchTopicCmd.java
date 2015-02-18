//package command;
//
//import view.ControlPanelView;
//import model.TwitterDataSubject;
//
//public class SetSearchTopicCmd implements Command {
//
//	
//	private TwitterDataSubject subject;
//	private ControlPanelView ctrlView;
//	
//	public SetSearchTopicCmd(TwitterDataSubject subject, ControlPanelView ctrlView) {
//		// TODO Auto-generated constructor stub
//		this.subject = subject;
//		this.ctrlView = ctrlView;
//	}
//
//	@Override
//	public void execute() {
//		// TODO Auto-generated method stub
//		this.subject.setTopic(this.ctrlView.getTextField());
//		System.out.println("search method executed");
//		
//	}
//
//}
