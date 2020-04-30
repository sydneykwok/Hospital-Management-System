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
 * Class that displays the panel for the admin to view the number of patients who have visited in 2018
 * Our appointment booking system stores dates in 2020 only, so assume that 9,500 patients have visited the hospital in 2018
 * @author shavonnetran
 */
public class AdminStats2018 extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame for viewing the number of patients who visited the hospital in the year 2018
	 * Displays the number of patients, assumed to be 9,500
	 * @param email The email of the admin. Used to uniquely identify the user so we can easily access their info
	 */
	public AdminStats2018(String email) {
		setBackground(new Color(135, 206, 235));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label to indicate the year of stats 
		JLabel lblNewLabel = new JLabel("Number of Patients That Visited in 2018:");
		lblNewLabel.setBounds(96, 70, 513, 14);
		contentPane.add(lblNewLabel);
		
		//Label for number of patients visited
		JLabel numPatientlbl = new JLabel("9,500");
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
		btnNewButton.setBounds(220, 279, 171, 41);
		contentPane.add(btnNewButton);
	}
}