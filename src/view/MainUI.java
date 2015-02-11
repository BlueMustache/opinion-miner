package view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.border.LineBorder;

import model.TwitterDataSubject;

import java.awt.Color;
import java.awt.FlowLayout;


/**
 * 
 * @author Michael Hagen
 */
public class MainUI extends JFrame {

	protected JPanel contentLayoutPanel = null;
//	private JMenuBar menuBar;
//	private JMenu menu;
//	private JMenuItem menuItem;
	private RapidMinerView rapidView;
	private TwitterDataSubject subject;

	public MainUI(TwitterDataSubject subject) {
		this.setTitle("Twitter Data Sentiment Analysis");
		// Setup menu
		//menuSetUp();
		this.subject = subject;
		rapidView = new RapidMinerView(subject);
		
		JPanel Btn_Panel = new JPanel();
		Btn_Panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		getContentPane().add(Btn_Panel, BorderLayout.WEST);
		Btn_Panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JSplitPane Btn_splitPane = new JSplitPane();
		Btn_splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		Btn_Panel.add(Btn_splitPane);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);
		
		tabbedPane.addTab("Rapid Miner Analysis", null, rapidView,null);

		// Add listeners
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

	}// end CTor

//	public void menuSetUp() {
//		menuBar = new JMenuBar();
//		menu = new JMenu("File");
//		menu.setMnemonic('F');
//		
//		menuItem = new JMenuItem("Exit");
//		menuItem.setMnemonic('x');
//		menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4,
//				KeyEvent.ALT_MASK));
//		menuItem.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent event) {// GET THIS IN THE
//															// CONTROLLER
//				System.exit(0);
//			}
//		});
//		menu.add(menuItem);
//		menuBar.add(menu);
//		setJMenuBar(menuBar);
//		getContentPane().setLayout(new BorderLayout(0, 0));
//	}
}
