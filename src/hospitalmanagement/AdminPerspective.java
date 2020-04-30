package hospitalmanagement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;

/**
* Class that displays the options the administrator can choose from upon logging in to their account.
* Includes options like: assigning doctors to departments, viewing departments/statistics
* @author sydneykwok, shavonnetran
*/
public class AdminPerspective extends JFrame {

	private JPanel contentPane;
	
	/**
	 * Create the frame for the panel with the administrator options upon logging in.
	 * Administrators will be directed to this frame and can do various tasks by clicking the appropriate buttons.
	 * @param email The email of the admin. Used to uniquely identify the user so we can easily access their info.
	 */
	public AdminPerspective(String email) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		setLocationRelativeTo(null);
		
		// create the panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// display welcome message for administrator
		JLabel menuLabel = new JLabel("Welcome, Admin!");
		menuLabel.setBounds(237, 70, 119, 14);
		contentPane.add(menuLabel);
		
		// add button for returning to login home page
		JButton btnReturn = new JButton("Return");
		// add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Return back to the login page after clicking return button
				Login loginPane = new Login();
				loginPane.setVisible(true);
				dispose();
			}
		});
		btnReturn.setBounds(225, 294, 119, 14);
		contentPane.add(btnReturn);
		
		//Adding button for assigning doctor to department
		JButton assignDocBtn = new JButton("Assign Doctor to Department");
		//Add event handler for assigning doctor to department button
		assignDocBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Switch to panel that displays the frame for assigning doctors to department
				AdminAssignDocDepView assignDocDep = new AdminAssignDocDepView(email);
				assignDocDep.setVisible(true);
			}
		});
		assignDocBtn.setBounds(178, 199, 219, 41);
		contentPane.add(assignDocBtn);
		
		//Adding button for viewing statistics 
		JButton viewStatBtn = new JButton("View Statistics");
		//Add event handler for view stats button
		viewStatBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Switch to panel that displays the frame for viewing statistics
				AdminViewStats viewStatPanel = new AdminViewStats(email);
				viewStatPanel.setVisible(true);
			}
		});
		viewStatBtn.setBounds(178, 125, 219, 41);
		contentPane.add(viewStatBtn);
	}
}
