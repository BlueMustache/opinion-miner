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
	public FetchedTweetsView(TwitterDataSubject subjectReference) throws IOException  {
		setBorder(new LineBorder(new Color(0, 0, 0)));
		// TODO Auto-generated constructor stub
		this.subject = subjectReference;
		subjectReference.registerObserver(this);
		
		this.setLayout(new ScrollPaneLayout());
		
		JPanel panel = new JPanel();
		setViewportView(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0};
		gbl_panel.rowHeights = new int[]{0};
		gbl_panel.columnWeights = new double[]{Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		setLableLayout();	
		//panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
	}
	
	public void gridSetUp(String str1){
		
		int tweetCount = subject.getTweetCount(); 
		String str = str1;
		
		for(int i=0; i<=tweetCount;i++){		
		//panel.add(new javax.swing.JScrollPane());
	//	panel.add(new  JTextArea("name"+ Integer.toString(i)));
		new javax.swing.JScrollPane();
		//JScrollPane str1 = new JScrollPane();
		GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
		gbc_scrollPane_1.insets = new Insets(0, 0, 5, 0);
		gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
		gbc_scrollPane_1.gridx = 0;
		gbc_scrollPane_1.gridy = 7;
		}
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		//this.subject = (TwitterDataSubject) subject;
		String tweetStr = "";
		ArrayList<String> tweetList = this.subject.getTweets();
//		for(int i=0;i<tweetList.size();i++){
//			tweetStr = tweetStr + "\n " + tweetList.get(i).toString();
//			
//		}
		 String html1 = "<html><body style='width: ";
	     String html2 = "px'>";
		
		
//		tweetLable_1.setText(html1+"200"+html2+tweetList.get(0).toString());
//		tweetLable_2.setText(html1+"200"+html2+tweetList.get(1).toString());
//		tweetLable_3.setText(html1+"200"+html2+tweetList.get(2).toString());
//		tweetLable_4.setText(html1+"200"+html2+tweetList.get(3).toString());
//		tweetLable_5.setText(html1+"200"+html2+tweetList.get(4).toString());
		//this.textField.setText(tweetStr);
		System.out.println("tweets added to view");
		
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
