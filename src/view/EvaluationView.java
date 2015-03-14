//package view;
//
//import java.awt.Color;
//import java.awt.Dimension;
//import java.awt.FlowLayout;
//import java.awt.Font;
//import java.awt.GridBagConstraints;
//import java.awt.GridBagLayout;
//import java.awt.Insets;
//import java.util.ArrayList;
//
//import javax.swing.JScrollPane;
//
//import model.Subject;
//import model.TwitterDataSubject;
//import controller.Controller.CommandListner;
//
//import javax.swing.GroupLayout;
//import javax.swing.JLabel;
//import javax.swing.JPanel;
//import javax.swing.JTable;
//import javax.swing.JTextArea;
//import javax.swing.ScrollPaneConstants;
//import javax.swing.ScrollPaneLayout;
//import javax.swing.GroupLayout.Alignment;
//import javax.swing.border.LineBorder;
//
//import org.json.simple.JSONObject;
//
//public class EvaluationView extends JScrollPane implements Observer {
//
//	private TwitterDataSubject subject;
//	private JPanel mainPanel;
//	private ArrayList<JPanel> panelList;
//	private ArrayList<JPanel> impactPanelList;
//	private ArrayList<JTextArea> textAreaList;
//	private ArrayList<JLabel> lableList;
//	private Subject subjectRef;
//
//	public EvaluationView(TwitterDataSubject subjectReference, String viewRef) {
//		// TODO Auto-generated constructor stub
//		setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
//		this.subject = subjectReference;
//		subjectReference.registerObserver(this);
//	}
//
//	@Override
//	public void update(Subject subject) {
//		// TODO Auto-generated method stub
//		super.revalidate();
//		this.subject = (TwitterDataSubject) subject;
//		// ArrayList<String> tweetList = this.subject.getTweets();
//
//		ArrayList<JSONObject> mongoDataStore = ((TwitterDataSubject) this.subject)
//				.getMongoDataStore();
//
//		Dimension d = new Dimension(15, 15);
//		this.setPreferredSize(d);
//		this.mainPanel = new JPanel();
//		this.setLayout(new ScrollPaneLayout());
//		panelList = new ArrayList<JPanel>();
//		impactPanelList = new ArrayList<JPanel>();
//		textAreaList = new ArrayList<JTextArea>();
//		lableList = new ArrayList<JLabel>();
//
//		GridBagLayout gbl_panel = new GridBagLayout();
//		gbl_panel.columnWidths = new int[] { 0, 0 };
//		gbl_panel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
//		gbl_panel.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
//		gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
//				1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE };
//		mainPanel.setLayout(gbl_panel);
//
//		GridBagConstraints gbc_panel_3 = new GridBagConstraints();
//		gbc_panel_3.insets = new Insets(0, 0, 5, 0);
//		gbc_panel_3.fill = GridBagConstraints.BOTH;
//		gbc_panel_3.gridx = 0;
//		this.setBorder(new LineBorder(Color.BLACK));
//		int i = 0;
//
//		for (JSONObject tweet : mongoDataStore) {
//			panelList.add(new JPanel());
//
//			impactPanelList.add(new JPanel());
//			panelList.get(i).setBorder(
//					new LineBorder(Color.LIGHT_GRAY, 2, true));
//			impactPanelList.get(i).setBorder(
//					new LineBorder(Color.LIGHT_GRAY, 2, true));
//			impactPanelList.get(i).setLayout(new FlowLayout());
//			textAreaList.add(new JTextArea());
//			lableList.add(new JLabel());
//
//			panelList.get(i).setName("panel_" + Integer.toString(i));
//			impactPanelList.get(i)
//					.setName("impactPanel_" + Integer.toString(i));
//			textAreaList.get(i).setName("txtArea_" + Integer.toString(i));
//
//			lableList.get(i).setName("Label_" + Integer.toString(i));
//			lableList.get(i).setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
//
//			textAreaList.get(i).setFont(
//					new Font("Comic Sans MS", Font.PLAIN, 14));
//			textAreaList.get(i).setWrapStyleWord(true);
//			lableList.get(i).setText(
//					"<html><center>RetweetCount ="
//							+ tweet.get("retweetCount").toString()
//							+ "<br>"
//							+ determioneTweetImpact(Integer.parseInt(tweet.get(
//									"retweetCount").toString()))
//							+ "</center></html>");
//
//			impactPanelList.get(i).setBackground(
//					determineTweetImpactColor(determioneTweetImpact(Integer
//							.parseInt(tweet.get("retweetCount").toString()))));
//			textAreaList.get(i).setText(
//					tweet.get("unProcessedTweet").toString());
//			textAreaList.get(i).setLineWrap(true);
//			textAreaList.get(i).setEnabled(true);
//			textAreaList.get(i).setEditable(false);
//
//			impactPanelList.get(i).add(lableList.get(i));
//
//			GroupLayout gl_panel_3 = new GroupLayout(panelList.get(i));
//			gl_panel_3.setHorizontalGroup(gl_panel_3.createParallelGroup(
//					Alignment.LEADING).addGroup(
//					gl_panel_3
//							.createSequentialGroup()
//							.addContainerGap()
//							.addComponent(textAreaList.get(i),
//									GroupLayout.PREFERRED_SIZE, 350,
//									Short.MAX_VALUE)
//							.addComponent(impactPanelList.get(i),
//									GroupLayout.PREFERRED_SIZE, 50,
//									Short.MAX_VALUE).addContainerGap()));
//			panelList.get(i).revalidate();
//			panelList.get(i).repaint();
//			gl_panel_3.setVerticalGroup(gl_panel_3
//					.createParallelGroup(Alignment.LEADING)
//					.addComponent(textAreaList.get(i),
//							GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)
//					.addComponent(impactPanelList.get(i),
//							GroupLayout.PREFERRED_SIZE, 30, Short.MAX_VALUE));
//			panelList.get(i).revalidate();
//			panelList.get(i).repaint();
//			panelList.get(i).setLayout(gl_panel_3);
//			gbc_panel_3.gridy = i;
//			mainPanel.add(panelList.get(i), gbc_panel_3);
//			mainPanel.revalidate();
//			mainPanel.repaint();
//			this.setViewportView(mainPanel);
//			this.revalidate();
//			this.repaint();
//			i++;
//		}
//
//	}
//
//	@Override
//	public void addActionListener(CommandListner commandListner) {
//		// TODO Auto-generated method stub
//
//	}
//
//}
