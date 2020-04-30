package hospitalmanagement;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
/**
 * This class allows nurses to add test info for patients. 
 *
 * @author erinpaslawski
 *
 */
public class AddTestInfoPerspective extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;


	/**
	 * Create the frame for the nurses to add test info for individual patients
	 * @author erinpaslawski
	 * @param email THe email of the nurse
	 * @param selectedPatient the patient for the test
	 */
	public AddTestInfoPerspective(String email, String selectedPatient, String accountType){
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
		// Get the name of the patient
		String name = "";
		// create reader
		BufferedReader reader;
		try {
			reader = new BufferedReader(
					new InputStreamReader(new FileInputStream("src/hospitalmanagement/accounts2.json")));
			// create parser
			JsonObject parser = (JsonObject) Jsoner.deserialize(reader);
			
			// read accounts array from json
			JsonArray accounts = (JsonArray) parser.get("accounts");
			
			// extract the object representation of the patients section of the accounts array
			// then get the array representation of that object
			JsonObject patients = (JsonObject) accounts.get(0);
			JsonArray patientArr = (JsonArray) patients.get("patient");
			
			// close reader
			reader.close();
			
		} catch (Exception e1) {
			// TODO Auto-generated catch block
		}

		JsonObject patient = Account.getAccountJSONObj("Patient", selectedPatient);
		name = (String) patient.get("first_name") + " " + (String) patient.get("last_name");
		
		JLabel MainLabel = new JLabel("Add Test Results for: " + name); 
		MainLabel.setFont(new Font("Lucida Grande", Font.BOLD, 14));
		MainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		MainLabel.setBounds(43, 73, 510, 22);
		contentPane.add(MainLabel);
		
		// Add a text field for typing the test info
		textField_1 = new JTextField();
		textField_1.setToolTipText("Write Notes Here...");
		textField_1.setHorizontalAlignment(SwingConstants.LEFT);
		textField_1.setBounds(43, 169, 510, 165);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		// Add a combo box for the test type.
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] {"X-Ray", "Blood Test", "MRI", "Ultrasound", "CAT Scan", "Other"}));
		comboBox.setBounds(276, 117, 165, 27);
		contentPane.add(comboBox);
		
		// Label for combo box
		JLabel lblNewLabel = new JLabel("Test Type:");
		lblNewLabel.setBounds(159, 121, 99, 16);
		contentPane.add(lblNewLabel);
		
		// Submit button, which is an action handler that will write to the JSON with the written info
		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				String textToSubmit = textField_1.getText();
				String typeOfTest = comboBox.getSelectedItem().toString();
				boolean successful = WriteToJSON.writeTestInfo(accountType, email, selectedPatient, typeOfTest, textToSubmit);
				if (successful == true) {
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "Successful.");
				}
				else {
					Login lframe = new Login();
					JOptionPane.showMessageDialog(lframe, "Not successful.");
				}
				//Return back to the main page
				if (accountType.equals("nurse")) {
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
		btnNewButton.setBounds(227, 346, 136, 29);
		contentPane.add(btnNewButton);
		
		//Adding button to return to nurse perspective home page
		JButton btnReturn = new JButton("Return");
		//Add event handler for return button
		btnReturn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				//Return back to the main page after clicking return button
				NursePerspective previousPane = new NursePerspective(email);
				previousPane.setVisible(true);
				dispose();
			}
		});
		btnReturn.setBounds(227, 383, 136, 14);
		contentPane.add(btnReturn);
	}
}
