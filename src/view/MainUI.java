package view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.LineBorder;

import strategy.RapidMinerSentimentAnalysis;
import model.ConcreteSubject;
import model.Subject;
import model.TwitterDataSubject;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;


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
	private ControlPanelView ctrlView;
	private TwitterDataSubject subject;
	private JPanel Btn_Panel = new JPanel();

	public MainUI(TwitterDataSubject subject) throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		this.setTitle("Twitter Data Sentiment Analysis");
		IconSetUP();
		// Setup menu
		//menuSetUp();
		this.subject = subject;
		rapidView = new RapidMinerView(subject);
		ctrlView = new ControlPanelView(subject);


		getContentPane().add(ctrlView, BorderLayout.WEST);
		
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
		
		this.setSize(1000, 600);
		this.setLocationRelativeTo(null);
		this.setVisible(true);

	}// end CTor

	
	public void IconSetUP(){
				 //Start the application
		Image img;
			try {
				img = ImageIO.read(new File("D:\\Workspace\\TattoDemo\\pics\\Twitter_logo_blue.png"));
				this.setIconImage(img);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
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
