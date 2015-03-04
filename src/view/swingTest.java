package view;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;

public class swingTest extends  JFrame {

	public swingTest() {
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.CENTER);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel.add(lblNewLabel);
		// TODO Auto-generated constructor stub
	}

}
