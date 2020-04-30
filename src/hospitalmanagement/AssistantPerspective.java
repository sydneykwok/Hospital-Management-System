package hospitalmanagement;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
* Class that displays the options the assistant can choose from upon logging in to their account.
* Includes approving appointments
* @author sydneykwok, shavonnetran
*/
public class AssistantPerspective extends JFrame {
	
	private JPanel contentPane;
	
	/**
	* Create the frame for the panel with the assistant options upon logging in.
	* Assistants will be directed to this frame and can do various tasks by clicking the appropriate buttons.
	* @param email The email of the assistant. Used to uniquely identify the user so we can easily access their info.
	*/
	public AssistantPerspective(String email) {
		
		//Set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 678, 438);
		setLocationRelativeTo(null);
		
		//Create the panel for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Display welcome message for assistant
		JLabel menuLabel = new JLabel("Welcome, Assistant!");
		menuLabel.setBounds(279, 28, 251, 29);
		contentPane.add(menuLabel);
		
		//Adding button for returning to login home page
		JButton btnReturn = new JButton("Return");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Return back to the login page after clicking return button
				Login loginPane = new Login();
				loginPane.setVisible(true);
				dispose();
			}
		});
		btnReturn.setBounds(284, 294, 119, 28);
		contentPane.add(btnReturn);
		
		//Adding button for approving appointments
		JButton approveBtn = new JButton("Approve Appointments");
		//Add event handler for approve appointment button
		approveBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Switch to panel that displays the page for approving appointments
				AssistantApproveAppointment approveAppointPanel = new AssistantApproveAppointment(email); 
				approveAppointPanel.setVisible(true);
			}
		});
		approveBtn.setBounds(242, 122, 215, 41);
		contentPane.add(approveBtn);
	}
}
