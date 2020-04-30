package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Class that displays the panel for the admin to view statistics 
 * Includes statistics about how many patients the hospital has had in prior years
 * Users can select a year to view statistics for
 * Assume the hospital has been in business since the year 2018 so it stores stats from 2018-present(2020)
 * @author shavonnetran
 */
public class AdminViewStats extends JFrame {

	private JPanel contentPane;

	/**
	 * Create the frame for viewing statistics
	 * @param email The email of the admin. Used to uniquely identify the user so we can easily access their info
	 */
	public AdminViewStats(String email) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
			
		JLabel selectlbl = new JLabel("Select a Year:");
		selectlbl.setBounds(168, 14, 171, 33);
		contentPane.add(selectlbl);
		
		//Create a dropdown for admins to select the year they want to view statistics from
		JComboBox<String> yearComboBox = new JComboBox<String>();
		
		//Add the years to combo box 
		yearComboBox.addItem("2018");
		yearComboBox.addItem("2019");
		yearComboBox.addItem("2020");
		
		yearComboBox.setBounds(139, 75, 143, 44);
		contentPane.add(yearComboBox);
		
		//Add button to go to next page
		JButton btnNewButton = new JButton("Next");
		//Add event handler to next button
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Get selected year from combo box
				String selectedYear = yearComboBox.getSelectedItem().toString();
				
				//If admin selects year 2018, switch to panel for 2018 stats
				if (selectedYear.equals("2018")) {
					AdminStats2018 stat2018 = new AdminStats2018(email);
					stat2018.setVisible(true);
				}
				//If admin selects year 2019, switch to panel for 2019 stats
				else if (selectedYear.equals("2019")) {
					AdminStats2019 stat2019 = new AdminStats2019(email);
					stat2019.setVisible(true);
				}
				//If admin selects year 2020, switch to panel for 2020 stats
				else {
					AdminStats2020 stat2020 = new AdminStats2020(email);
					stat2020.setVisible(true);
				}
			}
		});
		btnNewButton.setBounds(123, 153, 171, 41);
		contentPane.add(btnNewButton);
	}
}
