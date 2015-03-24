package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JScrollPane;

import model.Subject;
import model.TwitterDataSubject;
import controller.Controller.CommandListner;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.ScrollPaneLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.LineBorder;

import org.json.simple.JSONObject;

public class EvaluationView extends JScrollPane implements Observer {

//	private TwitterDataSubject subject;
	private JPanel mainPanel;
	private ArrayList<JPanel> panelList;
	private ArrayList<JPanel> resultsPanelList;
	private ArrayList<JPanel> datumResultsPanelList;
	private ArrayList<JPanel> rapidResultsPanelList;
	private ArrayList<JPanel> radioBtnPanelList;
	private ArrayList<JTextArea> textAreaList;
	private ArrayList<JLabel> lableList;
	private ArrayList<JRadioButton> radioBtnlistPos;
	private ArrayList<JRadioButton> radioBtnlistNeg;
	private ArrayList<JRadioButton> radioBtnlistNeu;
	private ArrayList<JButton> submitBtnlist;
	private ArrayList<ButtonGroup> btnGroupList;
	private Subject subjectRef;
	private String observerRef;
	 

	public EvaluationView(Subject subjectReference, String observserRef) {
		// TODO Auto-generated constructor stub
		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		this.subjectRef = subjectReference;
		this.observerRef = observserRef;
		subjectReference.registerObserver(this,observserRef);
	}

	@Override
	public void update(Subject subject) {
		// TODO Auto-generated method stub
		super.revalidate();
		this.subjectRef = (TwitterDataSubject) subject;
		// ArrayList<String> tweetList = this.subject.getTweets();

		final ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subjectRef).getMongoDataStore();

		Dimension d = new Dimension(15, 15);
		this.setPreferredSize(d);
		this.mainPanel = new JPanel();
		this.setLayout(new ScrollPaneLayout());
		panelList = new ArrayList<JPanel>();
		resultsPanelList = new ArrayList<JPanel>();
		textAreaList = new ArrayList<JTextArea>();
		lableList = new ArrayList<JLabel>();
		radioBtnlistPos =new ArrayList<JRadioButton>();
		radioBtnlistNeg =new ArrayList<JRadioButton>();
		radioBtnlistNeu =new ArrayList<JRadioButton>();
		radioBtnPanelList = new ArrayList<JPanel>();
		btnGroupList = new ArrayList<ButtonGroup>();
		submitBtnlist = new ArrayList<JButton>();
		datumResultsPanelList = new ArrayList<JPanel>();
		rapidResultsPanelList = new ArrayList<JPanel>();

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

		for (JSONObject tweet : mongoDataStore) {
			final int radioActioCount = i;
			panelList.add(new JPanel());
			datumResultsPanelList.add(new JPanel());//might get rid of these
			rapidResultsPanelList.add(new JPanel());
			radioBtnPanelList.add(new JPanel());
			btnGroupList.add(new ButtonGroup( ));
			
			submitBtnlist.add(new JButton());
			radioBtnPanelList.get(i).setBorder(new LineBorder(Color.black, 2, true));
			radioBtnPanelList.get(i).setName("rPanel_"+Integer.toString(i));
			radioBtnPanelList.get(i).setLayout(new FlowLayout());
			
			radioBtnlistPos.add(new JRadioButton());
			radioBtnlistPos.get(i).setName("radioBtnPos_"+Integer.toString(i));
			radioBtnlistPos.get(i).setText("Positive");
			radioBtnlistPos.get(i).setVerticalTextPosition(JRadioButton.BOTTOM);
			radioBtnlistPos.get(i).setHorizontalTextPosition(JRadioButton.CENTER);
			
			radioBtnlistNeg.add(new JRadioButton());
			radioBtnlistNeg.get(i).setName("radioBtnNeg_"+Integer.toString(i));
			radioBtnlistNeg.get(i).setText("Negative");
			radioBtnlistNeg.get(i).setVerticalTextPosition(JRadioButton.BOTTOM);
			radioBtnlistNeg.get(i).setHorizontalTextPosition(JRadioButton.CENTER);
			
			radioBtnlistNeu.add(new JRadioButton());
			radioBtnlistNeu.get(i).setName("radioBtnNeu_"+Integer.toString(i));
			radioBtnlistNeu.get(i).setText("Neutral");
			radioBtnlistNeu.get(i).setVerticalTextPosition(JRadioButton.BOTTOM);
			radioBtnlistNeu.get(i).setHorizontalTextPosition(JRadioButton.CENTER);
			submitBtnlist.get(i).setName("subBtn_"+Integer.toString(i));
			submitBtnlist.get(i).setSize(10, 10);
			submitBtnlist.get(i).setText("Submit");
			submitBtnlist.get(i).addActionListener(new ActionListener(){
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					// TODO Auto-generated method stub
					if(radioBtnlistPos.get(radioActioCount).isSelected()){
						System.out.println("Radio btn Pos "+ radioActioCount + " Selected");
						mongoDataStore.get(radioActioCount).put("userClasification", "postiive");
						for(JSONObject obj :mongoDataStore ){
							System.out.println(obj.toString());
						}
					}
					if(radioBtnlistNeg.get(radioActioCount).isSelected()){
						System.out.println("Radio btn Neg "+ radioActioCount + " Selected");
						mongoDataStore.get(radioActioCount).put("userClasification", "negative");
						for(JSONObject obj :mongoDataStore ){
							System.out.println(obj.toString());
						}
					}
					if(radioBtnlistNeu.get(radioActioCount).isSelected()){
						System.out.println("Radio btn Neu "+ radioActioCount + " Selected");
						mongoDataStore.get(radioActioCount).put("userClasification", "neutral");
						for(JSONObject obj :mongoDataStore ){
							System.out.println(obj.toString());
						}
					}
					if(!radioBtnlistPos.get(radioActioCount).isSelected()&&!radioBtnlistNeg.get(radioActioCount).isSelected()&&!radioBtnlistNeu.get(radioActioCount).isSelected()){
						JOptionPane.showMessageDialog(null,"Select re-classifaction before submitting ", "Error",JOptionPane.ERROR_MESSAGE);
					}else if(radioBtnlistPos.get(radioActioCount).isSelected()||radioBtnlistNeg.get(radioActioCount).isSelected()||radioBtnlistNeu.get(radioActioCount).isSelected()){
						JOptionPane.showMessageDialog(null, "Results Updated");
					}
					
						
				}
			});
			
