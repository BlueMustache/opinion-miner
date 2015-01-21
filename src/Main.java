import view.TestObserver;
import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Driver running");
		
		Subject subject = new ConcreteSubject();
		//System.out.println("Concrete subject created");
		System.out.println(subject.description());
		subject = new TwitterDataSubject(subject);
		///System.out.println("Concrete Subject decorated to TwitterData Subject");
		System.out.println(subject.description());
		TestObserver temp = new TestObserver(subject);
		((TwitterDataSubject) subject).setTweetStore("#ISIS exclude:retweets");
		
		
		
		
	}

}
