package hospitalmanagement;

import java.time.*;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.github.cliftonlabs.json_simple.JsonObject;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
* Creates the pane for adding a new medication for a patient.
*
*/
public class DoctorPatientsMedicationsAdd extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtMedicationName;
	private JTextField txtAmount;
	private JButton btnAdd;
	private JButton btnCancel;

	/**
	 * Creates the pane for adding a new medication for a patient
	 * @param email The email of the doctor
	 * @param patientIndex The index of the patient according to their position in the accounts2.json
	 * @author ggdizon
	 */
	public DoctorPatientsMedicationsAdd(String email, String patientIndex) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(135, 206, 235));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// Title Label
		JLabel lblPleaseFillThe = new JLabel("Please Fill The Following Information");
		lblPleaseFillThe.setBounds(119, 24, 211, 16);
		contentPane.add(lblPleaseFillThe);
		
		// Label asking for the medication name
		JLabel lblMedicationName = new JLabel("Medication Name:");
		lblMedicationName.setBounds(12, 68, 103, 16);
		contentPane.add(lblMedicationName);
		
		// Textfield where user can input the medication name
		txtMedicationName = new JTextField();
		txtMedicationName.setBounds(119, 56, 301, 40);
		txtMedicationName.setText(null);
		txtMedicationName.setHorizontalAlignment(SwingConstants.LEFT);
		contentPane.add(txtMedicationName);
		txtMedicationName.setColumns(10);
		
		// Label asking for the amount
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(39, 141, 49, 16);
		contentPane.add(lblAmount);
		
		// Textfield where user can input the amount
		txtAmount = new JTextField();
		txtAmount.setBounds(119, 129, 301, 40);
		txtAmount.setText(null);
		contentPane.add(txtAmount);
		txtAmount.setColumns(10);
		
		// Button that will add medication to patient's current medications list
		btnAdd = new JButton("Add");
		btnAdd.setBounds(119, 202, 71, 25);
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// get the values in the text fields
				String medName = txtMedicationName.getText();
				String amount = txtAmount.getText();
				
				// If one of the fields weren't filled, then show a message saying so and don't change anything
				if (medName.equals(null) || amount.equals(null)) {
					JOptionPane.showMessageDialog(contentPane, "You have not filled out all of the informations.");
				} else {
					String docName = getDoctorName(email);
					String date = getDate();
					WriteToJSON.addNewMedication(patientIndex, medName, docName, amount, date);
					DoctorPatientsMedicationsView medicationsPane = new DoctorPatientsMedicationsView(email, patientIndex);
					medicationsPane.setVisible(true);
					dispose();
				}
			}
		});
		contentPane.add(btnAdd);
		
		// Button to cancel the action of adding a medication
		btnCancel = new JButton("Cancel");
		btnCancel.setBounds(335, 202, 85, 25);
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				DoctorPatientsMedicationsView medicationsPane = new DoctorPatientsMedicationsView(email, patientIndex);
				medicationsPane.setVisible(true);
				dispose();
			}
		});
		contentPane.add(btnCancel);
		
	}

	/**
	 * Method for getting doctor's name
	 * @param email The email of the doctor
	 * @return String of doctor's last name
	 * @author ggdizon
	 */
	private String getDoctorName(String email) {
		JsonObject doctor = Account.getAccountJSONObj("Doctor", email);
		String last_name = "Dr." + (String) doctor.get("last_name");
		return last_name;
	}
	
	/**
	 * Method for getting the current date
	 * @return String of date in the format DD/MM/YYYY
	 * @author ggdizon
	 */
	private String getDate() {
		// get the local date right now
		LocalDate date = LocalDate.now();
		
		int day = date.getDayOfMonth();		// day expressed as integer
		int month = date.getMonthValue();	// months expressed as integer
		int year = date.getYear();			// year expressed as integer
		
		String stringDay;					// day expressed as String
		if (day < 10) {
			stringDay = "0" + Integer.toString(day);	// prepend 0 to day if single digit
		} else {
			stringDay = Integer.toString(day);
		}
		
		String stringMonth;					// months expressed as String
		if (month < 10) {
			stringMonth = "0" + Integer.toString(month);	// prepend 0 to month if single digit
		} else {
			stringMonth = Integer.toString(month);
		}
		
		String stringYear = Integer.toString(year);	//year expressed as String
		
		String formattedDate = stringDay + "/" + stringMonth + "/" + stringYear;
		return formattedDate;
	}
	
}
