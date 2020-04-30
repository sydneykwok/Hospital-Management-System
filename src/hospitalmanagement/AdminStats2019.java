package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that displays the panel for the admin to view the number of patients who have visited in 2019
 * Our appointment booking system stores dates in 2020 only, so assume that 11,000 patients have visited the hospital in 2019
 * @author shavonnetran
 */
public class AdminStats2019 extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame for viewing the number of patients who visited the hospital in the year 2019
	 * Displays the number of patients, assumed to be 11,000
	 * @param email The email of the admin. Used to uniquely identify the user so we can easily access their info
	 */
	public AdminStats2019(String email) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label to indicate the year of stats 
		JLabel lbl2019 = new JLabel("Number of Patients That Visited in 2019:");
		lbl2019.setBounds(57, 70, 554, 14);
		contentPane.add(lbl2019);
		
		//Label for number of patients visited
		JLabel numPatientlbl = new JLabel("11,000");
		numPatientlbl.setBounds(280, 125, 219, 41);
		contentPane.add(numPatientlbl);
		
		//Add button to return to previous page
		JButton btnNewButton = new JButton("Return");
		//Add event handler for return button
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Close current window and return to the previous window
				dispose();
			}
		});
		btnNewButton.setBounds(226, 266, 171, 41);
		contentPane.add(btnNewButton);
	}
}