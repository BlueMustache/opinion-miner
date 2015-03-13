package view;

import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneLayout;

import org.mcavallo.opencloud.Cloud;
import org.mcavallo.opencloud.Tag;

import command.BtnSetTopicByTrend;

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
	private Cloud cloud;
	private JPanel mainPanel; 
	private BtnSetTopicByTrend btnTrend;
	private ArrayList<BtnSetTopicByTrend> btnTrendList;
	
	public TrendsView(Subject subject, String viewRef) {
		// TODO Auto-generated constructor stub
		this.subject = subject;
		subject.registerObserver(this);
		this.viewRef = viewRef;
		this.setLayout(new ScrollPaneLayout());
		this.mainPanel = new JPanel();
		
		this.revalidate();
		this.repaint();
		trendList = new ArrayList<Trend>();
		
		this.twitterAcc =  ((TwitterDataSubject) subject).getTwitterAcc();
		
		try {
			Trends twitterTrends = this.twitterAcc.getPlaceTrends(23424803);
			for(Trend trends : twitterTrends.getTrends()){
				System.out.println("Trend 1 = "+ trends.getName());
				trendList.add(trends);
			}
//			twitterTrends = this.twitterAcc.getPlaceTrends(1);
//			for(Trend trends : twitterTrends.getTrends()){
//				System.out.println("Trend 1 = "+ trends.getName());
//				trendList.add(trends);
//			}
		} catch (TwitterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.random = new Random();
		this.cloud = new Cloud();
		displayTrends();
		
	}
	
	public void displayTrends(){
		
		btnTrendList = new ArrayList<BtnSetTopicByTrend>();
		
		for(Trend trend : this.trendList){
			for (int i = random.nextInt(this.trendList.size()); i > 0; i--) {
                cloud.addTag(trend.getName().toString());
            }
		}
		
		for (Tag tag : cloud.tags()) {
			btnTrend = new BtnSetTopicByTrend(this.subject);
			btnTrendList.add(btnTrend);
            btnTrend.setText(tag.getName());
            btnTrend.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
            btnTrend.setFont(btnTrend.getFont().deriveFont((float) tag.getWeight() * 20));
            btnTrend.setName("trendBtn");
            Dimension d = new Dimension(800, 475);
    		this.mainPanel.setPreferredSize(d);
            mainPanel.add(btnTrend);          
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
	}

	@Override
	public Observer getView() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setVisibility(boolean bool) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getViewRef() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void repaintParent() {
		// TODO Auto-generated method stub
		
	}

}
