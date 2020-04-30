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
 * Class that displays the next panel for the admin to view the number of patients who have visited in the year 2020
 * It will read from the appointments JSON file and count how many appointments were made 
 * @author shavonnetran
 */
public class AdminStats2020 extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame for viewing the number of patients who visited the hospital in the year 2020
	 * @param email The email of the admin. Used to uniquely identify the user so we can easily access their info
	 */
	public AdminStats2020(String email) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Label to indicate the year of stats 
		JLabel lbl2020 = new JLabel("Number of Appointments in 2020:");
		lbl2020.setBounds(63, 70, 494, 14);
		contentPane.add(lbl2020);
		
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
		btnNewButton.setBounds(233, 279, 171, 41);
		contentPane.add(btnNewButton);
		
	}
}
