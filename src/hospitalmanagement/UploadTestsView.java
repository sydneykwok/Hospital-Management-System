package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JButton;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

/**
* Class to upload test results.
*
*/
public class UploadTestsView extends JFrame {

	private JPanel contentPane;


	/**
	 * Create the frame .
	 */
	public UploadTestsView(String email, String accountType) {
		// set properties of the frame
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 590, 444);
		// create a new panel
		contentPane = new JPanel();
		contentPane.setBackground(new Color(135, 206, 235));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// add the main title label to the panel
		JLabel MainLabel = new JLabel("Update Test Result");
		MainLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MainLabel.setBounds(189, 73, 203, 22);
		contentPane.add(MainLabel);
		
		JLabel patLabel = new JLabel("Select a patient:");
		patLabel.setHorizontalAlignment(SwingConstants.CENTER);
		patLabel.setBounds(219, 123, 141, 22);
		contentPane.add(patLabel);
		
		
		JComboBox<String> patientDropdown = new JComboBox<String>();
		ArrayList<String> patientEmail= new ArrayList<String>();
		try {
			
		    // create reader
			BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
		    // create parser
		    JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
		    // read accounts array from json
		    JsonArray accounts = (JsonArray) parser.get("accounts");
		    // extract the object representation of patient from the accounts array
		    JsonObject patients = (JsonObject) accounts.get(0);
		    // then get the array representation of patient 
	    	JsonArray patientArr = (JsonArray) patients.get("patient");
	    	
	    	// then create an iterator to iterate through that array
	    	Iterator i = patientArr.iterator();
		    // iterate through all patients in the patient array
		    while (i.hasNext()) {
		        JsonObject patient = (JsonObject) i.next();
		        patientDropdown.addItem((String) patient.get("first_name") + " " + (String) patient.get("last_name"));
		        patientEmail.add((String) patient.get("email"));
		    }
		    
		    //close reader
		    reader.close();

		} catch (Exception ex) {
		    ex.printStackTrace();
		}
		
		patientDropdown.setBounds(219, 177, 141, 22);
		contentPane.add(patientDropdown);
		
		JButton btnNewButton = new JButton("Select");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// Get the email of the patient selected
				String selectedPatient = patientEmail.get(patientDropdown.getSelectedIndex());
				//Bring up a text box to upload test results?
				AddTestInfoPerspective nextPane = new AddTestInfoPerspective(email, selectedPatient, accountType);
				nextPane.setVisible(true);
				dispose();
				
			}
		});
		btnNewButton.setBounds(248, 235, 89, 29);
		contentPane.add(btnNewButton);
		
		//Adding button to return to login home page
		JButton btnReturn = new JButton("Return");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if (accountType.equals("nurse")) {
				//Return back to the login page after clicking return button
				NursePerspective previousPane = new NursePerspective(email);
				previousPane.setVisible(true);
				dispose();
				}
				else if (accountType.contentEquals("doctor")) {
					//Return back to the login page after clicking return button
					DoctorPerspective previousDocPane = new DoctorPerspective(email);
					previousDocPane.setVisible(true);
					dispose();
				}
			}
		});
		btnReturn.setBounds(224, 337, 136, 14);
		contentPane.add(btnReturn);
	}
}
