package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

import model.TwitterDataSubject;
import model.Subject;
import controller.Controller.CommandListner;

public class RapidMinerView extends JScrollPane implements Observer{

	private Subject subjectRef;
	private ArrayList<JPanel> panelList;
	private ArrayList<JTextArea> textAreaList; 
	private ArrayList<JButton> btnList;
	private JPanel mainPanel = new JPanel();

	public RapidMinerView(Subject subjectRef) {
		// TODO Auto-generated constructor stub
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectRef;
		subjectRef.registerObserver(this);
		Dimension d = new Dimension(600,800);
		this.setPreferredSize(d);
		
		panelList = new ArrayList<JPanel>();
		textAreaList = new ArrayList<JTextArea>();
		btnList = new ArrayList<JButton>();
	
		setViewportView(mainPanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[] { 0, 0 };
		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
		mainPanel.setLayout(gbl_panel);
		
		this.setViewportView(mainPanel);		
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.subjectRef =  subject;

		ArrayList<JSONObject> rapidResults = ((TwitterDataSubject) this.subjectRef).getRapidResultsJSON();
		
		int tweetCount = ((TwitterDataSubject) subject).getTweetCount(); 
		
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		
		for(int i=0; i<rapidResults.size();i++){	
		panelList.add(new JPanel());
		panelList.get(i).setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		textAreaList.add(new JTextArea());
		btnList.add(new JButton());
		
		btnList.get(i).setName("btn_"+Integer.toString(i));
		btnList.get(i).setBackground(Color.red);
		btnList.get(i).setSize(20, 20);
		
		btnList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		panelList.get(i).setName("panel_"+Integer.toString(i));
		textAreaList.get(i).setName("txtArea_"+Integer.toString(i));
		
		
		gbc_panel_3.gridy = i;
		mainPanel.add(panelList.get(i), gbc_panel_3);
		
		textAreaList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		textAreaList.get(i).setWrapStyleWord(true);
		textAreaList.get(i).setText(((TwitterDataSubject) subject).getPreProcessedTweetList().get(i).toString());	//THIS IS A BUG
		
		btnList.get(i).setText(rapidResults.get(i).get("result").toString());
		Color btnColor = rapidResults.get(i).get("result").equals("positive") ? Color.GREEN : Color.RED;

		btnList.get(i).setBackground(btnColor);
		
		textAreaList.get(i).setLineWrap(true);
		textAreaList.get(i).setEnabled(true);
		textAreaList.get(i).setEditable(false);
		GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(textAreaList.get(i), GroupLayout.PREFERRED_SIZE, 428, Short.MAX_VALUE)
					.addComponent(btnList.get(i),GroupLayout.PREFERRED_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textAreaList.get(i), GroupLayout.PREFERRED_SIZE, 37, Short.MAX_VALUE)
				.addComponent(btnList.get(i), GroupLayout.PREFERRED_SIZE, 37, Short.MAX_VALUE)
		);
		panelList.get(i).setLayout(gl_panel_3);

		}

		System.out.println("The text area is running and made "+ tweetCount +" panels & Views");
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub

	}
}
