import view.TestObserver;
import model.ConcreteSubject;
import model.DatumBoxSubject;
import model.Subject;
import model.TwitterDataSubject;


public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Driver running");
		
		Subject twitterSubject = new ConcreteSubject();
		Subject datumSubject = new ConcreteSubject();
		//System.out.println("Concrete subject created");
		System.out.println(twitterSubject.description());
		twitterSubject = new TwitterDataSubject(twitterSubject);
		datumSubject = new DatumBoxSubject(datumSubject);
		///System.out.println("Concrete Subject decorated to TwitterData Subject");
		System.out.println(twitterSubject.description());
		TestObserver temp = new TestObserver(twitterSubject);
		((TwitterDataSubject) twitterSubject).setTweetStore("#ISIS exclude:retweets");
		((DatumBoxSubject) datumSubject).setDatumAnalysisFile();
		
		
		
		
	}

}
