//package view;
//
//import java.awt.Dimension;
//import java.awt.Font;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.ScrollPaneLayout;
//
//import model.Subject;
//import model.TwitterDataSubject;
//
//import org.mcavallo.opencloud.Cloud;
//import org.mcavallo.opencloud.Tag;
//
//import twitter4j.Category;
//import twitter4j.ResponseList;
//import twitter4j.Status;
//import twitter4j.Trend;
//import twitter4j.Trends;
//import twitter4j.Twitter;
//import twitter4j.TwitterException;
//import twitter4j.User;
//import command.BtnSetTopicByCategory;
//import command.BtnSetTopicByTrend;
//import controller.Controller.CommandListner;
//
//public class CategoryView extends JScrollPane implements Observer {
//	
//	private Subject subject;
//	private Twitter twitterAcc;
//	private String viewRef;
//	private Random random ;
//	private Cloud cloud;
//	private JPanel mainPanel; 
//	private BtnSetTopicByCategory btnCategory;
//	private ArrayList<BtnSetTopicByCategory> btnCategoryList;
//	private List<Category> twitterCategoryList;
//	
//	public CategoryView(Subject subject, String viewRef) {
//		// TODO Auto-generated constructor stub
//		this.subject = subject;
//		subject.registerObserver(this);
//		this.viewRef = viewRef;
//		this.setLayout(new ScrollPaneLayout());
//		this.mainPanel = new JPanel();
//		
//		this.twitterAcc =  ((TwitterDataSubject) subject).getTwitterAcc();
//		
//		try {
//			this.twitterCategoryList = this.twitterAcc.getSuggestedUserCategories();
//			for(Category category : twitterCategoryList){
//				System.out.println("Category 1 = "+ category.getName());
//			}
//
//		} catch (TwitterException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		this.random = new Random();
//		this.cloud = new Cloud();
//		displayCategorys();
//		
//	}
//	
//	public void displayCategorys(){
//		btnCategoryList = new ArrayList<BtnSetTopicByCategory>();
//		
//		for(Category category : this.twitterCategoryList){
//                cloud.addTag(category.getName().toString());
//		}
//		int maxTag=50;
//		int minTag=20;
//		
//		for (Tag tag : cloud.tags()) {
//			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
//			btnCategory = new BtnSetTopicByCategory(this.subject);
//			btnCategoryList.add(btnCategory);
//			System.out.println("category btn added");
//            btnCategory.setText(tag.getName());
//            btnCategory.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
//            tag.setWeight(randTag);
//            btnCategory.setFont(btnCategory.getFont().deriveFont((float) tag.getWeight()));
//            btnCategory.setName("trendBtn");
//            Dimension d = new Dimension(800, 475);
//    		this.mainPanel.setPreferredSize(d);
//            mainPanel.add(btnCategory);          
//        }
//		this.setViewportView(mainPanel);
//		System.out.println("Category btn list size = " + btnCategoryList.size());
//	}
//
//	@Override
//	public void update(Subject subject) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void addActionListener(CommandListner commandListner) {
//		// TODO Auto-generated method stub
//		for(BtnSetTopicByCategory btn : btnCategoryList){
//			btn.addActionListener(commandListner);
//		}
//		
//	}
//
//	@Override
//	public String getObserverRef() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//}
