package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Class that displays the welcome page for new users who just registered for an account.
* @author shavonnetran, sydneykwok
*/
public class NewAccountWelcome extends JFrame {

	/**
	* Create the frame for the new account welcome page
	* It shows a welcome message and return button
	*/
	public NewAccountWelcome(JPanel contentPane, String email) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 900, 586);
		
		// create pane for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Add welcome message
		JLabel lblNewLabel = new JLabel("Welcome to your new account!");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 35));
		lblNewLabel.setBounds(165, 87, 542, 238);
		contentPane.add(lblNewLabel);
		
		//Adding button for returning to login home page
		JButton btnReturn = new JButton("Return");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				dispose();
			}
		});
		btnReturn.setBounds(385, 433, 97, 25);
		contentPane.add(btnReturn);
		
		// add label to inform the user that their username is derived from their email input
		JLabel lblPleaseRememberYour = new JLabel("Please remember your email and password!");
		lblPleaseRememberYour.setBounds(305, 279, 260, 16);
		contentPane.add(lblPleaseRememberYour);
	}
}