			btnGroupList.get(i).add(radioBtnlistPos.get(i));
			btnGroupList.get(i).add(radioBtnlistNeg.get(i));
			btnGroupList.get(i).add(radioBtnlistNeu.get(i));
			radioBtnPanelList.get(i).add(radioBtnlistPos.get(i));
			radioBtnPanelList.get(i).add(radioBtnlistNeg.get(i));
			radioBtnPanelList.get(i).add(radioBtnlistNeu.get(i));
			radioBtnPanelList.get(i).add(submitBtnlist.get(i));
			
			resultsPanelList.add(new JPanel());
			panelList.get(i).setBorder(new LineBorder(Color.BLACK, 4, true));
			resultsPanelList.get(i).setBorder(new LineBorder(Color.BLACK, 2, true));
			resultsPanelList.get(i).setLayout(new FlowLayout());
			datumResultsPanelList.get(i).setBorder(new LineBorder(Color.BLACK, 2, true));
			datumResultsPanelList.get(i).setLayout(new FlowLayout());
			textAreaList.add(new JTextArea());
			lableList.add(new JLabel());

			panelList.get(i).setName("panel_" + Integer.toString(i));
			resultsPanelList.get(i).setName("impactPanel_" + Integer.toString(i));
			datumResultsPanelList.get(i).setName("datumResultsPanel_" + Integer.toString(i));
			textAreaList.get(i).setName("txtArea_" + Integer.toString(i));
			

			lableList.get(i).setName("Label_" + Integer.toString(i));
			lableList.get(i).setFont(new Font("Gotham Medium", Font.PLAIN, 15));

			textAreaList.get(i).setFont(new Font("Gotham Book", Font.PLAIN, 20));
			textAreaList.get(i).setWrapStyleWord(true);
			lableList.get(i).setText("<html><center>DatumBox Result = "+tweet.get("datumResults").toString()+ "<br>"+ "RapidMiner Result = "+tweet.get("rapidMinerResults").toString()+ "</center></html>");

			resultsPanelList.get(i).setBackground(Color.LIGHT_GRAY);
			textAreaList.get(i).setText(tweet.get("unProcessedTweet").toString());
			textAreaList.get(i).setLineWrap(true);
			textAreaList.get(i).setEnabled(true);
			textAreaList.get(i).setEditable(false);

			resultsPanelList.get(i).add(lableList.get(i));

			GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
			gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
					Alignment.LEADING).addGroup(
					gl_panel_3
							.createSequentialGroup()
							.addContainerGap()
							.addComponent(textAreaList.get(i),GroupLayout.PREFERRED_SIZE, 350,Short.MAX_VALUE).addGap(30)
							.addComponent(radioBtnPanelList.get(i),GroupLayout.PREFERRED_SIZE, 50,Short.MAX_VALUE).addContainerGap()
							.addComponent(resultsPanelList.get(i),GroupLayout.PREFERRED_SIZE, 50,Short.MAX_VALUE)));
							
			panelList.get(i).revalidate();
			panelList.get(i).repaint();
			gl_panel_3.setVerticalGroup(gl_panel_3
					.createParallelGroup(Alignment.LEADING)
					.addComponent(textAreaList.get(i),GroupLayout.PREFERRED_SIZE, 150, Short.MAX_VALUE)
					.addComponent(radioBtnPanelList.get(i),GroupLayout.PREFERRED_SIZE, 30,Short.MAX_VALUE)
					.addComponent(resultsPanelList.get(i),GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE));
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
