package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import command.BtnSetTopicByCategory;
import command.BtnSetTopicByTrend;
import twitter4j.Category;
import twitter4j.Trend;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

public class TrendsView extends JScrollPane implements Observer {
	
	private Subject subject;
	private Twitter twitterAcc;
	private String viewRef;
	private ArrayList<Trend> trendList;
	private Random random ;
	private Cloud tendCloud;
	private Cloud categoryCloud;
	private JPanel mainPanel; 
	private JPanel trendPanel; 
	private JPanel categoryPanel; 
	private BtnSetTopicByTrend btnTrend;
	private BtnSetTopicByCategory btnCategory;
	private ArrayList<BtnSetTopicByTrend> btnTrendList;
	private ArrayList<BtnSetTopicByCategory> btnCategoryList;
	private List<Category> twitterCategoryList;
	
	public TrendsView(Subject subject, String viewRef) {
		// TODO Auto-generated constructor stub
		this.subject = subject;
		subject.registerObserver(this);
		this.viewRef = viewRef;
		this.setLayout(new ScrollPaneLayout());
		this.mainPanel = new JPanel();
		this.trendPanel = new JPanel();
		this.categoryPanel = new JPanel();
		
		this.revalidate();
		this.repaint();
		trendList = new ArrayList<Trend>();
		
		this.twitterAcc =  ((TwitterDataSubject) subject).getTwitterAcc();
		
		try {
			Trends twitterTrends = this.twitterAcc.getPlaceTrends(23424803);
			for(Trend trends : twitterTrends.getTrends()){
				System.out.println("Trend 1 = "+ trends.getName());
				System.out.println("Trend Query = " + trends.getQuery());
				trendList.add(trends);
			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			this.twitterCategoryList = this.twitterAcc.getSuggestedUserCategories();
			for(Category category : twitterCategoryList){
				System.out.println("Category 1 = "+ category.getName());
			}

		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.random = new Random();
		this.tendCloud = new Cloud();
		this.categoryCloud = new Cloud();
		displaySearchSuggestions();
		
	}
	
	public void displaySearchSuggestions(){
		
		btnTrendList = new ArrayList<BtnSetTopicByTrend>();
		btnCategoryList = new ArrayList<BtnSetTopicByCategory>();
		this.mainPanel.setLayout(new FlowLayout());
		Dimension d = new Dimension(800, 1000);
		this.mainPanel.setPreferredSize(d);
		
		for(Trend trend : this.trendList){
                tendCloud.addTag(trend.getName().toString());
		}
		
		for(Category category : this.twitterCategoryList){
            categoryCloud.addTag(category.getName().toString());
		}
		int maxTag=50;
		int minTag=20;
		
		for (Tag tag : tendCloud.tags()) {
			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
			btnTrend = new BtnSetTopicByTrend(this.subject);
			btnTrendList.add(btnTrend);
            btnTrend.setText(tag.getName());
            btnTrend.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            tag.setWeight(randTag);
            btnTrend.setFont(btnTrend.getFont().deriveFont((float) tag.getWeight()));
            btnTrend.setName("trendBtn");
            Dimension trendPanelDimension = new Dimension(1000, 275);
    		this.trendPanel.setPreferredSize(trendPanelDimension);
    		trendPanel.add(btnTrend); 
    		trendPanel.setBorder(new TitledBorder (new LineBorder(Color.BLACK, 4, true),"Trends",TitledBorder.LEFT,TitledBorder.CENTER,new Font("Comic Sans MS", Font.PLAIN, 20)));
    		mainPanel.add(trendPanel);
        }
		
		for (Tag tag : categoryCloud.tags()) {
			int randTag = random.nextInt((maxTag - minTag) + 1) + minTag;
			btnCategory = new BtnSetTopicByCategory(this.subject);
			btnCategoryList.add(btnCategory);
			System.out.println("category btn added");
            btnCategory.setText(tag.getName());
            btnCategory.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            tag.setWeight(randTag);
            btnCategory.setFont(btnCategory.getFont().deriveFont((float) tag.getWeight()));
            btnCategory.setName("trendBtn");
            Dimension categoryPanelDimension = new Dimension(1000, 500);
    		this.categoryPanel.setPreferredSize(categoryPanelDimension);
    		categoryPanel.add(btnCategory); 
    		categoryPanel.setBorder(new TitledBorder (new LineBorder(Color.BLACK, 4, true),"Categorys",TitledBorder.LEFT,TitledBorder.CENTER,new Font("Comic Sans MS", Font.PLAIN, 20)));
    		mainPanel.add(categoryPanel);
        }
		
		
		
		this.setViewportView(mainPanel);
		System.out.println("trend btn list size = " + btnTrendList.size());
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		for(BtnSetTopicByTrend btn : btnTrendList){
			btn.addActionListener(commandListner);
		}
		for(BtnSetTopicByCategory btn : btnCategoryList){
			btn.addActionListener(commandListner);
		}
	}

	@Override
	public String getObserverRef() {
		// TODO Auto-generated method stub
		return null;
	}

}
