package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

import model.TwitterDataSubject;
import model.Subject;
import controller.Controller.CommandListner;

public class RapidMinerView extends JScrollPane implements Observer{

	private Subject subjectRef;
	private ArrayList<JPanel> panelList;
	private ArrayList<JTextArea> textAreaList; 
	private ArrayList<JPanel> sentimentPanelList;
	private ArrayList<JLabel> lableList;
	private JPanel mainPanel;// = new JPanel();
	private String observerRef;

	public RapidMinerView(Subject subjectRef, String viewRef) {
		// TODO Auto-generated constructor stub
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;
		this.observerRef = viewRef;
		subjectRef.registerObserver(this,viewRef);		
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.subjectRef =  subject;

		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef).getMongoDataStore();
		
		int tweetCount = ((TwitterDataSubject) subject).getTweetCount(); 
		
		Dimension d = new Dimension(15,15);
		this.setPreferredSize(d);
		this.setLayout(new ScrollPaneLayout());
		panelList = new ArrayList<JPanel>();
		textAreaList = new ArrayList<JTextArea>();
		sentimentPanelList = new ArrayList<JPanel>();
		lableList = new ArrayList<JLabel>();
		this.mainPanel = new JPanel();
	

		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_panel);
		
	
		
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		
		for(int i=0; i<mongoDataStore.size();i++){	
		panelList.add(new JPanel());
		sentimentPanelList.add(new JPanel());
		lableList.add(new JLabel());
		textAreaList.add(new JTextArea());
		
		sentimentPanelList.get(i).setName("impactPanel_" + Integer.toString(i));
		sentimentPanelList.get(i).setLayout(new FlowLayout());
		panelList.get(i).setName("panel_"+Integer.toString(i));
		textAreaList.get(i).setName("txtArea_"+Integer.toString(i));
		lableList.get(i).setFont(new Font("Gotham Medium", Font.PLAIN, 20));
		lableList.get(i).setForeground(new Color(0, 132, 180));
		
		gbc_panel_3.gridy = i;
		mainPanel.add(panelList.get(i), gbc_panel_3);
		
		textAreaList.get(i).setFont(new Font("Gotham Book", Font.PLAIN, 20));
		textAreaList.get(i).setWrapStyleWord(true);
		textAreaList.get(i).setText(mongoDataStore.get(i).get("unProcessedTweet").toString());	//THIS IS A BUG
		
		lableList.get(i).setText("<html><center>"+"<br>"+mongoDataStore.get(i).get("RapidResult").toString()+"</center></html>");
		Color colour = mongoDataStore.get(i).get("RapidResult").equals("Positive") ? new Color(113,   213,   160) : new Color(236,   102,   111);
		panelList.get(i).setBorder(new LineBorder(colour, 4, true));
		sentimentPanelList.get(i).setBackground(colour);
		sentimentPanelList.get(i).add(lableList.get(i));
		
		textAreaList.get(i).setLineWrap(true);
		textAreaList.get(i).setEnabled(true);
		textAreaList.get(i).setEditable(false);
		GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGap(10)
					.addComponent(textAreaList.get(i),GroupLayout.PREFERRED_SIZE, 428,Short.MAX_VALUE).addGap(100)
					.addComponent(sentimentPanelList.get(i),GroupLayout.PREFERRED_SIZE, 50,Short.MAX_VALUE)));
		panelList.get(i).revalidate();
		panelList.get(i).repaint();
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textAreaList.get(i), GroupLayout.PREFERRED_SIZE, 100, Short.MAX_VALUE)
				.addComponent(sentimentPanelList.get(i), GroupLayout.PREFERRED_SIZE,30, Short.MAX_VALUE));
		panelList.get(i).revalidate();
		panelList.get(i).repaint();
		panelList.get(i).setLayout(gl_panel_3);
		mainPanel.revalidate();
		mainPanel.repaint();
		this.setViewportView(mainPanel);
		this.revalidate();
		this.repaint();

		}

		System.out.println("Rapidminer View is running and made "+ tweetCount +" panels & Views");
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getObserverRef() {
		// TODO Auto-generated method stub
		return null;
	}
}
