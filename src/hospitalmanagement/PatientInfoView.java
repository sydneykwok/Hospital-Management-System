package hospitalmanagement;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonObject;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

/*
* Class that displays panel for the personal information of the patient.
*/
public class PatientInfoView extends JFrame {

	private JPanel infoView;

	/**
	 * Create the frame.
	 * @param email The email of the patient. Used to uniquely identify the user so we can easily access their info.
	 */
	public PatientInfoView(String email) {
		
		// set frame properties
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1014, 581);
		
		// create panel for the frame
		infoView = new JPanel();
		infoView.setBackground(new Color(135, 206, 235));
		infoView.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(infoView);
		infoView.setLayout(null);
		
		// add label for the first name
		JLabel lblFirstName = new JLabel("First Name:");
		lblFirstName.setBounds(166, 118, 115, 33);
		infoView.add(lblFirstName);
		
		// add label for the last name
		JLabel lblLastName = new JLabel("Last Name:");
		lblLastName.setBounds(166, 172, 115, 33);
		infoView.add(lblLastName);
		
		// add label for the age
		JLabel lblAge = new JLabel("Age:");
		lblAge.setBounds(166, 231, 115, 33);
		infoView.add(lblAge);
		
		// add label for the email
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setBounds(166, 285, 115, 33);
		infoView.add(lblEmail);
		
		// add label for the gender
		JLabel lblGender = new JLabel("Gender:");
		lblGender.setBounds(166, 339, 115, 33);
		infoView.add(lblGender);
		
		// add label for the password
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(166, 397, 115, 33);
		infoView.add(lblPassword);
		
		// get the JsonObject for the patient with the passed email
		JsonObject user = Account.getAccountJSONObj("Patient", email);
		
		// add label to display first name
		JLabel lblStoredFirstName = new JLabel((String) user.get("first_name"));
		lblStoredFirstName.setBounds(401, 118, 115, 33);
		infoView.add(lblStoredFirstName);
		
		// add label to display last name
		JLabel lblStoredLastName = new JLabel((String) user.get("last_name"));
		lblStoredLastName.setBounds(401, 172, 115, 33);
		infoView.add(lblStoredLastName);
		
		// add label to display age
		JLabel lblStoredAge = new JLabel((String) user.get("age"));
		lblStoredAge.setBounds(401, 231, 115, 33);
		infoView.add(lblStoredAge);
		
		// add label to display email
		JLabel lblStoredEmail = new JLabel((String) user.get("email"));
		lblStoredEmail.setBounds(401, 285, 195, 33);
		infoView.add(lblStoredEmail);
		
		// add label to display gender
		JLabel lblStoredGender = new JLabel((String) user.get("gender"));
		lblStoredGender.setBounds(401, 339, 115, 33);
		infoView.add(lblStoredGender);
		
		// add label to display password
		JLabel lblStoredPass = new JLabel((String) user.get("password"));
		lblStoredPass.setBounds(401, 397, 195, 33);
		infoView.add(lblStoredPass);
		
		// add button for the user to edit their last name
		JButton btnLastNameEdit = new JButton("Edit");
		btnLastNameEdit.addMouseListener(new MouseAdapter() {
			// if the user clicks the button
			public void mouseReleased(MouseEvent e) {
				// prompt the user to enter a new value
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				// display the new entered value
				if(newVal != null) {
					lblStoredLastName.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnLastNameEdit.setBounds(672, 168, 171, 41);
		infoView.add(btnLastNameEdit);
		
		// add button for the user to edit their first name
		JButton btnFirstNameEdit = new JButton("Edit");
		btnFirstNameEdit.addMouseListener(new MouseAdapter() {
			// if the user clicks the button
			public void mouseReleased(MouseEvent arg0) {
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				// display the new entered value
				if(newVal != null) {
					lblStoredFirstName.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnFirstNameEdit.setBounds(672, 114, 171, 41);
		infoView.add(btnFirstNameEdit);
		
		// add button for the user to edit their age
		JButton btnAgeEdit = new JButton("Edit");
		btnAgeEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				if(newVal != null) {
					lblStoredAge.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnAgeEdit.setBounds(672, 227, 171, 41);
		infoView.add(btnAgeEdit);
		
		// add button for the user to edit their email
		JButton btnEmailEdit = new JButton("Edit");
		btnEmailEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				if(newVal != null) {
					lblStoredEmail.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnEmailEdit.setBounds(672, 281, 171, 41);
		infoView.add(btnEmailEdit);
		
		// add button for the user to edit their gender
		JButton btnGenderEdit = new JButton("Edit");
		btnGenderEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				if(newVal != null) {
					lblStoredGender.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnGenderEdit.setBounds(672, 335, 171, 41);
		infoView.add(btnGenderEdit);
		
		// add button for the user to edit their password
		JButton btnPasswordEdit = new JButton("Edit");
		btnPasswordEdit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				Login lframe = new Login();
				String newVal = JOptionPane.showInputDialog(lframe, "Enter new value: ");
				if(newVal != null) {
					lblStoredPass.setText(newVal);
					infoView.repaint();
				}
			}
		});
		btnPasswordEdit.setBounds(672, 389, 171, 41);
		infoView.add(btnPasswordEdit);
		
		// add button for the user to save their changes
		JButton btnSaveChanges = new JButton("Save Changes");
		btnSaveChanges.addMouseListener(new MouseAdapter() {
			// if the user clicks this button
			public void mouseReleased(MouseEvent e) {
				// notify the user that their data will be updated in the database
				Login lframe = new Login();
				JOptionPane.showMessageDialog(lframe, "Changes saved! Updates will be made to your file.");
				
				// update the user in the JSON with this data
				WriteToJSON.updatePatientInfo(lblStoredFirstName.getText(), lblStoredLastName.getText(),
						lblStoredAge.getText(), lblStoredEmail.getText(), lblStoredGender.getText(), lblStoredPass.getText());
			}
		});
		btnSaveChanges.setBounds(414, 464, 131, 25);
		infoView.add(btnSaveChanges);
		
		// add title label to the panel
		JLabel lblYourInformation = new JLabel("Your Information");
		lblYourInformation.setBounds(401, 57, 115, 16);
		infoView.add(lblYourInformation);
	}
}
