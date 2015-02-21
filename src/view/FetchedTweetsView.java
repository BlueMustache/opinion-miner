package view;

import java.util.ArrayList;

import javax.swing.*;

import controller.Controller.CommandListner;
import controller.Controller.TextListner;
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

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class FetchedTweetsView extends JScrollPane implements Observer {

	private TwitterDataSubject subject;
	private JPanel mainPanel = new JPanel();
	private ArrayList<JPanel> panelList; //= new ArrayList<JPanel>();
	private ArrayList<JTextArea> textAreaList; // = new ArrayList<JTextArea>();
	private ArrayList<GridBagConstraints> gridConstraintsList = new ArrayList<GridBagConstraints>();
	
	public FetchedTweetsView(TwitterDataSubject subjectReference) throws IOException  {
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		setBorder(new LineBorder(new Color(0, 0, 0)));
		// TODO Auto-generated constructor stub
		this.subject = subjectReference;
		subjectReference.registerObserver(this);
		
		this.setLayout(new ScrollPaneLayout());
		
		panelList = new ArrayList<JPanel>();
		textAreaList = new ArrayList<JTextArea>();
		//JPanel panel = new JPanel();
		setViewportView(mainPanel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
		mainPanel.setLayout(gbl_panel);
		
		
		//gridSetUp();
		setLableLayout();	
		this.setViewportView(mainPanel);
	}
	
	public void gridSetUp(){
		
		int tweetCount = subject.getTweetCount(); 
		
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		
		
		for(int i=0; i<=tweetCount;i++){		
		panelList.add(new JPanel());
		panelList.get(i).setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		textAreaList.add(new JTextArea());
		
		panelList.get(i).setName("panel_"+Integer.toString(i));
		textAreaList.get(i).setName("txtArea_"+Integer.toString(i));
		
		gbc_panel_3.gridy = i;
		mainPanel.add(panelList.get(i), gbc_panel_3);
		
		textAreaList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		textAreaList.get(i).setWrapStyleWord(true);
		textAreaList.get(i).setText("");
		textAreaList.get(i).setLineWrap(true);
		textAreaList.get(i).setEnabled(true);
		textAreaList.get(i).setEditable(true);
		GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(textAreaList.get(i), GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textAreaList.get(i), GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
		);
		panelList.get(i).setLayout(gl_panel_3);

		}
		System.out.println("THe text area is running and made "+ tweetCount +" panels & Views");
	}
	
	public void panelSetUp(JPanel panel,JTextArea textArea){
		
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		this.subject = (TwitterDataSubject) subject;
//		String tweetStr = "";
		ArrayList<String> tweetList = this.subject.getTweets();
//		for(int i=0;i<tweetList.size();i++){
//			tweetStr = tweetStr + "\n " + tweetList.get(i).toString();
//			
//		}
//		 String html1 = "<html><body style='width: ";
//	     String html2 = "px'>";
		
		
//		tweetLable_1.setText(html1+"200"+html2+tweetList.get(0).toString());
//		tweetLable_2.setText(html1+"200"+html2+tweetList.get(1).toString());
//		tweetLable_3.setText(html1+"200"+html2+tweetList.get(2).toString());
//		tweetLable_4.setText(html1+"200"+html2+tweetList.get(3).toString());
//		tweetLable_5.setText(html1+"200"+html2+tweetList.get(4).toString());
		//this.textField.setText(tweetStr);
//		System.out.println("tweets added to view");
//		gridSetUp();
		
		int tweetCount = ((TwitterDataSubject) subject).getTweetCount(); 
		
		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
		gbc_panel_3.fill = GridBagConstraints.BOTH;
		gbc_panel_3.gridx = 0;
		
		
		for(int i=0; i<tweetCount;i++){		
		panelList.add(new JPanel());
		panelList.get(i).setBorder(new LineBorder(Color.LIGHT_GRAY, 2, true));
		textAreaList.add(new JTextArea());
		
		panelList.get(i).setName("panel_"+Integer.toString(i));
		textAreaList.get(i).setName("txtArea_"+Integer.toString(i));
		
		gbc_panel_3.gridy = i;
		mainPanel.add(panelList.get(i), gbc_panel_3);
		
		textAreaList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
		textAreaList.get(i).setWrapStyleWord(true);
		textAreaList.get(i).setText(tweetList.get(i).toString());
		textAreaList.get(i).setLineWrap(true);
		textAreaList.get(i).setEnabled(true);
		textAreaList.get(i).setEditable(false);
		GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(textAreaList.get(i), GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(textAreaList.get(i), GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
		);
		panelList.get(i).setLayout(gl_panel_3);

		}
		System.out.println("THe text area is running and made "+ tweetCount +" panels & Views");
		
		
		
		
	}

	@Override
	public void addActionListener(CommandListner commandListner) {
		// TODO Auto-generated method stub
		
	}
	
	public void setLableLayout(){
//		Border border = LineBorder.createGrayLineBorder();
//		tweetLable_1.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
//		tweetLable_1.setBorder(border);	
//		
//		tweetLable_2.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
//		tweetLable_2.setBorder(border);	
//		
//		tweetLable_3.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
//		tweetLable_3.setBorder(border);	
//		
//		tweetLable_4.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
//		tweetLable_4.setBorder(border);	
//		
//		tweetLable_5.setFont(new Font("Comic Sans MS", Font.BOLD, 16));
//		tweetLable_5.setBorder(border);
//		ImageIcon icon = new ImageIcon("D:\\Workspace\\TattoDemo\\pics\\textBubble.png");
//		JButton btnNewButton = new JButton(icon);
//		btnNewButton.setForeground(Color.WHITE);
//		btnNewButton.setBackground(Color.LIGHT_GRAY);
//		btnNewButton.setEnabled(false);
//		this.add(tweetLable_1);
//		this.add(tweetLable_2);
//		this.add(tweetLable_3);
//		this.add(tweetLable_4);
//		this.add(tweetLable_5);
//		this.add(btnNewButton);
//		//btnNewButton.setSelectedIcon(new ImageIcon("D:\\Workspace\\TattoDemo\\pics\\Button-Delete-icon.png"));
//		
//		GroupLayout groupLayout = new GroupLayout(panel);
//		groupLayout.setHorizontalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addContainerGap()
//					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//								.addComponent(tweetLable_2, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
//								.addComponent(tweetLable_3, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
//								.addComponent(tweetLable_4, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE))
//							.addGap(238))
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(527)
//							.addComponent(tweetLable_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//							.addGap(388))
//						.addGroup(groupLayout.createSequentialGroup()
//							.addComponent(tweetLable_5, GroupLayout.DEFAULT_SIZE, 200, GroupLayout.PREFERRED_SIZE)
//							.addPreferredGap(ComponentPlacement.RELATED, 238, Short.MAX_VALUE)))
//					.addPreferredGap(ComponentPlacement.RELATED)
//					.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 361, GroupLayout.PREFERRED_SIZE)
//					.addGap(90))
//		);
//		groupLayout.setVerticalGroup(
//			groupLayout.createParallelGroup(Alignment.LEADING)
//				.addGroup(groupLayout.createSequentialGroup()
//					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(20)
//							.addComponent(tweetLable_1, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//							.addGap(161)
//							.addComponent(tweetLable_2, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//							.addPreferredGap(ComponentPlacement.UNRELATED)
//							.addComponent(tweetLable_3, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//							.addGap(18)
//							.addComponent(tweetLable_4, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
//							.addPreferredGap(ComponentPlacement.RELATED)
//							.addComponent(tweetLable_5, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
//						.addGroup(groupLayout.createSequentialGroup()
//							.addGap(221)
//							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 208, GroupLayout.PREFERRED_SIZE)))
//					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//		);
//		panel.setLayout(groupLayout);
		
	}
}
