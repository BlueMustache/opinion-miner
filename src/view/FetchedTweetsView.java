package view;

import java.util.ArrayList;
import java.util.Map;

import javax.swing.*;

import controller.Controller.CommandListner;
import model.Subject;
import model.TwitterDataSubject;

import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.Color;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import java.io.IOException;
import java.awt.Font;
import java.awt.Component;
import java.awt.GridLayout;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import net.miginfocom.swing.MigLayout;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

import org.json.simple.JSONObject;

public class FetchedTweetsView extends JScrollPane implements Observer {

	private TwitterDataSubject subject;
	private JPanel mainPanel;// = new JPanel();
	private ArrayList<JPanel> panelList;
	private ArrayList<JPanel> impactPanelList;
	private ArrayList<JTextArea> textAreaList;
	private ArrayList<JLabel> lableList;
	private Subject subjectRef;
	private String viewRef;
	private Map<String, Integer> tweetMap;

	public FetchedTweetsView(TwitterDataSubject subjectReference, String viewRef)
			throws IOException {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		// TODO Auto-generated constructor stub
		this.subject = subjectReference;
		this.viewRef = viewRef;
		subjectReference.registerObserver(this);

	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		super.revalidate();
		this.subject = (TwitterDataSubject) subject;
		// ArrayList<String> tweetList = this.subject.getTweets();

		ArrayList<JSONObject> mongoDataStore  = ((TwitterDataSubject) this.subject).getMongoDataStore();

		Dimension d = new Dimension(15, 15);
		this.setPreferredSize(d);
		this.tweetMap =  this.subject.getTweetMap();
		this.mainPanel = new JPanel();	
		this.setLayout(new ScrollPaneLayout());
		panelList = new ArrayList<JPanel>();
		impactPanelList = new ArrayList<JPanel>();
		textAreaList = new ArrayList<JTextArea>();
		lableList = new ArrayList<JLabel>();

		
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_panel);
		
		
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		this.setBorder(new LineBorder(Color.BLACK));
		int i = 0;
		
		for(JSONObject tweet : mongoDataStore ){
		//for (Map.Entry<String, Integer> entry : tweetMap.entrySet()) {
			// for(int i=0; i<tweetCount;i++){
			panelList.add(new JPanel());
			
			
			impactPanelList.add(new JPanel());
			panelList.get(i).setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
			impactPanelList.get(i).setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
			impactPanelList.get(i).setLayout(new FlowLayout());
			textAreaList.add(new JTextArea());
			lableList.add(new JLabel());
	
			panelList.get(i).setName("panel_" + Integer.toString(i));
			impactPanelList.get(i).setName("impactPanel_" + Integer.toString(i));
			textAreaList.get(i).setName("txtArea_" + Integer.toString(i));
			
			lableList.get(i).setName("Label_" + Integer.toString(i));
			lableList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));

			
			
			textAreaList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
			textAreaList.get(i).setWrapStyleWord(true);
			lableList.get(i).setText("<html><center>RetweetCount =" + tweet.get("retweetCount").toString() + "<br>"+ determioneTweetImpact(Integer.parseInt(tweet.get("retweetCount").toString()))+ "</center></html>");

			impactPanelList.get(i).setBackground(determineTweetImpactColor(determioneTweetImpact(Integer.parseInt(tweet.get("retweetCount").toString()))));
			textAreaList.get(i).setText(tweet.get("unProcessedTweet").toString());
			textAreaList.get(i).setLineWrap(true);
			textAreaList.get(i).setEnabled(true);
			textAreaList.get(i).setEditable(false);
			
			impactPanelList.get(i).add(lableList.get(i));
			
			GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
			gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
					Alignment.LEADING).addGroup(
					gl_panel_3
							.createSequentialGroup()
							.addContainerGap()
							.addComponent(textAreaList.get(i),
									GroupLayout.PREFERRED_SIZE, 350,
									Short.MAX_VALUE)
							.addComponent(impactPanelList.get(i),
									GroupLayout.PREFERRED_SIZE, 50,
									Short.MAX_VALUE).addContainerGap()));
			panelList.get(i).revalidate();
			panelList.get(i).repaint();
			gl_panel_3.setVerticalGroup(gl_panel_3
					.createParallelGroup(Alignment.LEADING)
					.addComponent(textAreaList.get(i),
							GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
					.addComponent(impactPanelList.get(i),
							GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE));
			panelList.get(i).revalidate();
			panelList.get(i).repaint();
			panelList.get(i).setLayout(gl_panel_3);
			gbc_panel_3.gridy = i;
			mainPanel.add(panelList.get(i), gbc_panel_3);
			mainPanel.revalidate();
			mainPanel.repaint();
			this.setViewportView(mainPanel);
			this.revalidate();
			this.repaint();
			i++;
		}
		for (Map.Entry<String, Integer> entry : tweetMap.entrySet()) {
			System.out.println(entry.getKey().toString());
		}
		System.out.println("THe text area is running and made " + i
				+ " panels & Views");

	}

	public String determioneTweetImpact(int reTweetCount) {
		if (reTweetCount <= 1000) {
			return "LOW IMPACT";
		} else if (reTweetCount > 1000 && reTweetCount <= 10000) {
			return "MEDIUM IMPACT";
		} else if (reTweetCount > 10000 && reTweetCount <= 100000) {
			return "HIGH IMPACT";
		} else {
			return "EXTREME IMPACT";
		}
	}

	public Color determineTweetImpactColor(String impact) {
		Color impactColor;

		switch (impact) {
		case "LOW IMPACT":
			impactColor = Color.GREEN;
			break;
		case "MEDIUM IMPACT":
			impactColor = Color.YELLOW;
			break;
		case "HIGH IMPACT":
			impactColor = Color.RED;
			break;
		case "EXTREME IMPACT":
			impactColor = Color.PINK;
			break;
		default:
			impactColor = Color.BLUE;
		}
		return impactColor;
	}
	
	public void repaintParent(){ 
	      this.revalidate();
	      this.repaint();
	  }


	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub

	}

	@Override
	public Observer getView() {
		// if(viewRef.equalsIgnoreCase(this.viewRef)){
		return this;
		// }
		// return null;
	}

	@Override
	public void setVisibility(boolean bool) {
		// TODO Auto-generated method stub
		// this.getParent().setEnabled(bool);;
		this.getViewport().setVisible(bool);
		// tidy this to make a ternary if statement
		if (!bool) {
			this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		} else {
			this.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
		}

	}

	@Override
	public String getViewRef() {
		// TODO Auto-generated method stub
		return this.viewRef;
	}
}
