package hospitalmanagement;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.SwingConstants;

/**
* Class that displays the options the nurse can choose from upon logging in to their account
* Includes options like: uploading test results and viewing schedules
* @author sydneykwok, shavonnetran, epaslawski
*/
public class NursePerspective extends JFrame {

	private JPanel contentPane;
	
	/**
	* Create the frame for the panel with the nurse options upon logging in
	* Nurses will be directed to this frame and can do various tasks by clicking the appropriate buttons
	* @param email The email of the nurse. Used to uniquely identify the user so we can easily access their info.
	*/
	public NursePerspective(String email) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 626, 436);
		setLocationRelativeTo(null);
		
		// create pane for the frame
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// add the main title label to the panel
		// Get the name of the nurse
		// create reader
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read accounts array from json
			JsonArray accounts = (JsonArray) parser.get("accounts");
			
			// extract the object representation of the nurses section of the accounts array
			// then get the array representation of that object
			JsonObject nurses = (JsonObject) accounts.get(2);
			JsonArray nurseArr = (JsonArray) nurses.get("nurse");
			
			// close reader
			reader.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}

		JsonObject nurse = Account.getAccountJSONObj("Nurse", email);
		final String name = (String) nurse.get("first_name") + " " + (String) nurse.get("last_name");
		
		
		//Display welcome message for nurse 
		JLabel menuLabel = new JLabel("Welcome, " + name + "!");
		menuLabel.setHorizontalAlignment(SwingConstants.CENTER);
		menuLabel.setBounds(206, 31, 171, 14);
		contentPane.add(menuLabel);
		
		//Adding button to return to login home page
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
		btnReturn.setBounds(206, 274, 171, 14);
		contentPane.add(btnReturn);
		
		//Adding button for uploading test results
		JButton btnNewButton = new JButton("Upload Test Results");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				UploadTestsView uploadTests = new UploadTestsView(email, "nurse");
				uploadTests.setVisible(true);
			}
		});
		btnNewButton.setBounds(206, 71, 171, 41);
		contentPane.add(btnNewButton);
		
		//Adding button for viewing schedule
		JButton btnNewButton_1 = new JButton("View Schedule");
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				ViewSchedulePerspective seeSched = new ViewSchedulePerspective(email, 2, getToday());
				seeSched.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(206, 195, 171, 41);
		contentPane.add(btnNewButton_1);
		
		//Adding button for setting schedule
		JButton scheduleButton = new JButton("Set Schedule");
		scheduleButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				SetAvailability availabilityPane = new SetAvailability(email, 2);
				availabilityPane.setVisible(true);
			}
		});
		scheduleButton.setBounds(206, 131, 171, 41);
		contentPane.add(scheduleButton);
	}
	
	/**
	 * @return the current date in the same format used in the JSON
	 * @author erinpaslawski
	 * 
	 * This method gets the current date using the System clock, and returns it
	 * as a string in the same format used in the JSONs
	 */
	private String getToday() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
		LocalDateTime now = LocalDateTime.now();
		String today;
		return(today = dtf.format(now)); 
	}
}
