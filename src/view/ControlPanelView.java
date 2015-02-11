package view;

import java.awt.Color;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import model.Subject;
import model.TwitterDataSubject;

public class ControlPanelView extends JPanel implements Observer {
	
	private TwitterDataSubject subject;
	private JButton btnSearch = new JButton("Search");
	private JButton btnAnalyze = new JButton("Analyze");
	private JTextField textField;
	private JSplitPane splitPane = new JSplitPane();
	
	public ControlPanelView(TwitterDataSubject subject) {
		// TODO Auto-generated constructor stub
		subject.registerObserver(this);
		this.subject = subject;
		
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		textField = new JTextField();
        splitPane.setLeftComponent(textField);
        textField.setColumns(10);
        splitPane.setRightComponent(btnSearch);
		
		this.setBorder((Border) new LineBorder(new Color(0, 0, 0)));
		GroupLayout ctrlLayout = new GroupLayout(this);
		
		ctrlLayout.setHorizontalGroup(
	         	ctrlLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(ctrlLayout.createSequentialGroup()
	         			.addContainerGap()
	         			.addGroup(ctrlLayout.createParallelGroup(Alignment.LEADING)
	         				.addComponent(btnAnalyze, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
	         				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
	         			.addContainerGap())
	         );
	         ctrlLayout.setVerticalGroup(
	         	ctrlLayout.createParallelGroup(Alignment.LEADING)
	         		.addGroup(ctrlLayout.createSequentialGroup()
	         			.addGap(5)
	         			.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
	         			.addPreferredGap(ComponentPlacement.RELATED)
	         			.addComponent(btnAnalyze)
	         			.addContainerGap(164, Short.MAX_VALUE))
	         );
	         this.setLayout(ctrlLayout);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		
	}
	
	public void setBtnLayout(){
		
	}

}
